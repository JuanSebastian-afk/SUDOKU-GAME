package com.example.sudokugame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageMain.setStage(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        StageMain.getInstance().setTitle("SUDOKU GAME");
        StageMain.getInstance().setScene(scene);
        StageMain.getInstance().show();
    }
}
