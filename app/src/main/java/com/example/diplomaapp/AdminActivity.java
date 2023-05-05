package com.example.diplomaapp;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.UserApi;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.fragments.ChangeInfoUserFragement;
import com.example.diplomaapp.fragments.admin.AdminMainFragment;
import com.example.diplomaapp.fragments.admin.AdminShowInfoRecordFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    Button btnHome;
    User user;
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
        user = new User();
        getUser();
        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        // Key, value
        Fragment Fragment_first=new AdminMainFragment();
        Fragment_first.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, Fragment_first, "TAG")
                .commit();

    }
    public void getUser(){
        String auth = createAuthToken(username, password);
        NetworkService networkService = NetworkService.getInstance();
        UserApi api = networkService.getUserApi();
        Call<User> call = api.getUser(auth);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                System.out.println(user.toString());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // handle failure
                Toast.makeText(AdminActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.account_settings :
                Bundle bundle = new Bundle();
                bundle.putString("username",  username);
                bundle.putString("password",  password);

                bundle.putParcelable("user", user);
                // Key, value
                Fragment Fragment_first=new ChangeInfoUserFragement();
                Fragment_first.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, Fragment_first, "TAG")
                        .commit();
                return true;

            case R.id.logout_settings:
                Intent i2 = new Intent(AdminActivity.this, LoginActivity.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i2);
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    private void showAdminMainFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        // Key, value
        Fragment Fragment_first=new AdminMainFragment();
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





