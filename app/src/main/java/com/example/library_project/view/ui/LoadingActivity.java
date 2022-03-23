package com.example.library_project.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.R;

public class LoadingActivity extends AppCompatActivity {

    public static final String KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mySharedPreferences.getBooleanValue(KEY_FIRST_INSTALL)){
                    if (mySharedPreferences.getTokenValue().isEmpty()){
                        startActivity(LoginSignUpActivity.class);
                    } else {
                        startActivity(MainActivity.class);
                    }
                } else {
                    startActivity(IntroduceActivity.class);
                    mySharedPreferences.putBooleanValue(KEY_FIRST_INSTALL, true);
                }
            }
        }, 2000);
    }

    private void startActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }
}
