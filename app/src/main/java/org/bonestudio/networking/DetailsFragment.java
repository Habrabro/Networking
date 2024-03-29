package org.bonestudio.networking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class DetailsFragment extends Fragment implements NetworkServiceListener, NetworkServiceListener.DetailsResponseReceiver
{
    private final static String ARG_PARAM_1 = "arg_param_1";
    private final static String ARG_PARAM_2 = "arg_param_2";

    private long id;
    private HashMap<String, String> statusMap = new HashMap<>();

    private NetworkService networkService;
    private TextView titleDetailsView, actualTimeDetailsView, locationDetailsView, statusDetailsView, descriptionView,
            specialistName;
    private LinearLayout specialistView;
    private Button startButton;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment()
    {

    }

    public static DetailsFragment newInstance(long id, HashMap<String, String> statusMap)
    {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_PARAM_1, id);
        bundle.putSerializable(ARG_PARAM_2, statusMap);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            id = getArguments().getLong(ARG_PARAM_1);
            statusMap = (HashMap<String, String>)getArguments().getSerializable(ARG_PARAM_2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        networkService = NetworkService.getInstance(this);
        networkService.getDescription(id);

        titleDetailsView = view.findViewById(R.id.tvTitleDetails);
        actualTimeDetailsView = view.findViewById(R.id.tvActualTimeDetails);
        locationDetailsView = view.findViewById(R.id.tvLocationDetails);
        statusDetailsView = view.findViewById(R.id.tvStatusDetails);
        descriptionView = view.findViewById(R.id.tvDescription);
        specialistView = view.findViewById(R.id.llSpecialist);
        specialistName = view.findViewById(R.id.tvSpecialistName);
        startButton = view.findViewById(R.id.btnStart);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SorryDialog sorryDialog = new SorryDialog();
                sorryDialog.show(getActivity().getSupportFragmentManager(), "sorryDialog");
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context)
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
    public void onDetailsResponseReceived(DetailsResponse response)
    {
        Request request = response.getData();

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String actualTime = simpleDate.format(request.getActualTime());

        titleDetailsView.setText(request.getTitle());
        actualTimeDetailsView.setText(actualTime);
        locationDetailsView.setText(request.getLocation());
        statusDetailsView.setText(statusMap.get(request.getStatus()));
        descriptionView.setText(request.getDescription());
        if (request.getStatus().equals(getActivity().getResources().getStringArray(R.array.statusValues)[1]))
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
                .make(getView(), getResources().getString(R.string.snackbarCheckNetworkConnection), Snackbar.LENGTH_INDEFINITE);
        snackbar
                .setAction(getResources().getString(R.string.snackbarButtonUpdate), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        networkService.getDescription(id);
                        snackbar.dismiss();
                    }
                })
                .show();
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
