module com.example.akos_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.akos_javafxrestclientdolgozat to javafx.fxml, com.google.gson;
    exports com.example.akos_javafxrestclientdolgozat;
}