package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.Record;
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

public interface RecordApi {

    @GET("/records")
    Call<List<Record>> getAllRecords(@Header("Authorization") String authToken);

    @POST("/records")
    Call<Record> saveRecord(@Header("Authorization") String authToken, @Body Record record);

    @PUT("/records")
    Call<Record> updateRecord(@Header("Authorization") String authToken, @Body Record record);
    @DELETE("/records/{id}")
    Call<ResponseBody> deleteRecord(@Header("Authorization") String authToken, @Path("id") int userId);

}
