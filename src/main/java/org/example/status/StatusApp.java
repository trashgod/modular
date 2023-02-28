package org.example.status;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Status Application
 */
public class StatusApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatusView.fxml"));
        fxmlLoader.load();
        StatusController controller = fxmlLoader.getController();
        stage.setScene(new Scene(controller.createContent()));
        stage.setTitle(controller.getShortName());
        stage.sizeToScene();
        stage.show();
        Platform.runLater(controller.whenSelected());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
