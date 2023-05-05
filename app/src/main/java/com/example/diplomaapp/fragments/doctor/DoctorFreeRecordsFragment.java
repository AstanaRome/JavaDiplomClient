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
import com.example.diplomaapp.adapters.RecordAdapter;
import com.example.diplomaapp.api.ClientApi;
import com.example.diplomaapp.api.DoctorUserApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.fragments.client.ClientCustomDialogFragment;
import com.example.diplomaapp.test.ClickInterface;
import com.example.diplomaapp.test.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorFreeRecordsFragment extends Fragment implements ClickInterface {

    public DoctorFreeRecordsFragment(){
        super(R.layout.fragment_doctor_list_records);
    }
    private RecyclerView rvRecords;
    private RecordAdapter adapter;
    private List<Record> records;

    private FloatingActionButton btnDoctorAddRecord;

    private String password;
    private String username;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDoctorAddRecord = view.findViewById(R.id.btnDoctorAddRecord);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
        }

        rvRecords = view.findViewById(R.id.rvList);

        fillAdapter();




        btnDoctorAddRecord.setOnClickListener(this::addRecordsFragment);



    }





    private void addRecordsFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        Fragment Fragment_Records=new DoctorAddRecordFragment();
        Fragment_Records.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, Fragment_Records, "TAG")
                .commit();
    }







    private void fillAdapter(){

        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        DoctorUserApi api = networkService.getDoctorUserApi();

        Call<List<Record>> call = api.getAllFreeRecords(auth);

        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                records = response.body();

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rvRecords.setHasFixedSize(true);
                rvRecords.setLayoutManager(manager);
                adapter = new RecordAdapter(getContext(), records);
                rvRecords.setAdapter(adapter);
                System.out.println("test!!!!!!!");
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


    @Override
    public void recyclerviewOnClick(int position) {

    }
}
