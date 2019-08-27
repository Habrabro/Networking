package org.bonestudio.networking;

import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Request extends AppCompatActivity
{
    private String title, location;
    private Date actualTime;
    private Status status;

    public String getRequestTitle() { return title; }
    public String getLocation() { return location; }
    public Date getActualTime() { return actualTime; }
    public Status getStatus() { return status; }

    public Request(String title, Date actualTime, String location, Status status)
    {
        this.title = title;
        this.actualTime = actualTime;
        this.location = location;
        this.status = status;
    }
}

enum Status
{
    OPEN("open"),
    CLOSED("closed"),
    IN_PROGRESS("in_progress");
    private String string;
    Status(String string)
    {
        this.string = string;
    }
    public String getString() { return string; }
}
