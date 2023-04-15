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
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    EditText etLogin;
    EditText etPassword;
    Button btnLogin;

    private List<User> users;

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
        String username = "admin";
        //etLogin.getText().toString();
        String password = "admin";
        //etPassword.getText().toString();

        String authToken = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        AuthApi api = networkService.getAuthApi();

        Call <String> call = api.checkUser(authToken, username, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

//
                System.out.println(response.body());
                if (response.isSuccessful()){

                    if (response.body().matches("succesfull")){
                        Toast.makeText(LoginActivity.this, "Succesfully Logged In", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i2);
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

    private String createAuthToken(String email, String password){
        byte[] data = new byte[0];
        try{
            data = (email + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "Basic " +  android.util.Base64.encodeToString(data, Base64.NO_WRAP);
    }





}