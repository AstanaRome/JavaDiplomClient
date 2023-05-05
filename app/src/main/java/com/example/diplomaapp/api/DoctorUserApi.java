package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.entity.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DoctorUserApi {



    @GET("/doctor/free")
    Call<List<Record>> getAllFreeRecords(@Header("Authorization") String authToken);

    @POST("/doctor/free")
    Call<Record> addRecord(@Header("Authorization") String authToken, @Body Record record);

    @POST("/doctor/visit")
    Call<Visit> addVisit(@Header("Authorization") String authToken, @Body Visit visit);
    @GET("/doctor/book")
    Call<List<Record>> getAllBookRecords(@Header("Authorization") String authToken);

    @PUT("/doctor/free")
    Call<Record> setRecord(@Header("Authorization") String authToken);


    @GET("/doctor/visit")
    Call<List<Visit>> getAllMyVisits(@Header("Authorization") String authToken);

}
