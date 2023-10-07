package com.example.eta;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;

import java.io.IOException;

public class TileView {

    @FXML
    AnchorPane tilePane;
    boolean selectec = false;

//    @FXML
//    private void addToCartOnClick(ActionEvent event) {
//        try {
//            MainController.shoppingCart.add(product);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText("Success");
//            alert.setTitle("Success");
//            alert.setContentText("Product Added To Cart");
//            alert.show();
//        } catch (ArrayStoreException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Cart Is Full");
//            alert.setTitle("Cart Is Full");
//            alert.setContentText("Cannot Add More To Cart");
//            alert.show();
//        }
//    }

//    public void setTile(Product product, int quantity) {
//        this.product = product;
//        itemName.setText(product.getName());
//        itemPrice.setText(String.format("$%.2f", product.getPrice()));
//        itemQuantity.setText(quantity + "");
//        productID.setText(product.getProductID());
//        itemImage.setImage(new Image("file:" + product.getImages().get(0)));
//    }

    @FXML
    private void mouseEnteredTile(MouseEvent event) {
        tilePane.setStyle("-fx-background-color: #0c6fa2; -fx-border-color: #eee");
    }

    @FXML
    private void mouseExitedTile(MouseEvent event) {
         tilePane.setStyle("-fx-background-color: #252525; -fx-border-color: #eee");
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
                        tilePane.setStyle("-fx-background-color: #138CD8; -fx-border-color: #eee");
                    }
                });
            }
        });
        thread.start();
    }
}
