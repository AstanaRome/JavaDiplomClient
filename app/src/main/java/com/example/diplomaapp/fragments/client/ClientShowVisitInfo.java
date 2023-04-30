package com.example.diplomaapp.fragments.client;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diplomaapp.R;
import com.example.diplomaapp.entity.Record;
import com.example.diplomaapp.entity.Visit;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;

public class ClientShowVisitInfo extends Fragment {
    private Visit visit;
    private String password;
    private String username;

    private TextView tvClientVisitInfoDate;
    private TextView tvClientVisitInfoDoctor;
    private TextView tvClientVisitInfoCommentary;


    public ClientShowVisitInfo(){
        super(R.layout.fragment_client_visit_show_info);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvClientVisitInfoDoctor = view.findViewById(R.id.tvClientVisitInfoDoctor_);
        tvClientVisitInfoDate = view.findViewById(R.id.tvClientVisitInfoDate_);
        tvClientVisitInfoCommentary = view.findViewById(R.id.tvClientVisitInfoResult_);
        visit = new Visit();

        Bundle bundle = getArguments();
        if (bundle != null) {
            visit = bundle.getParcelable("visit");
            username = bundle.getString("username");
            password = bundle.getString("password");
        }
        System.out.println(visit.getResult());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(visit.getRecord_day());
        tvClientVisitInfoDoctor.setText(visit.getDoctor().getUser().getFullName());
        tvClientVisitInfoDate.setText(s);
        tvClientVisitInfoCommentary.setText(visit.getResult());

    }




}
