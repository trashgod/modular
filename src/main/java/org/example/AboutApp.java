package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * About Modular Application
 */
public class AboutApp extends Application implements Modular {

    private static final int PADDING = 32;

    @Override
    public void start(Stage stage) {
        stage.setTitle("AboutApp");
        StackPane root = new StackPane();
        root.getChildren().add(createContent());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public Node createContent() {
        Text title = new Text("Modular Home");
        title.setStyle("-fx-font-family: serif; -fx-font-size: 42;"
            + "-fx-font-style: oblique; -fx-font-weight: bold");
        Label label = new Label(
            System.getProperty("os.name")
            + " v" + System.getProperty("os.version")
            + "; Java v" + System.getProperty("java.version")
            + "; JavaFX v" + System.getProperty("javafx.runtime.version"));
        VBox box = new VBox(PADDING, title, label);
        box.setPadding(new Insets(PADDING));
        box.setAlignment(Pos.CENTER);
        return box;
    }

    @Override
    public String getName() {
        return "About Modular Applpication";
    }

    @Override
    public String getShortName() {
        return "About";
    }

}
