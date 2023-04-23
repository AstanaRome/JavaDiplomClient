package com.example.diplomaapp.fragments.admin;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserFragment extends Fragment {

    private Button btnAddClient;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private List<Role> roles;
    private Spinner spRole;
    private Spinner spEnabled;

    private User user;
    private String password;
    private String username;


    public AddUserFragment() {
        super(R.layout.fragment_user_add);
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
        spRole = view.findViewById(R.id.spRole);
        spEnabled = view.findViewById(R.id.spEnabled);
        btnAddClient.setOnClickListener(this::change);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
            System.out.println(username);
            System.out.println(password);
        }

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
        saveClient(user);


    }


    private void saveClient(User user) {


        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        UserApi api = networkService.getUserApi();
        Call<User> call = api.saveUser(auth, user);

        call.enqueue(new Callback<User>() {
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
