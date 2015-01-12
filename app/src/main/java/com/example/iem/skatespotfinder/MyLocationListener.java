package com.example.iem.skatespotfinder;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by iem on 08/01/15.
 */
public class MyLocationListener implements LocationListener {

    public static double mLatitude;
    public static double mLongitude;

    @Override
    public void onLocationChanged(Location aLocation)
    {
        aLocation.getLatitude();
        aLocation.getLongitude();
        mLatitude=aLocation.getLatitude();
        mLongitude=aLocation.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider)
    {}
    @Override
    public void onProviderEnabled(String provider)
    {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {}
}
