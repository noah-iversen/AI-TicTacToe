package me.noahiversen.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private char[][] board;
    private char currentPlayer;
    private String difficulty;
    
    private char computer;
    private char player;
    
    private List<Point> availablePoints;
    
    public Game() {
    	board = new char[3][3];
    	currentPlayer = 'x';
    	difficulty = "";
    }
    
    public Game(String difficulty, char currentPlayer) {
        board = new char[3][3];
        this.difficulty = difficulty;
        this.currentPlayer = currentPlayer;
        player = currentPlayer;
        computer = player == 'x' ? 'o' : 'x';
    }
    
    public void createBoard() {
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 3; column++) {
                board[row][column] = '-';
            }
        }
    }
    
    public void printBoard() {
        for(int row = 0; row < 3; row++) {
            System.out.print("| ");
            for(int column = 0; column < 3; column++) {
                System.out.print(board[row][column] + " | ");
            }
            System.out.println();
            System.out.println();
        }
    }
    
    public boolean isWinner() {
        return (checkRows() || checkColumns() || checkDiagonals());
    }
    
    public boolean hasAIWon() {
        if ((board[0][0] == board[1][1]  && board[0][0] == board[2][2] 
            && board[0][0] == computer) || 
            (board[0][2] == board[1][1] && board[0][2] == board[2][0] 
            && board[0][2] == computer)) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == computer)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == computer))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlayerWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] 
            && board[0][0] == player) || 
            (board[0][2] == board[1][1] 
            && board[0][2] == board[2][0] && board[0][2] == player)) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)) {
                return true;
            }
        }

        return false;
    }
    
    
    public boolean checkRows() {
        for(int row = 0; row < 3; row++) {
            if(checkBoard(board[row][0], board[row][1], board[row][2])) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkColumns() {
        for(int column = 0; column < 3; column++) {
            if(checkBoard(board[0][column], board[1][column], board[2][column])) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkDiagonals() {
        return checkBoard(board[0][0], board[1][1], board[2][2]) ||
            checkBoard(board[0][2], board[1][1], board[2][0]);
    }
    
    public boolean checkBoard(char space1, char space2, char space3) {
        return space1 != '-' && space1 == space2 && space2 == space3;
    }
    
    public void changePlayer() {
        currentPlayer = currentPlayer == 'x' ? 'o' : 'x';
    }
    
    public char getCurrentPlayer() {
    	return currentPlayer;
    }
    
    public boolean makeMove(int row, int column) {
        if(row >= 0 && row < 3 && column >= 0 && column < 3) {
            if(board[row][column] == '-') {
                board[row][column] = currentPlayer;
                return true;
            }
        }
        return false;
    }
    
    public void makeRandomMove() {
        int row = (int) (Math.random() * 3);
        int column = (int) (Math.random() * 3);
        if(!makeMove(row, column) && !isBoardFull() && !isWinner())  {
            makeRandomMove();
        } else {
            changePlayer();
        }
    }
    
    public boolean isBoardFull() {
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 3; column ++) {
                if(board[row][column] == '-')
                    return false;
            }
        }
        return true;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 3; column++) {
                if(board[row][column] == '-')
                    availablePoints.add(new Point(row, column));
            }
        }
        return availablePoints;
    }
    
    public void chooseAISpace() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[] { 0, 0 };
        for(int column = 0; column < 3; column++) {
            for(int row = 0; row < 3; row ++) {
            	if(board[row][column] == '-') {
                    board[row][column] = computer;
                    int score = miniMax(0, false);
                    board[row][column] = '-';
                    if(score > bestScore) {
                        bestScore = score;
                        move[0] = row;
                        move[1] = column;
                    }
            	}
                
            }
        }
        board[move[0]][move[1]] = computer;
    }
    
    private int miniMax(int depth, boolean isMaximizing) {
        if(isBoardFull()) {
            return 0;
        } else if(hasAIWon()) {
            return 10;
        } else if(hasPlayerWon()) {
            return -10;
        } 
        
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char currentPlayer = isMaximizing ? computer : player;
        
        for(int column = 0; column < 3; column++) {
            for(int row = 0; row < 3; row++) {
            	if(board[row][column] == '-') {
                    board[row][column] = currentPlayer;
                    int score = miniMax(depth + 1, !isMaximizing);
                    board[row][column] = '-';
                    bestScore = isMaximizing ? Math.max(score, bestScore) :
                    Math.min(score, bestScore);
            	}
            }
        }
        return bestScore;
    }
}
