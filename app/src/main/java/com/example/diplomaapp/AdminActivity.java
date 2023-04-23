package com.example.diplomaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.adapters.UserAdapter;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.fragments.admin.MainAdminFragment;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    Button btnHome;

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this::showAdminMainFragment);
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            username = arguments.getString("username");
            password = arguments.getString("password");
        }
        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        // Key, value
        Fragment Fragment_first=new MainAdminFragment();
        Fragment_first.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, Fragment_first, "TAG")
                .commit();

    }



    private void showAdminMainFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        // Key, value
        Fragment Fragment_first=new MainAdminFragment();
        Fragment_first.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, Fragment_first, "TAG")
                .commit();


    }
























//    public void authUser(View view) {
//        String username = "admin";
//        //etLogin.getText().toString();
//        String password = "admin";
//        //etPassword.getText().toString();
//
//
//
//        NetworkService networkService = NetworkService.getInstance();
//        UsersApi api = networkService.getPostApi();
//
//        String auth = createAuthToken(username, password);
//
//        Call<List<User>> call = api.getAllUsers(auth);
//
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
//                if (response.isSuccessful()){
//                    users = response.body();
//                    for (User user: users  ) {
//                        System.out.println(user.toString());
//                    }
//                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
//                    rvUsers.setHasFixedSize(true);
//                    rvUsers.setLayoutManager(manager);
//                    adapter = new UserAdapter(getApplicationContext(), users);
//                    rvUsers.setAdapter(adapter);
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>>call, Throwable t) {
//            }
//        });
//
//
//    }
//    private String createAuthToken(String email, String password){
//        byte[] data = new byte[0];
//        try{
//            data = (email + ":" + password).getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//        return "Basic " +  android.util.Base64.encodeToString(data, Base64.NO_WRAP);
//    }
}





