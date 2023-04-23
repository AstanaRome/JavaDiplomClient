package com.example.diplomaapp.fragments.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diplomaapp.R;
import com.example.diplomaapp.archieve.Login;

public class MainAdminFragment extends Fragment {
    private ImageButton btnUsers;
    private ImageButton btnClient;


    private String password;
    private String username;
    public MainAdminFragment(){
        super(R.layout.fragment_admin_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnUsers = view.findViewById(R.id.btnUsers);
        btnClient = view.findViewById(R.id.btnClient);
        btnUsers.setOnClickListener(this::showUsersAdminFragment);
        btnClient.setOnClickListener(this::addUserAdminFragment);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
        }
//        btnDoctors.setOnClickListener(this::showThirdFragment);
//        btnVisits.setOnClickListener(this::showVisitFragment);

    }

    private void showUsersAdminFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);

        Fragment Fragment_Second=new ShowAllUsersFragment();
        Fragment_Second.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Fragment_Second, "TAG")
                .commit();
    }
    private void addUserAdminFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Second=new AddUserFragment();
        Fragment_Second.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Fragment_Second, "TAG")
                .commit();
    }


//    private void showThirdFragment(View view) {
//        Fragment Fragment_Third=new DoctorFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView3, Fragment_Third, "TAG")
//                .commit();
//    }
//    private void showVisitFragment(View view) {
//        Fragment Fragment_Visit=new VisitFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView3, Fragment_Visit, "TAG")
//                .commit();
//    }

}