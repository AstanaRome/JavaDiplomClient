package com.example.diplomaapp;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.UserApi;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.fragments.ChangeInfoUserFragement;
import com.example.diplomaapp.fragments.client.ClientMainFragment;
import com.example.diplomaapp.fragments.client.ClientShowAllRecordsFragment;
import com.example.diplomaapp.test.Removable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends AppCompatActivity implements  Removable{

    String username;
    String password;
    Button btnClientHome;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        btnClientHome = findViewById(R.id.btnClientHome);

        Bundle arguments = getIntent().getExtras();


        if (arguments != null) {
            username = arguments.getString("username");
            password = arguments.getString("password");
        }

        user = new User();
        getUser();

        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        Fragment Fragment_first=new ClientMainFragment();
        Fragment_first.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_first, "TAG")
                .commit();

        btnClientHome.setOnClickListener(this::showClientMainFragment);

    }
    private void showClientMainFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        // Key, value
        Fragment Fragment_first=new ClientMainFragment();
        Fragment_first.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_first, "TAG")
                .commit();


    }
    private void ShowAllRecordsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("username",  username);
        bundle.putString("password",  password);
        // Key, value
        Fragment Fragment_first=new ClientShowAllRecordsFragment();
        Fragment_first.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_first, "TAG")
                .commit();
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
                        .replace(R.id.fragmentContainerView2, Fragment_first, "TAG")
                        .commit();
                return true;

            case R.id.logout_settings:
                Intent i2 = new Intent(ClientActivity.this, LoginActivity.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i2);
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
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
                Toast.makeText(ClientActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void remove() {
     ShowAllRecordsFragment();
    }
}
