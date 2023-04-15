package com.example.diplomaapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

;


public class Role {
    @Expose
    @SerializedName("id")
    private Integer id;
    @Expose
    @SerializedName("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
// remaining getters and setters
}
