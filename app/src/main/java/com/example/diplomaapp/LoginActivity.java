package com.example.diplomaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.diplomaapp.api.AuthApi;
import com.example.diplomaapp.api.AuthToken;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.archieve.Login;
import com.example.diplomaapp.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    EditText etLogin;
    EditText etPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this::authUser);

    }

    public void authUser(View view) {
        String username = "patrick";
        String password = "patrick";

//        String username = "admin";
//        String password = "admin";

//
//        String password = etPassword.getText().toString();
//        String username = etLogin.getText().toString();

        String authToken = AuthToken.createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        AuthApi api = networkService.getAuthApi();

        Call <String> call = api.checkUser(authToken, username, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

//
                System.out.println(response.body());
                if (response.isSuccessful()){

                    if (response.body().contains("succesfull")){
                        Toast.makeText(LoginActivity.this, "Succesfully Logged In", Toast.LENGTH_SHORT).show();

                        if (response.body().contains("ADMIN")){
                            Intent i2 = new Intent(LoginActivity.this, AdminActivity.class);
                            i2.putExtra("username", username);
                            i2.putExtra("password", password);
                            startActivity(i2);
                        } else if(response.body().contains("CLIENT")){
                            Intent i2 = new Intent(LoginActivity.this, ClientActivity.class);
                            i2.putExtra("username", username);
                            i2.putExtra("password", password);
                            startActivity(i2);
                        } else if (response.body().contains("DOCTOR")){
                            Intent i2 = new Intent(LoginActivity.this, DoctorActivity.class);
                            i2.putExtra("username", username);
                            i2.putExtra("password", password);
                            startActivity(i2);
                            System.out.println("DOCTOR");
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    System.out.println("fail");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              }
        });


    }







}