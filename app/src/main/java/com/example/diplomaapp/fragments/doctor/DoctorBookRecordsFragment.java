package com.example.diplomaapp.fragments.doctor;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.adapters.RecordAdapter;
import com.example.diplomaapp.api.ClientApi;
import com.example.diplomaapp.api.DoctorUserApi;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.test.ClickInterface;
import com.example.diplomaapp.test.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorBookRecordsFragment extends Fragment implements ClickInterface {

    public DoctorBookRecordsFragment(){
        super(R.layout.fragment_list_records);
    }
    private RecyclerView rvRecords;
    private RecordAdapter adapter;
    private List<Record> records;

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

        rvRecords = view.findViewById(R.id.rvList);

        fillAdapter();

        rvRecords.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvRecords ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
//
//                        Fragment Fragment_Record_Info   =  new FullInfoRecordFragment();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragmentContainerView2, Fragment_Record_Info, "TAG")
//                                .commit();
//                        Record record = records.get(position);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("username", username);
//                        bundle.putString("password", password);
//                        bundle.putParcelable("record",  record);  // Key, value
//                        Fragment_Record_Info.setArguments(bundle);
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

        Call<List<Record>> call = api.getAllBookRecords(auth);

        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {

                records = response.body();
                System.out.println(records.size());
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
