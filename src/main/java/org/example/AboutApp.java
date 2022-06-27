package org.example;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * About Modular Application
 */
public class AboutApp extends Application implements Modular {

    private static final int PADDING = 32;
    private static final int DROP_RADIUS = 24;
    private static final Color START_TITLE_COLOR = Color.NAVY;
    private static final Color TARGET_TITLE_COLOR = Color.BLUE;
    private final DropShadow dropShadow = new DropShadow();
    private final Text title  = new Text("Modular Home"); 
    private final Timeline timeline = new Timeline();

    @Override
    public void start(Stage stage) {
        stage.setTitle("AboutApp");
        StackPane root = new StackPane();
        root.getChildren().add(createContent());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Platform.runLater(whenSelected());
    }

    @Override
    public Node createContent() {
        title.setStyle("-fx-font-family: serif; -fx-font-size: 42;"
            + "-fx-font-style: oblique; -fx-font-weight: bold");
        Text version = new Text(
            System.getProperty("os.name")
            + " v" + System.getProperty("os.version")
            + "; Java v" + System.getProperty("java.version")
            + "; JavaFX v" + System.getProperty("javafx.runtime.version"));
        version.setStyle("-fx-font-family: serif; -fx-font-size: 16");
        Hyperlink link = new Hyperlink("https://github.com/trashgod/modular");
        link.setTextFill(TARGET_TITLE_COLOR);
        link.setOnAction((a) -> getHostServices().showDocument(link.getText()));
        VBox box = new VBox(PADDING, title, version, link);
        box.setPadding(new Insets(PADDING));
        box.setAlignment(Pos.CENTER);

        dropShadow.setRadius(DROP_RADIUS);
        dropShadow.setSpread(0.75);
        title.setEffect(dropShadow);
        title.setFill(START_TITLE_COLOR);
        KeyValue r = new KeyValue(dropShadow.radiusProperty(), 0, Interpolator.EASE_OUT);
        KeyValue c = new KeyValue(title.fillProperty(), TARGET_TITLE_COLOR, Interpolator.EASE_OUT);
        KeyFrame k = new KeyFrame(new Duration(1000), c, r);
        timeline.getKeyFrames().add(k);
        return box;
    }

    @Override
    public String getName() {
        return "About Modular Application";
    }

    @Override
    public String getShortName() {
        return "About";
    }

    @Override
    public Runnable whenSelected() {
        return this::animateDropShadow;
    }

    private void animateDropShadow() {
        dropShadow.setRadius(DROP_RADIUS);
        title.setFill(START_TITLE_COLOR);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
