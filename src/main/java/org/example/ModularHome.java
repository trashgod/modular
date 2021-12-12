package org.example;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.example.gui.HSBApp;
import org.example.math.HTreeApp;
import org.example.math.ModularApp;

/**
 * A home for {@code Modular} applications.
 */
public class ModularHome extends Application {

    private final TabPane tabpane = new TabPane();

    private void createTab(Stage stage, Modular module) {
        Tab tab = new Tab(module.getShortName());
        tab.setTooltip(new Tooltip(module.getName()));
        tab.selectedProperty().addListener((var o) -> {
            if (tab.isSelected()) {
                stage.setTitle(module.getName());
                if (tab.getContent() == null) {
                    tab.setContent(module.createContent());
                }
            }
        });
        tabpane.getTabs().add(tab);
    }

    @Override
    public void start(Stage stage) {
        tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        createTab(stage, new HSBApp());
        createTab(stage, new HTreeApp());
        createTab(stage, new ModularApp());
        Scene scene = new Scene(tabpane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
