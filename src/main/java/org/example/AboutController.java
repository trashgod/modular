package org.example;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * About application controller
 */
public class AboutController implements ModularController {

    @FXML
    private Parent root;
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
    private DropShadow dropShadow;
    private double radius;
    private final Timeline timeline = new Timeline();
    private HostServices hostServices;

    @FXML
    public void initialize() {
        radius = dropShadow.getRadius();
        version.setText(
            System.getProperty("os.name")
            + " v" + System.getProperty("os.version")
            + "; Java v" + System.getProperty("java.version")
            + "; JavaFX v" + System.getProperty("javafx.runtime.version"));
        link.setOnAction((a) -> hostServices.showDocument(link.getText()));
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
    public Parent createContent() {
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
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
}
