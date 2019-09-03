package org.bonestudio.networking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements DataAdapter.DataAdapterListener
{
    private OnFragmentInteractionListener mListener;

    private DataAdapter dataAdapter;

    private List<Request> requests = new ArrayList<>();
    private List<Request> filteredRequests = new ArrayList<>();
    private String[] spinnerItems;
    private String[] statusValues;
    private HashMap<String, String> spinnerMap = new HashMap<>();

    public ListFragment()
    {

    }

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

        setInitialData();
        filterList(0);

        spinnerItems = getActivity().getResources().getStringArray(R.array.spinnerItems);
        statusValues = getActivity().getResources().getStringArray(R.array.statusValues);

        setupSpinner();

        RecyclerView recyclerView = getActivity().findViewById(R.id.list);
        dataAdapter = new DataAdapter(getActivity(), filteredRequests, spinnerMap);
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
    public void OnListItemClick(DataAdapter.ViewHolder viewHolder)
    {
        Toast.makeText(getActivity().getApplicationContext(), viewHolder.titleView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void setupSpinner()
    {
        if (spinnerItems.length != statusValues.length)
        {
            throw new IndexOutOfBoundsException("Arrays of spinner items and status values must be equals in length!");
        }
        for (int i = 0; i < spinnerItems.length; i++)
        {
            spinnerMap.put(statusValues[i], spinnerItems[i]);
        }

        final Spinner spinner = getActivity().findViewById(R.id.spFilter);
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
                filterList(i);
                Log.i("Tag", Integer.toString(filteredRequests.size()));
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void filterList(int i)
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

    private void setInitialData()
    {
        NetworkService.getInstance()
                .getJSONApi()
                .getRequests()
                .enqueue(new Callback<List<Request>>()
                {
                    @Override
                    public void onResponse(@NonNull Call<List<Request>> call, @NonNull Response<List<Request>> response) {
                        requests.addAll(response.body());
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
                        filteredRequests.addAll(requests);

                        dataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Request>> call, @NonNull Throwable t) {
                        Log.i("Tag", "Fail");
                    }
                });
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
