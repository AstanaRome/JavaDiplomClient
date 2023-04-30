package com.example.diplomaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diplomaapp.fragments.client.ClientMainFragment;
import com.example.diplomaapp.fragments.client.ClientShowAllRecordsFragment;
import com.example.diplomaapp.test.Removable;

public class DoctorActivity extends AppCompatActivity implements  Removable{

    String username;
    String password;
    Button btnClientHome;


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

    @Override
    public void remove() {
     ShowAllRecordsFragment();
    }
}
