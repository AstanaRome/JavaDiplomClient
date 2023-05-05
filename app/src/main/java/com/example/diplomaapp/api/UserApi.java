package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.entity.UserForm;

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

public interface UserApi {

    @GET("/users")
    Call<List<User>> getAllUsers(@Header("Authorization") String authToken);

    @POST("/users/add")
    Call<ResponseBody> saveUser(@Header("Authorization") String authToken, @Body User user);

    @PUT("/users")
    Call<User> updateUser(@Header("Authorization") String authToken, @Body User user);
    @GET("/users/{id}")
    Call <User> getUser(@Header("Authorization") String authToken, @Path("id") int userId);

    @GET("/info")
    Call <User> getUser(@Header("Authorization") String authToken);

    @DELETE("/users/{id}")
    Call<ResponseBody> deleteUser(@Header("Authorization") String authToken, @Path("id") int userId);


    @POST("/change_user")
    Call<String> changeUser(@Header("Authorization") String authToken, @Body UserForm userForm);


}
