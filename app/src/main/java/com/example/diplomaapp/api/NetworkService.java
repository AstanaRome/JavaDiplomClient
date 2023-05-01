package com.example.diplomaapp.api;

import com.example.diplomaapp.adapters.DateJsonAdapter;
import com.example.diplomaapp.adapters.TimeJsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService networkService;
    private static final String BASE_URL = "http://192.168.1.66:8080/";
    private Retrofit retrofit;

    private NetworkService() {
//        GsonBuilder builder = new GsonBuilder().setLenient();
//        builder.registerTypeAdapter(Date.class, new GsonDateDeSerializer());
//        Gson gson = builder.create();
//
        Gson gson = new GsonBuilder()
                .setLenient().registerTypeAdapter(Date.class,new DateJsonAdapter().nullSafe()).registerTypeAdapter(Time.class, new TimeJsonAdapter().nullSafe())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public AuthApi getAuthApi () {
        return retrofit.create(AuthApi.class);
    }

    public UserApi getUserApi(){
        return retrofit.create(UserApi.class);
    }
    public RoleApi getRoleApi(){
        return retrofit.create(RoleApi.class);
    }

    public RecordApi getRecordApi(){
        return retrofit.create(RecordApi.class);
    }

    public DoctorApi getDoctorApi(){
        return retrofit.create(DoctorApi.class);
    }
    public ClientApi getClientApi(){
        return retrofit.create(ClientApi.class);
    }
    public DoctorUserApi getDoctorUserApi(){
        return retrofit.create(DoctorUserApi.class);
    }



}
