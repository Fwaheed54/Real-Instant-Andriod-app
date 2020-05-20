package com.FYP.Adil.realinstant.OtherObjects;

public class LocalCoordinate {

    String Latitude;
    String Longitude;

    public LocalCoordinate(String latitude, String longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public LocalCoordinate() {
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
