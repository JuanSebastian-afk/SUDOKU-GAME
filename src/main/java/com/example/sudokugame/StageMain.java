package com.example.sudokugame;

import javafx.stage.Stage;

public class StageMain {

    private static Stage stage;

    private StageMain(){}

    public static void setStage(Stage stage){StageMain.stage = stage;}

    public static Stage getScene(){
        if(stage == null){
            stage = new Stage();
        }
        return stage;
    }

}
