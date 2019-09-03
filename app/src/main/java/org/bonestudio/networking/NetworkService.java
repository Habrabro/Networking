package org.bonestudio.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NetworkService
{
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://my-json-server.typicode.com/Habrabro/JSON_server/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public ServerAPI getJSONApi() {
        return mRetrofit.create(ServerAPI.class);
    }

    public interface ServerAPI
    {
        @GET("getRequests")
        Call<List<Request>> getRequests();
    }
}
