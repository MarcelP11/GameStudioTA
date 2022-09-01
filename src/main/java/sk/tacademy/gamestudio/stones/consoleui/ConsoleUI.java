package sk.tacademy.gamestudio.stones.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tacademy.gamestudio.entity.Score;
import sk.tacademy.gamestudio.service.*;
import sk.tacademy.gamestudio.stones.consoleui.WrongFormatException;
import sk.tacademy.gamestudio.stones.core.Field;
import sk.tacademy.gamestudio.stones.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {
    private int rowItems = 4;  //defaultne nastavenie
    private int columnItems = 4; //defaultne nastavenie
    private Field field;
    private String username;
    @Autowired
    private ScoreService scoreService;   //nechavam na spring co je prenho vhodne
    @Autowired
    private CommentService commentService;   //nechavam na spring co je prenho vhodne
    @Autowired
    private RatingService ratingService;   //nechavam na spring co je prenho vhodne
    @Autowired
    private PlayerService playerService;
    @Autowired
    private OccupationService occupationService;
    @Autowired
    private CountryService countryService;


    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public Field getField() {
        return field;
    }

    //** reader na citanie vstupu od pouzivatela
    private String reader() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }


    public void newGame() {
        int gameScore = 0;
        this.field = field;
        System.out.println("Please enter username: \n");  //zadanie mena hraca
        username = reader();
        System.out.println("Welcome, " + username);
        settingPlayingField();   //nastavenie hracieho pola
        do {
            update();
            processInput();
            if (isGameSolved()) {
                field.setState(GameState.SOLVED);  //nastavi sa gamestate
                gameScore = this.field.getScore();
                System.out.println("Vyhral si!");
                break;
            }
        } while (true);
        Score player_score = new Score("Stones", username, gameScore, new Date());  //vytvorenie noveho objektu score
        //ScoreService scoreService = new ScoreServiceJDBC();  //vytvorenie noveho objektu ScoreServiceJDBC
        scoreService.addScore(player_score); //pridanie casu
    }

    public void settingPlayingField() { //metoda pre nastavenie velkosti hracieho pola
        int row = 0, column = 0;
        System.out.println("Zadaj pocet riadkov hracieho pola (min. 4)");
        String r = reader(); //do r ulozime hodnotu
        //tu dat podmienku
        Pattern patternRow = Pattern.compile("\\d");
        Matcher matcherRow = patternRow.matcher(r);
        if (matcherRow.matches()) {
            row = Integer.parseInt(r);
        }
        System.out.println("Zadaj pocet stlpcov hracieho pola (min. 4)");
        String c = reader();
        Pattern patternColumn = Pattern.compile("\\d");
        Matcher matcherColumn = patternColumn.matcher(c);
        if (matcherColumn.matches()) {
            column = Integer.parseInt(c);
        }
        settingRowsAndColumns(row, column);  //nastavi hodnoty premennych

    }

    public void settingRowsAndColumns(int rows, int columns) {  //metoda s podmienkou pre nastavenie velkosti hracieho pola
        if (rows > 4) {    //ak je 4 a menej tak sa pouzije defaltna hodnota 4
            this.rowItems = rows;
        }
        if (columns > 4)
            this.columnItems = columns;
        field = new Field(this.rowItems, this.columnItems);
        System.out.println("Hracie pole sa nastavilo na " + this.rowItems + " riadkov a " + this.columnItems + " stlpcov.");
    }


    public void update() {  //vykreslenie pola
        System.out.println();
        for (int i = 0; i < field.getColumnCount(); i++) {
            for (int j = 0; j < field.getRowCount(); j++) {
                if (field.getBoxes(i, j).getValue() != 0) {
                    System.out.printf("%4s", field.getBoxes(i, j).getValue());
                } else {
                    System.out.printf("%4s", "X");
                }
            }
            System.out.println();  //na novy riadok
        }
        //System.out.println("Cas hry: " + Stones.getInstance().getPlayingTime() + "s");
    }

    public void processInput(){
        System.out.println("Game instructions: W - move up, S - move down, A - move left, D - move right, X - exit game");
        String input = reader();
        try{
            handleInput(input);
        }catch (WrongFormatException e){
            System.out.println(e.getMessage());
            processInput();
        }
    }

    void handleInput(String input) throws WrongFormatException{
        //pattern pre vstup
        Pattern patternInput = Pattern.compile("([w,a,s,d,]{1})", Pattern.CASE_INSENSITIVE);
        Matcher matcherInput = patternInput.matcher(input);
        if (matcherInput.matches()) {
            field.moveBox(input);
        }


        //Pattern pre EXIT
        Pattern patternExit = Pattern.compile("[x]{1}", Pattern.CASE_INSENSITIVE);
        //triedu Pattern trebalo naimportovat
        Matcher matcherExit = patternExit.matcher(input);
        if (matcherExit.matches()) {
            System.exit(0);    //prikaz na ukoncenie programu
        }
        if (!(matcherInput.matches()  || matcherExit.matches())) {
            throw new WrongFormatException("Zly vystup");
        }
    }

    //** metoda pre verenie ci je hra vyhrata
    public boolean isGameSolved(){
        int valueComparator = 0;  //premenna pre porovnavanie hodnoty boxu
        int countComparator = 0;  //premenna na porovnavanie poctu spravne ulozenych boxov
        for (int r = 0; r < rowItems; r++) {
            for (int c = 0; c < columnItems; c++) {
                if (field.getBoxes(r, c).getValue() == valueComparator) {  //ak je hodnota boxu zhodna s hodnotou value comparatora tak je box na spravnom
                    //mieste a teda sa countComparator zvysi o 1
                    countComparator++;
                }
                if (countComparator == (rowItems * columnItems)) {  //ak je countComparator zhodny s poctom boxov celeho hracieho pola tak je hra vyriesena
                    return true;  //vrati true
                }
                valueComparator++;
            }
        }
        return false;
    }

    public void play(){
        newGame();
    }

}
