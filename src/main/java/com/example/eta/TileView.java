package com.example.eta;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.example.eta.UserView;
import javafx.scene.paint.Color;

public class TileView {

    @FXML
    AnchorPane tilePane;

    @FXML
    public MFXButton addBtn;

    @FXML
    Label timeLabel;

    @FXML
    Label charityID;

    @FXML
    Label itemName;

    Routes routes;

    public void setTile(Routes routes, double time, boolean add) {
        if (!add) {
            addBtn.setVisible(false);
            addBtn.setDisable(true);
        }
        itemName.setText(routes.getItem());
        charityID.setText(routes.getCharity());
        timeLabel.setText(String.valueOf(time));
        this.routes = routes;
    }

    @FXML
    private void mouseEnteredTile(MouseEvent event) {
        if (!Objects.equals(tilePane.getStyle(), "-fx-background-color: #32a852; -fx-border-color: #eee")) tilePane.setStyle("-fx-background-color: #0c6fa2; -fx-border-color: #eee");
    }

    @FXML
    private void mouseExitedTile(MouseEvent event) {
        if (!Objects.equals(tilePane.getStyle(), "-fx-background-color: #32a852; -fx-border-color: #eee")) tilePane.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
    }

    @FXML
    private void tilePaneOnClick(MouseEvent event) throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        tilePane.setStyle("-fx-background-color: #fff; -fx-border-color: #eee");
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Thread Error");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (Node tile : tilePane.getParent().getChildrenUnmodifiable()) {
                            tile.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
                        }
                        tilePane.setStyle("-fx-background-color: #32a852; -fx-border-color: #eee");
                    }
                });
            }
        });
        thread.start();

        UserView.graphicsOverlay.getGraphics().clear();
        RouteTask routeTask = new RouteTask("https://route-api.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World");
        ListenableFuture<RouteParameters> routeParametersFuture = routeTask.createDefaultParametersAsync();
        routeParametersFuture.addDoneListener(() -> {
            try {
                UserView.routeParameters = routeParametersFuture.get();

                if (UserView.routeStops.size() == 2) {
                    UserView.routeStops.add(1, new Stop(new Point(routes.getToLocation()[0],routes.getToLocation()[1],SpatialReference.create(102100))));
                    UserView.routeStops.add(2, new Stop(new Point(routes.getFromLocation()[0],routes.getFromLocation()[1],SpatialReference.create(102100))));
                } else {
                    UserView.routeStops.remove(1);
                    UserView.routeStops.remove(2);
                    UserView.routeStops.add(1, new Stop(new Point(routes.getToLocation()[0],routes.getToLocation()[1],SpatialReference.create(102100))));
                    UserView.routeStops.add(2, new Stop(new Point(routes.getFromLocation()[0],routes.getFromLocation()[1],SpatialReference.create(102100))));
                }
                SimpleMarkerSymbol stopMarker = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 20);
                UserView.routeParameters.setStops(UserView.routeStops);
                ListenableFuture<RouteResult> routeResultFuture = routeTask.solveRouteAsync(UserView.routeParameters);
                routeResultFuture.addDoneListener(() -> {
                    try {
                        RouteResult result = routeResultFuture.get();
                        List<Route> routes = result.getRoutes();
                        if (!routes.isEmpty()) {
                            Route route = routes.get(0);
                            Geometry shape = route.getRouteGeometry();
                            Graphic routeGraphic = new Graphic(shape, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2));
                            UserView.graphicsOverlay.getGraphics().add(routeGraphic);

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
