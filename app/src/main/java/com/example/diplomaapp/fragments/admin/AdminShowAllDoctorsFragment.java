package com.example.diplomaapp.fragments.admin;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.adapters.ClientDoctorAdapter;
import com.example.diplomaapp.api.ClientApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.Doctor;
import com.example.diplomaapp.test.ClickInterface;
import com.example.diplomaapp.test.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminShowAllDoctorsFragment extends Fragment implements ClickInterface {

    public AdminShowAllDoctorsFragment(){
        super(R.layout.fragment_list_doctors);
    }
    private RecyclerView rvDoctors;
    private ClientDoctorAdapter adapter;
    private List<Doctor> doctors;

    private String password;
    private String username;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");

        }

        rvDoctors = view.findViewById(R.id.rvDoctors);

        fillAdapter();

        rvDoctors.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvDoctors, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Fragment Fragment_Doctor_Info = new AdminChangeDoctorFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, Fragment_Doctor_Info, "TAG")
                                .commit();
                        Doctor doctor = doctors.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        bundle.putParcelable("doctor", doctor);  // Key, value
                        Fragment_Doctor_Info.setArguments(bundle);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }


    private void fillAdapter(){

        String auth = createAuthToken(username, password);


        NetworkService networkService = NetworkService.getInstance();
        ClientApi api = networkService.getClientApi();

        Call<List<Doctor>> call = api.getAllDoctors(auth);

        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                doctors = response.body();
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rvDoctors.setHasFixedSize(true);
               rvDoctors.setLayoutManager(manager);
                adapter = new ClientDoctorAdapter(getContext(), doctors);
                rvDoctors.setAdapter(adapter);
                System.out.println("test!!!!!!!");
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


    @Override
    public void recyclerviewOnClick(int position) {

    }
}
