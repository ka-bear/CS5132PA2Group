package com.example.eta;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.OpenStreetMapLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import com.esri.arcgisruntime.geometry.Geometry;

public class UserView {

    @FXML
    private AnchorPane userPane;

    @FXML
    private StackPane mapPane;

    @FXML
    private ImageView btnImage;

    @FXML
    private MFXButton multiBtn;

    @FXML
    private GridPane gridPane;

    MapView mapView;
    private Graphic routeGraphic;
    private RouteTask routeTask;
    private RouteParameters routeParameters;
    public static ObservableList<Stop> routeStops = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        ArcGISRuntimeEnvironment.setApiKey("AAPK0e766a9bd7694608894d44be143c92a0yuacC21QXleV1poxQa9beOnsFj257IueMvpErtPz8JMlApkdRm2ML1_iCO8WEMzU\n");
        var openStreetMapLayer = new OpenStreetMapLayer();

        // if the layer failed to load, display an alert
        openStreetMapLayer.addDoneLoadingListener(() -> {
            if (openStreetMapLayer.getLoadStatus() != LoadStatus.LOADED) {
                new Alert(Alert.AlertType.INFORMATION, "Open Street Map Layer failed to load").show();
            }
        });

        // create a map and set the open street map layer as its basemap
        ArcGISMap map = new ArcGISMap();
        map.setBasemap(new Basemap(openStreetMapLayer));

        // create a map view and set the map to it
        mapView = new MapView();
        mapView.setMap(map);

        // set a viewpoint on the map view
        mapView.setViewpointGeometryAsync(new Envelope(11531238.957102917, 133131.3018712258, 11580870.529434115, 166663.23881347742, SpatialReference.create(102100)));

        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        File f = new File("dailyPath.txt");
        if (f.exists()) {
            multiBtn.setText("Edit Route");
            btnImage.setImage(new Image("file:src/main/resources/com/example/eta/edit.png"));
            Scanner scan = new Scanner(f);
            Scanner scanline = new Scanner(scan.nextLine());
            int x1 = scanline.nextInt();
            int y1 = scanline.nextInt();
            int x2 = scanline.nextInt();
            int y2 = scanline.nextInt();

            routeTask = new RouteTask("https://route-api.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World");
            ListenableFuture<RouteParameters> routeParametersFuture = routeTask.createDefaultParametersAsync();
            routeParametersFuture.addDoneListener(() -> {
                try {
                    routeParameters = routeParametersFuture.get();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
                    alert.show();
                    e.printStackTrace();
                }
            });
            routeStops.addListener((ListChangeListener<Stop>) e -> {
                // tracks the number of stops added to the map, and use it to create graphic geometry and symbol text
                int routeStopsSize = routeStops.size();
                SimpleMarkerSymbol stopMarker = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 20);
                Geometry routeStopGeometry = routeStops.get(routeStopsSize-1).getGeometry();

                graphicsOverlay.getGraphics().add(new Graphic(routeStopGeometry, stopMarker));

            });
            routeStops.add(new Stop(new Point(x1,y1,SpatialReference.create(102100))));
            routeStops.add(new Stop(new Point(x2,y2,SpatialReference.create(102100))));
            routeParameters.setStops(routeStops);
            ListenableFuture<RouteResult> routeResultFuture = routeTask.solveRouteAsync(routeParameters);
            routeResultFuture.addDoneListener(() -> {
                try {
                    RouteResult result = routeResultFuture.get();
                    List<Route> routes = result.getRoutes();
                    if (!routes.isEmpty()) {
                        Route route = routes.get(0);
                        Geometry shape = route.getRouteGeometry();
                        routeGraphic = new Graphic(shape, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2));
                        graphicsOverlay.getGraphics().add(routeGraphic);

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });


        }

        mapPane.getChildren().add(mapView);

        setProducts();
    }

    private void setProducts() {
        int row = 0;
        for (int i = 0; i < 10; i++) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(HelloApplication.class.getResource("tile.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                TileView tileController = fxmlLoader.getController();
//                ProductCategory productCategory = inventory.getProductCode(((String)inventory.getInventoryList().get(i).get(0)).substring(0,2));
//                tileController.setTile(productCategory.getProduct(((String)inventory.getInventoryList().get(i).get(0))) ,(int) inventory.getInventoryList().get(i).get(1));
                gridPane.add(anchorPane, 0, row++);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
