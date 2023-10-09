package com.example.eta;


import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class DialogController {
    @FXML
    MFXTextField charityTF;

    @FXML
    MFXTextField itemTF;

    @FXML
    MFXButton submitBtn;

    @FXML
    MFXButton cancelBtn;

    @FXML
    private void cancelAction() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        AdminView.graphicsOverlayStatic.getGraphics().clear();
        AdminView.routeStopsStatic.clear();
        stage.close();
    }

    @FXML
    private void submitAction() throws Exception {
        Routes newRoute = new Routes(itemTF.getText(), charityTF.getText(), new double[]{AdminView.routeStopsStatic.get(1).getGeometry().getX(), AdminView.routeStopsStatic.get(1).getGeometry().getY()}, new double[]{AdminView.routeStopsStatic.get(0).getGeometry().getX(), AdminView.routeStopsStatic.get(0).getGeometry().getY()});
        TravelTimeEstimator eta = new TravelTimeEstimator();
        double time = eta.getTravelTime(AdminView.routeStopsStatic);
        HelloApplication.priorityStatic.enqueue(newRoute, time);
        Writer output = new BufferedWriter(new FileWriter("routes.csv", true));
        output.append("\n" + newRoute.getItem() + "," + newRoute.getCharity() + "," + String.valueOf(newRoute.getToLocation()[0]) + "," + String.valueOf(newRoute.getToLocation()[1]) + "," + String.valueOf(newRoute.getFromLocation()[0]) + "," + String.valueOf(newRoute.getFromLocation()[1]) + "," + String.valueOf(time));
        output.close();
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        AdminView.graphicsOverlayStatic.getGraphics().clear();
        AdminView.routeStopsStatic.clear();
        stage.close();
    }
}
