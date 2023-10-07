package com.example.eta;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.layers.OpenStreetMapLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.io.File;
import java.io.IOException;

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
        mapView.setViewpoint(new Viewpoint(34.056295, -117.195800, 577790));

        // add the map view to stack pane
        mapPane.getChildren().add(mapView);

        File f = new File("dailyPath.txt");
        if (f.exists()) {
            multiBtn.setText("Edit Route");
            btnImage.setImage(new Image("file:src/main/resources/com/example/eta/edit.png"));
        }
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
