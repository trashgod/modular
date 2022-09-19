package org.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * About Modular Application
 */
public class AboutApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutView.fxml"));
        fxmlLoader.load();
        AboutController controller = fxmlLoader.getController();
        controller.setHostServices(getHostServices());
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
