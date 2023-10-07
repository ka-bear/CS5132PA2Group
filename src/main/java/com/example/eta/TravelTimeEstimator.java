package com.example.eta;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class TravelTimeEstimator {

    private static final String API_KEY = "5b3ce3597851110001cf62481becab6d226a4e669a8e157108c069af";
    private static final String GEOCODING_URL = "https://api.openrouteservice.org/geocode/search?text=Singapore+";
    private static final String DIRECTIONS_URL = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

    public static void main(String[] args) {
        try {
            System.out.println(getTravelTime("639798", "138602", "119963", "486025"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getTravelTime(String postalA, String postalX, String postalY, String postalB) throws Exception {
        String[] postals = {postalA, postalX, postalY, postalB};
        String[] coordinates = new String[4];

        // Geocode each postal code to get coordinates
        for (int i = 0; i < postals.length; i++) {
            URL url = new URL(GEOCODING_URL + postals[i] + "&api_key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
            double lon = jsonResponse.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getDouble(0);
            double lat = jsonResponse.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getDouble(1);
            coordinates[i] = lon + "," + lat;
        }

        // Calculate travel time for each segment
        double totalSeconds = 0;
        for (int i = 0; i < coordinates.length - 1; i++) {
            URL url = new URL(DIRECTIONS_URL + "?start=" + coordinates[i] + "&end=" + coordinates[i + 1] + "&api_key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
            totalSeconds += jsonResponse.getJSONObject("features").getJSONObject(0).getJSONObject("properties").getDouble("duration");
        }

        return totalSeconds / 60; 
    }
}

