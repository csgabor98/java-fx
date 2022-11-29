package mink.controllers.database;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mink.models.Club;
import mink.models.Player;
import mink.models.Stageable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReadController implements Stageable {
    private Stage stage;

    @FXML private TableView tvRead;
    @FXML private TableColumn<Club, String> idCol;
    @FXML private TableColumn<Club, String> jerseyCol;
    @FXML private TableColumn<Club, String> clubCol;
    @FXML private TableColumn<Club, String> postCol;
    @FXML private TableColumn<Club, String> firstNameCol;
    @FXML private TableColumn<Club, String> lastNameCol;
    @FXML private TableColumn<Club, String> birthDateCol;
    @FXML private TableColumn<Club, String> isHungarianCol;
    @FXML private TableColumn<Club, String> valueCol;

    SessionFactory factory;

    @FXML void initialize() {
        setElementsVisibility(false);
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();

        showTable();
    }

    @FXML void showTable() {
        setElementsVisibility(true);
        tvRead.getColumns().removeAll(tvRead.getColumns());

        idCol = new TableColumn<>("Id");
        jerseyCol = new TableColumn<>("Mezszám");
        clubCol = new TableColumn<>("Klub");
        postCol = new TableColumn<>("Poszt");
        firstNameCol = new TableColumn<>("Keresztnév");
        lastNameCol = new TableColumn<>("Vezetéknév");
        birthDateCol = new TableColumn<>("Született");
        isHungarianCol = new TableColumn<>("Magyar?");
        valueCol = new TableColumn<>("Érték");

        tvRead.getColumns().addAll(idCol, jerseyCol, clubCol, postCol, firstNameCol, lastNameCol, birthDateCol, isHungarianCol, valueCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        jerseyCol.setCellValueFactory(new PropertyValueFactory<>("jersey"));
        clubCol.setCellValueFactory(new PropertyValueFactory<Club, String>("club"));
        postCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        isHungarianCol.setCellValueFactory(new PropertyValueFactory<>("isHungarian"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        tvRead.getItems().clear();

        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        List<Player> players = session.createQuery("FROM Player").list();

        for (Player player : players) {
            tvRead.getItems().add(player);
        }

        t.commit();
    }

    void setElementsVisibility(boolean value) {
        tvRead.setVisible(value);
        tvRead.setManaged(value);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
