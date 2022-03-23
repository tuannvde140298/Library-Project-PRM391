package com.example.library_project.view.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import com.example.library_project.R;

public class LoginSignUpFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_signup, container,false);
        Button btnLinkLogin = v.findViewById(R.id.btn_link_login);
        Button btnLinkSignUp = v.findViewById(R.id.btn_link_sign_up);

        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.activity_main_container, new LoginFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.activity_main_container, new SignUpFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return v;
    }
}
