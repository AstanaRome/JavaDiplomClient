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
import com.example.diplomaapp.adapters.MyErrorMessage;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.RoleApi;
import com.example.diplomaapp.api.UserApi;
import com.example.diplomaapp.entity.Role;
import com.example.diplomaapp.entity.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import okhttp3.internal.http2.ErrorCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddUserFragment extends Fragment {

    private Button btnAddClient;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private List<Role> roles;
    private Spinner spRole;
    private Spinner spEnabled;
    private TextView tvUserBirthdayAddAdmin;
    private Button btnUserAddDateAdmin;

    private User user;
    private String password;
    private String username;


    public AdminAddUserFragment() {
        super(R.layout.fragment_admin_user_add);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = new User();
        etFirstName = view.findViewById(R.id.etFirstNameAdd);
        etLastName = view.findViewById(R.id.etLastNameAdd);
        etUsername = view.findViewById(R.id.etUsernameAdd);
        etPassword = view.findViewById(R.id.etPasswordAdd);
        etEmail = view.findViewById(R.id.etEmailAdd);
        btnAddClient = view.findViewById(R.id.btnSaveAdd);
        btnUserAddDateAdmin = view.findViewById(R.id.btnUserAddDateAdmin);
        spRole = view.findViewById(R.id.spRole);
        spEnabled = view.findViewById(R.id.spEnabled);
        tvUserBirthdayAddAdmin = view.findViewById(R.id.tvUserBirthdayAddAdmin);
        btnAddClient.setOnClickListener(this::change);



        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
            System.out.println(username);
            System.out.println(password);
        }

        btnUserAddDateAdmin.setOnClickListener(new View.OnClickListener() {
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
                                tvUserBirthdayAddAdmin.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );

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

    private void change(View view) {
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setUserName(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        if (tvUserBirthdayAddAdmin.getText().toString() != ""){{
            String date = tvUserBirthdayAddAdmin.getText().toString();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date2;
            try {
                date2 = formatter1.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            user.setBirthDate(date2);
        }}
        saveClient(user);
    }


    private void saveClient(User user) {


        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        UserApi api = networkService.getUserApi();
        Call<ResponseBody> call = api.saveUser(auth, user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                Toast.makeText(getContext(), "Succesful",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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
                    names.add(role.getName());
                }
                ArrayAdapter<String> adapter_doctor = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, names);
                adapter_doctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRole.setAdapter(adapter_doctor);
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

    }

}
