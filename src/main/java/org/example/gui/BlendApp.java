package org.example.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Modular;

/**
 * A {@code Modular} application illustrating available BlendModes.
 */
public class BlendApp extends Application implements Modular {

    private final BlendModel model = new BlendModel();
    private final BlendView view = new BlendView(model);

    @Override
    public BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setCenter(view);
        root.setLeft(createButtonPane());
        root.setBottom(createDescriptionPane());
        return root;
    }

    private Pane createButtonPane() {
        VBox buttonBox = new VBox(8);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(new Label("Choose colors…"));
        ColorPicker topColor = new ColorPicker(model.topProperty().get());
        topColor.setTooltip(new Tooltip("Top color."));
        model.topProperty().bind(topColor.valueProperty());
        buttonBox.getChildren().add(topColor);
        ColorPicker botColor = new ColorPicker(model.botProperty().get());
        botColor.setTooltip(new Tooltip("Bottom color."));
        model.botProperty().bind(botColor.valueProperty());
        buttonBox.getChildren().add(botColor);
        ObservableList<BlendMode> modes
            = FXCollections.observableArrayList(BlendMode.values());
        ChoiceBox<BlendMode> cb = new ChoiceBox<>(modes);
        cb.setTooltip(new Tooltip("Blend mode."));
        cb.setValue(model.modeProperty().get());
        model.modeProperty().bind(cb.valueProperty());
        Platform.runLater(cb::requestFocus);
        buttonBox.getChildren().add(new Label("Select blend mode…"));
        buttonBox.getChildren().add(cb);
        ToggleButton patternButton = new ToggleButton("Show pattern");
        patternButton.setTooltip(new Tooltip("Togle background pattern."));
        patternButton.setSelected(view.patternProperty().get());
        patternButton.textProperty().bind(Bindings.when(patternButton
            .selectedProperty()).then("Hide pattern").otherwise("Show pattern"));
        view.patternProperty().bind(patternButton.selectedProperty());
        buttonBox.getChildren().add(patternButton);
        Button copyButton = new Button("Copy");
        copyButton.setTooltip(new Tooltip("Copy image to clipboard."));
        copyButton.setOnAction(t -> view.copyImage());
        buttonBox.getChildren().add(copyButton);
        TextField text = new TextField();
        text.setPrefColumnCount(8);
        text.setAlignment(Pos.CENTER);
        text.setEditable(false);
        text.textProperty().bind(view.argbProperty());
        buttonBox.getChildren().add(new Label("ARGB at pointer…"));
        buttonBox.getChildren().add(text);
        buttonBox.setPadding(new Insets(8, 8, 8, 8));
        return buttonBox;
    }

    private TextArea createDescriptionPane() {
        TextArea text = new TextArea();
        text.setPrefRowCount(3);
        text.setEditable(false);
        text.setWrapText(true);
        text.setStyle("-fx-font-family: serif; -fx-font-size: 16;");
        text.setPadding(new Insets(8, 8, 8, 8));
        text.textProperty().bind(model.textProperty());
        return text;
    }

    @Override
    public String getName() {
        return "Blend Mode Application";
    }

    @Override
    public String getShortName() {
        return "Blend";
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
