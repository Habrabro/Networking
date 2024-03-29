package org.bonestudio.networking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>
{
    private LayoutInflater layoutInflater;
    private DataAdapterListener listener;
    private List<Request> requests;
    private HashMap<String, String> statusMap;

    DataAdapter(Context context, List<Request> requests, HashMap<String, String> statusMap)
    {
        this.requests = requests;
        this.statusMap = statusMap;
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

        holder.titleView.setText(request.getTitle());
        holder.actualTimeView.setText(actualTime);
        holder.locationView.setText(request.getLocation());
        holder.statusView.setText(statusMap.get(request.getStatus()));
    }

    @Override
    public int getItemCount()
    {
        return requests.size();
    }

    public interface DataAdapterListener
    {
        void OnListItemClick(ViewHolder viewHolder, Request request, HashMap<String, String> statusMap);
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
            listener.OnListItemClick(this, requests.get(getAdapterPosition()), statusMap);
        }
    }
}
