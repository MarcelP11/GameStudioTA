package sk.tacademy.gamestudio.wordchain;

import sk.tacademy.gamestudio.minesweeper.Minesweeper;
import sk.tacademy.gamestudio.minesweeper.UserInterface;
import sk.tacademy.gamestudio.minesweeper.consoleui.ConsoleUI;

public class Wordchain {
    private Wordchain(){
        final UserInterface userInterface = new ConsoleUI();
        userInterface.play();
    }

    public static void main(String[] args) {
        new Wordchain();
    }
}
