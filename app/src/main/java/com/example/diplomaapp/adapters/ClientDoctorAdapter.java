package com.example.diplomaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.entity.Doctor;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClientDoctorAdapter extends RecyclerView.Adapter<ClientDoctorAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Doctor> doctors;

    public ClientDoctorAdapter(Context context, List<Doctor> doctors) {
        this.doctors = doctors;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ClientDoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.client_doctor_list_item, parent, false);
        return new ViewHolder(view);
    }

    public Doctor getItem(int position){
        return doctors.get(position);
    }

    @Override
    public void onBindViewHolder(ClientDoctorAdapter.ViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);


        holder.qualView.setText(doctor.getQualification());
        holder.idView.setText(String.valueOf(position + 1));
        holder.fullnameView.setText(doctor.getUser().getFullName());
        holder.expView.setText(String.valueOf(doctor.getExperience()));
               // holder.doctorTimeView.setText(doctor.getDoctor_time().toString());


    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView idView, fullnameView, expView, qualView;
        ViewHolder(View view){
            super(view);
            idView = view.findViewById(R.id.tvClientListDoctorId);
            fullnameView = view.findViewById(R.id.tvClientListDoctorFullname);
            expView = view.findViewById(R.id.tvClientListDoctorExperience);
            qualView = view.findViewById(R.id.tvClientListDoctorQualification);

        }
    }



}