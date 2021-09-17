package org.example.math;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
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
        graph.getM().bindBidirectional(mSpinner.getValueFactory().valueProperty());
        Spinner<Number> pSpinner = new Spinner<>(MIN, MAX, Graph.POINTS);
        pSpinner.setEditable(true);
        graph.getP().bindBidirectional(pSpinner.getValueFactory().valueProperty());
        return new Pane(new ToolBar(
            new Label("Multiplier:"), mSpinner,
            new Label("Points:"), pSpinner));
    }

    public Pane createSettingsPane() {
        ColorPicker bgPicker = new ColorPicker(graph.getB().get());
        bgPicker.setOnAction((ActionEvent e) -> {
            graph.getB().set(bgPicker.getValue());
        });
        ColorPicker fgPicker = new ColorPicker(graph.getC().get());
        fgPicker.setOnAction((ActionEvent e) -> {
            graph.getC().set(fgPicker.getValue());
        });
        final VBox vBox = new VBox(bgPicker, fgPicker);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
