package com.example.diplomaapp;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.adapters.UserAdapter;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.UsersApi;
import com.example.diplomaapp.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    Button btnTest;
    private List<User> users;
    private RecyclerView rvUsers;

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
  //      Bundle arguments = getIntent().getExtras();
//        String name = arguments.get("role").toString();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = findViewById(R.id.btnTest);
        rvUsers = findViewById(R.id.rvUsers);
        btnTest.setOnClickListener(this::authUser);

    }
    public void authUser(View view) {
        String username = "admin";
        //etLogin.getText().toString();
        String password = "admin";
        //etPassword.getText().toString();



        NetworkService networkService = NetworkService.getInstance();
        UsersApi api = networkService.getPostApi();

        String auth = createAuthToken(username, password);

        Call<List<User>> call = api.getAllUsers(auth);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
                if (response.isSuccessful()){
                    users = response.body();
                    for (User user: users  ) {
                        System.out.println(user.toString());
                    }
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    rvUsers.setHasFixedSize(true);
                    rvUsers.setLayoutManager(manager);
                    adapter = new UserAdapter(getApplicationContext(), users);
                    rvUsers.setAdapter(adapter);
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<User>>call, Throwable t) {
            }
        });


    }
    private String createAuthToken(String email, String password){
        byte[] data = new byte[0];
        try{
            data = (email + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "Basic " +  android.util.Base64.encodeToString(data, Base64.NO_WRAP);
    }
}





