package com.example.diplomaapp.fragments.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diplomaapp.R;
import com.example.diplomaapp.fragments.client.ClientShowAllDoctorsFragment;
import com.example.diplomaapp.fragments.client.ClientShowAllRecordsFragment;
import com.example.diplomaapp.fragments.client.ClientShowAllVisitsFragment;
import com.example.diplomaapp.fragments.client.ClientShowMyRecordsFragment;

public class DoctorMainFragment extends Fragment{
    private ImageButton btnDoctorFreeRecords;
    private ImageButton btnDoctorBookedRecords;
    private ImageButton btnDoctorAllVisits;

    private String password;
    private String username;
    public DoctorMainFragment(){
        super(R.layout.fragment_doctor_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDoctorFreeRecords = view.findViewById(R.id.btnDoctorFreeRecords);
        btnDoctorAllVisits = view.findViewById(R.id.btnDoctorAllVisits);
        btnDoctorBookedRecords = view.findViewById(R.id.btnDoctorBookedRecords);

        btnDoctorFreeRecords.setOnClickListener(this::ShowFreeRecordsFragment);
        btnDoctorBookedRecords.setOnClickListener(this::ShowBookRecordsFragment);
        btnDoctorAllVisits.setOnClickListener(this::ShowVisitsFragment);


        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
        }

    }

    private void ShowFreeRecordsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new DoctorFreeRecordsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, Fragment_Records, "TAG")
                .commit();
    }

    private void ShowVisitsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new DoctorAllVisitsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, Fragment_Records, "TAG")
                .commit();
    }

    private void ShowBookRecordsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new DoctorBookRecordsFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, Fragment_Records, "TAG")
                .commit();
    }


}