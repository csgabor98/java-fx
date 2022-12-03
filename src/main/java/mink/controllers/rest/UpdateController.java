package mink.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mink.models.Player;
import mink.models.RestUser;
import mink.models.Stageable;
import mink.models.Todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UpdateController implements Stageable {
    private Stage stage;
    private int selectedTodoId;

    @FXML private ChoiceBox cbTodo;
    @FXML private TextField title;
    @FXML private DatePicker dueOn;
    @FXML private RadioButton rbPending;
    @FXML private RadioButton rbCompleted;

    @FXML
    void initialize() throws IOException {
        getData();
    }

    private void getData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        URL url = new URL("https://gorest.co.in/public/v2/users/"+ RestUser.getUser().getId() +"/todos?access-token=813826f7b1e79691bc07861aa811f490eb02cdfa4545d69224f67fbac87b8179");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer jsonResponseData = new StringBuffer();
        String readLine = null;
        while ((readLine = in.readLine()) != null)
            jsonResponseData.append(readLine);
        in.close();

        List<Todo> todos = objectMapper.readValue( jsonResponseData.toString(), new TypeReference<List<Todo>>(){});

        ObservableList todosList = FXCollections.observableArrayList();
        todosList.removeAll();

        for (Todo todo : todos) {
            todosList.add(todo);
        }

        System.out.println(todosList);

        cbTodo.getItems().clear();
        cbTodo.getItems().addAll(todosList);

        cbTodo.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    changeTodo(todos.get(new_val.intValue()));
                });
    }

    @FXML public void refresh(ActionEvent event) throws IOException {
        getData();
    }

    private void changeTodo(Todo todo) {
        selectedTodoId = todo.getId();
        title.setText(todo.getTitle());
        if (todo.getDue_on() != null) {
            dueOn.setValue(todo.getDue_on().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            dueOn.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }


    }

    @FXML public void updateTodo(ActionEvent event) throws IOException {
        ToggleGroup group = new ToggleGroup();
        rbPending.setToggleGroup(group);
        rbCompleted.setToggleGroup(group);

        URL url = new URL("https://gorest.co.in/public/v2/todos/"+ selectedTodoId +"?access-token=813826f7b1e79691bc07861aa811f490eb02cdfa4545d69224f67fbac87b8179");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
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
