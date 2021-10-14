package org.example;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;
import org.example.math.ModularApp;

/**
 * A home for {@code Modular} applications.
 */
public class ModularHome extends Application {

    private final TabPane tabpane = new TabPane();

    private void createTab(Stage stage, Modular module) {
        Tab tab = new Tab(module.getShortName());
        tab.selectedProperty().addListener((var o) -> {
            stage.setTitle(module.getName());
            if (tab.getContent() == null) {
                tab.setContent(module.createApp());
            }
        });
        tabpane.getTabs().add(tab);
    }

    @Override
    public void start(Stage stage) {
        tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        createTab(stage, new ModularApp());
        Scene scene = new Scene(tabpane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
