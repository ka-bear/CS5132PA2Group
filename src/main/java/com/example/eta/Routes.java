package com.example.eta;

public class Routes {
    private String item;
    private String charity;
    private int time;
    private int[] toLocation;
    private int[] fromLocation;

    public Routes(String item, String charity, int time, int[] toLocation, int[] fromLocation) {
        this.item = item;
        this.charity = charity;
        this.time = time;
        if (toLocation.length == 2) {
            this.toLocation = toLocation;
        }
        if (fromLocation.length == 2) {
            this.fromLocation = fromLocation;
        }
    }

}
