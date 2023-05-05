package com.example.diplomaapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class Record implements Parcelable {


    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("record_day")
    private Date record_day;

    @Expose
    @SerializedName("record_time")
    private Time record_time;


    @Expose
    @SerializedName("user")
    private User user;

    @Expose
    @SerializedName("doctor")
    private Doctor doctor;

    @Expose
    @SerializedName("enabled")
    private boolean enabled;


    protected Record(Parcel in) {
        id = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        doctor = in.readParcelable(Doctor.class.getClassLoader());
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecord_day() {
        return record_day;
    }

    public void setRecord_day(Date record_day) {
        this.record_day = record_day;
    }

    public Time getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Time record_time) {
        this.record_time = record_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Record() {
    }

    public Record(int id, Date record_day, Time record_time, User user, Doctor doctor, boolean enabled) {
        this.id = id;
        this.record_day = record_day;
        this.record_time = record_time;
        this.user = user;
        this.doctor = doctor;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Record(Date record_day, Time record_time, User user, Doctor doctor, boolean enabled) {
        this.record_day = record_day;
        this.record_time = record_time;
        this.user = user;
        this.doctor = doctor;
        this.enabled = enabled;
    }

    //    public Record(Date record_day, LocalTime record_time, User user, Doctor doctor) {
//        this.record_day = record_day;
//        this.record_time = record_time;
//        this.user = user;
//        this.doctor = doctor;
//    }
//
//    public Record(int id, Date record_day, LocalTime record_time, User user, Doctor doctor) {
//        this.id = id;
//        this.record_day = record_day;
//        this.record_time = record_time;
//        this.user = user;
//        this.doctor = doctor;
//    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", record_day=" + record_day +
                ", record_time=" + record_time +
                ", user=" + user +
                ", doctor=" + doctor +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(doctor, i);
    }
}
