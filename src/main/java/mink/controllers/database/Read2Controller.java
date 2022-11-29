package mink.controllers.database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.util.List;

public class Read2Controller implements Stageable {

    Stage stage;
    SessionFactory factory;
    Session session;
    @FXML RadioButton rdHun;
    @FXML RadioButton rdInter;
    @FXML TextField tfName;
    @FXML ComboBox cbPosts;
    @FXML TableView tvRead;
    ToggleGroup group;
    @FXML private TableColumn<Club, String> idCol;
    @FXML private TableColumn<Club, String> jerseyCol;
    @FXML private TableColumn<Club, String> clubCol;
    @FXML private TableColumn<Club, String> postCol;
    @FXML private TableColumn<Club, String> firstNameCol;
    @FXML private TableColumn<Club, String> lastNameCol;
    @FXML private TableColumn<Club, String> birthDateCol;
    @FXML private TableColumn<Club, String> isHungarianCol;
    @FXML private TableColumn<Club, String> valueCol;

    @FXML void initialize() {
        group = new ToggleGroup();
        rdHun.setToggleGroup(group);
        rdInter.setToggleGroup(group);

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        session = factory.openSession();

        getAllPosts();
    }

    void getAllPosts() {
        Transaction t = session.beginTransaction();

        List<Post> Posts = session.createQuery("FROM Post").list();

        cbPosts.getItems().clear();
        for (Post post : Posts) {
            cbPosts.getItems().add(post.getName());
        }

        t.commit();
    }

    @FXML void btSearchClick(ActionEvent event) {

        String hql = "";
        if (!tfName.getText().trim().isEmpty()) {
            if (hql != "") hql += " AND";
            hql += " P.firstName = :name OR P.lastName = :name";
        }

        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        String radioBtnValue = selectedRadioButton.getText();
        if (radioBtnValue == "Magyar") {
            if (hql != "") hql += " AND";
            hql += " P.magyar == 1";
        }
        if (radioBtnValue == "Kűlföldi") {
            if (hql != "") hql += " AND";
            hql += " P.magyar == 0";
        }

        if (hql != "") {
            hql = "FROM Player P WHERE" + hql;
        } else {
            hql = "FROM Player";
        }

        System.out.println(hql);
        Query query = session.createQuery(hql);

        if (!tfName.getText().trim().isEmpty()) {
            query.setParameter("name", tfName.getText().trim());
        }

        List<Player> players = query.list();

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

        for (Player player : players) {
            tvRead.getItems().add(player);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
