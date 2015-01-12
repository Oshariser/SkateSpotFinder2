package com.example.iem.skatespotfinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by root on 1/8/15.
 */
public class Spot {
    private double mLatitude;
    private double mLongitude;
    private byte[] mImage;
    private float mRating;
    private String mDescription;

    public Spot(double aLatitude, double aLongitude, File aImage, float aRating, String aDescription) {
        this.setLatitude(aLatitude);
        this.setLongitude(aLongitude);
        this.setRating(aRating);
        this.setDescription(aDescription);
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(File mImage) {
        byte[] lByte = new byte[(int) mImage.length()];
        try {
            FileInputStream lFileInputStream = new FileInputStream(mImage);
            lFileInputStream.read(lByte);
            lFileInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.mImage = lByte;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float mRating) {
        this.mRating = mRating;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
