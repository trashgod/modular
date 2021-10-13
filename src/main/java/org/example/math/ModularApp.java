package org.example.math;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.Modular;

/**
 * A modular application that displays a {@code GraphView} and {@code Controls}.
 *
 * @see <a href="https://www.youtube.com/watch?v=qhbuKbxJsk8"><i>Times Tables,
 * Mandelbrot and the Heart of Mathematics.</i></a>
 */
public class ModularApp extends Application implements Modular {

    @Override
    public BorderPane createApp() {
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

    @Override
    public String getName() {
        return "Modular Arithmetic Graph";
    }

    @Override
    public String getShortName() {
        return "Modular Graph";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(getName());
        primaryStage.setScene(new Scene(createApp()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
