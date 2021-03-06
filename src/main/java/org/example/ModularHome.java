package org.example;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.example.gui.BlendApp;
import org.example.gui.HSBApp;
import org.example.math.HTreeApp;
import org.example.math.ModularApp;

/**
 * A home for {@code Modular} applications.
 */
public class ModularHome extends Application {

    private Tab createTab(Stage stage, Modular module) {
        Tab tab = new Tab(module.getShortName());
        tab.setTooltip(new Tooltip(module.getName()));
        tab.selectedProperty().addListener((var o) -> {
            if (tab.isSelected()) {
                stage.setTitle(module.getName());
                if (tab.getContent() == null) {
                    tab.setContent(module.createContent());
                }
                Platform.runLater(module.whenSelected());
            } else {
                Platform.runLater(module.whenDeselected());
            }
        });
        return tab;
    }

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        ObservableList<Tab> list = tabPane.getTabs();
        list.add(createTab(stage, new BlendApp()));
        list.add(createTab(stage, new HSBApp()));
        list.add(createTab(stage, new HTreeApp()));
        list.add(createTab(stage, new ModularApp()));
        list.add(createTab(stage, new AboutApp()));
        Scene scene = new Scene(tabPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
