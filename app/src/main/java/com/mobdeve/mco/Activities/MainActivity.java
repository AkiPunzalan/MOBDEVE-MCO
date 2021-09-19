package com.mobdeve.mco.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.mobdeve.mco.DatabaseHelper;
import com.mobdeve.mco.Fragments.*;
import com.mobdeve.mco.R;
import com.mobdeve.mco.ServiceHolder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //service variable used to access API
    public com.google.api.services.calendar.Calendar mService;

    public static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR, "https://www.googleapis.com/auth/calendar.events" };

    GoogleAccountCredential credential;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();


    private SharedPreferences sp;
    private DatabaseHelper db;
    private TextView tvToday;
    private BottomNavigationView nav;
    private FloatingActionButton fab_add;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCreds();
        db = new DatabaseHelper(this);
        resetStatus();
        createNotifChannel();

        toolbar = getSupportActionBar();
        toolbar.setTitle("Daily Habits");

        initComponents();
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming activity result.
     * @param data Intent (containing result data) returned by incoming activity result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == RESULT_OK) {
                    try {
                        refresh();
                    } catch (IOException ignored) {}
                } else {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                        try {
                            refresh();
                        } catch (IOException e) {}
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Account Unspecified", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    try {
                        refresh();
                    } catch (IOException ignored) { }
                } else {
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void resetStatus(){
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        //Reset Daily Statuses on the next day
        int lastStartupTime= sp.getInt("last_time_started", -1);
        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

        if (today != lastStartupTime) {
            Toast.makeText(this, "New Day!", Toast.LENGTH_SHORT).show();
            db.resetStatus();

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("last_time_started", today);
            editor.apply();
        }
    }

    private void initComponents(){
        tvToday = findViewById(R.id.tv_today);
        tvToday.setText(getToday());

        nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(navlistener);

        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

        //fragment on startup
        loadFragment(new DailyFragment());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frc_main, fragment);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;

            switch (item.getItemId()) {
                case R.id.nav_daily:
                    toolbar.setTitle("Daily Habits");
                    selectedFragment = new DailyFragment();
                    loadFragment(selectedFragment);
                    return true;

                case R.id.nav_todo:
                    toolbar.setTitle("To Do List");
                    selectedFragment = new TodoFragment();
                    loadFragment(selectedFragment);
                    return true;

                case R.id.nav_goals:
                    toolbar.setTitle("Habit Goals");
                    selectedFragment = new GoalFragment();
                    loadFragment(selectedFragment);
                    return true;

                case R.id.nav_metrics:
                    toolbar.setTitle("Habit Metrics");
                    selectedFragment = new MetricsFragment();
                    loadFragment(selectedFragment);
                    return true;

            }
            return false;
        }
    };

    private String getToday(){
        LocalDateTime today = LocalDateTime.now();
        return today.format(DateTimeFormatter.ofPattern("EE, dd MMM"));
    }

    public void createNotifChannel(){
        CharSequence name = "TaskNotifChannel";
        String desc = "Channel for Tasks";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel = new NotificationChannel("DoWhile", name, importance);
        channel.setDescription(desc);

        NotificationManager notifManager = getSystemService(NotificationManager.class);
        notifManager.createNotificationChannel(channel);
    }

    private void initCreds(){
        // Initialize credentials and service object.
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();

        ServiceHolder.mService = mService;
    }

    // GOOGLE LOGIN METHODS

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    public void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode,
                        MainActivity.this,
                        REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refresh() throws IOException {
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (!isDeviceOnline())
                Toast.makeText(this, "No network connection available", Toast.LENGTH_SHORT).show();

        }
    }

}