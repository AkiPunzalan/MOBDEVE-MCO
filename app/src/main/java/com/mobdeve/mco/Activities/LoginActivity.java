package com.mobdeve.mco.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mobdeve.mco.R;

public class LoginActivity extends AppCompatActivity {

    Button btnSignin;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignin = findViewById(R.id.btn_signin);

        //SET VISIBLE WHEN LOGED IN AND SET TEXT TO USER EMAIL
        tvEmail = findViewById(R.id.tv_login_email);
    }
}