package com.example.rxjavaretrofit.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String name;
    @SerializedName("body")
    private String website;

    public User(int id, String name, String website) {
        this.id = id;
        this.name = name;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }
}
