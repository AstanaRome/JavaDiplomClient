package com.example.diplomaapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService networkService;
    private static final String BASE_URL = "http://192.168.1.66:8080/";
    private Retrofit retrofit;

    private NetworkService() {
        Gson gson = new GsonBuilder()
                .setLenient().setDateFormat("yyyy-MM-dd")
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
//}
//public class NetworkService {
//    private static NetworkService networkService;
//    private static final String BASE_URL = "http://192.168.1.66:8080/";
//    private static Retrofit retrofit;
//
//    private NetworkService() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    public static NetworkService getInstance() {
//        if (networkService == null) {
//            networkService = new NetworkService();
//        }
//        return networkService;
//    }

    //   public PersonApi getPersonApi(){
//        return retrofit.create(PersonApi.class);
//    }
//
//    public DoctorApi getDoctorApi(){
//        return retrofit.create(DoctorApi.class);
//    }
//    public VisitApi getVisitApi(){
//        return retrofit.create(VisitApi.class);
//    }


}
