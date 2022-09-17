package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * About application controller
 */
public class AboutController extends Application implements Modular {

    @FXML
    private Node root;
    @FXML
    private String name;
    @FXML
    private String shortName;
    @FXML
    private Text title;
    @FXML
    private Text version;
    @FXML
    private Hyperlink link;

    @FXML
    public void initialize() {
        version.setText(
            System.getProperty("os.name")
            + " v" + System.getProperty("os.version")
            + "; Java v" + System.getProperty("java.version")
            + "; JavaFX v" + System.getProperty("javafx.runtime.version"));
        link.setOnAction((a) -> getHostServices().showDocument(link.getText()));
    }

    @Override
    public Node createContent() {
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
    public void start(Stage stage) throws Exception {
    }
}
