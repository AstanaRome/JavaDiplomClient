package com.example.diplomaapp.fragments.doctor;

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
import com.example.diplomaapp.adapters.ClientVisitAdapter;
import com.example.diplomaapp.adapters.DoctorVisitAdapter;
import com.example.diplomaapp.api.ClientApi;
import com.example.diplomaapp.api.DoctorUserApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.Visit;
import com.example.diplomaapp.fragments.client.ClientShowVisitInfo;
import com.example.diplomaapp.test.ClickInterface;
import com.example.diplomaapp.test.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorAllVisitsFragment extends Fragment implements ClickInterface {

    public DoctorAllVisitsFragment(){
        super(R.layout.fragment_doctor_list_visits);
    }
    private RecyclerView rvVisits;
    private DoctorVisitAdapter adapter;
    private List<Visit> visits;

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

        rvVisits = view.findViewById(R.id.rvList);

        fillAdapter();

        rvVisits.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvVisits ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Visit visit = visits.get(position);
                        Fragment Fragment_Record_Info   =  new DoctorVisitInfo();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView3, Fragment_Record_Info, "TAG")
                                .commit();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("visit",  visit);  // Key, value
                        Fragment_Record_Info.setArguments(bundle);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
    private void fillAdapter(){

        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        DoctorUserApi api = networkService.getDoctorUserApi();

        Call<List<Visit>> call = api.getAllMyVisits(auth);

        call.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {

                visits = response.body();
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rvVisits.setHasFixedSize(true);
                rvVisits.setLayoutManager(manager);
                adapter = new DoctorVisitAdapter(getContext(), visits);
                rvVisits.setAdapter(adapter);
                System.out.println("test!!!!!!!");
            }

            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


    @Override
    public void recyclerviewOnClick(int position) {

    }
}
