package mink.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import mink.MainApp;
import mink.models.SceneName;
import mink.models.Stageable;

public class MenuController implements Stageable {
    @FXML private MenuBar menuBar;
    private Stage stage;

    @FXML public void menuDatabaseReadClick(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.DATABASE_READ).getScene());
    }

    @FXML public void menuDatabaseRead2Click(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.DATABASE_READ2).getScene());
    }

    @FXML public void menuDatabaseWriteClick(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.DATABASE_WRITE).getScene());
    }

    @FXML public void menuDatabaseEditClick(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.DATABASE_EDIT).getScene());
    }
    @FXML public void menuDatabaseDeleteClick(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.DATABASE_DELETE).getScene());
    }

    @FXML public void menuRestCreateClick(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.REST_CREATE).getScene());
    }
    @FXML public void menuRestReadClick(ActionEvent event) {
        stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(MainApp.getScenes().get(SceneName.REST_READ).getScene());
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
