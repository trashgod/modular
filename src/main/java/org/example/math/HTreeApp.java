package org.example.math;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.Modular;

/**
 * A modular application that displays an {@code HTree} and
 * {@code HTreeControls}.
 *
 * @see <a href="https://en.wikipedia.org/wiki/H_tree"><i>H Tree</i>.</a>
 * @see <a href="https://stackoverflow.com/q/37449686/230513"><i>Beginner Swing recursion</i>.</a>
 */
public class HTreeApp extends Application implements Modular {

    @Override
    public BorderPane createContent() {
        HTreeModel model = new HTreeModel();
        HTreeView view = new HTreeView(model);
        HTreeControls controls = new HTreeControls(model, view);
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
        return "HTree";
    }

    @Override
    public String getShortName() {
        return "HTree";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(getName());
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
