package com.example.diplomaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.entity.Record;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Record> records;

    public RecordAdapter(Context context, List<Record> records) {
        this.records = records;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.record_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position) {
        Record record = records.get(position);
        if (record.getRecord_day() != null){
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String s = formatter.format(record.getRecord_day());
            holder.recordDayView.setText(s);
            System.out.println(s);
        }
        if (record.getRecord_time() != null){
            Format formatter = new SimpleDateFormat("HH:mm:ss");
            System.out.println(record.getRecord_time().toString());
            String s = formatter.format(record.getRecord_time());
            holder.recordTimeView.setText(s);
        }

        System.out.println("Proverka!!!!!!!!!!!!!!!!");
        holder.recordIdView.setText(String.valueOf(record.getId()));
                holder.recordDoctorView.setText(record.getDoctor().getUser().getFullName());
        if (record.getUser() != null){
            holder.recordPacientView.setText(record.getUser().getFullName());
        }
               // holder.recordTimeView.setText(record.getRecord_time().toString());


    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recordIdView, recordDoctorView, recordDayView, recordTimeView, recordPacientView;
        ViewHolder(View view){
            super(view);
            recordIdView = view.findViewById(R.id.tv_record_list_id);
            recordDoctorView = view.findViewById(R.id.tv_record_list_doctor);
            recordDayView = view.findViewById(R.id.tv_record_list_day);
            recordTimeView = view.findViewById(R.id.tv_record_list_time);
            recordPacientView = view.findViewById(R.id.tv_record_list_client);
        }
    }



}