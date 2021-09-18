package org.example.math;

import javafx.application.Platform;
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
        graph.bgProperty().bind(bgPicker.valueProperty());

        ColorPicker fgPicker = new ColorPicker(graph.fgProperty().get());
        fgPicker.setTooltip(new Tooltip("Foreground color."));
        graph.fgProperty().bind(fgPicker.valueProperty());

        CheckBox cb = new CheckBox("Flip origin:");
        cb.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        cb.setSelected(graph.flipProperty().get());
        graph.flipProperty().bind(cb.selectedProperty());

        final VBox vBox = new VBox(bgPicker, fgPicker, cb);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        return vBox;
    }
}
