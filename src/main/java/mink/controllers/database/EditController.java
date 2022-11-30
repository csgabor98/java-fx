package mink.controllers.database;

import javafx.beans.value.ChangeListener;
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

public class EditController implements Stageable {

    Stage stage;
    Session session;
    List<Player> players;
    List<Post> posts;
    List<Club> clubs;
    @FXML
    TextField firstname, lastname, value, jersey;
    @FXML
    DatePicker birthdate;

    @FXML
    ChoiceBox<Post> postChoiceBox;
    @FXML
    ChoiceBox<Club> clubChoiceBox;

    @FXML
    ChoiceBox<Player> playerChoiceBox;

    ToggleGroup group;
    @FXML
    RadioButton rbHun;
    @FXML RadioButton rbInter;

    Player player;

    @FXML void initialize() {
        group = new ToggleGroup();
        rbHun.setToggleGroup(group);
        rbInter.setToggleGroup(group);

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        session = factory.openSession();
        Transaction t = session.beginTransaction();

        clubs =  session.createQuery("FROM Club").list();
        posts = session.createQuery("FROM Post").list();
        players = session.createQuery("FROM Player").list();

        ObservableList clubNames = FXCollections.observableArrayList();

        for (Club club : clubs) {
            clubNames.add(club);
        }

        ObservableList postNames = FXCollections.observableArrayList();

        for (Post post : posts) {
            postNames.add(post);
        }

        ObservableList playerNames = FXCollections.observableArrayList();

        for (Player player : players) {
            playerNames.add(player);
        }

        player = (Player) session.createQuery("FROM Player p WHERE p.id = :id").setParameter("id", players.get(0).getId()).getSingleResult();

        t.commit();

        postChoiceBox.getItems().addAll(postNames);
        clubChoiceBox.getItems().addAll(clubNames);
        playerChoiceBox.getItems().addAll(playerNames);

        firstname.setText(player.getFirstName());
        lastname.setText(player.getLastName());
        value.setText(String.valueOf(player.getValue()));
        jersey.setText(String.valueOf(player.getJersey()));
        birthdate.setValue(player.getBirthDate().toLocalDate());

        int selectedPost = 1;
        int i = 0;
        for (Post post1 : posts) {
            if(post1.getId() == player.getPostID()){
                selectedPost = i;
            }
            i++;
        }

        int selectedClub = 1;
        i = 0;
        for (Club club1 : clubs) {
            if(club1.getId() == player.getClubID()){
                selectedClub = i;
            }
            i++;
        }

        postChoiceBox.getSelectionModel().select(selectedPost);
        clubChoiceBox.getSelectionModel().select(selectedClub);

        if(player.getIsHungarian() == 1){
            rbHun.setSelected(true);
        } else {
            rbInter.setSelected(true);
        }

        playerChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
        (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            changePlayer(new_val.intValue());
        });

    }

    @FXML public void savePlayer(ActionEvent event) {
        if (
            !firstname.getText().trim().isEmpty() ||
            !lastname.getText().trim().isEmpty() ||
            !jersey.getText().trim().isEmpty() ||
            !value.getText().trim().isEmpty()
        ) {

            player.setJersey(Integer.parseInt(jersey.getText()));
            player.setFirstName(firstname.getText());
            player.setLastName(lastname.getText());
            java.sql.Date sqlDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date utilDate = formatter.parse(birthdate.getValue().toString());
                sqlDate = new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            player.setBirthDate(sqlDate);
            player.setValue(Integer.parseInt(value.getText()));
            player.setPostID(postChoiceBox.getValue().getId());
            player.setClubID(clubChoiceBox.getValue().getId());
            player.setIsHungarian(rbHun.isSelected() ? 1 : 0);

            Transaction t = session.beginTransaction();

            session.update(player);

            t.commit();
        } else {
            System.out.println("Valami nem jó!");
        }
    }

    private void changePlayer(int index){
        Transaction t = session.beginTransaction();

        player = (Player) session.createQuery("FROM Player p WHERE p.id = :id").setParameter("id", players.get(index).getId()).getSingleResult();

        t.commit();

        firstname.setText(player.getFirstName());
        lastname.setText(player.getLastName());
        value.setText(String.valueOf(player.getValue()));
        jersey.setText(String.valueOf(player.getJersey()));
        birthdate.setValue(player.getBirthDate().toLocalDate());

        int selectedPost = 1;
        int i = 0;
        for (Post post1 : posts) {
            if(post1.getId() == player.getPostID()){
                selectedPost = i;
            }
            i++;
        }

        int selectedClub = 1;
        i = 0;
        for (Club club1 : clubs) {
            if(club1.getId() == player.getClubID()){
                selectedClub = i;
            }
            i++;
        }

        postChoiceBox.getSelectionModel().select(selectedPost);
        clubChoiceBox.getSelectionModel().select(selectedClub);

        if(player.getIsHungarian() == 1){
            rbHun.setSelected(true);
        } else {
            rbInter.setSelected(true);
        }

    }

    @Override
    public void setStage(Stage stage) {

    }
}
