package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.entity.Role;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DoctorApi {

    @GET("/doctors")
    Call<List<Doctor>> getAllDoctors(@Header("Authorization") String authToken);

}
