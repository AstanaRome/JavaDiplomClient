package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.entity.Visit;

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

public interface ClientApi {



    @GET("/client/free")
    Call<List<Record>> getAllFreeRecords(@Header("Authorization") String authToken);

    @GET("/client/my_records")
    Call<List<Record>> getAllMyRecords(@Header("Authorization") String authToken);

    @PUT("/client/free")
    Call<Record> setFreeRecord(@Header("Authorization") String authToken, @Body Record record);

    @GET("/client/my_visits")
    Call<List<Visit>> getAllMyVisits(@Header("Authorization") String authToken);

    @GET("/client/doctors")
    Call<List<Doctor>> getAllDoctors(@Header("Authorization") String authToken);

}
