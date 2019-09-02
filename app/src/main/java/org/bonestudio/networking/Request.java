package org.bonestudio.networking;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.HashMap;

public class Request extends AppCompatActivity
{
    private int id;
    private String title, location, status;
    private Date actualTime;

    public int getId() { return id; }
    public String getRequestTitle() { return title; }
    public String getLocation() { return location; }
    public Date getActualTime() { return actualTime; }
    public String getStatus() { return status; }

    public Request(int id, String title, Date actualTime, String location, String status)
    {
        this.id = id;
        this.title = title;
        this.actualTime = actualTime;
        this.location = location;
        this.status = status;
    }
}
