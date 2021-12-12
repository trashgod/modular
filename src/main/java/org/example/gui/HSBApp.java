package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Modular;

/**
 * A {@code Modular} application with controls that adjust the hue, saturation
 * and brightness of a color swatch.
 *
 * @see <a href="https://en.wikipedia.org/wiki/HSL_and_HSV"><i>HSB
 * Color</i>.</a>
 */
public class HSBApp extends Application implements Modular {

    private final HSBModel model = new HSBModel(Color.RED);
    private final HSBPane pane = new HSBPane(model);

    public enum Hue {
        Red, Yellow, Lime, Cyan, Blue, Magenta;
    }

    @Override
    public String getName() {
        return "HSB Application";
    }

    @Override
    public String getShortName() {
        return "HSB";
    }

    @Override
    public BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setCenter(pane);
        root.setLeft(createButtonPane());
        root.setBottom(createControlPane());
        return root;
    }

    private Pane createButtonPane() {
        VBox buttonBox = new VBox(8);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(new Label("Choose a color…"));
        ColorPicker colorPicker = new ColorPicker(model.getInitialColor());
        colorPicker.setOnAction((ActionEvent e) -> {
            model.setColor(colorPicker.getValue());
        });
        Platform.runLater(colorPicker::requestFocus);
        buttonBox.getChildren().add(colorPicker);
        for (Hue hue : Hue.values()) {
            Button button = new Button(hue.name());
            Color color = Color.web(hue.name());
            button.setGraphic(new Circle(8, color));
            button.setOnAction((ActionEvent e) -> {
                model.setColor(color);
                colorPicker.setValue(color);
            });
            buttonBox.getChildren().add(button);
        }
        Button reset = new Button("Reset");
        Color color = model.getInitialColor();
        reset.setGraphic(new Circle(8, color));
        reset.setOnAction(e -> {
            model.setColor(color);
            colorPicker.setValue(color);
        });
        buttonBox.getChildren().add(reset);
        buttonBox.setPadding(new Insets(8, 8, 8, 8));
        return buttonBox;
    }

    private Pane createControlPane() {
        Slider hSlider = new Slider(0, 360, model.hProperty().get());
        model.hProperty().bindBidirectional(hSlider.valueProperty());
        Spinner hSpinner = new Spinner(0, 360, model.hProperty().get());
        model.hProperty().bindBidirectional(hSpinner.getValueFactory().valueProperty());

        Slider sSlider = new Slider(0, 1, model.sProperty().get());
        sSlider.setBlockIncrement(0.1);
        model.sProperty().bindBidirectional(sSlider.valueProperty());
        Spinner sSpinner = new Spinner(0, 1, model.sProperty().get(), 0.1);
        model.sProperty().bindBidirectional(sSpinner.getValueFactory().valueProperty());

        Slider bSlider = new Slider(0, 1, model.bProperty().get());
        bSlider.setBlockIncrement(0.1);
        model.bProperty().bindBidirectional(bSlider.valueProperty());
        Spinner bSpinner = new Spinner(0, 1, model.bProperty().get(), 0.1);
        model.bProperty().bindBidirectional(bSpinner.getValueFactory().valueProperty());

        model.setColor(model.getInitialColor());

        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Hue:"), new Label("Saturation:"), new Label("Brightness:"));
        grid.addRow(1, hSlider, sSlider, bSlider);
        grid.addRow(2, hSpinner, sSpinner, bSpinner);
        ColumnConstraints sc = new ColumnConstraints(Slider.USE_COMPUTED_SIZE,
            Slider.USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, null, true);
        grid.getColumnConstraints().addAll(sc, sc, sc);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(4);
        grid.setVgap(4);
        return grid;
    }

    @Override
    public void start(Stage primaryStage) {
//        primaryStage.setTitle(getName());
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
