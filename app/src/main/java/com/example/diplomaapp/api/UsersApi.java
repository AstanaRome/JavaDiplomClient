package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UsersApi {

    @GET("/users")
    Call<List<User>> getAllUsers(@Header("Authorization") String authToken);
}
