package com.example.sudokugame.controller;

import com.example.sudokugame.StageMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button btPlay;

    @FXML
    private Button btRulesGame;

    @FXML
    public void onActionButtonPlay() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Nueva Partida");
        alert.setHeaderText("¿Deseas iniciar un nuevo juego?");
        alert.setContentText("Se borrará cualquier progreso no guardado.");

        // Mostrar la alerta y esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/view/game-view.fxml"));
                    Parent root = loader.load();
                    StageMain.getScene().setScene(new Scene(root));
                    StageMain.getScene().show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Las instrucciones se muestran en forma de Alert
    @FXML
    public void onActionRulesGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reglas del Sudoku 6x6");
        alert.setHeaderText("Instrucciones de Juego");
        alert.setContentText("1. Debes rellenar las casillas vacías con números del 1 al 6.\n" +
                "2. No se pueden repetir números en la misma fila o columna.\n" +
                "3. No se pueden repetir números dentro del mismo bloque de 2x3.\n" +
                "4. Las casillas de color gris son pistas del sistema y no se pueden editar.");
        alert.showAndWait();
    }
}