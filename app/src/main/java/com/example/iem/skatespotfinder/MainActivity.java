package com.example.iem.skatespotfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private ImageButton mImageButtonMap;
    private ImageButton mImageButtonAddSpot;
    private ImageButton mImageButtonFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "QTQlI0JRr2ZqU22Zfekt22saXkWfNwqd0eUS3tJD", "d7w6ruFuWmLLlShtxdqe898ttl0FkJqTtgNtN2cE");

        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);

        Spots.getRemoteSpots();

        mImageButtonMap = (ImageButton)findViewById(R.id.imageButtonMap);
        mImageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntentMap = new  Intent(getApplicationContext(), MapsActivity.class);
                startActivity(lIntentMap);
            }
        });

        mImageButtonAddSpot = (ImageButton)findViewById(R.id.imageButtonAddSpot);
        mImageButtonAddSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntentAddSpot = new  Intent(getApplicationContext(), AddSpotActivity.class);
                startActivity(lIntentAddSpot);
            }
        });

        mImageButtonFavorite = (ImageButton)findViewById(R.id.imageButtonFavorite);
        mImageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntentFavorite = new  Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(lIntentFavorite);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                ParseUser lCurrentUser = ParseUser.getCurrentUser();
                if(lCurrentUser != null) {
                    Toast.makeText(this, lCurrentUser.getUsername(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Error ParseUser is null", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "You must be logged on !", Toast.LENGTH_LONG).show();
                ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
                startActivityForResult(builder.build(), 0);*/
                finish();
            }
        }
    }
}
