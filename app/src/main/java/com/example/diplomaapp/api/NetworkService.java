package com.example.diplomaapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

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

//    new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer());
//
//    private static final String[] DATE_FORMATS = new String[] {
//            "MMM dd, yyyy HH:mm:ss",
//            "MMM dd, yyyy"
//    };
//
//
//    private class DateDeserializer implements JsonDeserializer<Date> {
//
//        @Override
//        public Date deserialize(JsonElement jsonElement, Type typeOF,
//                                JsonDeserializationContext context) throws JsonParseException {
//            for (String format : DATE_FORMATS) {
//                try {
//                    return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
//                } catch (ParseException e) {
//                }
//            }
//            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
//                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
//        }
//    }
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
