package com.example.eta;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.OpenStreetMapLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.Stop;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AdminView {

    public MFXButton addBtn;
    public ImageView btnImage;
    public StackPane mapPane;
    public ScrollPane tllePane;
    public GridPane gridPane;
    public Text instructionText;
    @FXML
    private AnchorPane adminPane;
    MapView mapView;
    private GraphicsOverlay graphicsOverlay;
    public ObservableList<Stop> routeStops = FXCollections.observableArrayList();


    public void initialize() {
        ArcGISRuntimeEnvironment.setApiKey("AAPK0e766a9bd7694608894d44be143c92a0yuacC21QXleV1poxQa9beOnsFj257IueMvpErtPz8JMlApkdRm2ML1_iCO8WEMzU");
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

        graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        mapPane.getChildren().add(mapView);
    }

    public void addAction(ActionEvent actionEvent) throws IOException {
        if (addBtn.getText().equals("    Add Dropoff")) {
            addBtn.setText("    Submit Dropoff");
            addBtn.setDisable(true);
            addStopsOnMouseClicked();
            instructionText.setText("Select Pickup point");
            int initialSize = routeStops.size();

            routeStops.addListener((ListChangeListener<Stop>) e -> {
                // tracks the number of stops added to the map, and use it to create graphic geometry and symbol text
                int routeStopsSize = routeStops.size();
                System.out.println(routeStopsSize);
                // handle user interaction
                Color markerColor = Color.BLUE;
                if (routeStopsSize == initialSize + 0) {
                    return;
                } else if (routeStopsSize == initialSize + 1) {
                    System.out.println("one");
                    graphicsOverlay.getGraphics().clear();
                    markerColor = Color.RED;
                    instructionText.setText("Select Dropoff point");
                    //if (!directionsList.getItems().isEmpty())
                    //    directionsList.getItems().clear();
                    //directionsList.getItems().add("Click to add two points to the map to find a route.");
                } else if (routeStopsSize == initialSize + 2) {
                    markerColor = Color.GREEN;
                    instructionText.setText("");
                }
                // create a blue circle symbol for the stop
                SimpleMarkerSymbol stopMarker = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, markerColor, 20);
                // get the stop's geometry
                Geometry routeStopGeometry = routeStops.get(routeStopsSize - 1).getGeometry();

                graphicsOverlay.getGraphics().add(new Graphic(routeStopGeometry, stopMarker));

                if (routeStopsSize == initialSize + 2) {
                    // find the ROUTE
                    System.out.println("two");
                    // remove the mouse clicked event if two stops have been added
                    instructionText.setText("");
                    mapView.setOnMouseClicked(null);
                    addBtn.setDisable(false);



                }
            });
        } else if (addBtn.getText().equals("    Submit Dropoff")) {
//            Dialog<Results> dialog = new Dialog<>();
//            dialog.setTitle("");
//            dialog.setHeaderText("Specify details");
//            DialogPane dialogPane = dialog.getDialogPane();
//            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//            //dialogPane.setGraphic(new ImageView(new Image("file:src/main/resources/com/example/eta/logo.png")));
//
//
//            MFXTextField tf1 = new MFXTextField("");
//            tf1.setPromptText("Item");
//            MFXTextField tf2 = new MFXTextField("");
//            tf2.setPromptText("Charity");
//            dialogPane.setContent(new VBox(8, tf1, tf2));
//            dialog.setResultConverter((ButtonType button) -> {
//                if (button == ButtonType.OK) {
//                    return new Results(tf1.getText(), tf2.getText());
//                }
//                return null;
//            });
//            Optional<Results> optionalResult = dialog.showAndWait();
//            optionalResult.ifPresent((Results results) -> {
//                System.out.println(results.item + " " + results.charity);
//
//            });
//
//            Platform.runLater(() -> routeStops.clear());
//            graphicsOverlay.getGraphics().clear();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("dialog.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Add");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mapView.getScene().getWindow());
            stage.show();

        }
    }

    private void addStopsOnMouseClicked() {
        mapView.setOnMouseClicked(event -> {
            if (event.isStillSincePress() && event.getButton() == MouseButton.PRIMARY) {
                Point2D mapPoint = new Point2D(event.getX(), event.getY());
                routeStops.add(new Stop(mapView.screenToLocation(mapPoint)));
            }
        });
    }

    private static class Results {

        String item;
        String charity;

        public Results(String item, String charity) {
            this.item = item;
            this.charity = charity;
        }
    }



}
