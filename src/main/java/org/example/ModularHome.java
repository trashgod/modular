package org.example;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.example.gui.BlendApp;
import org.example.gui.HSBApp;
import org.example.math.HTreeApp;
import org.example.math.ModularApp;

/**
 * A home for {@code Modular} applications.
 */
public class ModularHome implements Modular {

    @FXML
    private TabPane tabPane;
    @FXML
    private String name;
    @FXML
    private String shortName;

    private final Application application;
    private final Stage stage;

    public ModularHome(Application application, Stage stage) {
        this.application = application;
        this.stage = stage;
    }

    private Modular load(String name) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
        fxmlLoader.setControllerFactory(
            new ModularController.HostServicesFactory(
                application.getHostServices()));
        try {
            fxmlLoader.load();
            return fxmlLoader.getController();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private Tab createTab(Modular module) {
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

    @FXML
    public void initialize() {
        ObservableList<Tab> list = tabPane.getTabs();
        list.add(createTab(new BlendApp()));
        list.add(createTab(new HSBApp()));
        list.add(createTab(new HTreeApp()));
        list.add(createTab(new ModularApp()));
        list.add(createTab(load("status/StatusView.fxml")));
        list.add(createTab(load("AboutView.fxml")));
    }

    @Override
    public Parent createContent() {
        return tabPane;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }
}
