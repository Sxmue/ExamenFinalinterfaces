module com.example.examenfinaldesarrollointerfaces {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires lombok;
    requires java.desktop;


    opens com.example.examenfinaldesarrollointerfaces to javafx.fxml;
    exports com.example.examenfinaldesarrollointerfaces;
}