package com.example.diplomaapp.api;

import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.entity.Role;
import com.example.diplomaapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface DoctorApi {

    @GET("/doctors")
    Call<List<Doctor>> getAllDoctors(@Header("Authorization") String authToken);

    @PUT("/doctors")
    Call<Doctor> updateDoctor(@Header("Authorization") String authToken, @Body Doctor doctor);

}
