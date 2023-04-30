package com.example.diplomaapp.fragments.admin;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diplomaapp.R;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.RecordApi;
import com.example.diplomaapp.api.DoctorApi;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.Doctor;

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

public class AdminAddRecordFragment extends Fragment {



    Button btnRecordDateAdmin;
    TextView tvRecordDateAdmin;

    Spinner spRecordAddAdminDoctor;
    Spinner spRecordAddAdminTime;
    private String password;
    private String username;
    private Button btnRecordAddSaveAdmin;


    private Record record;
    private List<Doctor> doctors;
    private List<String> times;

    public AdminAddRecordFragment() {
        super(R.layout.fragment_admin_record_add);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRecordDateAdmin = view.findViewById(R.id.btnAdminRecordAddDate);
        tvRecordDateAdmin = view.findViewById(R.id.tvAdminRecordAddDay);
        spRecordAddAdminDoctor = view.findViewById(R.id.spAdminRecordAddDoctor);
        spRecordAddAdminTime = view.findViewById(R.id.spRecordAddTimeAdmin);
        btnRecordAddSaveAdmin = view.findViewById(R.id.btnRecordAddSaveAdmin);
        record = new Record();

        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
            System.out.println(username);
            System.out.println(password);
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tvRecordDateAdmin.setText(date);

        btnRecordAddSaveAdmin.setOnClickListener(this::saveClient);

        btnRecordDateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tvRecordDateAdmin.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        spRecordAddAdminTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spRecordAddAdminTime.getSelectedItem();

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

                try {
                    Time time = new Time(formatter.parse(str).getTime());
                    record.setRecord_time(time);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        spRecordAddAdminDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spRecordAddAdminDoctor.getSelectedItem();
                for (Doctor doctor: doctors) {
                    String str2 = doctor.getUser().getFullName();
                    if (str2.equals(str)) {
                        System.out.println(doctor);
                        record.setDoctor(doctor);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        fillSpDoctor();
        fillSpTime();
    }

    private void saveClient(View view) {

        String str = tvRecordDateAdmin.getText().toString();
        if (!str.equals("!")){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try {
                Date date=formatter.parse(str);
                record.setRecord_day(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        RecordApi api = networkService.getRecordApi();
        Call<Record> call = api.saveRecord(auth, record);

        call.enqueue(new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                Record buf = response.body();
                Toast.makeText(getContext(), "Succesful",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {
            }
        });

    }

//    private void change(View view) {
//        doctor.setFirstName(etFirstName.getText().toString());
//        doctor.setLastName(etLastName.getText().toString());
//        doctor.setEmail(etEmail.getText().toString());
//        doctor.setDoctorName(etDoctorname.getText().toString());
//        doctor.setPassword(etPassword.getText().toString());
//        saveClient(doctor);
//
//
//    }
//
//
//    private void saveClient(Doctor doctor) {
//
//
//        String auth = createAuthToken(doctorname, password);
//
//        NetworkService networkService = NetworkService.getInstance();
//        DoctorApi api = networkService.getDoctorApi();
//        Call<Doctor> call = api.saveDoctor(auth, doctor);
//
//        call.enqueue(new Callback<Doctor>() {
//            @Override
//            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
//                Doctor buf = response.body();
//                Toast.makeText(getContext(), "Succesful",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Doctor> call, Throwable t) {
//            }
//        });
//
//    }
//
//
    public void fillSpDoctor() {

        NetworkService networkService = NetworkService.getInstance();
        DoctorApi api = networkService.getDoctorApi();
        Call<List<Doctor>> call = api.getAllDoctors(createAuthToken(username, password));


        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                doctors = response.body();
                ArrayList<String> names = new ArrayList<>();
                for (Doctor doctor : doctors) {
                        names.add(doctor.getUser().getFullName());
                }
                ArrayAdapter<String> adapter_doctor = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, names);
                adapter_doctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRecordAddAdminDoctor.setAdapter(adapter_doctor);
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
//
    public void fillSpTime() {

        ArrayList<String> names = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            String temp = i + ":00:00";
            names.add(temp);
        }
        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, names);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRecordAddAdminTime.setAdapter(adapter_time);
    }



}
