package org.example;

import javafx.scene.Node;

/**
 * Interface for a {@code Modular} FXML controller.
 */
public interface ModularController extends Modular {

    /**
     * Allow a {@code Modular} FXML controller to specify its root node.
     *
     * @param root the root content
     */
    public void setContent(Node root);
}
