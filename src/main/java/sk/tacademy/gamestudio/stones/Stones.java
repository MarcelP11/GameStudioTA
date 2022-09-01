package sk.tacademy.gamestudio.stones;
import sk.tacademy.gamestudio.stones.consoleui.ConsoleUI;
import sk.tacademy.gamestudio.stones.consoleui.UserInterface;
import sk.tacademy.gamestudio.stones.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Stones {
    private long startTime;

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static Stones instance;  //singleton

    public int getPlayingTime() {
        return ((int) (System.currentTimeMillis() - startTime) / 1000);  //vracia cas v sekundach
    }

    public static Stones getInstance() {  //metoda singletonu vyuzita v consoleUI
        if (instance == null) {
            new Stones();
        }
        return instance;
    }

    //** reader
    private String reader() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private Stones() {
        instance = this;
        //times.loadTimes();  //nacita casy zo suboru
//        System.out.println("Chces nacitat ulozenu hru? Ak ano, zadaj Y");
//        String option = reader();
//        if (option.toLowerCase() == "y") {
//            console.getField().loadField();
//        } else {
            final UserInterface userInterface = new ConsoleUI();
            userInterface.play();
//            startTime = System.currentTimeMillis();  //zaznamenanie casu spustenia hry
//            console.newGame();
//        }
    }

    //**** main metoda
    public static void main(String[] args) {
        new Stones();
    }
}
