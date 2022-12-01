package mink.controllers.database;

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
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteController implements Stageable {

    Stage stage;
    Session session;

    @FXML
    TextField firstname, lastname, value, jersey;
    @FXML
    DatePicker birthdate;

    @FXML
    ChoiceBox<Post> post;
    @FXML
    ChoiceBox<Club> club;

    ToggleGroup group;
    @FXML RadioButton rbHun;
    @FXML RadioButton rbInter;

    @FXML void initialize() {
        group = new ToggleGroup();
        rbHun.setToggleGroup(group);
        rbInter.setToggleGroup(group);

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        session = factory.openSession();
        Transaction t = session.beginTransaction();

        List<Club> clubs =  session.createQuery("FROM Club").list();
        List<Post> posts = session.createQuery("FROM Post").list();

        ObservableList clubNames = FXCollections.observableArrayList();

        for (Club club : clubs) {
            clubNames.add(club);
        }

        ObservableList postNames = FXCollections.observableArrayList();

        for (Post post : posts) {
            postNames.add(post);
        }

        t.commit();

        club.getItems().addAll(clubNames);

        post.getItems().addAll(postNames);
    }

    @FXML public void saveNewPlayer(ActionEvent event) {
        if (
            !firstname.getText().trim().isEmpty() ||
            !lastname.getText().trim().isEmpty() ||
            !jersey.getText().trim().isEmpty() ||
            !value.getText().trim().isEmpty()
        ) {
            Player p = new Player();
            p.setJersey(Integer.parseInt(jersey.getText()));
            p.setFirstName(firstname.getText());
            p.setLastName(lastname.getText());
            /*java.sql.Date sqlDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date utilDate = formatter.parse(birthdate.getValue().toString());
                sqlDate = new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }*/
            p.setBirthDate(birthdate.getValue().toString());
            p.setValue(Integer.parseInt(value.getText()));
            p.setPostID(post.getValue().getId());
            p.setClubID(club.getValue().getId());
            p.setIsHungarian(rbHun.isSelected());

            Transaction t = session.beginTransaction();

            session.save(p);

            t.commit();
        } else {
            System.out.println("Valami nem jó!");
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
