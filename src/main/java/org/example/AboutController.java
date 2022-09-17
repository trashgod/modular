package org.example;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * About application controller
 */
public class AboutController extends Application implements Modular {

    @FXML
    private Node root;
    @FXML
    private Text title;
    @FXML
    private Text version;
    @FXML
    private Hyperlink link;
    @FXML
    private String name;
    @FXML
    private String shortName;
    @FXML
    private Double radius;
    @FXML
    private Double spread;

    private final DropShadow dropShadow = new DropShadow();
    private final Timeline timeline = new Timeline();

    @FXML
    public void initialize() {
        version.setText(
            System.getProperty("os.name")
            + " v" + System.getProperty("os.version")
            + "; Java v" + System.getProperty("java.version")
            + "; JavaFX v" + System.getProperty("javafx.runtime.version"));
        link.setOnAction((a) -> getHostServices().showDocument(link.getText()));
        dropShadow.setRadius(radius);
        dropShadow.setSpread(spread);
        title.setEffect(dropShadow);
        KeyValue r = new KeyValue(dropShadow.radiusProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame k = new KeyFrame(new Duration(1000), r);
        timeline.getKeyFrames().add(k);
    }

    @Override
    public Runnable whenSelected() {
        return this::animateDropShadow;
    }

    private void animateDropShadow() {
        dropShadow.setRadius(radius);
        timeline.play();
    }

    @Override
    public Node createContent() {
        return root;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public void start(Stage stage) {
    }
}
