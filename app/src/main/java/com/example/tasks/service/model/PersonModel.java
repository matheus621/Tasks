package com.example.tasks.service.model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class PersonModel {

    @SerializedName("token")
    private String token;
    @SerializedName("personKey")
    private String personKey;
    @SerializedName("name")
    private String name;
}
