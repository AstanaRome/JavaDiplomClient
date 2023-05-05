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
import com.example.diplomaapp.api.DoctorApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.RecordApi;
import com.example.diplomaapp.api.UserApi;
import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.User;

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

public class AdminShowInfoRecordFragment extends Fragment {



    Button btnAdminRecordFullInfoSave;
    Button btnAdminRecordFullInfoDate;
    TextView tvAdminRecordShowInfoDay;

    Spinner spAdminRecordShowInfoDoctor;
    Spinner spAdminRecordShowInfoTime;
    Spinner spAdminRecordShowInfoUser;
    private String password;
    private String username;
    private Record record;
    private List<Doctor> doctors;
    private List<User> users;

    public AdminShowInfoRecordFragment() {
        super(R.layout.fragment_admin_record_show_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       btnAdminRecordFullInfoSave = view.findViewById(R.id.btnAdminRecordFullInfoSave);
       btnAdminRecordFullInfoDate = view.findViewById(R.id.btnAdminRecordShowInfoDate);
       tvAdminRecordShowInfoDay = view.findViewById(R.id.tvAdminRecordShowInfoDay);
       spAdminRecordShowInfoTime = view.findViewById(R.id.spAdminRecordShowInfoTime);
       spAdminRecordShowInfoUser = view.findViewById(R.id.spAdminRecordShowInfoUser);
       spAdminRecordShowInfoDoctor = view.findViewById(R.id.spAdminRecordShowInfoDoctor);


        record = new Record();

        Bundle bundle = getArguments();
        if (bundle != null) {
            record = bundle.getParcelable("record");
            username = bundle.getString("username");
            password = bundle.getString("password");
            System.out.println(username);
            System.out.println(password);
        }

        if (record.getRecord_day() != null){
            String date = new SimpleDateFormat("yyyy-MM-dd").format(record.getRecord_day());
            tvAdminRecordShowInfoDay.setText(date);
        }


        btnAdminRecordFullInfoSave.setOnClickListener(this::saveClient);

        btnAdminRecordFullInfoDate.setOnClickListener(new View.OnClickListener() {
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
                                tvAdminRecordShowInfoDay.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );

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

        spAdminRecordShowInfoTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spAdminRecordShowInfoTime.getSelectedItem();

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

        spAdminRecordShowInfoDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spAdminRecordShowInfoDoctor.getSelectedItem();
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


        spAdminRecordShowInfoUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spAdminRecordShowInfoUser.getSelectedItem();
                for (User user: users) {
                    String str2 = user.getFullName();
                    if (str2.equals(str)) {
                        System.out.println(user);
                        record.setUser(user);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });



        fillSpUser();
        fillSpDoctor();
        fillSpTime();
    }

    private void saveClient(View view) {
        String str = tvAdminRecordShowInfoDay.getText().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date date=formatter.parse(str);
                record.setRecord_day(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        RecordApi api = networkService.getRecordApi();
        Call<Record> call = api.updateRecord(auth, record);

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


    public void fillSpDoctor() {

        NetworkService networkService = NetworkService.getInstance();
        DoctorApi api = networkService.getDoctorApi();
        Call<List<Doctor>> call = api.getAllDoctors(createAuthToken(username, password));


        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                doctors = response.body();
                String doctorName;
                ArrayList<String> names = new ArrayList<>();
                for (Doctor doctor : doctors) {

                        names.add(doctor.getUser().getFullName());
                }
                ArrayAdapter<String> adapter_doctor = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, names);
                adapter_doctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAdminRecordShowInfoDoctor.setAdapter(adapter_doctor);
                int index = adapter_doctor.getPosition(record.getDoctor().getUser().getFullName());
                spAdminRecordShowInfoDoctor.setSelection(index);
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
//

    public void fillSpUser() {

        NetworkService networkService = NetworkService.getInstance();
        UserApi api = networkService.getUserApi();
        Call<List<User>> call = api.getAllUsers(createAuthToken(username, password));


        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                ArrayList<String> names = new ArrayList<>();
                for (User user : users) {
                    if(user.getRole_id().getId() == 1){
                        names.add(user.getFullName());
                    }

                }
                ArrayAdapter<String> adapter_user = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, names);
                adapter_user.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAdminRecordShowInfoUser.setAdapter(adapter_user);
                if(record.getUser()!= null){
                    int index = adapter_user.getPosition(record.getUser().getFullName());
                    spAdminRecordShowInfoUser.setSelection(index);
                }


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    public void fillSpTime() {

        ArrayList<String> names = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            String temp = i + ":00:00";
            names.add(temp);
        }
        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, names);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdminRecordShowInfoTime.setAdapter(adapter_time);
        int index = adapter_time.getPosition(record.getRecord_time().toString());
        System.out.println(record.getRecord_time().toString());
        spAdminRecordShowInfoTime.setSelection(index);
    }



}
