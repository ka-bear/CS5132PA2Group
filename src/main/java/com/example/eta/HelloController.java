package com.example.eta;

import io.github.palexdev.mfxresources.fonts.IconsProviders;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML
    private StackPane centerStackPane;


    private AnchorPane adminPane;
    private AnchorPane userPane;

    @FXML
    private MFXRectangleToggleNode userButton;


    public void initialize() throws IOException {
        adminPane = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
        userPane = FXMLLoader.load(getClass().getResource("user-view.fxml"));
        centerStackPane.getChildren().addAll(adminPane, userPane);
        adminPane.setVisible(false);



    }

    @FXML
    private void userbuttonclick() {
        adminPane.setVisible(false);
        userPane.setVisible(true);
        System.out.println("user");
    }

    @FXML
    private void adminbuttonclick() {
        adminPane.setVisible(true);
        userPane.setVisible(false);
        System.out.println("admin");
    }





}