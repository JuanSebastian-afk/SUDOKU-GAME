package com.example.sudokugame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageMain.setStage(stage);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/welcome-view.fxml"));
        Parent root = loader.load();

        StageMain.getScene().setTitle("SUDOKU GAME");
        StageMain.getScene().setScene(new Scene(root));
        StageMain.getScene().show();
    }
}
