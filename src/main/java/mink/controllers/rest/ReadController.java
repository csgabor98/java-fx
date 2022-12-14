package mink.controllers.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mink.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ReadController implements Stageable {

    private Stage stage;

    @FXML TableView tvTodos;
    @FXML private TableColumn<Todo, Integer> idCol;
    @FXML private TableColumn<Todo, Integer> userIdCol;
    @FXML private TableColumn<Todo, String> titleCol;
    @FXML private TableColumn<Todo, String> dueOnCol;
    @FXML private TableColumn<Todo, String> statusCol;

    @FXML void initialize() throws IOException {
        getData();
    }

    @FXML private void getData() throws IOException {
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

        tvTodos.getColumns().removeAll(tvTodos.getColumns());

        idCol = new TableColumn<>("Id");
        userIdCol = new TableColumn<>("Felhaszn??l?? Id");
        titleCol = new TableColumn<>("C??m");
        dueOnCol = new TableColumn<>("Lej??rati d??tum");
        statusCol = new TableColumn<>("St??tusz");

        tvTodos.getColumns().addAll(idCol, userIdCol, titleCol, dueOnCol, statusCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dueOnCol.setCellValueFactory(new PropertyValueFactory<>("due_on"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tvTodos.getItems().clear();

        for (Todo todo : todos) {
            System.out.println(todo.toString());
            tvTodos.getItems().add(todo);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void refresh(ActionEvent event) throws IOException {
        getData();
    }
}
