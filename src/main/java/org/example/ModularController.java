package org.example;

import javafx.application.HostServices;

/**
 * Interface for a {@code Modular} FXML controller.
 */
public interface ModularController extends Modular {

    /**
     * Allow a {@code Modular} FXML controller to access {@code HostServices}.
     *
     * @param hostServices the application's host services.
     */
    public void setHostServices(HostServices hostServices);
}
