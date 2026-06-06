package com.example.sudokugame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * Esta clase representa el modelo del Sudoku bajo la arquitectura MVC.
 * Implementa el patrón Singleton para garantizar una única instancia del estado del juego.
 */
public class Sudoku {

    private static Sudoku instance;
    private final int columns = 6;
    private final int rows = 6;

    // Listas bidimensionales para representar los diferentes estados del tablero
    private List<ArrayList<Integer>> sudoku;       // Tablero donde interactúa el jugador
    private List<ArrayList<Integer>> initialBoard;  // Pistas iniciales (fijas)
    private List<ArrayList<Integer>> solvedBoard;   // Solución completa generada por Backtracking

    public static Sudoku getInstance() {
        if (instance == null) {
            instance = new Sudoku();
        }
        return instance;
    }

    private Sudoku() {
        inicializarEstructuras();
    }

    // Inicializa las listas con ceros para evitar errores de punteros o índices fuera de rango
    private void inicializarEstructuras() {
        sudoku = new ArrayList<>();
        initialBoard = new ArrayList<>();
        solvedBoard = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> rowSudoku = new ArrayList<>();
            ArrayList<Integer> rowInitial = new ArrayList<>();
            ArrayList<Integer> rowSolved = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                rowSudoku.add(0);
                rowInitial.add(0);
                rowSolved.add(0);
            }
            sudoku.add(rowSudoku);
            initialBoard.add(rowInitial);
            solvedBoard.add(rowSolved);
        }
    }

    public List<ArrayList<Integer>> getSudoku() { return sudoku; }
    public List<ArrayList<Integer>> getInitialBoard() { return initialBoard; }
    public List<ArrayList<Integer>> getSolvedBoard() { return solvedBoard; }

    // VALIDACIONES: Comprueban si un número se repite en su fila, columna o bloque actual (sin contarse a sí mismo)

    public boolean isValidRow(int value, int row, int col) {
        for (int c = 0; c < columns; c++) {
            if (c != col && sudoku.get(row).get(c) == value) return false;
        }
        return true;
    }

    public boolean isValidColumn(int value, int row, int col) {
        for (int r = 0; r < rows; r++) {
            if (r != row && sudoku.get(r).get(col) == value) return false;
        }
        return true;
    }

    public boolean isValidBlock(int value, int row, int col) {
        // En un Sudoku de 6x6, los bloques son de 2 filas por 3 columnas
        int rowStart = (row / 2) * 2;
        int columnStart = (col / 3) * 3;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int currentR = rowStart + i;
                int currentC = columnStart + j;
                if ((currentR != row || currentC != col) && sudoku.get(currentR).get(currentC) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    // Algoritmo de Backtracking para asegurar un tablero con solución única y válida
    public boolean generarSolucion(int row, int col) {
        if (row == rows - 1 && col == columns) return true;
        if (col == columns) {
            row++;
            col = 0;
        }

        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        Collections.shuffle(numbers); // Aleatoriedad para que cada partida sea distinta

        for (int num : numbers) {
            if (esSeguroSolucion(num, row, col)) {
                solvedBoard.get(row).set(col, num);
                if (generarSolucion(row, col + 1)) return true;
                solvedBoard.get(row).set(col, 0); // Deshacer cambio (Backtrack)
            }
        }
        return false;
    }

    private boolean esSeguroSolucion(int value, int row, int col) {
        for (int c = 0; c < columns; c++) if (solvedBoard.get(row).get(c) == value) return false;
        for (int r = 0; r < rows; r++) if (solvedBoard.get(r).get(col) == value) return false;

        int rowStart = (row / 2) * 2;
        int colStart = (col / 3) * 3;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (solvedBoard.get(rowStart + i).get(colStart + j) == value) return false;
            }
        }
        return true;
    }

    // Cumple con la HU-2: Rellena cada bloque de 2x3 con exactamente 2 números base
    public void fillInitialBoard() {
        inicializarEstructuras();
        generarSolucion(0, 0);

        Random rand = new Random();

        // El tablero de 6x6 tiene 3 filas de bloques y 2 columnas de bloques
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                int rStart = blockRow * 2; // Multiplica por el alto del bloque (2)
                int cStart = blockCol * 3; // Multiplica por el ancho del bloque (3)

                // Creamos los índices relativos de las celdas del bloque (0 a 5) y los mezclamos
                List<Integer> cellIndices = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5));
                Collections.shuffle(cellIndices);

                // Escogemos las primeras 2 celdas mezcladas para colocar las pistas fijas
                for (int k = 0; k < 2; k++) {
                    int chosenIndex = cellIndices.get(k);
                    int targetRow = rStart + (chosenIndex / 3);
                    int targetCol = cStart + (chosenIndex % 3);

                    int valorSolucion = solvedBoard.get(targetRow).get(targetCol);
                    initialBoard.get(targetRow).set(targetCol, valorSolucion);
                    sudoku.get(targetRow).set(targetCol, valorSolucion);
                }
            }
        }
    }
}
