package org.bonestudio.networking;

import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Request extends AppCompatActivity
{
    private String title, location;
    private Date actial_time;
    private Status status;

    public String get_title() { return title; }
    public String get_location() { return location; }
    public Date get_actial_time() { return actial_time; }
    public Status get_status() { return status; }

    public Request(String title, Date actial_time, String location, Status status)
    {
        this.title = title;
        this.actial_time = actial_time;
        this.location = location;
        this.status = status;
    }
}

enum Status
{
    OPEN(Resources.getSystem().getString(R.string.Request_status_open)),
    CLOSED(Resources.getSystem().getString(R.string.Request_status_closed)),
    IN_PROGRESS(Resources.getSystem().getString(R.string.Request_status_in_progress));
    private String string;
    Status(String string)
    {
        this.string = string;
    }
    public String getString() { return string; }
}
