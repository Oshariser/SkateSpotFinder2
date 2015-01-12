package com.example.iem.skatespotfinder;

import android.net.Uri;
import android.util.Log;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/9/15.
 */
public class Spots {
    private static final String TAG = "Spots";
    public static List<Spot> mSpots = new ArrayList<Spot>();

    public static void getRemoteSpots() {
        ParseQuery query = new ParseQuery("Spot");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> aList, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Retrieved " + aList.size() + " scores");
                    fillSpots(aList);
                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public static void fillSpots(List<ParseObject> aList) {
        for(ParseObject item : aList) {
            mSpots.add(parseObjectToSpot(item));
        }

        //mSpots.addAll(aList);
        Log.i(TAG, mSpots.get(0).getDescription());
    }

    public static Spot parseObjectToSpot(ParseObject aParseObject) {
        Spot lSpot = new Spot(
                aParseObject.getParseGeoPoint("localisation").getLatitude(),
                aParseObject.getParseGeoPoint("localisation").getLongitude(),
                parseFileToFile(aParseObject.getParseFile("photo")),
                aParseObject.getInt("rating"),
                aParseObject.getString("description")
        );
        return lSpot;
    }

    public static File parseFileToFile(ParseFile aParseFile) {
        return new File("Pic.jpg");
    }
}
