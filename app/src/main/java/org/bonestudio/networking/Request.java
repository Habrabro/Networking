package org.bonestudio.networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request
{
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("actual_time")
    @Expose
    private long actualTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("specialist")
    @Expose
    private Specialist specialist;

    public long getId() { return id; }
    public String getTitle() { return title; }
    public long getActualTime() { return actualTime; }
    public String getStatus() { return status; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public Specialist getSpecialist() { return specialist; }
}