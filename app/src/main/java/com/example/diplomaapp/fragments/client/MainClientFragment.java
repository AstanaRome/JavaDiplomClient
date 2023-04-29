package com.example.diplomaapp.fragments.client;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diplomaapp.R;
import com.example.diplomaapp.fragments.admin.AddRecordFragment;
import com.example.diplomaapp.fragments.admin.AddUserFragment;
import com.example.diplomaapp.fragments.admin.ShowAllRecordsFragment;
import com.example.diplomaapp.fragments.admin.ShowAllUsersFragment;

public class MainClientFragment extends Fragment{
    private ImageButton btnClientShowAllRecords;
    private ImageButton btnClientShowClientRecords;


    private String password;
    private String username;
    public MainClientFragment(){
        super(R.layout.fragment_client_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnClientShowAllRecords = view.findViewById(R.id.btnClientShowAllRecords);
        btnClientShowClientRecords = view.findViewById(R.id.btnClientShowClientRecords);
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



    private void ShowAllRecordsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new ShowAllRecordsFragmentClient();
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
        Fragment Fragment_Records=new ShowClientRecordsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, Fragment_Records, "TAG")
                .commit();
    }


}