module com.example.eta {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.esri.arcgisruntime;
    requires org.slf4j.nop;
    requires org.json;
    opens com.example.eta to javafx.fxml;
    exports com.example.eta;
}