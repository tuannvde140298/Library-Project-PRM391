package com.example.library_project.view.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.library_project.R;
import com.example.library_project.data.models.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private EditText edtEmail, edtPassword, edtRePassword;
    private Button btnSignUp;
    private TextView tvLogin;
    private ProgressDialog dialog;

    public SignUpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container,false);
        initUi(v);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait....");
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment loginFragment = new LoginFragment ();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_container, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupUser();
            }
        });
        return v;
    }

    private void signupUser() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String rePassword = edtRePassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Your email is required!", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)){
            Toast.makeText(getActivity(), "Password required", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6 || rePassword.length() < 6){
            Toast.makeText(getActivity(), "Password  may be at least 6 characters long.", Toast.LENGTH_SHORT).show();
        }else if (!password.equals(rePassword)){
            Toast.makeText(getActivity(), "Re-password must mathing with password", Toast.LENGTH_SHORT).show();
        } else {
            Call<LoginModel> userCall = LoginSignUpActivity.apiInterface.doRegister(email, password, rePassword);
            dialog.show();
            userCall.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.body().isIs_success()){
                        dialog.dismiss();
                        Toast.makeText(getActivity(), response.body().getMessages(), Toast.LENGTH_SHORT).show();
                        Fragment loginFragment = new LoginFragment ();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Toast.makeText(getActivity(), "Register user successful", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.activity_main_container, loginFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    if (response.body() == null){
                        Toast.makeText(getActivity(), "Register user fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "Register user fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initUi(View v) {
        btnSignUp = v.findViewById(R.id.btn_signup);
        edtEmail = v.findViewById(R.id.edt_email_res);
        edtPassword = v.findViewById(R.id.edt_password_res);
        edtRePassword = v.findViewById(R.id.edt_repassword_res);
        tvLogin = v.findViewById(R.id.tv_login);
    }
}
