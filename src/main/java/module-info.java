module com.example.akos_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.akos_javafxrestclientdolgozat to javafx.fxml;
    exports com.example.akos_javafxrestclientdolgozat;
}