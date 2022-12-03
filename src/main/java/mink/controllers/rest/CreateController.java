package mink.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import mink.models.RestUser;
import mink.models.Stageable;
import mink.models.Todo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;

public class CreateController implements Stageable {
    private Stage stage;

    @FXML private TextField title;
    @FXML private DatePicker dueOn;
    @FXML private RadioButton rbPending;
    @FXML private RadioButton rbCompleted;

    @FXML
    void initialize() {
        title.setText("");
        dueOn.setValue(null);
    }

    @FXML public void saveTodo(ActionEvent event) throws IOException {
        ToggleGroup group = new ToggleGroup();
        rbPending.setToggleGroup(group);
        rbCompleted.setToggleGroup(group);

        URL url = new URL("https://gorest.co.in/public/v2/users/"+ RestUser.getUser().getId() +"/todos?access-token=813826f7b1e79691bc07861aa811f490eb02cdfa4545d69224f67fbac87b8179");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        String status = selectedRadioButton.getText();

        String json = "{\"title\": \""+ title.getText() +"\", \"due_on\": \""+ dueOn.getValue()+" 23:59:00" +"\", \"status\": \""+ status +"\"}";
        System.out.println(json);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
