package org.bonestudio.networking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements DataAdapter.DataAdapterListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("ListFragment") == null)
        {
            ListFragment listFragment = ListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.container, listFragment, "ListFragment")
                    .commit();
        }
    }

    @Override
    public void OnListItemClick(DataAdapter.ViewHolder viewHolder, Request request, HashMap<String, String> statusMap)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailsFragment detailsFragment = DetailsFragment.newInstance(request.getId(), statusMap);
        fragmentManager.beginTransaction()
                .replace(R.id.container, detailsFragment, "DetailsFragment")
                .addToBackStack(null)
                .commit();
    }
}
