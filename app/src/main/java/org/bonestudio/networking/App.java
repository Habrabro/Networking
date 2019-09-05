package org.bonestudio.networking;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class App extends Application
{
    private List<Request> requests = new ArrayList<>();

    private static final String BASE_URL = "https://glabstore.blob.core.windows.net/test/";
    Retrofit retrofit;
    ServerAPI serverAPI;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i("Tag", "App: hello!");
    }

    public static App from(Context context)
    {
        return (App)context.getApplicationContext();
    }

    public List<Request> getDataFromServer(final ListFragment listFragment, boolean updateData)
    {

        if (retrofit == null || updateData)
        {
            if (isNetworkConnected())
            {
                retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                serverAPI = retrofit.create(ServerAPI.class);
                serverAPI
                    .getRequests()
                    .enqueue(new Callback<Resp>()
                    {
                        @Override
                        public void onResponse(@NonNull Call<Resp> call, @NonNull Response<Resp> response)
                        {
                            requests.addAll(response.body().getData());
                            listFragment.onDataReceived(response.body());
                        }

                        @Override
                        public void onFailure(@NonNull Call<Resp> call, @NonNull Throwable t)
                        {
                            Log.i("Tag", "Fail");
                        }
                    });
            }
            else
            {
                listFragment.onDisconnected();
            }
        }
        return requests;
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public interface ServerAPI
    {
        @GET("list.json")
        Call<Resp> getRequests();
    }

    public interface NetworkServiceListener
    {
        void onDataReceived(Resp response);
        void onDisconnected();
    }
}
