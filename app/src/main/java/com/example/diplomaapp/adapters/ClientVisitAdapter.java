package com.example.diplomaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.entity.Visit;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClientVisitAdapter extends RecyclerView.Adapter<ClientVisitAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Visit> visits;

    public ClientVisitAdapter(Context context, List<Visit> visits) {
        this.visits = visits;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ClientVisitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.client_visit_list_item, parent, false);
        return new ViewHolder(view);
    }

    public Visit getItem(int position){
        return visits.get(position);
    }

    @Override
    public void onBindViewHolder(ClientVisitAdapter.ViewHolder holder, int position) {
        Visit visit = visits.get(position);
        if (visit.getRecord_day() != null){
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s = formatter.format(visit.getRecord_day());
            holder.visitDateView.setText(s);
            System.out.println(s);
        }

        holder.visitDoctorView.setText(visit.getDoctor().getUser().getFullName());
        holder.visitIdView.setText(String.valueOf(position + 1));
               // holder.visitTimeView.setText(visit.getVisit_time().toString());


    }

    @Override
    public int getItemCount() {
        return visits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView visitIdView, visitDoctorView, visitDateView;
        ViewHolder(View view){
            super(view);
            visitIdView = view.findViewById(R.id.tvClientVisitId);
            visitDoctorView = view.findViewById(R.id.tvClientVisitDoctor);
            visitDateView = view.findViewById(R.id.tvClientVisitDate);
        }
    }



}