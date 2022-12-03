package mink.controllers.rest2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mink.models.Club;
import mink.models.Player;
import mink.models.Stageable;
import mink.models.Todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ReadController implements Stageable {

    private Stage stage;

    @FXML TableView tvPlayer;
    @FXML private TableColumn<Club, String> idCol;
    @FXML private TableColumn<Club, String> jerseyCol;
    @FXML private TableColumn<Club, String> clubIdCol;
    @FXML private TableColumn<Club, String> postIdCol;
    @FXML private TableColumn<Club, String> firstNameCol;
    @FXML private TableColumn<Club, String> lastNameCol;
    @FXML private TableColumn<Club, String> birthDateCol;
    @FXML private TableColumn<Club, String> isHungarianCol;
    @FXML private TableColumn<Club, String> valueCol;

    @FXML void initialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        URL url = new URL("https://java-spring-beadando.azurewebsites.net/api/players");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer jsonResponseData = new StringBuffer();
        String readLine = null;
        while ((readLine = in.readLine()) != null)
            jsonResponseData.append(readLine);
        in.close();

        List<Player> players = objectMapper.readValue( jsonResponseData.toString(), new TypeReference<List<Player>>(){});

        tvPlayer.getColumns().removeAll(tvPlayer.getColumns());

        idCol = new TableColumn<>("Id");
        jerseyCol = new TableColumn<>("Mezszám");
        clubIdCol = new TableColumn<>("Klub Id");
        postIdCol = new TableColumn<>("Poszt Id");
        firstNameCol = new TableColumn<>("Keresztnév");
        lastNameCol = new TableColumn<>("Vezetéknév");
        birthDateCol = new TableColumn<>("Született");
        isHungarianCol = new TableColumn<>("Magyar?");
        valueCol = new TableColumn<>("Érték");

        tvPlayer.getColumns().addAll(idCol, jerseyCol, clubIdCol, postIdCol, firstNameCol, lastNameCol, birthDateCol, isHungarianCol, valueCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        jerseyCol.setCellValueFactory(new PropertyValueFactory<>("jersey"));
        clubIdCol.setCellValueFactory(new PropertyValueFactory<Club, String>("clubID"));
        postIdCol.setCellValueFactory(new PropertyValueFactory<>("postID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        isHungarianCol.setCellValueFactory(new PropertyValueFactory<>("isHungarian"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        tvPlayer.getItems().clear();

        for (Player player : players) {
            System.out.println(player.toString());
            tvPlayer.getItems().add(player);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
