package com.example.akos_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateDogeController extends Controller{
    @FXML
    private TextField nameField;
    @FXML
    private TextField wtddField;

    private Doge doge;
    @FXML
    private Button updateButton;
    @FXML
    private Spinner<Integer> warcrimesField;
    @FXML
    private CheckBox yes;
    @FXML
    private CheckBox no;

    public void setDoge(Doge doge) {
        this.doge = doge;
        nameField.setText(this.doge.getName());
        wtddField.setText(this.doge.getWtdd());
        warcrimesField.getValueFactory().setValue(this.doge.getWarcrimes());
        if (this.doge.isGay()){
            yes.selectedProperty().setValue(true);
            no.selectedProperty().setValue(false);
        }else{
            yes.selectedProperty().setValue(false);
            no.selectedProperty().setValue(true);
        }
    }
    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 0);
        warcrimesField.setValueFactory(valueFactory);
    }
    @FXML
    public void updateClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String wtdd = wtddField.getText().trim();
        int warcrimes = warcrimesField.getValue();
        boolean gay = false;
        if (name.isEmpty()) {
            warning("Name is required");
            return;
        }
        if ((yes.isSelected() && no.isSelected() ) || (!yes.isSelected() && !no.isSelected())) {
            warning("Egyet válassz!");
            return;
        }else if (yes.isSelected()){
            gay = true;
        } else if (no.isSelected()) {
            gay = false;
        }
        this.doge.setName(name);
        this.doge.setWtdd(wtdd);
        this.doge.setWarcrimes(warcrimes);
        this.doge.setGay(gay);
        Gson converter = new Gson();
        String json = converter.toJson(this.doge);
        try {
            String url = App.BASE_URL + "/" + this.doge.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            } else {
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
