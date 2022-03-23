package com.example.library_project.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library_project.R;
import com.example.library_project.data.remote.ApiClient;
import com.example.library_project.data.remote.ApiInterface;

public class LoginSignUpActivity extends AppCompatActivity {
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }
}
