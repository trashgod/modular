package org.example.math;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @see https://www.youtube.com/watch?v=qhbuKbxJsk8
 */
public class ModularApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Modular Arithmetic Graph");
        Graph graph = new Graph();
        Controls controls = new Controls(graph);
        BorderPane root = new BorderPane();
        Label t = new Label("Adjust the controls below to change the graph.");
        BorderPane.setAlignment(t, Pos.CENTER);
        root.setTop(t);
        root.setCenter(graph.getPane());
        root.setBottom(controls.createValuesPane());
        root.setLeft(controls.createSettingsPane());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
