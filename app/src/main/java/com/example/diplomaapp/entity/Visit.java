package com.example.diplomaapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class Visit implements Parcelable {


    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("record_day")
    private Date record_day;


    @Expose
    @SerializedName("user")
    private User user;

    @Expose
    @SerializedName("doctor")
    private Doctor doctor;

    @Expose
    @SerializedName("record")
    private Record record;

    @Expose
    @SerializedName("result")
    private String result;


    public Visit() {
    }

    protected Visit(Parcel in) {
        id = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        doctor = in.readParcelable(Doctor.class.getClassLoader());
        record = in.readParcelable(Record.class.getClassLoader());
        result = in.readString();
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", record_day=" + record_day +
                ", user=" + user +
                ", doctor=" + doctor +
                ", record=" + record +
                ", result='" + result + '\'' +
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
        parcel.writeParcelable(record, i);
        parcel.writeString(result);
    }
}
