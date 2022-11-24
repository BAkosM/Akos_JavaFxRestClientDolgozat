package com.example.akos_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateDogeController extends Controller {
    @FXML
    private TextField nameField;
    @FXML
    private TextField wtddField;
    @FXML
    private Spinner<Integer> warcrimesField;
    @FXML
    private Button submitButton;
    @FXML
    private CheckBox yes;
    @FXML
    private CheckBox no;

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 0);
        warcrimesField.setValueFactory(valueFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String wtdd = wtddField.getText().trim();
        int warcrimes = warcrimesField.getValue();
        boolean gay = false;
        if (name.isEmpty()) {
            warning("Name is required");
            return;
        }
        if ((yes.isSelected() && no.isSelected() ) || (yes.isSelected() == false && no.isSelected() == false)) {
            warning("Egyet válassz!");
            return;
        }else if (yes.isSelected()){
            gay = true;
        } else if (no.isSelected()) {
            gay = false;
        }
        Doge newDoge = new Doge(0, name, wtdd, warcrimes, gay);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newDoge);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                warning("Doge added!");
                nameField.setText("");
                wtddField.setText("");
                warcrimesField.getValueFactory().setValue(0);
            } else {
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
