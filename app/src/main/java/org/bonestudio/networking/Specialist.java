package org.bonestudio.networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Specialist
{
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
