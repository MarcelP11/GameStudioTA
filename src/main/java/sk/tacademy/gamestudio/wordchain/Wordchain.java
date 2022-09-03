package sk.tacademy.gamestudio.wordchain;

import sk.tacademy.gamestudio.wordchain.consoleui.ConsoleUI;
import sk.tacademy.gamestudio.wordchain.consoleui.UserInterface;

public class Wordchain {
    private Wordchain(){
        final UserInterface userInterface = new ConsoleUI();
        userInterface.newGameStarted();
    }

    public static void main(String[] args) {
        new Wordchain();
    }
}
