package org.bonestudio.networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Resp
{
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("error")
    @Expose
    private String error;

    public boolean isStatus() {
        return status;
    }
    public String getError() { return error; }
}
