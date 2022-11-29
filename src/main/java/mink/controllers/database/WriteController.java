package mink.controllers.database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import mink.models.Club;
import mink.models.Player;
import mink.models.Post;
import mink.models.Stageable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class WriteController implements Stageable {

    Stage stage;
    Session session;

    @FXML
    TextField firstname, lastname, value, jersey;
    @FXML
    DatePicker birthdate;

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
            p.setBirthDate(birthdate.getValue().toString());
            p.setValue(Integer.parseInt(value.getText()));
            p.setPostID(1);
            p.setClubID(1);
            p.setIsHungarian(true);

            session.persist(p);
        } else {
            System.out.println("Valami nem jó!");
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
