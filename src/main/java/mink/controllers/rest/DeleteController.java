package mink.controllers.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import mink.models.RestUser;
import mink.models.Stageable;
import mink.models.Todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DeleteController implements Stageable {
    private Stage stage;
    private int deletedTodoId;

    @FXML ChoiceBox cbTodo;

    @FXML void initialize() throws IOException {
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

        cbTodo.getItems().clear();
        cbTodo.getItems().addAll(todosList);
        System.out.println(todosList);

        cbTodo.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    deletedTodoId = todos.get(new_val.intValue()).getId();
                });
    }

    @FXML public void refresh(ActionEvent event) throws IOException {
        System.out.println("REFRESH");
        getData();
    }
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void deleteTodo(ActionEvent event) throws IOException {
        URL url = new URL("https://gorest.co.in/public/v2/todos/"+ deletedTodoId +"?access-token=813826f7b1e79691bc07861aa811f490eb02cdfa4545d69224f67fbac87b8179");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

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
}
