package org.bonestudio.networking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resp {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<Request> data = null;

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public List<Request> getData()
    {
        return data;
    }

    public void setData(List<Request> data)
    {
        this.data = data;
    }

}
