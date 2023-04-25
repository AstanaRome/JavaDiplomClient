package com.example.diplomaapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Doctor implements Parcelable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("qualification")
    private String qualification;

    @Expose
    @SerializedName("experience")
    private int experience;


    @Expose
    @SerializedName("user")
    private User user;


    public Doctor() {
    }

    protected Doctor(Parcel in) {
        id = in.readInt();
        qualification = in.readString();
        experience = in.readInt();
    }



    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(qualification);
        parcel.writeInt(experience);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", qualification='" + qualification + '\'' +
                ", experience=" + experience +
                ", user=" + user +
                '}';
    }
}
