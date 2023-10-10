package com.example.eta;


import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class DialogController {
    @FXML
    MFXTextField charityTF;

    @FXML
    MFXTextField itemTF;

    @FXML
    MFXButton submitBtn;

    @FXML
    MFXButton cancelBtn;

    private RouteParameters routeParameters;

    public static ObservableList<Stop> routeStops = FXCollections.observableArrayList();

    @FXML
    private void cancelAction() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        AdminView.graphicsOverlayStatic.getGraphics().clear();
        AdminView.routeStopsStatic.clear();
        stage.close();
    }

    @FXML
    private void submitAction() throws Exception {
        ArcGISRuntimeEnvironment.setApiKey("AAPK0e766a9bd7694608894d44be143c92a0yuacC21QXleV1poxQa9beOnsFj257IueMvpErtPz8JMlApkdRm2ML1_iCO8WEMzU");
        Routes newRoute = new Routes(itemTF.getText(), charityTF.getText(), new double[]{AdminView.routeStopsStatic.get(1).getGeometry().getX(), AdminView.routeStopsStatic.get(1).getGeometry().getY()}, new double[]{AdminView.routeStopsStatic.get(0).getGeometry().getX(), AdminView.routeStopsStatic.get(0).getGeometry().getY()});


        RouteTask routeTask = new RouteTask("https://route-api.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World");
        ListenableFuture<RouteParameters> routeParametersFuture = routeTask.createDefaultParametersAsync();
        routeParametersFuture.addDoneListener(() -> {
            try {
                routeParameters = routeParametersFuture.get();
                routeParameters.setReturnDirections(true);

                double x1 = UserView.start.getX();
                double y1 = UserView.start.getY();
                double x2 = newRoute.getFromLocation()[0];
                double y2 = newRoute.getFromLocation()[1];
                double x3 = newRoute.getToLocation()[0];
                double y3 = newRoute.getToLocation()[1];
                double x4 = UserView.end.getX();
                double y4 = UserView.end.getY();
                Point start = new Point(x1,y1,SpatialReference.create(102100));
                Point end = new Point(x2,y2,SpatialReference.create(102100));
                Point three = new Point(x3,y3,SpatialReference.create(102100));
                Point four = new Point(x4,y4,SpatialReference.create(102100));
                routeStops.add(new Stop(start));
                routeStops.add(new Stop(end));
                routeStops.add(new Stop(three));
                routeStops.add(new Stop(four));


                routeParameters.setStops(routeStops);
                ListenableFuture<RouteResult> routeResultFuture = routeTask.solveRouteAsync(routeParameters);
                routeResultFuture.addDoneListener(() -> {
                    try {
                        
                        RouteResult result = routeResultFuture.get();
                        List<Route> routes = result.getRoutes();
                        if (!routes.isEmpty()) {
                            Route route = routes.get(0);
                            double t = route.getTravelTime();

                            HelloApplication.priorityStatic.enqueue(newRoute, -t);

                            Stage stage = (Stage) cancelBtn.getScene().getWindow();
                            AdminView.graphicsOverlayStatic.getGraphics().clear();
                            AdminView.routeStopsStatic.clear();
                            stage.close();
                        }



                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });




            } catch (Exception e2) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e2.toString());
                alert.show();
                e2.printStackTrace();
            }
        });







    }
}
