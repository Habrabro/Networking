package org.bonestudio.networking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("fragment") == null)
        {
            ListFragment listFragment = ListFragment.newInstance("1", "2");
            fragmentManager.beginTransaction()
                    .add(R.id.container, listFragment, "fragment")
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}
