package com.bookingsystemfortutoring;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookingsystemfortutoring.response.LoginRequest;
import com.bookingsystemfortutoring.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity implements View.OnClickListener, Callback<LoginResponse> {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (TextUtils.isEmpty(username.getText().toString())) {
                    showMessage("Enter Username");
                    return;
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    showMessage("Enter Password");
                    return;
                }
                showProgressDialog();
                hitLogin();

                break;
        }
    }

    private void hitLogin() {
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        Call<LoginResponse> requestCall = retrofitAdapter.getRetrofitApiService().hitLogin(loginRequest);
        requestCall.enqueue(this);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        dismissProgressDialog();
        if (response.body() != null && TextUtils.equals(response.body().getResponse().getStatus(), "success")) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            intent.putExtra("user_id", response.body().getResponse().getUser_id());
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        dismissProgressDialog();
    }
}