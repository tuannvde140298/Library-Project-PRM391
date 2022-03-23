package com.example.library_project.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.remote.ApiClient;
import com.example.library_project.data.remote.ApiInterface;
import com.example.library_project.view.ui.interfaces.ShowBackButton;
import com.example.library_project.view.ui.interfaces.ToolbarTitle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ToolbarTitle, ShowBackButton, BottomNavigationView.OnNavigationItemSelectedListener {
    private long backPressedTime;
    private Toast mToast;

    private BottomNavigationView navigationView;
    private FragmentManager fm;
    private Fragment fragment;
    private TextView titleItem;
    private ImageView btnBack;
    String booksTitle = null;
    int cat_id = 0;

    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        MainActivity.apiInterface.getCategories(mySharedPreferences.getTokenValue()).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mySharedPreferences.putCategories(response.body());
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) { }
        });
        navigationView = findViewById(R.id.bottom_nav);
        titleItem = findViewById(R.id.item_title);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButtonClick();
            }
        });

        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction t = fm.beginTransaction();
            fragment = new HomeFragment();
            t.replace(R.id.frame_main, fragment);
            t.commit();
        } else {
            fragment = (Fragment) fm.findFragmentById(R.id.frame_main);
        }
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        backButtonClick();
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {
        titleItem.setText(toolbarTitle);
    }

    @Override
    public void saveBooksTitle(int categoryId,String toolbaTitle) {
        booksTitle = toolbaTitle;
        cat_id = categoryId;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = fm.beginTransaction();
        // Hide Back Button
        btnBack.setVisibility(View.INVISIBLE);

        switch (item.getItemId()) {
            case R.id.action_home:
                titleItem.setText("Home");
                ft.replace(R.id.frame_main, new HomeFragment());
                ft.commit();
                return true;
            case R.id.action_favorite:
                titleItem.setText("Favorite");
                ft.replace(R.id.frame_main, new FavoriteFragment());
                ft.commit();
                return true;
            case R.id.action_add:
                titleItem.setText("Add Book");
                ft.replace(R.id.frame_main, new AddBookFragment());
                ft.commit();
                return true;
            case R.id.action_categories:
                titleItem.setText("Categories");
                ft.replace(R.id.frame_main, new CategoriesFragment());
                ft.commit();
                return true;
            case R.id.action_profile:
                titleItem.setText("Profile");
                ft.replace(R.id.frame_main, new ProfileFragment());
                ft.commit();
                return true;
        }
            return false;
    }

    private void backButtonClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {
            if (fragmentManager.findFragmentByTag(Common.FRAG_BOOK).isVisible()) {
                fragmentTransaction.replace(R.id.frame_main, new CategoriesFragment());
                fragmentTransaction.commit();
                titleItem.setText("Categories");
                btnBack.setVisibility(View.INVISIBLE);
                return;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (fragmentManager.findFragmentByTag(Common.FRAG_BDETAIL).isVisible()) {
                // add bundle arguments
                Bundle bundle = new Bundle();
                bundle.putInt(Common.CAT_ID_KEY, cat_id);
                bundle.putString(Common.CAT_NAME, booksTitle);

                BooksFragment booksFragment = new BooksFragment();
                booksFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_main, booksFragment, Common.FRAG_BOOK);
                fragmentTransaction.commit();
                return;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            mToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mToast = Toast.makeText(MainActivity.this, "Press back again to exit the application", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public void showBackButton() {
        btnBack.setVisibility(View.VISIBLE);
    }
}