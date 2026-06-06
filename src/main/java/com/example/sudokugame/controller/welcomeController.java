package com.example.sudokugame.controller;

import com.example.sudokugame.StageMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;


/*
* Esta clase controladora es la encargada de la transición entre la
* vista de bienvenida y la vista de juego, así como de de proporcinar
* una opción para visuallizar las reglas del juego y una alerta de
* comfirmación al iniciar una núeva partida.
* */

public class welcomeController {

    @FXML
    private Button btPlay;

    public void onActionButtonPlay() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alerta comfirmación");
        alert.setContentText("¿Está seguro iniciar una núeva partida?");
        alert.setHeaderText("Aleta de comfirmación");
        alert.showAndWait();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/view/game-view.fxml"));
        Parent root = loader.load();

        StageMain.getScene().setScene(new Scene(root));
        StageMain.getScene().show();
    }

}
