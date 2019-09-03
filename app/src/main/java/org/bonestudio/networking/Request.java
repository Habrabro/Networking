package org.bonestudio.networking;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;

public class Request extends AppCompatActivity
{
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("actual_time")
    @Expose
    private long actual_time;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("location")
    @Expose
    private String location;

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setActual_time(long actual_time) { this.actual_time = actual_time; }
    public void setStatus(String status) { this.status = status; }
    public void setLocation(String location) { this.location = location; }

    public long getId() { return id; }
    public String getRequestTitle() { return title; }
    public String getLocation() { return location; }
    public long getActualTime() { return actual_time; }
    public String getStatus() { return status; }


}
