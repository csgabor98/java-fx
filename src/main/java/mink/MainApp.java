package mink;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Stage;

import mink.models.SceneName;
import mink.util.FxmlInfo;

public class MainApp extends Application {
    private static final String SCENE_MAIN = "/mink/main-view.fxml";
    private static final String SCENE_DATABASE_READ = "/mink/database/read-view.fxml";
    private static final String SCENE_DATABASE_READ2 = "/mink/database/read2-view.fxml";
    private static final String SCENE_DATABASE_WRITE = "/mink/database/write-view.fxml";
    private static final String SCENE_REST = "/mink/rest-view.fxml";

    private static Map<SceneName, FxmlInfo> scenes = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        scenes.put(SceneName.MAIN, new FxmlInfo(SCENE_MAIN, SceneName.MAIN, stage));
        scenes.put(SceneName.DATABASE_READ, new FxmlInfo(SCENE_DATABASE_READ, SceneName.DATABASE_READ, stage));
        scenes.put(SceneName.DATABASE_READ2, new FxmlInfo(SCENE_DATABASE_READ2, SceneName.DATABASE_READ2, stage));
        scenes.put(SceneName.DATABASE_WRITE, new FxmlInfo(SCENE_DATABASE_WRITE, SceneName.DATABASE_WRITE, stage));
        scenes.put(SceneName.REST, new FxmlInfo(SCENE_REST, SceneName.REST, stage));

        stage.setScene(scenes.get(SceneName.MAIN).getScene());
        stage.setTitle("JavaFX beadando");
        stage.show();
    }

    public static Map<SceneName, FxmlInfo> getScenes() {
        return scenes;
    }

    public static void updateScenes(SceneName name, FxmlInfo info) {
        scenes.put(name, info);
    }
}