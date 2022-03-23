package com.example.library_project.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.User;
import com.example.library_project.view.adapter.ProfileAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvCreated ;
    private ImageView avatar;
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private Button tvLogout;
    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container,false);
        initUIProfile(v);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());
        Call<User> listCall = MainActivity.apiInterface.getUserInfo(mySharedPreferences.getTokenValue(),mySharedPreferences.getIntValue(Common.USER_ID));
        listCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                tvName.setText(response.body().getUsername());
                tvEmail.setText(response.body().getEmail());
                tvCreated.setText("Created Date: " + response.body().getCreatedDate());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

        profileAdapter = new ProfileAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Call<List<Book>> bookCall = MainActivity.apiInterface.getListBooksByUserId(mySharedPreferences.getTokenValue(),mySharedPreferences.getIntValue(Common.USER_ID));
        bookCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                profileAdapter.setData(response.body());
                recyclerView.setAdapter(profileAdapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySharedPreferences.clearPreferences();
                Intent intent = new Intent(getActivity(), LoginSignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_LONG).show();
                getActivity().overridePendingTransition(0,0);
                getActivity().finish();
            }
        });
        return v;
    }

    private void initUIProfile(View v) {
        tvName = v.findViewById(R.id.profile_username);
        tvEmail = v.findViewById(R.id.profile_email);
        tvCreated = v.findViewById(R.id.profile_created);
        tvLogout = v.findViewById(R.id.logout);
        avatar = v.findViewById(R.id.profile_avatar);
        recyclerView = v.findViewById(R.id.rcv_profile);
    }

}
