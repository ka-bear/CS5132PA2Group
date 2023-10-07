package com.example.eta;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class UserView {

    @FXML
    private AnchorPane userPane;

    @FXML
    private GridPane gridPane;

    public void initialize() throws IOException {
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
