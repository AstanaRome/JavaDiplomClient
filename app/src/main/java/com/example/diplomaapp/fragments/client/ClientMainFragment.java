package com.example.diplomaapp.fragments.client;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diplomaapp.R;

public class ClientMainFragment extends Fragment{
    private ImageButton btnClientShowAllRecords;
    private ImageButton btnClientShowClientRecords;
    private ImageButton btnClientShowClientVisits;
    private ImageButton btnClientShowAllDoctors;

    private String password;
    private String username;
    public ClientMainFragment(){
        super(R.layout.fragment_client_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnClientShowAllRecords = view.findViewById(R.id.btnClientShowAllRecords);
        btnClientShowClientRecords = view.findViewById(R.id.btnClientShowClientRecords);
        btnClientShowClientVisits = view.findViewById(R.id.btnShowAllHistory);
        btnClientShowAllDoctors = view.findViewById(R.id.btnClientShowAllDoctors);
        btnClientShowAllDoctors.setOnClickListener(this::ShowAllDoctorsFragment);
        btnClientShowClientVisits.setOnClickListener(this::ShowAllVisitsFragment);
        btnClientShowAllRecords.setOnClickListener(this::ShowAllRecordsFragment);
        btnClientShowClientRecords.setOnClickListener(this::ShowMyRecordsFragment);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
        }
//        btnDoctors.setOnClickListener(this::showThirdFragment);
//        btnVisits.setOnClickListener(this::showVisitFragment);

    }

    private void ShowAllVisitsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new ClientShowAllVisitsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_Records, "TAG")
                .commit();
    }

    private void ShowAllDoctorsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);

        Fragment Fragment_Second=new ClientShowAllDoctorsFragment();
        Fragment_Second.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_Second, "TAG")
                .commit();
    }

    private void ShowAllRecordsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new ClientShowAllRecordsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_Records, "TAG")
                .commit();
    }

    private void ShowMyRecordsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new ClientShowMyRecordsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_Records, "TAG")
                .commit();
    }


}