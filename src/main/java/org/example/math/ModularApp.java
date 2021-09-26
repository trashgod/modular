package org.example.math;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * An application that display a {@code GraphView} and {@code Controls}.
 *
 * @see <a href="https://www.youtube.com/watch?v=qhbuKbxJsk8"><i>Times Tables,
 * Mandelbrot and the Heart of Mathematics.</i></a>
 */
public class ModularApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Modular Arithmetic Graph");
        Graph model = new Graph();
        GraphView view = new GraphView(model);
        Controls controls = new Controls(model, view);
        BorderPane root = new BorderPane();
        Label t = new Label("Adjust the controls below to change the graph.");
        BorderPane.setAlignment(t, Pos.CENTER);
        root.setTop(t);
        root.setCenter(view);
        root.setBottom(controls.createValuesPane());
        root.setLeft(controls.createSettingsPane());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
