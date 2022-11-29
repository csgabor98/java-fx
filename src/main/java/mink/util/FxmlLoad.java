package mink.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import mink.models.Stageable;
import mink.MainApp;

public class FxmlLoad {
    public Scene load(FxmlInfo fxmlInfo) {

        if (fxmlInfo.hasScene()) {
            return fxmlInfo.getScene();
        }

        URL url = getClass().getResource(fxmlInfo.getResourceName());

        if (url == null) {
            Platform.exit();
            return null;
        }

        FXMLLoader loader = new FXMLLoader(url);
        Scene scene;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
            return null;
        }

        // Write back the updated FxmlInfo to the scenes Map in Main
        fxmlInfo.setScene(scene);
        MainApp.updateScenes(fxmlInfo.getSceneName(), fxmlInfo);

        Stageable controller = loader.getController();
        if (controller != null) {
            controller.setStage(fxmlInfo.getStage());
        }

        return scene;
    }
}
