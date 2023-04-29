package com.example.diplomaapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class User implements Parcelable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("username")
    private String userName;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("role_id")
    private Role role_id;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("birthDate")
    //@JsonAdapter(DateJsonAdapter.class)

    private Date birthDate;

    @Expose
    @SerializedName("enabled")
    private boolean enabled;




    public User() {
    }

    public User(String userName, String email, String password, String firstName, Role role_id, String lastName, Date birthDate, boolean enabled) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.role_id = role_id;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.enabled = enabled;
    }

    public User(int id, String userName, String email, String password, String firstName, Role role_id, String lastName, Date birthDate, boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.role_id = role_id;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.enabled = enabled;
    }

    protected User(Parcel in) {
        id = in.readInt();
        userName = in.readString();
        email = in.readString();
        password = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        enabled = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Role getRole_id() {
        return role_id;
    }

    public void setRole_id(Role role_id) {
        this.role_id = role_id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(userName);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeByte((byte) (enabled ? 1 : 0));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", role_id=" + role_id +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", enabled=" + enabled +
                '}';
    }
}
