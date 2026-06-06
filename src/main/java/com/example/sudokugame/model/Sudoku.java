package com.example.sudokugame.model;

import java.util.ArrayList;
import java.util.List;


/*
* Esta clase se encarga de representar el tablero del sudoku en el juego, proporciona métodos para
* comprobar la validez de un número ingresado y para rellenar un tablero núevo, asegurando que
* el tablero generado tenga al menos una posible solución.
* */


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
    * Para encontrar el bloque inical del tablero se utiliza una aproximación con
    * datos de tipo entero.
    * */

    public boolean isValidRow(int value, int column, int row){
        for(int i = 1; i <= rows; i++){
            if(value == sudoku.get(row).get(i)) return false;
        }
        return true;
    }

    public boolean isValidColumn(int value, int column, int row){
        for(int i = 1; i <= rows; i++){
            if(value == sudoku.get(i).get(column)) return false;
        }
        return true;
    }

    public boolean isValidBlock(int value, int column, int row){
        int rowStart = (row / 2) * 2;
        int columnStart = (column / 3) * 3;

        for(int i = 1; i <= 2; i++){
            for(int j = 1; j <= 3; j++){
                if(value == sudoku.get(rowStart + i).get(columnStart + j)) return false;
            }
        }

        return true;
    }


    public void fillBoard(){
        for(int i = 1; i <= 6; i++){
            for(int j = 1; j <= 6; j++){
                if(sudoku.get(i).get(j) == null){
                    // Aquí creamos un arrayList con enteros del 1 al 6 para ir rellenando la cuadrucula del sudoku.

                    List<Integer> numbersSudoku = new ArrayList<Integer>(6);
                    for(int k = 1; k <= 6; k++){
                        numbersSudoku.add(k);
                    }

                    for(int k = 1; k <= 6; k++){
                        if(isValidBlock(numbersSudoku.get(k), j, i) &&
                           isValidColumn(numbersSudoku.get(k), j, i) && isValidRow(numbersSudoku.get(k), j, i)){
                            sudoku.get(i).add(j, k);  //incertamos el dato en la fila y columna correspondiente
                        }
                    }

                }

            }
        }
    }

}
