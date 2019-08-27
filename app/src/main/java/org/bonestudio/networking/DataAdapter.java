package org.bonestudio.networking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>
{
    private List<Request> requests;
    private LayoutInflater layoutInflater;
    private DataAdapterListener listener;

    DataAdapter(Context context, List<Request> requests)
    {
        this.requests = requests;
        this.layoutInflater = LayoutInflater.from(context);
        if (context instanceof DataAdapterListener)
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
        holder.titleView.setText(request.getRequestTitle());
        holder.actualTimeView.setText(request.getActualTime().toString());
        holder.locationView.setText(request.getLocation());
        holder.statusView.setText(request.getStatus().getString());
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
