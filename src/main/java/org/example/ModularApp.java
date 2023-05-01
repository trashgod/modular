package org.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A {@code Modular} application that loads the {@code ModularHome} controller.
 */
public class ModularApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModularView.fxml"));
        fxmlLoader.setControllerFactory(new ModularController.HostServicesFactory(this.getHostServices()));
        fxmlLoader.load();
        ModularHome controller = fxmlLoader.getController();
        stage.setScene(new Scene(controller.createContent()));
        stage.setTitle(controller.getName());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
