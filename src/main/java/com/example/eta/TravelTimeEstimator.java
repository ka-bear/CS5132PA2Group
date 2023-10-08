package com.example.eta;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.Route;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteParameters;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteResult;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteTask;
import com.esri.arcgisruntime.tasks.networkanalysis.Stop;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;



public class TravelTimeEstimator {

    



    public static void main(String[] args) {
        try {
            System.out.println(getTravelTime(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getTravelTime(ObservableList<Stop> routeStops) throws Exception {
            RouteResult result;
            RouteParameters routeParameters;
            RouteTask routeTask;
            routeTask = new RouteTask("https://route-api.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World");
            ListenableFuture<RouteParameters> routeParametersFuture = routeTask.createDefaultParametersAsync();


            routeParameters.setStops(routeStops);
            ListenableFuture<RouteResult> routeResultFuture = routeTask.solveRouteAsync(routeParameters);
            routeResultFuture.addDoneListener(() -> {
                try {
                    result = routeResultFuture.get();
                    List<Route> routes = result.getRoutes();
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            
            });

                return result.getRoutes().get(0).getTravelTime();
}

