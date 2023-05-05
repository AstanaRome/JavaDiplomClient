package com.example.diplomaapp.fragments.admin;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diplomaapp.R;
import com.example.diplomaapp.api.DoctorApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.RecordApi;
import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.entity.Record;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChangeDoctorFragment extends Fragment {


    TextView tvAdminDoctorName;
    EditText etAdminDoctorExperience;
    Button btnAdminDoctorSave;
    Spinner spAdminDoctorQualification;
    private String password;
    private String username;

    private Doctor doctor;
    private List<String> qualifications;

    public AdminChangeDoctorFragment() {
        super(R.layout.fragment_admin_doctor_show_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvAdminDoctorName = view.findViewById(R.id.tvAdminDoctorName);
        spAdminDoctorQualification = view.findViewById(R.id.spAdminDoctorQualification);
        etAdminDoctorExperience = view.findViewById(R.id.etAdminDoctorExperience2);
        btnAdminDoctorSave = view.findViewById(R.id.btnAdminDoctorSave);
        doctor = new Doctor();

        Bundle bundle = getArguments();
        if (bundle != null) {
            doctor = bundle.getParcelable("doctor");
            username = bundle.getString("username");
            password = bundle.getString("password");
            System.out.println(username);
            System.out.println(password);
            System.out.println(doctor.toString());
        }

        etAdminDoctorExperience.setText(String.valueOf(doctor.getExperience()));
        tvAdminDoctorName.setText(doctor.getUser().getFullName());
        btnAdminDoctorSave.setOnClickListener(this::saveDoctor);


        spAdminDoctorQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spAdminDoctorQualification.getSelectedItem();
                doctor.setQualification(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fillSpQual();
    }

    private void saveDoctor(View view) {

        doctor.setExperience(Integer.valueOf(etAdminDoctorExperience.getText().toString()));
        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        DoctorApi api = networkService.getDoctorApi();
        Call<Doctor> call = api.updateDoctor(auth, doctor);

        call.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                Doctor buf = response.body();
                System.out.println(buf);
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

    }



    //
    public void fillSpQual() {

        ArrayList<String> names = new ArrayList<>();
        names.add("Neurologist");
        names.add("Therapist");
        names.add("Ophthalmologist");
        names.add("Dermatologist");
        names.add("Surgeon");
        names.add("Urologist");
        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, names);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdminDoctorQualification.setAdapter(adapter_time);
        if(doctor.getQualification()!= null){
            int index = adapter_time.getPosition(doctor.getQualification());
            spAdminDoctorQualification.setSelection(index);
        }


    }


}
