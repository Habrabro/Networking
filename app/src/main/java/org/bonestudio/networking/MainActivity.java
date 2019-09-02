package org.bonestudio.networking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DataAdapter.DataAdapterListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("fragment") == null)
        {
            ListFragment listFragment = ListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.container, listFragment, "fragment")
                    .commit();
        }
    }

    @Override
    public void OnListItemClick(DataAdapter.ViewHolder viewHolder)
    {
        Toast.makeText(this,viewHolder.titleView.getText(), Toast.LENGTH_SHORT).show();
    }
}
