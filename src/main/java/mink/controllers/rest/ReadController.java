package mink.controllers.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mink.models.Club;
import mink.models.Player;
import mink.models.Stageable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import mink.models.Todo;
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String url = "https://gorest.co.in/public/v2/todos";
        List<Todo> todos = objectMapper.readValue( new URL(url), new TypeReference<List<Todo>>(){});

        tvTodos.getColumns().removeAll(tvTodos.getColumns());

        idCol = new TableColumn<>("Id");
        userIdCol = new TableColumn<>("Felhasználó Id");
        titleCol = new TableColumn<>("Cím");
        dueOnCol = new TableColumn<>("Lejárati dátum");
        statusCol = new TableColumn<>("Státusz");

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
}
