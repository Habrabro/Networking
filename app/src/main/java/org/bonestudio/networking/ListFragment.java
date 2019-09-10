package org.bonestudio.networking;

import android.app.LauncherActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import icepick.Icepick;
import icepick.State;
import retrofit2.Call;
import retrofit2.Callback;

public class ListFragment extends Fragment implements NetworkServiceListener, NetworkServiceListener.ListResponseReceiver
{
    private OnFragmentInteractionListener mListener;

    private DataAdapter dataAdapter;
    private Spinner spinner;
    private NetworkService networkService;

    private List<Request> requests;
    private List<Request> filteredRequests = new ArrayList<>();
    private String[] spinnerItems;
    private String[] statusValues;
    private HashMap<String, String> statusMap = new HashMap<>();

    public ListFragment() { }

    public static ListFragment newInstance()
    {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        requests = new ArrayList<>();
        networkService = NetworkService.getInstance(this);
        requests.addAll(networkService.getRequests(false));
        sortList(requests);
        filterListByStatus(0);

        spinnerItems = getActivity().getResources().getStringArray(R.array.spinnerItems);
        statusValues = getActivity().getResources().getStringArray(R.array.statusValues);

        setupSpinner();

        RecyclerView recyclerView = getActivity().findViewById(R.id.rvList);
        dataAdapter = new DataAdapter(getActivity(), filteredRequests, statusMap);
        recyclerView.setAdapter(dataAdapter);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListResponseReceived(ListResponse response)
    {
        requests.addAll(response.getData());
        sortList(requests);
        filterListByStatus(spinner.getSelectedItemPosition());
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Resp response)
    {
        final Snackbar snackbar = Snackbar
                .make(getView(), response.getError(), Snackbar.LENGTH_INDEFINITE);
        snackbar
                .setAction("Ok", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        getActivity().getSupportFragmentManager().popBackStack();
                        snackbar.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onDisconnected()
    {
        final Snackbar snackbar = Snackbar
            .make(getView(), "Check internet connection!", Snackbar.LENGTH_INDEFINITE);
        snackbar
            .setAction("Update", new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                networkService.getRequests(true);
                snackbar.dismiss();
                }
            })
            .show();
    }

    private void setupSpinner()
    {
        if (spinnerItems.length != statusValues.length)
        {
            throw new IndexOutOfBoundsException("Arrays of spinner items and status values must be equals in length!");
        }
        for (int i = 0; i < spinnerItems.length; i++)
        {
            statusMap.put(statusValues[i], spinnerItems[i]);
        }

        spinner = getActivity().findViewById(R.id.spFilter);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                spinnerItems);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                filterListByStatus(i);
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void sortList(List<Request> requests)
    {
        Collections.sort(requests, new Comparator<Request>()
        {
            @Override
            public int compare(Request o1, Request o2)
            {
                if (o1.getActualTime() < o2.getActualTime()) { return -1; }
                if (o1.getActualTime() > o2.getActualTime()) { return 1; }
                return 0;
            }
        });
    }

    private void filterListByStatus(int i)
    {
        filteredRequests.clear();
        if (i != 0)
        {
            for (Request request : requests)
            {
                if (request.getStatus().equals(statusValues[i]))
                {
                    filteredRequests.add(request);
                }
            }
        }
        else
        {
            filteredRequests.addAll(requests);
        }
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
