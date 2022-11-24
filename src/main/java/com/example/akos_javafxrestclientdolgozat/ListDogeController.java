package com.example.akos_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class ListDogeController extends Controller {
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Doge> dogeTable;
    @FXML
    private TableColumn<Doge, String> nameCol;
    @FXML
    private TableColumn<Doge, Integer> wtddCol;
    @FXML
    private TableColumn<Doge, Integer> warcrimesCol;
    @FXML
    private TableColumn<Doge, Boolean> gayCol;

    @FXML
    private void initialize() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        wtddCol.setCellValueFactory(new PropertyValueFactory<>("wttd"));
        warcrimesCol.setCellValueFactory(new PropertyValueFactory<>("warcrimes"));
        gayCol.setCellValueFactory(new PropertyValueFactory<>("gay"));

        Platform.runLater(() -> {
            try {
                loadDogeFromServer();
            } catch (IOException e) {
                error("Couldn't get data from server",e.getMessage());
                Platform.exit();
            }
        });
    }
    private void loadDogeFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Doge[] doges = converter.fromJson(content, Doge[].class);
        dogeTable.getItems().clear();
        for (Doge doge : doges) {
            dogeTable.getItems().add(doge);
        }
    }
    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-doge-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create Doge");
            stage.setScene(scene);
            stage.show();
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnCloseRequest(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadDogeFromServer();
                } catch (IOException e) {
                    error("An error occurred while communicating with the server");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }
    @FXML
    public void updateClick(ActionEvent actionEvent) {
        int selectedIndex = dogeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Please select a doge from the list first");
            return;
        }
        Doge selected = dogeTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-doge-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Update Doge");
            stage.setScene(scene);
            UpdateDogeController controller = fxmlLoader.getController();
            controller.setDoge(selected);
            stage.show();
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnHidden(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadDogeFromServer();
                } catch (IOException e) {
                    error("An error occurred while communicating with the server");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }
    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectedIndex = dogeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("pls select a doge from a list");
            return;
        }

        Doge selected = dogeTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Are you sure want to delete %s?", selected.getName()));
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (optionalButtonType.isEmpty()) {
            System.out.println("Unknown error occurred");
            return;
        }
        ButtonType clickButton = optionalButtonType.get();
        if (clickButton.equals(ButtonType.OK)) {
            String url = App.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
            } catch (IOException e) {
                error("An error occurred while communicating with the server");
            }
        }
        try {
            loadDogeFromServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}