/* Game of battleships implemented without GUI
from EDX Object Oriented Programming in Java course
https://courses.edx.org/courses/course-v1:Microsoft+DEV277x+1T2020/course/

Todo Refractor code with OOP
Todo Certain user inputs will break game
Todo GUI with javaFX
Todo Allow ships bigger than one unit
 */

package com.company;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int fieldSize = 10;
        String[][] playArea = new String[fieldSize][fieldSize];
        int playerShips = 5;
        int enemyShips = 5;

        // Start with empty ocean
        for(int i = 0; i < playArea.length; i++) {
            for(int j = 0; j < playArea[0].length; j++) {
                playArea[i][j] = " ";
            }
        }
        // Deploy enemy and player ships to the battlefield
        displayPlayArea(playArea);
        deployPlayerShips(reader, playArea, playerShips, fieldSize);
        deployEnemyShips(playArea, enemyShips, fieldSize);

        // Main game loop - Play until player or enemy's ships are sunken
        // TODO alloy player to exit game with some input
        while(true) {
            displayBattleArea(playArea, playerShips, enemyShips);
            enemyShips = playerTurn(reader, playArea, enemyShips, fieldSize);
            if (enemyShips == 0) {
                System.out.println("YOU HAVE WON!");
                break;
            }
            playerShips = enemyTurn(playArea, playerShips, fieldSize);
            if (playerShips == 0) {
                System.out.println("ENEMY HAS WON!");
                break;
            }
        }
        System.out.println("Thanks for playing!!");
    }


    public static void displayPlayArea(String[][] field) {
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println();
        System.out.println("Right now, the sea is empty");
        System.out.println();
        System.out.println("   0123456789   ");
        for(int row = 0; row < field.length; row++) {
            System.out.print(row + " |");
            for(int cols = 0; cols < field[0].length; cols++) {
                System.out.print(field[row][cols]);
            }
            System.out.print("| " + row);
            System.out.println();
        }
        System.out.println("   0123456789   ");
    }

    public static void displayBattleArea(String[][] field, int playerShips, int enemyShips) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Battle arena contains " + enemyShips + " enemy ships and " + playerShips + " player ships");
        System.out.println("   0123456789   ");
        for(int row = 0; row < field.length; row++) {
            System.out.print(row + " |");
            for(int cols = 0; cols < field[0].length; cols++) {
                // IF enemy on the position - Hide it.
                if(field[row][cols].equals("2")) {
                    System.out.print(" ");
                } else {
                    System.out.print(field[row][cols]);
                }
            }
            System.out.print("| " + row);
            System.out.println();
        }
        System.out.println("   0123456789   ");
        System.out.println("-------------------------------------------------------");
    }

    public static void deployPlayerShips(Scanner reader, String[][] field, int maxShips, int fieldSize) {
        System.out.println("Deploy your ships");
        int currentShip = 1;

        while (currentShip <= maxShips) {
            System.out.print("Enter X coordinate for your " + currentShip + ". ship: ");
            int cols = Integer.valueOf(reader.nextInt());
            if(cols < 0 || cols > fieldSize - 1) {
                System.out.println("Out of play area - Give new coordinate");
                continue;
            }
            System.out.print("Enter Y coordinate for your " + currentShip + ". ship: ");
            int row = Integer.valueOf(reader.nextInt());
            if(row < 0 || row > fieldSize - 1) {
                System.out.println("Out of play area - Give new coordinate");
                continue;
            }
            if(field[row][cols].equals("@")) {
                System.out.println("There is place is occupied! - Give new coordinate");
                continue;
            }
            field[row][cols] = "@";
            currentShip++;
        }
    }

    public static void deployEnemyShips(String[][] field, int maxShips, int fieldSize) {
        int currentShip = 1;
        Random r = new Random();
        System.out.println("Computer is deploying ships");

        while(currentShip <= maxShips) {
            int row = r.nextInt(fieldSize);
            int cols = r.nextInt(fieldSize);
            if (!field[row][cols].equals(" ")) {
                continue;
            }
            System.out.println(currentShip + ". ship DEPLOYED");
            currentShip++;
            field[row][cols] = "2";
        }
    }

    public static int playerTurn(Scanner reader, String[][] field, int enemyShips, int fieldSize) {
        System.out.println("YOUR TURN");
        while(true) {
            System.out.print("Enter X coordinate: ");
            int cols = Integer.valueOf(reader.nextInt());
            if(cols < 0 || cols > fieldSize - 1) {
                System.out.println("Out of play area - Give new coordinate");
                continue;
            }
            System.out.print("Enter Y coordinate: ");
            int row = Integer.valueOf(reader.nextInt());
            if(row < 0 || row > fieldSize - 1) {
                System.out.println("Out of play area - Give new coordinate");
                continue;
            }

            if(field[row][cols].equals("!") || field[row][cols].equals("X")) {
                System.out.println("You have already given this coordinate - Give new coordinate");
                continue;
            }

            if(field[row][cols].equals("2")) {
                System.out.println("Boom! You sunk the ship!");
                field[row][cols] = "!";
                enemyShips -= 1;
                break;
            }

            if(field[row][cols].equals(" ")) {
                System.out.println("Sorry, you missed");
                field[row][cols] = "X";
                break;
            }
        }
        return enemyShips;
    }

    public static int enemyTurn(String[][] field, int playerShips, int fieldSize) {
        System.out.println("ENEMY'S TURN");
        Random r = new Random();
        while(true){
            int row = r.nextInt(fieldSize);
            int cols = r.nextInt(fieldSize);
            if (field[row][cols].equals("X") || field[row][cols].equals("+")) {
                continue;
            }
            if (field[row][cols].equals("@")) {
                System.out.println("Your ship has been hit!!");
                field[row][cols] = "+";
                playerShips -= 1;
                break;
            }
            if (field[row][cols].equals(" ")) {
                System.out.println("Enemy has missed!");
                field[row][cols] = "X";
                break;
            }
        }
        return playerShips;
    }
}
