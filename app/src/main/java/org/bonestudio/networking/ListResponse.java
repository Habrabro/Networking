package org.bonestudio.networking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse extends Resp {

    @SerializedName("data")
    @Expose
    private List<Request> data = null;



    public List<Request> getData()
    {
        return data;
    }

    public void setData(List<Request> data)
    {
        this.data = data;
    }

}
