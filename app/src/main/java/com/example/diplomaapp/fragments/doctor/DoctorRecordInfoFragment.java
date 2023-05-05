package com.example.diplomaapp.fragments.doctor;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diplomaapp.R;
import com.example.diplomaapp.adapters.RecordAdapter;
import com.example.diplomaapp.api.DoctorUserApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.Visit;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRecordInfoFragment extends Fragment {
    private Record record;
    private String password;
    private String username;
    private Button btnDoctorRecordInfo;
    private TextView tvDoctorRecordInfoDate;
    private TextView tvDoctorRecordInfoPacient;
    private EditText etDoctorRecordInfoResult;


    public DoctorRecordInfoFragment(){
        super(R.layout.fragment_doctor_record_info);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDoctorRecordInfoPacient = view.findViewById(R.id.tvDoctorRecordInfoPacient);
        tvDoctorRecordInfoDate = view.findViewById(R.id.tvDoctorRecordInfoDate);
        etDoctorRecordInfoResult = view.findViewById(R.id.etDoctorRecordInfoResult);
        btnDoctorRecordInfo = view.findViewById(R.id.btnDoctorRecordInfo);
        record = new Record();

        Bundle bundle = getArguments();
        if (bundle != null) {
            record = bundle.getParcelable("record");
            username = bundle.getString("username");
            password = bundle.getString("password");
        }
        System.out.println("Date!!!!!!!!!!!");

        System.out.println(record.getRecord_day());

        if (record.getRecord_day()!=null){
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s = formatter.format(record.getRecord_day());
            tvDoctorRecordInfoDate.setText(s);
        }

        tvDoctorRecordInfoPacient.setText(record.getDoctor().getUser().getFullName());

        btnDoctorRecordInfo.setOnClickListener(this::saveToVisit);

    }
    private void saveToVisit(View view){
        Visit visit = new Visit();

        visit.setDoctor(record.getDoctor());
        visit.setRecord_day(record.getRecord_day());
        visit.setUser(record.getUser());
        visit.setRecord(record);
        visit.setResult(etDoctorRecordInfoResult.getText().toString());
        visit.getRecord().setEnabled(false);
        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        DoctorUserApi api = networkService.getDoctorUserApi();

        Call<Visit> call = api.addVisit(auth, visit);

        call.enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {

            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

    }



}
