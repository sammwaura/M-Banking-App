package com.meshsami27.m_bankingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
