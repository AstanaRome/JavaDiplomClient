package com.example.diplomaapp.fragments.client;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diplomaapp.R;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.test.Removable;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientCustomDialogFragment extends DialogFragment {
    private String password;
    private String username;
    private Removable removable;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        removable = (Removable) context;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        Record record = getArguments().getParcelable("record");
        username = getArguments().getString("username");
        password = getArguments().getString("password");
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(record.getRecord_day());
        return builder
                .setTitle("Запись на прием")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Для записи на приеме " + " в "
                 + record.getRecord_time() + " " + date +
                    " к врачу "    + record.getDoctor().getUser().getFullName()
                    + "(" + record.getDoctor().getQualification() + ")"
                    + " нажмите добавить"

                )
                .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            updateRecord(record);
                            removable.remove();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
    private void updateRecord(Record record) throws ParseException {
        String auth = createAuthToken(username, password);
        NetworkService
                .getInstance()
                .getClientApi()
                .setFreeRecord(auth, record)
                .enqueue(new Callback<Record>() {
                    @Override
                    public void onResponse(Call<Record> call, Response<Record> response) {
                        Record buf = response.body();
                        //ShowAllRecordsFragment();
//                        Toast.makeText(getContext(), "Succesful",
//                                Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Record> call, Throwable t) {
                    }
                });
    }
//    private void ShowAllRecordsFragment() {
//        Bundle bundle = new Bundle();
//        bundle.putString("username", username);
//        bundle.putString("password", password);
//        Fragment Fragment_Records=new ShowAllRecordsFragmentClient();
//        Fragment_Records.setArguments(bundle);
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView2, Fragment_Records, "TAG")
//                .commit();
//    }
}
