package com.example.iem.skatespotfinder;

import android.app.Activity;
import android.app.ListActivity;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FavoriteActivity extends Activity {

    ListView mListViewFavoriteSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mListViewFavoriteSpots = (ListView)findViewById(R.id.listViewFavoriteSpots);
        ArrayAdapter<Spot> adapter = new Adapter(this,
                getSpots());
        mListViewFavoriteSpots.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Spot> getSpots() {
        List<Spot> list = new ArrayList<Spot>();
        /*list.add(new Spot(null, 2 ,"description 1"));
        list.add(new Spot(null, 4 ,"description 2"));
        list.add(new Spot(null, 6 ,"description 3"));
        */
        return Spots.mSpots;
    }
}
