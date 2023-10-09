package com.example.eta;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Objects;

import com.example.eta.UserView;

public class TileView {

    @FXML
    AnchorPane tilePane;

    @FXML
    MFXButton addBtn;

    @FXML
    Label timeLabel;

    @FXML
    Label charityID;

    @FXML
    Label itemName;

    @FXML
    private void addAction() {
        HelloApplication.priorityStatic.dequeue();
    }

    public void setTile(Routes routes, double time, boolean add) {
        if (add) {
            addBtn.setVisible(false);
            addBtn.setDisable(true);
        }
        itemName.setText(routes.getItem());
        charityID.setText(routes.getCharity());
        timeLabel.setText(String.valueOf(time));
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
    }
}
