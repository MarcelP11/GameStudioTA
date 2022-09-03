package sk.tacademy.gamestudio.wordchain.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tacademy.gamestudio.entity.Comment;
import sk.tacademy.gamestudio.entity.Rating;
import sk.tacademy.gamestudio.entity.Score;
import sk.tacademy.gamestudio.entity.Words;
import sk.tacademy.gamestudio.service.*;
import sk.tacademy.gamestudio.wordchain.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {
    private String userName = "";
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private int wordCountInLevels = 3;
    private GameState state = GameState.PLAYING;
    private int remainingAttempts = 5;
    private int correctCount = 0;
    private String word = "";
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
    @Autowired
    private WordService wordService;


    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGameStarted() {
        int gameScore = 0;   //nastavenie pociatocneho skore na 0
        processInputUsername();  //zadanie hracskeho mena
//wordService.addWord(new Words("sova",4));
//wordService.addWord(new Words("ahoj",4));
//wordService.addWord(new Words("maco",4));
//wordService.addWord(new Words("skrt",4));

        generateFirstWord();    //do globalnej premennej ulozime hodnotu nahodneho slova s dlzkou 4 z databazy
        System.out.println("generated word is" + word);
        /*
        do {
            processInput(4);
            checkAttempts();
            if (state == GameState.SOLVED) {  //overovanie stavu hry
                //**PRIDAT GAME SCORE
                System.out.println(userName + " ,you win!!! Your score is: " + ".");  //ak vyhra tak sa vypise tento text a vyskoci zo slucky
                break;
            } else if (state == GameState.FAILED) {
                System.out.println(userName + " ,you lose!!!");  //ak prehra tak sa vypise tento text a vyskoci sa zo slucky
                break;
            }
        } while (true);

        */
        Score player_score = new Score("Wordchain", userName, gameScore, new Date());  //vytvorenie noveho objektu score
        if (state != GameState.FAILED) {
            scoreService.addScore(player_score);  //ak hrac neprehral tak sa zapise skore do databazy
            var scores = scoreService.getBestScores("Wordchain");
            System.out.printf("\nTable of best players:\n%s\n\n", scores);  //vypis skore
        }
        processInputComment();  //pridanie a vypis komentarov
        processInputRate();  //pridanie a vypis ratingu
    }

    public void update() {
    }

    ;

    public void checkWordInDatabase(String word) {  //metoda ktora skontroluje ci je slovo v databaze a ak tak prida do countera +1 a vrati true alebo false
        if (isWordInDatabase(word) == true) {
            correctCount++;  //do premennej so spravnymi zadaniami sa prida +1
            System.out.println("super");

        } else {  //ak neexistuje tak sa vypise ze neexistuje a remainingAttempts sa znizi o 1
            System.out.println("Word is not exist in database.");
            remainingAttempts--;
        }
    }

    public boolean isWordInDatabase(String word) {
        if (wordService.getWord(word)) {
            return true;
        }
        return false;
    }

    public void handleInputUsername(String inputUsername) throws WrongFormatException {
        Pattern pattern2 = Pattern.compile(".{1,64}");
        Matcher matcher2 = pattern2.matcher(inputUsername);
        if (matcher2.matches()) {
            userName = inputUsername;
            System.out.println("Welcome " + userName + ".");
        } else {
            throw new WrongFormatException("Wrong input. Please insert at least one character. Maximum length of username is 64 characters.");
        }
    }

    public void generateFirstWord() {
        List<Words> words = wordService.getWords(4);  //natiahneme si zoznam slov s dlzkou 4 znaky
        for (int i = 0; i < words.size(); i++) {
            System.out.printf("%-5d%s\n", i, words.get(i));

        }
        int plrNo = Integer.parseInt(readLine());
        word=words.get(plrNo).getWord();

//        String generatedWord;   //inicializacia premennej ktoru budeme vracat
//        Random randomNumber = new Random();    //vytvorenie objektu nahodnenho cisla
//        int wRandom = randomNumber.nextInt(words.size());    //vygenerovanie nahodneho cisla z rozsahu 0 az do dlzky listu -1
//        word = words.get(wRandom).getWord();   //do premennej ulozime hodnotu zo zo stlpca word
    }

    private void checkAttempts() {
        System.out.println("Your remaining attempts are: " + remainingAttempts);  //vypis s poctom zostavajucich pokusov
        if (remainingAttempts == 0) {  //ak sa vycerpal pocet pokusov tak sa hra skonci
            state = GameState.FAILED;
        }
    }

    private void processInputUsername() {
        // throw new UnsupportedOperationException("Method processInput not yet implemented");
        System.out.println("Please enter your username:");
        String usernameToCheck = readLine();
        try {
            handleInputUsername(usernameToCheck);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInputUsername(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    private void processInput(int wordLength) {
        System.out.println("Please input word (or type X for exit game):");  //vypis pre zadnaie slova
        String input = readLine();  //nacitanie vstupu od pouzivatela do premennej input
        try {
            handleInput(input, wordLength);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
            checkWordInDatabase(input);  //skontrolujeme ci je slovo v databaze a bud sa prida 1 do spravnych slov alebo sa znizi remainingAttempts
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            // processInput(wordLength); //ak vyskoci chyba tak chceme nanovo spracovat vstup - nemozem pouzit pretoze by sa to zacyklilo
        }
    }

    void handleInput(String input, int wordLength) throws WrongFormatException {   //throws WrongFromatException znamena ze sa moze vyskytnut chyba ciza tato klasa
        //Pattern pre slovo a pre spravnu dlzku slova
        String regex = "([a-z]{" + wordLength + "})"; // vytvorenie premennej regexu so spravnou dlzkou slova
        Pattern patternWord = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcherWord = patternWord.matcher(input);
        if (matcherWord.matches()) {
            int row = matcherWord.group(1).charAt(0) - 'a';
//vlozit metodu ktora vykona nieco
        }

        //Pattern pre EXIT
        Pattern patternExit = Pattern.compile("[x]{1}", Pattern.CASE_INSENSITIVE);
        Matcher matcherExit = patternExit.matcher(input);
        if (matcherExit.matches()) {
            System.exit(0);    //prikaz na ukoncenie programu
        }

        if (!(matcherWord.matches() || matcherExit.matches())) {
            remainingAttempts--;  //ak sa zada slovo so zlou dlzkou ako je aktualna alebo nejake iny vstup tak sa premenna wrong inputs zvysi o 1
            //remainingAttempts sa musi zvysit o 1 aj ked sa slovo nenachadza v databaze
            throw new WrongFormatException("Wrong input.");
        }
    }

    //*KOMENTY
    public void handleInputComment(String inputComment) throws WrongFormatException {
        Pattern pattern = Pattern.compile(".{1,64}");
        Matcher matcher = pattern.matcher(inputComment);
        if (matcher.matches()) {
            Comment player_comment = new Comment("Wordchain", userName, inputComment, new Date());
            //CommentService commentService = new CommentServiceJDBC();
            commentService.addComment(player_comment);
            var comments = commentService.getComments("Wordchain");
            System.out.println(comments);
        } else {
            throw new WrongFormatException("Wrong input. Please insert at least one character. Maximum length of comment is 64 characters.");
        }
    }

    private void processInputComment() {
        System.out.println("Please write your comment:");
        var userComment = readLine();
        try {
            handleInputComment(userComment);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInputComment(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    public void handleInputRate(String inputRate) throws WrongFormatException {
        Pattern pattern = Pattern.compile("[1,2,3,4,5]{1}");
        Matcher matcher = pattern.matcher(inputRate);
        if (matcher.matches()) {
            Rating player_rating = new Rating("Wordchain", userName, Integer.parseInt(inputRate), new Date());
            //RatingService ratingService = new RatingServiceJDBC();
            ratingService.setRating(player_rating);
            var averageRating = ratingService.getAverageRating("Wordchain");
            System.out.println("Thank you for your rating. Average rating of game Minesweeper is: " + averageRating);
        } else {
            throw new WrongFormatException("Wrong input. Please rate from 1 to 5.");
        }
    }


    //*RATING
    private void processInputRate() {
        System.out.println("Please rate the game from 1 to 5:");
        var userRating = readLine();

        try {
            handleInputRate(userRating);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInputRate(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }


}
