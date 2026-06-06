package com.example.sudokugame.controller;

import com.example.sudokugame.StageMain;
import com.example.sudokugame.model.Sudoku;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {

    @FXML private GridPane sudokuGrid;
    @FXML private Button btRulesGame;
    @FXML private Button btHelp;
    @FXML private Button btAbandonGame;
    @FXML private Label lbErrorMessage;

    private int helpCount = 0;
    private final int MAX_HELP = 3; // Límite de ayuda establecido para mitigar abuso (HU-5)

    @FXML
    public void initialize() {
        // Inicializar el tablero lógico antes de pintar la rejilla gráfica
        Sudoku.getInstance().fillInitialBoard();
        createBoardView();
    }

    private void createBoardView() {
        sudokuGrid.getChildren().clear();
        int rows = 6;
        int columns = 6;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                TextField txtBox = new TextField();
                txtBox.setPrefWidth(60);
                txtBox.setPrefHeight(60);

                // 💡 Algoritmo CSS para calcular bordes gruesos de bloques 2x3 (Top, Right, Bottom, Left)
                String topW = (i % 2 == 0) ? "3px" : "1px";
                String bottomW = (i == 5) ? "3px" : "1px";
                String leftW = (j % 3 == 0) ? "3px" : "1px";
                String rightW = (j == 5) ? "3px" : "1px";

                // Guardamos la configuración base del borde en el estilo inline
                String borderStyle = String.format("-fx-alignment: center; -fx-font-size: 18px; -fx-border-color: #222222; -fx-border-width: %s %s %s %s; -fx-border-style: solid;", topW, rightW, bottomW, leftW);
                txtBox.setStyle(borderStyle + " -fx-background-color: white;");

                int numberInitial = Sudoku.getInstance().getInitialBoard().get(i).get(j);
                if (numberInitial != 0) {
                    txtBox.setText(String.valueOf(numberInitial));
                    txtBox.setEditable(false);
                    // Pistas iniciales en fondo gris claro
                    txtBox.setStyle(borderStyle + " -fx-background-color: #e0e0e0; -fx-font-weight: bold;");
                }

                int[] position = {i, j};
                txtBox.setUserData(position);

                addListener(txtBox, borderStyle); // Pasamos el diseño base del borde al listener
                sudokuGrid.add(txtBox, j, i);
            }
        }
    }

    private void addListener(TextField txtBox, String borderStyle) {
        txtBox.textProperty().addListener((observable, oldValue, newValue) -> {
            int[] position = (int[]) txtBox.getUserData();
            int row = position[0];
            int column = position[1];

            if (newValue.isEmpty()) {
                Sudoku.getInstance().getSudoku().get(row).set(column, 0);
                txtBox.setStyle(borderStyle + " -fx-background-color: white; -fx-text-fill: black;");
                lbErrorMessage.setText("");
                return;
            }

            if (!newValue.matches("[1-6]")) {
                txtBox.setText(oldValue);
                lbErrorMessage.setText("Advertencia: Solo se permiten números entre 1 y 6.");
                return;
            }

            int val = Integer.parseInt(newValue);
            Sudoku.getInstance().getSudoku().get(row).set(column, val);

            boolean isValidRow = Sudoku.getInstance().isValidRow(val, row, column);
            boolean isValidColumn = Sudoku.getInstance().isValidColumn(val, row, column);
            boolean isValidBlock = Sudoku.getInstance().isValidBlock(val, row, column);

            if (!isValidRow || !isValidColumn || !isValidBlock) {
                // 💡 Si falla, cambiamos fondo a rojo tenue y letra a rojo, manteniendo el borde grueso intacto
                txtBox.setStyle(borderStyle + " -fx-background-color: #ffcccc; -fx-text-fill: #cc0000; -fx-font-weight: bold;");
                lbErrorMessage.setText("¡Error! El número viola las reglas del Sudoku.");
            } else {
                // Si es correcto, fondo blanco y letra negra
                txtBox.setStyle(borderStyle + " -fx-background-color: white; -fx-text-fill: black;");
                lbErrorMessage.setText("");
                checkWinCondition();
            }
        });
    }

    private void checkWinCondition() {
        boolean hasWon = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int current = Sudoku.getInstance().getSudoku().get(i).get(j);
                int expected = Sudoku.getInstance().getSolvedBoard().get(i).get(j);
                if (current != expected) {
                    hasWon = false;
                    break;
                }
            }
        }

        if (hasWon) {
            lbErrorMessage.setStyle(lbErrorMessage.getStyle() + "-fx-text-fill: #6FFF36");
            lbErrorMessage.setText("¡Felicidades! Has completado el Sudoku con éxito.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Victoria!");
            alert.setHeaderText(null);
            alert.setContentText("Has resuelto el tablero de juego sin errores.");
            alert.showAndWait();
        }
    }

    @FXML
    public void onActionRulesGame() {
        new WelcomeController().onActionRulesGame();
    }

    @FXML
    public void onActionHelp() {
        if (helpCount >= MAX_HELP) {
            lbErrorMessage.setText("Has alcanzado el límite máximo de ayudas (" + MAX_HELP + ").");
            return;
        }

        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (Sudoku.getInstance().getSudoku().get(i).get(j) == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            lbErrorMessage.setText("No quedan casillas vacías por resolver.");
            return;
        }

        // Selección pseudoaleatoria de una casilla libre para aplicar la ayuda (HU-5)
        Random rand = new Random();
        int[] chosenCell = emptyCells.get(rand.nextInt(emptyCells.size()));
        int r = chosenCell[0];
        int c = chosenCell[1];

        int correctSolutionValue = Sudoku.getInstance().getSolvedBoard().get(r).get(c);

        // Recorrido de los nodos de la interfaz para pintar el resultado sugerido
        for (javafx.scene.Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField txt = (TextField) node;
                int[] pos = (int[]) txt.getUserData();
                if (pos[0] == r && pos[1] == c) {
                    txt.setText(String.valueOf(correctSolutionValue));
                    // Resaltado visual diferenciado en azul celeste (HU-5)
                    txt.setStyle("-fx-alignment: center; -fx-font-size: 18px; -fx-background-color: #b3d9ff; -fx-font-weight: bold;");
                    break;
                }
            }
        }

        helpCount++;
        lbErrorMessage.setText("Pista aplicada (" + helpCount + "/" + MAX_HELP + ").");
    }

    @FXML
    public void onActionAbandonGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Abandonar Partida");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea salir al menú principal?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/view/welcome-view.fxml"));
                    Parent root = loader.load();
                    StageMain.getScene().setScene(new Scene(root));
                    StageMain.getScene().show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}