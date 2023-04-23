package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.Role;
import com.example.diplomaapp.entity.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoleApi {

    @GET("/roles")
    Call<List<Role>> getAllRoles(@Header("Authorization") String authToken);

}
