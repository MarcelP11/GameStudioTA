package sk.tacademy.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tacademy.gamestudio.entity.Score;
import sk.tacademy.gamestudio.minesweeper.core.Clue;
import sk.tacademy.gamestudio.minesweeper.core.Field;
import sk.tacademy.gamestudio.minesweeper.core.GameState;
import sk.tacademy.gamestudio.minesweeper.core.Tile;
import sk.tacademy.gamestudio.service.CommentService;
import sk.tacademy.gamestudio.service.RatingService;
import sk.tacademy.gamestudio.service.ScoreService;
import sk.tacademy.gamestudio.service.ScoreServiceJPA;

import java.util.Date;

//xmlns:th je adresa kde sa najde ako sa dany subor ma pouzivat
//minewseeper controller prebera ulohu consoleUI

@Controller
@RequestMapping("/minesweeper")  //ked dame adresu nasho servera tak ma prevzat konrolu ta metodu ku ktorej je priradena tao cesta teda minesweeper
@Scope(WebApplicationContext.SCOPE_SESSION)   //aby pre kazdeho hraca sa vytvorila nova instancia controllera
public class MinesweeperController {
    private Field field = new Field(9, 9, 10);  //vytvorime pole
    //zapismee metodu ktora vypise/vygeneruje hracie pole

    private boolean marking=false; //premenna ci oznacujem alebo otvaram

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
    //aka sablona sa ma spracovat ked sa spusti controller minewseerper
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {   //aby parametre boli povinne
        if(row!=null && column !=null){
            if(this.field.getState()==GameState.PLAYING){  //osetrenie  - robi sa iba ak je stav PLAYING
                if(marking){
                    field.markTile(row,column);  //updatne vnutorny stav
                }else{
                    field.openTile(row,column);
                }
            }
        }

        if(this.field.getState()!=GameState.PLAYING && this.isPlaying==true){  //ak je hra vyriesenia alebo fail tak sa zmeni stav isPlaying
            this.isPlaying=false;

            if(userController.isLogged()){  //ak je pouzival prihlaseny tak sa zapise jeho skore do tabulky inak sa nezapise
                Score newScore = new Score("Minesweeper", userController.getLoggedUser(),this.field.getScore(),new Date());
                scoreService.addScore(newScore);  //prida sa do databazy zapis noveho skore
            }

        }


        prepareModel(model);
        return "minesweeper";  //vrati sablonu v html subore
    }

    @RequestMapping("/mark")
    public String changeMarking(Model model){
        marking = !marking;
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/new")
    public String newGame(Model model){
        field=new Field(9,9,10);
        prepareModel(model);
        return "minesweeper";
    }


    public String getCurrTime() {   //do html budeme chciet vlozit retazec
        return new Date().toString();
    }

    public boolean getMarking(){
        return marking;
    }

    public String getFieldAsHtml(){
        int rowCount = field.getRowCount();
        int colCount = field.getColumnCount();

        StringBuilder sb = new StringBuilder();
        sb.append("<table class='minefield'>\n");
        for (int row = 0; row < rowCount; row++) {
            sb.append("<tr>\n");
            for (int col = 0; col < colCount; col++) {
                Tile tile =field.getTile(row,col);

                sb.append("<td class='"+getTileClass(tile)+"'> ");
                sb.append("<a href='/minesweeper?row="+row+"&column="+col+"'>");

                sb.append("<span>" + getTileText(tile) + "</span>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");

        return sb.toString();
    }


    public String getTileText(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return "-";
            case MARKED:
                return "M";
            case OPEN:
                if (tile instanceof Clue) {
                    return String.valueOf(((Clue) tile).getValue());
                } else {
                    return "X";
                }
            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
        }
    }

    public String getTileClass(Tile tile) {
        switch (tile.getState()) {
            case OPEN:
                if (tile instanceof Clue)
                    return "open" + ((Clue) tile).getValue();
                else
                    return "mine";
            case CLOSED:
                return "closed";
            case MARKED:
                return "marked";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }
public String getGameScore(){
       String result="";
        if(field.getState()==GameState.SOLVED){
            //pridavanie hraca do databazy pridat do samostatnej metody pretoze toto je getter a nemal by robit dalsie ine veci
            result ="Your score is:"+ String.valueOf(field.getScore());
        }
        return result;
    }

    //vytvorime metodu ktora pripravi model
    private void prepareModel(Model model){
        model.addAttribute("message","Sprava z modelu");
        model.addAttribute("minesweeperField", field.getTiles());
        model.addAttribute("bestScores", scoreService.getBestScores("Minesweeper"));
        model.addAttribute("gameStatus", field.getState());
        model.addAttribute("lastComments", commentService.getComments("Minesweeper"));
        model.addAttribute("avgRating", ratingService.getAverageRating("Minesweeper"));
    }
}

