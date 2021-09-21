package org.example.math;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Controls {

    private static final int MIN = 2;
    private static final int MAX = 256;
    private final Graph graph;

    public Controls(Graph graph) {
        this.graph = graph;
    }

    public Pane createValuesPane() {
        Slider mSlider = new Slider(0, MAX, graph.mProperty().get());
        mSlider.setBlockIncrement(0.1);
        graph.mProperty().bindBidirectional(mSlider.valueProperty());
        Spinner<Number> mSpinner = new Spinner<>(MIN, MAX, Graph.MULTIPLIER, 0.1);
        mSpinner.setEditable(true);
        graph.mProperty().bindBidirectional(mSpinner.getValueFactory().valueProperty());
        Platform.runLater(mSpinner::requestFocus);

        Slider pSlider = new Slider(0, MAX, graph.pProperty().get());
        pSlider.setBlockIncrement(1);
        graph.pProperty().bindBidirectional(pSlider.valueProperty());
        Spinner<Number> pSpinner = new Spinner<>(MIN, MAX, Graph.POINTS);
        pSpinner.setEditable(true);
        graph.pProperty().bindBidirectional(pSpinner.getValueFactory().valueProperty());

        HBox mBox = new HBox(new Label("Multiplier: "), mSpinner);
        mBox.setAlignment(Pos.CENTER);
        HBox pBox = new HBox(new Label("Points: "), pSpinner);
        pBox.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.addRow(0, mSlider, pSlider);
        grid.addRow(1, mBox, pBox);
        ColumnConstraints sc = new ColumnConstraints(Slider.USE_COMPUTED_SIZE,
            Slider.USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, null, true);
        grid.getColumnConstraints().addAll(sc, sc);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(4);
        grid.setVgap(4);
        return grid;
    }

    public Pane createSettingsPane() {
        ColorPicker bgPicker = new ColorPicker(graph.bgProperty().get());
        bgPicker.setTooltip(new Tooltip("Background color."));
        graph.bgProperty().bindBidirectional(bgPicker.valueProperty());

        ColorPicker fgPicker = new ColorPicker(graph.fgProperty().get());
        fgPicker.setTooltip(new Tooltip("Foreground color."));
        graph.fgProperty().bindBidirectional(fgPicker.valueProperty());

        CheckBox cb = new CheckBox("Flip origin:");
        cb.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        cb.setSelected(graph.flipProperty().get());
        graph.flipProperty().bind(cb.selectedProperty());

        final VBox vBox = new VBox(bgPicker, fgPicker, cb, createAnimation());
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        return vBox;
    }

    private CheckBox createAnimation() {
        CheckBox cb = new CheckBox("Animate:");
        cb.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        KeyValue mKV = new KeyValue(graph.mProperty(), MAX);
        KeyValue pKV = new KeyValue(graph.pProperty(), MAX);
        KeyValue fgKV = new KeyValue(graph.fgProperty(),
            graph.fgProperty().get(), new HSBInterpolator());
        KeyFrame mKF = new KeyFrame(Duration.seconds(30), mKV);
        KeyFrame pKF = new KeyFrame(Duration.seconds(30), pKV);
        KeyFrame fgKF = new KeyFrame(Duration.seconds(30), fgKV);
        timeline.getKeyFrames().addAll(mKF, pKF, fgKF);
        cb.selectedProperty().addListener((Observable o) -> {
            if (cb.isSelected()) {
                timeline.play();
            } else {
                timeline.stop();
            }
        });
        return cb;
    }

    /**
     * A linear interpolator that spans the hue of the first and last color or
     * 360 degrees if the colors match.
     */
    private static class HSBInterpolator extends Interpolator {

        @Override
        protected double curve(double t) {
            return t; // linear
        }

        @Override
        public Object interpolate(Object first, Object last, double t) {
            Color f = (Color) first;
            Color l = (Color) last;
            double a = f.equals(l) ? 360 : l.getHue() - f.getHue();
            return Color.hsb(f.getHue() + curve(t) * a, f.getSaturation(), f.getBrightness());
        }
    }
}
