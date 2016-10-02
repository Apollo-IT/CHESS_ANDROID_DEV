package com.app.hrms.utils.gps;

import java.io.Serializable;

public class Position implements Serializable {
    public double latitude = 0.0f;
    public double longitude = 0.0f;

    public Position() {

    }

    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
