package mink.controllers.database;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mink.models.Club;
import mink.models.Player;
import mink.models.Post;
import mink.models.Stageable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DeleteController implements Stageable {

    Stage stage;
    Session session;
    List<Player> players;
    @FXML
    ChoiceBox<Player> playerChoiceBox;

    Player player;

    @FXML
    void initialize() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        session = factory.openSession();
        Transaction t = session.beginTransaction();

        players = session.createQuery("FROM Player").list();

        ObservableList playerNames = FXCollections.observableArrayList();

        for (Player player : players) {
            playerNames.add(player);
        }

        player = (Player) session.createQuery("FROM Player p WHERE p.id = :id").setParameter("id", players.get(0).getId()).getSingleResult();

        t.commit();

        playerChoiceBox.getItems().addAll(playerNames);

        playerChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
        (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            changePlayer(new_val.intValue());
        });

    }

    @FXML public void deletePlayer(ActionEvent event) {
        Transaction t = session.beginTransaction();

        session.delete(player);

        t.commit();
    }

    private void changePlayer(int index){
        Transaction t = session.beginTransaction();

        player = (Player) session.createQuery("FROM Player p WHERE p.id = :id").setParameter("id", players.get(index).getId()).getSingleResult();

        t.commit();
    }

    @Override
    public void setStage(Stage stage) {

    }
}
