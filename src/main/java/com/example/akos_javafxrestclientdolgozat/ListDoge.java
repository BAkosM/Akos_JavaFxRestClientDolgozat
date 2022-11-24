package com.example.akos_javafxrestclientdolgozat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ListDoge {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}