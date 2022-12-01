package mink.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mink.models.Stageable;
import mink.models.Todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;

public class CreateController implements Stageable {
    private Stage stage;

    @FXML private TextField id;
    @FXML private TextField user_id;
    @FXML private TextField title;
    @FXML private DatePicker dueOn;
    @FXML private TextField status;

    @FXML
    void initialize() {}

    @FXML public void saveTodo(ActionEvent event) throws IOException {
        URL url = new URL("https://gorest.co.in/public/v2/todos");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        Todo todo = new Todo(
                Integer.parseInt(id.getText().trim()),
                Integer.parseInt(user_id.getText().trim()),
                title.getText().trim(),
                new Date(dueOn.getValue().toEpochDay()),
                status.getText()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String todoJson = objectMapper.writeValueAsString(todo);

        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        wr.write(todoJson);
        wr.close();
        connection.connect();
        connection.disconnect();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
