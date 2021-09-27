package org.example.math;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
        primaryStage.setScene(new Scene(createApp()));
        primaryStage.show();
    }

    private BorderPane createApp() {
        Graph model = new Graph();
        GraphView view = new GraphView(model);
        Controls controls = new Controls(model, view);
        BorderPane root = new BorderPane();
        root.setCenter(view);
        root.setTop(controls.createStatusPane());
        root.setBottom(controls.createValuesPane());
        root.setLeft(controls.createSettingsPane());
        BorderPane.setAlignment(root.getTop(), Pos.CENTER);
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
