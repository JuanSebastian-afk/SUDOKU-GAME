package com.example.sudokugame.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class FinalController {

    @FXML private Button btExitGame;
    @FXML private Button btReplay;
    @FXML private Label lbFinalMessage;

    @FXML
    public void onActionExitGame() {
        Platform.exit(); // Cierre limpio de la ejecución de JavaFX
        System.exit(0);
    }

    @FXML
    public void onActionReplay() throws IOException {
        // Reutilizamos la lógica del menú principal para instanciar un nuevo tablero ordenadamente
        new WelcomeController().onActionButtonPlay();
    }
}