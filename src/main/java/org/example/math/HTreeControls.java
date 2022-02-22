package org.example.math;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * {@code Controls} that alter the properties of a {@code HTree} and its
 * {@code HTreeView}.
 */
public class HTreeControls {

    private final HTreeModel model;
    private final HTreeView view;
    // Some controls will be disabled during animation
    private final ToggleButton animate = new ToggleButton("Animate");

    /**
     * Controls that alter an {@code HTree}.
     *
     * @param model the {@code HTree} that the controls affect
     * @param view the {@code HTreeView} that the controls affect
     */
    public HTreeControls(HTreeModel model, HTreeView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * A panel of controls to adjust the multiplier and number of points on the
     * {@code HTree} using a {@code Spinner} and {@code Slider} for each.
     *
     * @see <a href="https://stackoverflow.com/a/55427307/230513"><i>Connecting
     * a Slider and Spinner…</i></a>
     *
     * @return a {@code Pane} containing value controls
     */
    public Pane createValuesPane() {
        Slider nSlider = new Slider(HTreeModel.MIN, HTreeModel.MAX, model.nProperty().get());
        nSlider.setBlockIncrement(1);
        nSlider.setMajorTickUnit(1);
        nSlider.setSnapToTicks(true);
        nSlider.disableProperty().bind(animate.selectedProperty());
        model.nProperty().bindBidirectional(nSlider.valueProperty());
        Spinner<Number> nSpinner = new Spinner<>(HTreeModel.MIN, HTreeModel.MAX, HTreeModel.ORDER, 1);
        nSpinner.disableProperty().bind(animate.selectedProperty());
        model.nProperty().bindBidirectional(nSpinner.getValueFactory().valueProperty());
        Platform.runLater(nSpinner::requestFocus);

        HBox nBox = new HBox(new Label("Order: "), nSpinner);
        nBox.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.addRow(0, nSlider);
        grid.addRow(1, nBox);
        ColumnConstraints sc = new ColumnConstraints(Slider.USE_COMPUTED_SIZE,
            Slider.USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, null, true);
        grid.getColumnConstraints().addAll(sc);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(4);
        grid.setVgap(4);
        return grid;
    }

    /**
     * A general message label.
     *
     * @return a {@code Label} containing a message.
     */
    public Label createStatusPane() {
        String greet = "Adjust the controls below to change the tree.";
        String alert = "Some controls are disabled during animation.";
        Label label = new Label(greet);
        animate.selectedProperty().addListener(
            (o) -> label.setText(animate.isSelected() ? alert : greet));
        return label;
    }

    /**
     * A panel of controls to adjust the foreground and background colors of the
     * {@code HTree}, as well as controls for rotation, animation, reset and
     * copy.
     *
     * @return a {@code Pane} containing value controls
     */
    public Pane createSettingsPane() {
        final ColorPicker c1Picker = createC1Picker();
        final ColorPicker c2Picker = createc2Picker();
        final ToggleButton stroke = createStroke();
        final ToggleButton animation = createAnimation(Duration.seconds(2));
        final Button reset = createReset();
        final Button copy = createCopyButton();
        final VBox vBox = new VBox(8);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(8, 8, 8, 8));
        vBox.getChildren().addAll(c1Picker, c2Picker, stroke, animation, reset, copy);
        return vBox;
    }

    private ColorPicker createC1Picker() {
        ColorPicker c1Picker = new ColorPicker(view.c1Property().get());
        c1Picker.setTooltip(new Tooltip("First color."));
        view.c1Property().bindBidirectional(c1Picker.valueProperty());
        return c1Picker;
    }

    private ColorPicker createc2Picker() {
        ColorPicker c2Picker = new ColorPicker(view.c2Property().get());
        c2Picker.setTooltip(new Tooltip("Second color."));
        view.c2Property().bindBidirectional(c2Picker.valueProperty());
        return c2Picker;
    }

    private ToggleButton createStroke() {
        ToggleButton vary = new ToggleButton("Vary stroke");
        vary.setTooltip(new Tooltip("Vary stroke by order."));
        vary.setSelected(view.strokeProperty().get());
        view.strokeProperty().bind(vary.selectedProperty());
        return vary;
    }

    private ToggleButton createAnimation(Duration seconds) {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        KeyValue nMin = new KeyValue(model.nProperty(), HTreeModel.MIN);
        KeyValue nMax = new KeyValue(model.nProperty(), HTreeModel.MAX);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), nMin));
        timeline.getKeyFrames().add(new KeyFrame(seconds, nMax));
        animate.setTooltip(new Tooltip("Toggle animation."));
        animate.selectedProperty().addListener((o) -> {
            if (animate.isSelected()) {
                timeline.play();
            } else {
                timeline.stop();
            }
        });
        return animate;
    }

    private Button createReset() {
        Button reset = new Button("Reset");
        reset.setTooltip(new Tooltip("Reset model and view parameters."));
        reset.setOnAction((t) -> {
            model.nProperty().set(HTreeModel.ORDER);
            view.c1Property().set(HTreeView.c1Color);
            view.c2Property().set(HTreeView.c2Color);
        });
        reset.disableProperty().bind(animate.selectedProperty());
        return reset;
    }

    private Button createCopyButton() {
        Button copy = new Button("Copy");
        copy.setTooltip(new Tooltip("Copy image to clipboard."));
        copy.setOnAction((t) -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            Canvas canvas = view.getCanvas();
            int w = (int) canvas.getWidth();
            int h = (int) canvas.getHeight();
            WritableImage image = canvas.snapshot(
                new SnapshotParameters(), new WritableImage(w, h));
            content.putImage(image);
            clipboard.setContent(content);
        });
        return copy;
    }
}
