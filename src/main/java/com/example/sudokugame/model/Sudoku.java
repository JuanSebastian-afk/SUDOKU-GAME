package com.example.sudokugame.model;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {

    int columns = 6;
    int rows = 6;

    List<ArrayList<Integer>> sudoku = new ArrayList<ArrayList<Integer>>(rows);  // instanciamos una matriz de enteros que representara la cuadricula del sudoku

    /*
    * Comprueba primero que el valor de la casilla no este repetido en la fila o en
    * la columna respectivamente.
    * Luego ubica cual es la casilla de inicio del bloque en el que se encuentra el
    * valor a validar y lo recorre con dos ciclos for anidados para validar que el
    * valor no sea repetido en ese bloque.
    * */

    public boolean isValidRow(int value, int column, int row){
        for(int i = 0; i <= rows; i++){
            if(value == sudoku.get(row).get(i)) return false;
        }
        return true;
    }

    public boolean isValidColumn(int value, int column, int row){
        for(int i = 0; i <= rows; i++){
            if(value == sudoku.get(i).get(column)) return false;
        }
        return true;
    }

    public boolean isValidBlock(int value, int column, int row){
        int rowStart = (row / 2) * 2;
        int columnStart = (column / 3) * 3;

        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 3; j++){
                if(value == sudoku.get(rowStart + i).get(columnStart + j)) return false;
            }
        }

        return true;
    }


    public void fillBoard(){
        for(int i = 0; i <= 6; i++){
            for(int j = 0; j <= 6; j++){
                if(sudoku.get(i).add(j) == null){

                }

            }
        }
    }

}
