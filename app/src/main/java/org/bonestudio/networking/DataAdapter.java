package org.bonestudio.networking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>
{
    private LayoutInflater layoutInflater;
    private DataAdapterListener listener;
    private List<Request> requests;
    private HashMap<String, String> spinnerMap;

    DataAdapter(Context context, List<Request> requests, HashMap<String, String> spinnerMap)
    {
        this.requests = requests;
        this.spinnerMap = spinnerMap;
        this.layoutInflater = LayoutInflater.from(context);
        if (context instanceof DataAdapter.DataAdapterListener)
        {
            listener = (DataAdapterListener)context;
        }
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position)
    {
        Request request = requests.get(position);

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String actualTime = simpleDate.format(request.getActualTime());

        holder.titleView.setText(request.getRequestTitle());
        holder.actualTimeView.setText(actualTime);
        holder.locationView.setText(request.getLocation());
        holder.statusView.setText(spinnerMap.get(request.getStatus()));
    }

    @Override
    public int getItemCount()
    {
        return requests.size();
    }

    public interface DataAdapterListener
    {
        void OnListItemClick(ViewHolder viewHolder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        final TextView titleView, actualTimeView, locationView, statusView;

        ViewHolder(View view)
        {
            super(view);
            titleView = view.findViewById(R.id.tvTitle);
            actualTimeView = view.findViewById(R.id.tvActualTime);
            locationView = view.findViewById(R.id.tvLocation);
            statusView = view.findViewById(R.id.tvStatus);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener.OnListItemClick(this);
        }
    }
}
