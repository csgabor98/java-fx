package mink.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import mink.MainApp;
import mink.models.SceneName;
import mink.models.Stageable;

public class MainController implements Stageable {
    private Stage stage;

    @FXML private void handleOnActionClose(ActionEvent event) {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
