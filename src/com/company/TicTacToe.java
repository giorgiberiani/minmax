package com.company;

import java.util.Arrays;
import java.util.Scanner;

class TicTacToe {

    private static final int SIZE = 3;
    private static final char EMPTY = '.';
//    ადამიანი
    private static final char ACCUSER = 'X';
//    კომპიუტერი
    private static final char OPPOSER = 'O';

    // მდგომარეობის დაფა
    private char [][]board = new char[SIZE][SIZE];
    private Scanner in;

//    კონსტრუქტორი
    TicTacToe() {
        in = new Scanner(System.in);
        for (int i = 0; i < board.length; i++) {
            board[i] = new char[SIZE];
            Arrays.fill(board[i],EMPTY);
        }
    }

//    მდგომარეობის დაფის გამოტანა
    private void printBoard() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

//    მოცემული მეთოდი ამოწმებს მოთამაშემ მოიგო თურ არა თამაში
    private boolean checkWin(Player player){
        char playerChar = player == Player.ACCUSER ? ACCUSER : OPPOSER;
// აქ მოწმდება სვეტი და სტრიქონი
        for(int i = 0; i < SIZE; i++) {
            if(board[i][0] == playerChar && board[i][1] == playerChar
                    && board[i][2] == playerChar)
                return true;

            if(board[0][i] == playerChar && board[1][i] == playerChar
                    && board[2][i] == playerChar)
                return true;
        }
//აქ მოწმდება ჰორიზონტალი
        if (board[0][0] == playerChar && board[1][1] == playerChar
                && board[2][2] == playerChar) {
            return true;
        } else return board[0][2] == playerChar && board[1][1] == playerChar
                && board[2][0] == playerChar;

    }

//    მეთოდი აბრუნებს 1-ს თუ თამაში დამთავრდა
    private boolean gameOver() {
        if(checkWin(Player.ACCUSER)) {
            return true;
        }
        else
        if(checkWin(Player.OPPOSER)) {
            return true;
        }

        boolean emptySpace = false;
        for(int i = 0; i < SIZE; i++) {
            if(board[i][0] == '.' || board[i][1] == '.' || board[i][2] == '.') {
                emptySpace = true;
            }
        }
        return !emptySpace;
    }

// მოცემული ფუნქცია აბრუნებს ქულე
    private int score() {
        if(checkWin(Player.ACCUSER)) {
            return 10;
        }
        else
        if(checkWin(Player.OPPOSER)) {
            return -10;
        }
        return 0;
    }

    private int minSearch(char AIboard[][]) {
        if(gameOver()) return score();
        Pair bestMove = new Pair();

        int bestMoveScore = 1000;
        bestMoveScore = getBestMoveScore(AIboard, bestMove, bestMoveScore);

        return bestMoveScore;
    }

//    მოცემული მეთოდი აბრუნებს ოპტიმალური სვლის ფასს
    private int getBestMoveScore(char[][] AIboard, Pair bestMove, int bestMoveScore) {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(AIboard[i][j] == '.') {
                    AIboard[i][j] = OPPOSER;
                    int tempMove = maxSearch(AIboard);
                    if(tempMove <= bestMoveScore) {
                        bestMoveScore = tempMove;
                        bestMove.row = i;
                        bestMove.column = j;
                    }
                    AIboard[i][j] = '.';
                }
            }
        }
        return bestMoveScore;
    }

    private int maxSearch(char AIboard[][]) {
        if(gameOver()) return score();
        Pair bestMove = new Pair();

        int bestMoveScore = -1000;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(AIboard[i][j] == '.') {
                    AIboard[i][j] = ACCUSER;
                    int tempMoveScore = minSearch(AIboard);
                    if(tempMoveScore >= bestMoveScore) {
                        bestMoveScore = tempMoveScore;
                        bestMove.row = i;
                        bestMove.column = j;
                    }
                    AIboard[i][j] = '.';
                }
            }
        }

        return bestMoveScore;
    }

//    მეთოდი ირჩევს საუკეთესო სვლას
    private Pair minimax(char AIboard[][]) {
        int bestMoveScore = 1000;
        Pair bestMove = new Pair();

        getBestMoveScore(AIboard, bestMove, bestMoveScore);

        return bestMove;
    }

//    თამაშის მსვლელობა
    void play() {
        int turn = 0;
        while(!checkWin(Player.ACCUSER) && !checkWin(Player.OPPOSER) && !gameOver()) {
            if(turn % 2 == 1) {
                System.out.print("შენი სვლა: ");
                int x = in.nextInt();
                int y = in.nextInt();
                board[x][y] = ACCUSER;
                if(checkWin(Player.ACCUSER))
                    System.out.println("შენ მოიგო");
                turn++;
                printBoard();
            } else {
                System.out.println("კომპიუტერის სვლა: ");
                Pair AImove = minimax(board);
                board[AImove.row][AImove.column] = OPPOSER;
                if(checkWin(Player.OPPOSER))
                    System.out.println("კომპიუტერიმ მოიგო");
                turn++;
                printBoard();
            }
        }
    }

    class Pair {
        int row;
        int column;
    }

    enum Player {
        ACCUSER,
        OPPOSER
    }
}

