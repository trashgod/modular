package org.example;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

/**
 * About application controller
 */
public class AboutController implements ModularController {

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
    private Node root;

    @FXML
    public void initialize() {
        version.setText(
            System.getProperty("os.name")
            + " v" + System.getProperty("os.version")
            + "; Java v" + System.getProperty("java.version")
            + "; JavaFX v" + System.getProperty("javafx.runtime.version"));
    }

    @Override
    public Node createContent() {
        return root;
    }

    @Override
    public void setContent(Node root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }
}
