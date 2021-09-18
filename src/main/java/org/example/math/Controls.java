package org.example.math;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controls {

    private static final int MIN = 2;
    private static final int MAX = 256;
    private final Graph graph;

    public Controls(Graph graph) {
        this.graph = graph;
    }

    public Pane createValuesPane() {
        Spinner<Number> mSpinner = new Spinner<>(MIN, MAX, Graph.MULTIPLIER, 0.1);
        mSpinner.setEditable(true);
        graph.mProperty().bindBidirectional(mSpinner.getValueFactory().valueProperty());
        Spinner<Number> pSpinner = new Spinner<>(MIN, MAX, Graph.POINTS);
        pSpinner.setEditable(true);
        graph.pProperty().bindBidirectional(pSpinner.getValueFactory().valueProperty());
        return new Pane(new ToolBar(
            new Label("Multiplier:"), mSpinner,
            new Label("Points:"), pSpinner));
    }

    public Pane createSettingsPane() {
        ColorPicker bgPicker = new ColorPicker(graph.bProperty().get());
        bgPicker.setTooltip(new Tooltip("Background color."));
        graph.bProperty().bind(bgPicker.valueProperty());
        ColorPicker fgPicker = new ColorPicker(graph.cProperty().get());
        fgPicker.setTooltip(new Tooltip("Foreground color."));
        graph.cProperty().bind(fgPicker.valueProperty());
        CheckBox cb = new CheckBox("Flip origin:");
        cb.setSelected(graph.fProperty().get());
        graph.fProperty().bind(cb.selectedProperty());
        final VBox vBox = new VBox(bgPicker, fgPicker, cb);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        return vBox;
    }
}
