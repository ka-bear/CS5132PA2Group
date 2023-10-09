package com.example.eta;

import com.esri.arcgisruntime.geometry.Point;
import org.json.JSONObject;

public class Routes {
    private String item;
    private String charity;
    private double[] toLocation;
    private double[] fromLocation;
    private Point toRoute;
    private Point fromRoute;

    public Point getToRoute() { return toRoute; }
    public Point getFromRoute() { return fromRoute; }
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

    public Routes(String item, String charity, double[] toLocation, double[] fromLocation, Point toRoute, Point fromRoute) {
        this.item = item;
        this.charity = charity;
        if (toLocation.length == 2) {
            this.toLocation = toLocation;
        }
        if (fromLocation.length == 2) {
            this.fromLocation = fromLocation;
        }
        this.toRoute = toRoute;
        this.fromRoute = fromRoute;
    }

}
