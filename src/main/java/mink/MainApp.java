package mink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import mink.models.RestUser;
import mink.models.SceneName;
import mink.models.Todo;
import mink.models.User;
import mink.util.FxmlInfo;

public class MainApp extends Application {
    private static final String SCENE_MAIN = "/mink/main-view.fxml";
    private static final String SCENE_DATABASE_READ = "/mink/database/read-view.fxml";
    private static final String SCENE_DATABASE_READ2 = "/mink/database/read2-view.fxml";
    private static final String SCENE_DATABASE_WRITE = "/mink/database/write-view.fxml";
    private static final String SCENE_DATABASE_EDIT = "/mink/database/edit-view.fxml";
    private static final String SCENE_DATABASE_DELETE = "/mink/database/delete-view.fxml";
    private static final String SCENE_REST_CREATE = "/mink/rest/create-view.fxml";
    private static final String SCENE_REST_READ = "/mink/rest/read-view.fxml";
    private static final String SCENE_REST_UPDATE = "/mink/rest/update-view.fxml";
    private static final String SCENE_REST_DELETE = "/mink/rest/delete-view.fxml";
    private static final String SCENE_REST2_READ = "/mink/rest2/read-view.fxml";

    private static Map<SceneName, FxmlInfo> scenes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        makeUser();
        launch(args);
    }

    private static void makeUser() throws IOException {
        URL url = new URL("https://gorest.co.in/public/v2/users?access-token=813826f7b1e79691bc07861aa811f490eb02cdfa4545d69224f67fbac87b8179");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        String json = "{\"email\": \"java-test"+ new Date().toInstant().toEpochMilli() +"@email.com\", \"name\": \"Java Fx\", \"gender\": \"male\", \"status\": \"active\"}";
        System.out.println(json);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        String responseJson;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            responseJson = response.toString();
            System.out.println(responseJson);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = objectMapper.readValue( responseJson, new TypeReference<User>(){});
        RestUser.setUser(user);
    }

    @Override
    public void start(Stage stage) {

        scenes.put(SceneName.MAIN, new FxmlInfo(SCENE_MAIN, SceneName.MAIN, stage));

        scenes.put(SceneName.DATABASE_READ, new FxmlInfo(SCENE_DATABASE_READ, SceneName.DATABASE_READ, stage));
        scenes.put(SceneName.DATABASE_READ2, new FxmlInfo(SCENE_DATABASE_READ2, SceneName.DATABASE_READ2, stage));
        scenes.put(SceneName.DATABASE_WRITE, new FxmlInfo(SCENE_DATABASE_WRITE, SceneName.DATABASE_WRITE, stage));
        scenes.put(SceneName.DATABASE_EDIT, new FxmlInfo(SCENE_DATABASE_EDIT, SceneName.DATABASE_EDIT, stage));
        scenes.put(SceneName.DATABASE_DELETE, new FxmlInfo(SCENE_DATABASE_DELETE, SceneName.DATABASE_DELETE, stage));

        scenes.put(SceneName.REST_CREATE, new FxmlInfo(SCENE_REST_CREATE, SceneName.REST_CREATE, stage));
        scenes.put(SceneName.REST_READ, new FxmlInfo(SCENE_REST_READ, SceneName.REST_READ, stage));
        scenes.put(SceneName.REST_UPDATE, new FxmlInfo(SCENE_REST_UPDATE, SceneName.REST_UPDATE, stage));
        scenes.put(SceneName.REST_DELETE, new FxmlInfo(SCENE_REST_DELETE, SceneName.REST_DELETE, stage));

        scenes.put(SceneName.REST2_READ, new FxmlInfo(SCENE_REST2_READ, SceneName.REST2_READ, stage));

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