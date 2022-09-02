package sk.tacademy.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tacademy.gamestudio.entity.Score;
import sk.tacademy.gamestudio.minesweeper.core.Clue;
import sk.tacademy.gamestudio.minesweeper.core.Tile;
import sk.tacademy.gamestudio.stones.core.Box;
import sk.tacademy.gamestudio.stones.core.Field;
import sk.tacademy.gamestudio.stones.core.GameState;
import sk.tacademy.gamestudio.service.CommentService;
import sk.tacademy.gamestudio.service.RatingService;
import sk.tacademy.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/stones")
//ked dame adresu nasho servera tak ma prevzat konrolu ta metodu ku ktorej je priradena tao cesta teda stones
@Scope(WebApplicationContext.SCOPE_SESSION)   //aby pre kazdeho hraca sa vytvorila nova instancia controllera
public class StoneController {
    private Field field;  //vytvorime pole
    private boolean isPlaying = true;
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserController userController;

    private GameState gamestate;

    @RequestMapping
    //aka sablona sa ma spracovat ked sa spusti controller stones
    public String processUserInput(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {   //aby parametre boli povinne
        startOrUpdateGame(row, column);
        prepareModel(model);
        return "stones";  //vrati sablonu v html subore
    }

    private void startNewGame() {
        this.field = new Field(4, 4);
        this.isPlaying = true;
    }

    private boolean startOrUpdateGame(Integer row, Integer column) {
        boolean justFinished=false;
        if (field == null) {  //ak je pole prazdne tak sa vytvori nove pole
            startNewGame();
        }

        if (this.field.getState() != GameState.PLAYING && this.isPlaying == true) {  //ak je hra vyriesenia alebo fail tak sa zmeni stav isPlaying
            this.isPlaying = false;
            justFinished=true;

            if (userController.isLogged() && this.field.getState()== GameState.SOLVED) {  //ak je pouzival prihlaseny tak sa zapise jeho skore do tabulky inak sa nezapise a ked vyhral tiez
                Score newScore = new Score("Stones", userController.getLoggedUser(), this.field.getScore(), new Date());
                scoreService.addScore(newScore);  //prida sa do databazy zapis noveho skore
            }
        }

        field.setJustFinished(justFinished);  //nastavenie premennej justfinished v poli na hodnotu true alebo false
        return justFinished;


    }

    public boolean isFinished() {   //metoda aby sa k nej dalo cez thymeleaf pristuput
        return this.field.getState() != GameState.PLAYING;
    }
public void settingScore(){
    if (userController.isLogged() && this.field.getState()== GameState.SOLVED) {  //ak je pouzival prihlaseny tak sa zapise jeho skore do tabulky inak sa nezapise a ked vyhral tiez
        Score newScore = new Score("Stones", userController.getLoggedUser(), this.field.getScore(), new Date());
        scoreService.addScore(newScore);  //prida sa do databazy zapis noveho skore
    }
}

    @RequestMapping("/left")
    public String moveLeft(Model model) {
        if(field.getState()==GameState.PLAYING){
            field.moveBox("a");
        }
        settingScore();
        prepareModel(model);
        return "stones";
    }

    @RequestMapping("/right")
    public String moveRight(Model model) {
        if(field.getState()==GameState.PLAYING){
            field.moveBox("d");
        }
        settingScore();
        prepareModel(model);
        return "stones";
    }
    @RequestMapping("/up")
    public String moveUp(Model model) {
        if(field.getState()==GameState.PLAYING){
            field.moveBox("w");
        }
        settingScore();
        prepareModel(model);
        return "stones";
    }
    @RequestMapping("/down")
    public String moveDown(Model model) {
        if(field.getState()==GameState.PLAYING){
            field.moveBox("s");
        }
        settingScore();
        prepareModel(model);
        return "stones";
    }

    @RequestMapping("/new")
    public String newGame(Model model) {
        startNewGame();
        prepareModel(model);
        return "stones";
    }

    public String getGameScore() {
        String result = "";
        if (field.getState() == GameState.SOLVED) {
            //pridavanie hraca do databazy pridat do samostatnej metody pretoze toto je getter a nemal by robit dalsie ine veci
            result = "Your score is:" + String.valueOf(field.getScore());
        }
        return result;
    }

    public String getBoxValue(Box box) {
        return Integer.toString(box.getValue());  //vracia stringovu podobu ciselnej hodnoty boxu
    }



    //metody pre dynamicke riesenie
    @RequestMapping("/asynch")
    public String loadInAsynchMode(){
        startOrUpdateGame(null, null);   //row a column mozu byt null pretoze sme to tak nastavili, vsetko ide priamo cez reqeusty a nechceme aby paramaetre boli v url
        return "stonesAsynch";   //vraciame sablonu s asyncrhonnym rezimom
    }

    @RequestMapping(value="/json", produces= MediaType.APPLICATION_JSON_VALUE)  //nastavime mapping a format udajov ktore sa budu posielat
    @ResponseBody  //toto co vrati tato metoda bude obsahom spravy ktora pojde na klienta
    public Field processUserInputJson(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) {   //aby parametre boli povinne
        startOrUpdateGame(row, column);
        return this.field;  //vraciame konkretny typ ktory sa prevedie do medialnehho typu JSON
    }
    @RequestMapping(value="/jsonnew", produces= MediaType.APPLICATION_JSON_VALUE)  //nastavime mapping a format udajov ktore sa budu posielat
    @ResponseBody  //toto co vrati tato metoda bude obsahom spravy ktora pojde na klienta
    public Field newGameJson() {
        startNewGame();
        this.field.setJustFinished(false);  //ked sa zmeni ovladanie tak sa neskonci hra takze preto sa to nastavuje
        return this.field;
    }

    @RequestMapping(value="/jsonleft", produces= MediaType.APPLICATION_JSON_VALUE)  //nastavime mapping a format udajov ktore sa budu posielat
    @ResponseBody  //toto co vrati tato metoda bude obsahom spravy ktora pojde na klienta
    public Field changeToLeft() {
        this.field.moveBox("a");
       // this.field.setJustFinished(false);  //ked sa zmeni ovladanie tak sa neskonci hra takze preto sa to nastavuje
        settingScore();
        return this.field;
    }

    @RequestMapping(value="/jsonright", produces= MediaType.APPLICATION_JSON_VALUE)  //nastavime mapping a format udajov ktore sa budu posielat
    @ResponseBody  //toto co vrati tato metoda bude obsahom spravy ktora pojde na klienta
    public Field changeToRight() {
        this.field.moveBox("d");
       // this.field.setJustFinished(false);  //ked sa zmeni ovladanie tak sa neskonci hra takze preto sa to nastavuje
        settingScore();
        return this.field;
    }

    @RequestMapping(value="/jsonup", produces= MediaType.APPLICATION_JSON_VALUE)  //nastavime mapping a format udajov ktore sa budu posielat
    @ResponseBody  //toto co vrati tato metoda bude obsahom spravy ktora pojde na klienta
    public Field changeToUp() {
        this.field.moveBox("w");
       // this.field.setJustFinished(false);  //ked sa zmeni ovladanie tak sa neskonci hra takze preto sa to nastavuje
        settingScore();
        return this.field;
    }

    @RequestMapping(value="/jsondown", produces= MediaType.APPLICATION_JSON_VALUE)  //nastavime mapping a format udajov ktore sa budu posielat
    @ResponseBody  //toto co vrati tato metoda bude obsahom spravy ktora pojde na klienta
    public Field changeToDown() {
        this.field.moveBox("s");
       // this.field.setJustFinished(false);  //ked sa zmeni ovladanie tak sa neskonci hra takze preto sa to nastavuje
        settingScore();
        return this.field;
    }


    private void prepareModel(Model model) {
        model.addAttribute("stoneField", field.getBoxes());
        model.addAttribute("bestScores", scoreService.getBestScores("Stones"));
        model.addAttribute("gameStatus", field.getState());
        model.addAttribute("lastComments", commentService.getComments("Stones"));
        model.addAttribute("avgRating", ratingService.getAverageRating("Stones"));
    }

}
