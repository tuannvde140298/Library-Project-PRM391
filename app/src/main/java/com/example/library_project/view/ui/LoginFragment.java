package com.example.library_project.view.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.models.LoginModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvForgetPassword, tvSignUp;
    private ProgressDialog dialog;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        initUi(v);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait....");
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment signupActivity = new SignUpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_container, signupActivity);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        return v;
    }

    private void loginUser() {
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Your email is required!", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Password required", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), "Password  may be at least 6 characters long.", Toast.LENGTH_SHORT).show();
        } else {
            Call<LoginModel> userCall = LoginSignUpActivity.apiInterface.doLogin(email, password);
            dialog.show();
            userCall.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    LoginModel result = response.body();
                    try {
                        if (result.isIs_success()) {
                            dialog.dismiss();
                            String authenticationToken = result.getData().getUser().getAuthenticationToken();
                            mySharedPreferences.putTokenValue(authenticationToken);
                            mySharedPreferences.putIntValue(Common.USER_ID, result.getData().getUser().getId());
                            Toast.makeText(getActivity(), response.body().getMessages(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } catch(NullPointerException e) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initUi(View v) {
        btnLogin = v.findViewById(R.id.btn_login);
        edtEmail = v.findViewById(R.id.edt_email);
        edtPassword = v.findViewById(R.id.edt_password);
        tvForgetPassword = v.findViewById(R.id.tv_forgetPassword);
        tvSignUp = v.findViewById(R.id.tv_signup);
    }
}
