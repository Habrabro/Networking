package org.bonestudio.networking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class DetailsFragment extends Fragment implements NetworkServiceListener, NetworkServiceListener.DescriptionResponseReceiver
{
    private final static String ARG_PARAM = "arg_param";

    private long id;
    private HashMap<String, String> statusMap = new HashMap<>();

    private NetworkService networkService;
    private TextView titleDetailsView, actualTimeDetailsView, locationDetailsView, statusDetailsView, descriptionView,
            specialistName;
    private LinearLayout specialistView;
    private Button startButton;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment(long id, HashMap<String, String> statusMap)
    {
        this.id = id;
        this.statusMap = statusMap;
    }

    public static DetailsFragment newInstance(long id, HashMap<String, String> statusMap)
    {
        DetailsFragment fragment = new DetailsFragment(id, statusMap);
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        networkService = NetworkService.getInstance(this);
        networkService.getDescription(id);

        titleDetailsView = getActivity().findViewById(R.id.tvTitleDetails);
        actualTimeDetailsView = getActivity().findViewById(R.id.tvActualTimeDetails);
        locationDetailsView = getActivity().findViewById(R.id.tvLocationDetails);
        statusDetailsView = getActivity().findViewById(R.id.tvStatusDetails);
        descriptionView = getActivity().findViewById(R.id.tvDescription);
        specialistView = getActivity().findViewById(R.id.llSpecialist);
        specialistName = getActivity().findViewById(R.id.tvSpecialistName);
        startButton = getActivity().findViewById(R.id.btnStart);
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
    public void onDescriptionResponseReceived(DetailsResponse response)
    {
        Request request = response.getData();

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String actualTime = simpleDate.format(request.getActualTime());

        titleDetailsView.setText(request.getTitle());
        actualTimeDetailsView.setText(actualTime);
        locationDetailsView.setText(request.getLocation());
        statusDetailsView.setText(statusMap.get(request.getStatus()));
        descriptionView.setText(request.getDescription());
        if (request.getStatus() == getActivity().getResources().getStringArray(R.array.statusValues)[1])
        {
            startButton.setVisibility(View.VISIBLE);
        }
        else
        {
            if (request.getSpecialist() != null)
            {
                String fName = request.getSpecialist().getFirstName();
                String lName = request.getSpecialist().getLastName();
                specialistName.setText(fName + " " + lName);
                specialistView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onError(Resp response)
    {

    }

    @Override
    public void onDisconnected()
    {

    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
