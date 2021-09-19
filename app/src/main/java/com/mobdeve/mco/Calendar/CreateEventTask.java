package com.mobdeve.mco.Calendar;


import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.mobdeve.mco.Activities.AddActivity;
import com.mobdeve.mco.Activities.MainActivity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class CreateEventTask extends AsyncTask<Void, Void, Void> {
    public static final int REQUEST_AUTHORIZATION = 1001;
    private AddActivity mActivity;
    private Calendar mService;
    private String name, desc;
    private LocalDateTime timeStart, timeEnd;

    /**
     * Constructor.
     * @param activity MainActivity that spawned this task.
     */
    public CreateEventTask(AddActivity activity, Calendar mService, String name, String desc, LocalDateTime time) {
        this.mActivity = activity;
        this.mService = mService;
        this.name = name;
        this.desc = desc;
        timeStart = time;
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            CreateEvent();
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
//            mActivity.showGooglePlayServicesAvailabilityErrorDialog(
//                    availabilityException.getConnectionStatusCode());
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(userRecoverableException.getIntent(), REQUEST_AUTHORIZATION);

        } catch (IOException ignored) { }
        return null;
    }

    private void CreateEvent() throws IOException {
        timeEnd = timeStart.plusMinutes(30);

        Event event = new Event()
                .setSummary(name)
                .setDescription(desc);

        DateTime startDateTime = new DateTime(formatDateTime(timeStart, "+08:00"));
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(formatDateTime(timeEnd, "+08:00"));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(10)
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = mService.events().insert(calendarId, event).execute();

        //return "added event " + event.getHtmlLink();
    }

    private String formatDateTime(LocalDateTime time, String timezone){
        //format : yyyy-MM-ddThh:mm:ss<timezone>
        return String.format("%02d-%02d-%02dT%02d:%02d:%02d" + timezone,
                time.getYear(), time.getMonth().getValue(), time.getDayOfMonth(), time.getHour(), time.getMinute(), 0);
    }

}