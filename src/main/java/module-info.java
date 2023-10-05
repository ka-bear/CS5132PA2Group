module com.example.eta {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens com.example.eta to javafx.fxml;
    exports com.example.eta;
}