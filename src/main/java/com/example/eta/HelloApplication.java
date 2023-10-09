package com.example.eta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {
    public static PriorityQueue<Routes, Double> priorityStatic = new PriorityQueue<Routes, Double>(10);
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image("file:src/main/resources/com/example/eta/logo.png"));
        stage.setResizable(false);
        stage.setTitle("ETA");
        stage.setScene(scene);
        stage.show();

        File f = new File(HelloController.csvName);

        Scanner scan = new Scanner(f);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] vals = line.split(",");
            String item = vals[0], charity = vals[1];
            Double x1 = Double.parseDouble(vals[2]),
                    y1 = Double.parseDouble(vals[3]),
                    x2 = Double.parseDouble(vals[4]),
                    y2 = Double.parseDouble(vals[5]),
                    t = Double.parseDouble(vals[6]);
            priorityStatic.enqueue(new Routes(item, charity, new double[]{x2, y2}, new double[]{x1, y1}), t);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}