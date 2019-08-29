package org.bonestudio.networking;

import android.content.Context;
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
    OPEN(R.string.Request_status_open),
    CLOSED(R.string.Request_status_closed),
    IN_PROGRESS(R.string.Request_status_in_progress);
    private int stringId;
    Status(int stringId)
    {
        this.stringId = stringId;
    }
    public String getString(Context context)
    {
        return context.getResources().getString(stringId);
    }
}
