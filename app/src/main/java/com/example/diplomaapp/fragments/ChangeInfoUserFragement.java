package com.example.diplomaapp.fragments;

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
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.RoleApi;
import com.example.diplomaapp.api.UserApi;
import com.example.diplomaapp.entity.Role;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.entity.UserForm;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeInfoUserFragement extends Fragment {

    private TextView tvUsernameInfo;
    private TextView tvFirstnameInfo;
    private TextView tvLastnameInfo;
    private EditText etEmailInfo;
    private EditText etPasswordInfo;
    private TextView tvUserBirthdayShowInfoAdmin;
    private String password;
    private String username;

    private Button btnChangeEmail;
    private Button btnChangeDate;
    private Button btnChangePassword;
    private Button btnUserBirhday;
    private User user;
    public ChangeInfoUserFragement() {
        super(R.layout.fragment_all_change_user_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        user = new User();

        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
            user = bundle.getParcelable("user");
        }




        tvFirstnameInfo = view.findViewById(R.id.tvFirstNameInfo);
        tvLastnameInfo = view.findViewById(R.id.tvLastNameInfo);
        tvUsernameInfo = view.findViewById(R.id.tvUsernameInfo);
        tvUserBirthdayShowInfoAdmin = view.findViewById(R.id.tvUserBirthdayShowInfoAdmin);
        etEmailInfo = view.findViewById(R.id.etEmailInfo);
        etPasswordInfo = view.findViewById(R.id.etPasswordInfo);
        btnUserBirhday = view.findViewById(R.id.btnUserBirhday);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnChangeEmail = view.findViewById(R.id.btnChangeEmail);
        btnChangeDate = view.findViewById(R.id.btnChangeDate);
        if (user.getUserName()!=null){
            tvUsernameInfo.setText(user.getUserName());
        }
        if(user.getFirstName()!=null){
            tvFirstnameInfo.setText(user.getFirstName());
        }
        if(user.getLastName()!=null){
            tvLastnameInfo.setText(user.getLastName());
        }
        if(user.getEmail()!=null){
            etEmailInfo.setText(user.getEmail());
        }

        if (user.getBirthDate() != null){
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s = formatter.format(user.getBirthDate());
            tvUserBirthdayShowInfoAdmin.setText(s);
        }
        btnUserBirhday.setOnClickListener(new View.OnClickListener() {
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
                                tvUserBirthdayShowInfoAdmin.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );

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

        btnChangeDate.setOnClickListener(this::changeDate);
        btnChangeEmail.setOnClickListener(this::changeEmail);
        btnChangePassword.setOnClickListener(this::changePassword);

    }



    private void changeEmail(View view)  {
        user.setEmail(etEmailInfo.getText().toString());
        UserForm userForm = new UserForm(user, "email");
        updateUser(userForm);
    }

    private void changePassword(View view)  {
            user.setPassword(etPasswordInfo.getText().toString());
            UserForm userForm = new UserForm(user, "password");
                updateUser(userForm);
    }

    private void changeDate(View view)  {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            if (!tvUserBirthdayShowInfoAdmin.getText().toString().equals("")){
                Date date=formatter1.parse(tvUserBirthdayShowInfoAdmin.getText().toString());
                //java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                user.setBirthDate(date);
            }
            UserForm userForm = new UserForm(user, "birthdate");
            updateUser(userForm);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {

        }

    }
        private void updateUser(UserForm userForm) {
            String auth = createAuthToken(username, password);
            NetworkService
                    .getInstance()
                    .getUserApi()
                    .changeUser(auth, userForm)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getContext(), response.body().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                        }
                    });
        }








}