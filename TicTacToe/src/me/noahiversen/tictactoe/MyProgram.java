package me.noahiversen.tictactoe;

import java.util.Scanner;

public class MyProgram
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("Are you playing with a friend?");
        System.out.println("Type \"yes\" if you are.");
        System.out.println("Type \"no\" if you are not.");
        if(input.nextLine().equalsIgnoreCase("yes")) {
            Game game = new Game();
            runGame(game);
        } else {
            System.out.println("Choose your difficulty against the AI.");
            
            System.out.println("Type \"easy\" or \"hard\" to choose your difficulty.");
            String difficulty = input.nextLine();
            
            System.out.println("Would you like to start with X or O? Type 'x' or 'o'");
            char player = input.next().charAt(0);
            
            Game game = new Game(difficulty, player);
            runGame(game);
        }
        input.close();
    }
    
    
    private static void runGame(Game game) {
        Scanner input = new Scanner(System.in);
        game.createBoard();
        do {
            game.printBoard();
            int row; int column;
            do {
                System.out.println("Player " + game.getCurrentPlayer() + " make your move!");
                System.out.print("Enter row: ");
                row = input.nextInt() - 1;
                System.out.print("Enter column: ");
                column = input.nextInt() - 1;
             
            } while(!game.makeMove(row, column));
                game.changePlayer();
                if(game.getDifficulty().equalsIgnoreCase("easy"))  {
                    System.out.println("AI choosing move...");
                    if(!game.isWinner() && !game.isBoardFull()) {
                        game.makeRandomMove();
                    }
                } else if(game.getDifficulty().equalsIgnoreCase("hard")) {
                    game.chooseAISpace();
                    game.changePlayer();
                }
        } while(!game.isWinner() && !game.isBoardFull());
            if(game.isBoardFull() && !game.isWinner()) {
                System.out.println("The game was a tie!");
            } else {
                game.printBoard();
                game.changePlayer();
                System.out.println("Player " + game.getCurrentPlayer() + " wins!");
            }
    }
}


