package com.example.iem.skatespotfinder;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import java.io.File;


public class AddSpotActivity extends Activity {

    private double mLatitude;
    private double mLongitude;
    private Uri mUri;
    private File mFile;
    private static final int TAKE_PICTURE = 1;
    private Button mButtonGetLocalisation;
    private TextView mTextViewLatitude;
    private TextView mTextViewLongitude;
    private ImageView mImageViewSpot;
    private Button mButtonBrowse;
    private RatingBar mRatingBarSpot;
    private EditText mEditTextDescription;
    private Button mButtonAddSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spot);
        mButtonGetLocalisation = (Button) findViewById(R.id.buttonGetLocalisation);
        mButtonGetLocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCurrentLocation();
                GpsTracker lGpsTracker = new GpsTracker(AddSpotActivity.this);
                if (lGpsTracker.canGetLocation()) {
                    mTextViewLatitude.setText("Latitude : " + String.valueOf(lGpsTracker.getLatitude()));
                    mTextViewLongitude.setText("Longitude : " + String.valueOf(lGpsTracker.getLongitude()));
                } else {
                    lGpsTracker.showSettingsAlert();
                }
            }
        });
        mTextViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        mTextViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
        mImageViewSpot = (ImageView)findViewById(R.id.imageViewSpot);
        mButtonBrowse = (Button) findViewById(R.id.buttonBrowse);
        mButtonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        mRatingBarSpot = (RatingBar)findViewById(R.id.ratingBarSpot);
        mEditTextDescription = (EditText)findViewById(R.id.editTextDescription);
        mButtonAddSpot = (Button)findViewById(R.id.buttonAddSpot);
        mButtonAddSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spot lSpot = new Spot(mLatitude, mLongitude, mFile, mRatingBarSpot.getRating(), mEditTextDescription.getText().toString());
                addSpot(lSpot);
            }
        });

        mTextViewLatitude.setText("Latitude : " + MyLocationListener.mLatitude);
        mTextViewLongitude.setText("Longitude : " + MyLocationListener.mLongitude);

    }

    private void takePicture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        mFile = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(mFile));
        mUri = Uri.fromFile(mFile);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_spot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSpot(Spot aSpot){
        Spots.mSpots.add(aSpot);
        ParseObject lParseObject = new ParseObject("Spot");
        lParseObject.put("localisation", new ParseGeoPoint(aSpot.getLatitude(), aSpot.getLongitude()));
        ParseFile lParseFile = new ParseFile("Pic.jpg", aSpot.getImage());
        lParseObject.put("photo", lParseFile);
        lParseObject.put("rating", aSpot.getRating());
        lParseObject.put("description", aSpot.getDescription());
        lParseObject.saveInBackground();
    }

    private void getCurrentLocation(){
        LocationManager lLocationManager = null;
        LocationListener lLocationListener;
        lLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lLocationListener = new MyLocationListener();
        lLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lLocationListener);
        if (lLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLatitude = MyLocationListener.mLatitude;
            mLongitude = MyLocationListener.mLongitude;
            mTextViewLatitude.setText("Latitude : " + mLatitude);
            mTextViewLongitude.setText("Longitude : " + mLongitude);
        } else {
            Toast.makeText(AddSpotActivity.this, "GPS is not turned on ...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    setImageSpot();
                }
        }
    }

    private void setImageSpot() {
        Uri lUri = mUri;
        getContentResolver().notifyChange(lUri, null);
        ContentResolver lContentResolver = getContentResolver();
        Bitmap lBitmap;
        try {
            lBitmap = MediaStore.Images.Media.getBitmap(lContentResolver, lUri);
            mImageViewSpot.setImageBitmap(lBitmap);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            Log.e("Camera", e.toString());
        }
    }

}