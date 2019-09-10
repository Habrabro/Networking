package org.bonestudio.networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsResponse extends Resp {

    @SerializedName("data")
    @Expose
    private Request data;

    public Request getData() {
        return data;
    }
}
