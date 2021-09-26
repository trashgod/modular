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
import javafx.scene.control.Button;
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

/**
 * {@code Controls} that alter the properties of a {@code Graph} and its
 * {@code GraphView}.
 */
public class Controls {

    private static final int MIN = 2;
    private static final int MAX = 256;
    private final Graph graph;
    private final GraphView view;

    /**
     * Controls that alter a {@code Graph}.
     *
     * @param graph the {@code Graph} that the controls affect
     * @param view the {@code GraphView} that the controls affect
     */
    public Controls(Graph graph, GraphView view) {
        this.graph = graph;
        this.view = view;
    }

    /**
     * A panel of controls to adjust the multiplier and number of points on the
     * {@code Graph} using a {@code Spinner} and {@code Slider} for each.
     *
     * @see <a href="https://stackoverflow.com/a/55427307/230513"><i>Connecting
     * a Slider and Spinner…</i></a>
     *
     * @return a {@code Pane} containing value controls
     */
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

    /**
     * A panel of controls to adjust the foreground and background colors of the
     * {@code Graph}, as well as controls for rotation and animation.
     *
     * @return a {@code Pane} containing value controls
     */
    public Pane createSettingsPane() {
        final ColorPicker bgPicker = createBackgroundPicker();
        final ColorPicker fgPicker = createForegroundPicker();
        final CheckBox rotation = createRotateOrigin();
        final CheckBox animation = createAnimation(Duration.seconds(30));
        final Button reset = createReset();
        final VBox vBox = new VBox(8);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(bgPicker, fgPicker, rotation, animation, reset);
        return vBox;
    }

    private ColorPicker createBackgroundPicker() {
        ColorPicker bgPicker = new ColorPicker(view.bgProperty().get());
        bgPicker.setTooltip(new Tooltip("Background color."));
        view.bgProperty().bindBidirectional(bgPicker.valueProperty());
        return bgPicker;
    }

    private ColorPicker createForegroundPicker() {
        ColorPicker fgPicker = new ColorPicker(view.fgProperty().get());
        fgPicker.setTooltip(new Tooltip("Foreground color."));
        view.fgProperty().bindBidirectional(fgPicker.valueProperty());
        return fgPicker;
    }

    private CheckBox createRotateOrigin() {
        CheckBox cb = new CheckBox("Rotate origin:");
        cb.setTooltip(new Tooltip("Rotate origin 180°."));
        cb.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        cb.setSelected(view.rotProperty().get());
        view.rotProperty().bind(cb.selectedProperty());
        return cb;
    }

    private CheckBox createAnimation(Duration seconds) {
        CheckBox cb = new CheckBox("Animate:");
        cb.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        KeyValue mKV = new KeyValue(graph.mProperty(), MAX);
        KeyValue pKV = new KeyValue(graph.pProperty(), MAX);
        KeyValue fgKV = new KeyValue(view.fgProperty(), null, new HueInterpolator());
        KeyFrame mKF = new KeyFrame(seconds, mKV);
        KeyFrame pKF = new KeyFrame(seconds, pKV);
        KeyFrame fgKF = new KeyFrame(seconds, fgKV);
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

    private Button createReset() {
        Button reset = new Button("Reset");
        reset.setOnAction((t) -> {
            graph.pProperty().set(Graph.POINTS);
            graph.mProperty().set(Graph.MULTIPLIER);
            view.bgProperty().set(GraphView.bgColor);
            view.fgProperty().set(GraphView.fgColor);
        });
        return reset;
    }

    /**
     * A linear {@code Interpolator} that spans the full 360 degree hue range of
     * the first color; saturation, brightness and the last color are ignored.
     */
    private static class HueInterpolator extends Interpolator {

        @Override
        protected double curve(double t) {
            return t; // linear
        }

        @Override
        public Object interpolate(Object first, Object last, double t) {
            Color c = (Color) first;
            double hue = c.getHue() + curve(t) * 360;
            return Color.hsb(hue, c.getSaturation(), c.getBrightness());
        }
    }
}
