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
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.RoleApi;
import com.example.diplomaapp.entity.Role;
import com.example.diplomaapp.entity.User;

import java.text.Format;
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

public class AdminShowInfoUserFragement extends Fragment {
    private Button btnOkInfo;
    private EditText etUsernameInfo;
    private EditText etFirstnameInfo;
    private EditText etLastnameInfo;
    private EditText etEmailInfo;
    private EditText etPasswordInfo;
    private EditText etBirthdayInfo;

    private List<Role> roles;
    private Spinner spRole;
    private Spinner spEnabled;
    private String password;
    private String username;
    private TextView tvUserBirthdayShowInfoAdmin;
    private Button btnUserBirhdayShowInfoAdmin;
    private String roleFromDb;
    private String enabledFromDb;
    private User user;
    public AdminShowInfoUserFragement() {
        super(R.layout.fragment_admin_user_show_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOkInfo = view.findViewById(R.id.btnOkInfo);
        etFirstnameInfo = view.findViewById(R.id.etFirstNameInfo);
        etLastnameInfo = view.findViewById(R.id.etLastNameInfo);
        etEmailInfo = view.findViewById(R.id.etEmailInfo);
        etUsernameInfo = view.findViewById(R.id.etUsernameInfo);
        etPasswordInfo = view.findViewById(R.id.etPasswordInfo);
        spRole = view.findViewById(R.id.spRoleInfo);
        spEnabled = view.findViewById(R.id.spEnabledInfo);
        btnUserBirhdayShowInfoAdmin = view.findViewById(R.id.btnUserBirhdayShowInfoAdmin);
        tvUserBirthdayShowInfoAdmin = view.findViewById(R.id.tvUserBirthdayShowInfoAdmin);
        user = new User();
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getParcelable("user");
            username = bundle.getString("username");
            password = bundle.getString("password");
        }
        System.out.println(username);
        System.out.println(password);
        etUsernameInfo.setText(user.getUserName());
        etFirstnameInfo.setText(user.getFirstName());
        etLastnameInfo.setText(user.getLastName());
        etEmailInfo.setText(user.getEmail());
        if (user.getBirthDate() != null){
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s = formatter.format(user.getBirthDate());
            tvUserBirthdayShowInfoAdmin.setText(s);
        }
        btnOkInfo.setOnClickListener(this::change);
        btnUserBirhdayShowInfoAdmin.setOnClickListener(new View.OnClickListener() {
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
        spEnabled.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spEnabled.getSelectedItem();
                if (str.equals("Active")) {
                    user.setEnabled(true);
                } else {
                    user.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = (String) spRole.getSelectedItem();
                for (Role role : roles) {
                    String str2 = role.getName();
                    if (str2.equals(str)) {
                        user.setRole_id(role);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        fillSpRole();
        fillSpEnabled();
    }

    private void change(View view)  {
        user.setFirstName(etFirstnameInfo.getText().toString());
        user.setLastName(etLastnameInfo.getText().toString());
        user.setUserName(etUsernameInfo.getText().toString());
        if (!etPasswordInfo.getText().toString().equals("")){
            user.setPassword(etPasswordInfo.getText().toString());
        }

        user.setEmail(etEmailInfo.getText().toString());
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            if (!tvUserBirthdayShowInfoAdmin.getText().toString().equals("")){
                Date date=formatter1.parse(tvUserBirthdayShowInfoAdmin.getText().toString());
                //java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                user.setBirthDate(date);
            }

            updateUser(user);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {

        }

    }
        private void updateUser(User user) throws ParseException{
            String auth = createAuthToken(username, password);
            NetworkService
                    .getInstance()
                    .getUserApi()
                    .updateUser(auth, user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User buf = response.body();
                            Toast.makeText(getContext(), "Succesful",
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        }
                    });
        }


    public void fillSpRole() {

        NetworkService networkService = NetworkService.getInstance();
        RoleApi api = networkService.getRoleApi();
        Call<List<Role>> call = api.getAllRoles(createAuthToken(username, password));


        call.enqueue(new Callback<List<Role>>() {
            @Override
            public void onResponse(Call<List<Role>> call, Response<List<Role>> response) {
                roles = response.body();
                ArrayList<String> names = new ArrayList<>();
                for (Role role : roles) {
                    if (role.getId() == user.getRole_id().getId()) {
                        roleFromDb = user.getRole_id().getName();
                    }
                    names.add(role.getName());
                }
                ArrayAdapter<String> adapter_doctor = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, names);
                adapter_doctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRole.setAdapter(adapter_doctor);
                int index = adapter_doctor.getPosition(roleFromDb);
                spRole.setSelection(index);
            }

            @Override
            public void onFailure(Call<List<Role>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    public void fillSpEnabled() {

        ArrayList<String> names = new ArrayList<>();

        names.add("Active");
        names.add("Banned");
        ArrayAdapter<String> adapter_doctor = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, names);
        adapter_doctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEnabled.setAdapter(adapter_doctor);
        int index;
        if (user.isEnabled() == true){
           index = adapter_doctor.getPosition("Active");
        } else{
            index = adapter_doctor.getPosition("Banned");
        }

        spRole.setSelection(index);

    }



}