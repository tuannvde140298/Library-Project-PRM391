package com.example.library_project.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.library_project.R;
import com.example.library_project.view.ui.interfaces.ShowBackButton;
import com.example.library_project.view.ui.interfaces.ToolbarTitle;

public class SearchActivity extends AppCompatActivity implements ToolbarTitle, ShowBackButton {
    private ImageButton backButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        backButton = findViewById(R.id.btn_back);
        Intent i = new Intent(this, MainActivity.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment initialSearchFragment = new SearchInitialFragment();
        FragmentTransaction t = fm.beginTransaction();
        t.replace(R.id.frame_main, initialSearchFragment);
        t.commit();
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {

    }

    @Override
    public void saveBooksTitle(int categoryId, String toolbaTitle) {

    }

    @Override
    public void showBackButton() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
