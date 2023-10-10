package com.example.eta;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.mfxresources.fonts.IconsProviders;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML
    private StackPane centerStackPane;


    private AnchorPane adminPane;
    private AnchorPane userPane;
    private MFXScrollPane tilePane;

    @FXML
    private MFXButton userBtn;

    @FXML
    private MFXButton adminBtn;

    public final static String csvName = System.getProperty("user.dir") + "routes.csv";

    UserView cont;

    public void initialize() throws IOException {
        adminPane = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("user-view.fxml"));
        userPane = loader.load();
        cont =  loader.getController();

        centerStackPane.getChildren().addAll(adminPane, userPane);
        adminPane.setVisible(false);
        userBtn.setDisable(true);
        userBtn.setStyle(
                "    -fx-text-fill: #fff;\n" +
                        "    -fx-background-color: #333;\n" +
                        "    -fx-opacity: 1.0;"
        );

        File f = new File(csvName);
        if (!f.exists()) {
            f.createNewFile();
        }
    }

    @FXML
    private void userbuttonclick() {
        adminPane.setVisible(false);
        userPane.setVisible(true);
        userBtn.setDisable(true);
        adminBtn.setDisable(false);
        //userBtn.setStyle("-fx-background-color: #333");
        userBtn.setStyle(
                "    -fx-text-fill: #fff;\n" +
                "    -fx-background-color: #333;\n" +
                "    -fx-opacity: 1.0;"
        );
        adminBtn.setStyle("");
        cont.setProducts();
    }

    @FXML
    private void adminbuttonclick() {
        adminPane.setVisible(true);
        userPane.setVisible(false);
        adminBtn.setDisable(true);
        userBtn.setDisable(false);
        //adminBtn.setStyle("-fx-background-color: #333");
        adminBtn.setStyle(
                "    -fx-text-fill: #fff;\n" +
                        "    -fx-background-color: #333;\n" +
                        "    -fx-opacity: 1.0;"
        );
        userBtn.setStyle("");
    }
}