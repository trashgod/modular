package org.example.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
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
        model.topProperty().bind(topColor.valueProperty());
        Platform.runLater(topColor::requestFocus);
        buttonBox.getChildren().add(topColor);
        ColorPicker botColor = new ColorPicker(model.botProperty().get());
        model.botProperty().bind(botColor.valueProperty());
        buttonBox.getChildren().add(botColor);
        ObservableList<BlendMode> modes
            = FXCollections.observableArrayList(BlendMode.values());
        ChoiceBox<BlendMode> cb = new ChoiceBox<>(modes);
        cb.setValue(model.modeProperty().get());
        buttonBox.getChildren().add(cb);
        buttonBox.setPadding(new Insets(8, 8, 8, 8));
        return buttonBox;
    }

    private Label createDescriptionPane() {
        Label label = new Label(model.textProperty().get());
        return label;
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