package com.example.eta;

import com.esri.arcgisruntime.geometry.Point;
import org.json.JSONObject;

public class Routes {
    private String item;
    private String charity;
    private double[] toLocation;
    private double[] fromLocation;

    public String getItem() {
        return item;
    }

    public String getCharity() {
        return charity;
    }

    public double[] getToLocation() {
        return toLocation;
    }

    public double[] getFromLocation() {
        return fromLocation;
    }

    public Routes(String item, String charity, double[] toLocation, double[] fromLocation) {
        this.item = item;
        this.charity = charity;
        if (toLocation.length == 2) {
            this.toLocation = toLocation;
        }
        if (fromLocation.length == 2) {
            this.fromLocation = fromLocation;
        }
    }

}
