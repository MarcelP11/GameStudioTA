package sk.tacademy.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tacademy.gamestudio.minesweeper.core.Clue;
import sk.tacademy.gamestudio.minesweeper.core.Field;
import sk.tacademy.gamestudio.minesweeper.core.Tile;

import java.util.Date;

//xmlns:th je adresa kde sa najde ako sa dany subor ma pouzivat
//minewseeper controller prebera ulohu consoleUI

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)   //aby pre kazdeho hraca sa vytvorila nova instancia controllera
public class MinesweeperController {
    private Field field = new Field(9, 9, 10);  //vytvorime pole
    //zapismee metodu ktora vypise/vygeneruje hracie pole

    private boolean marking=false; //premenna ci oznacujem alebo otvaram

    @RequestMapping
    //aka sablona sa ma spracovat ked sa spusti controller minewseerper
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) {   //aby parametre boli povinne
        if(row!=null && column !=null){

            if(marking){
                field.markTile(row,column);  //updatne vnutorny stav
            }else{
                field.openTile(row,column);
            }

        }
        return "minesweeper";  //vrati sablonu v html subore
    }

    @RequestMapping("/mark")
    public String changeMarking(){
        marking = !marking;
        return "minesweeper";
    }

    @RequestMapping("/new")
    public String newGame(){
        field=new Field(9,9,10);
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
        sb.append("<table class='minefield>\n");
        for (int row = 0; row < rowCount; row++) {
            sb.append("<tr>\n");
            for (int col = 0; col < colCount; col++) {
                Tile tile =field.getTile(row,col);

                sb.append("<td class='"+getTileClass(tile)+"'> ");
                sb.append("<a href='/minesweeper/?row="+row+"&column="+col+"'>");

                sb.append(getTileText(tile));
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");

        return sb.toString();
    }

    private String getTileText(Tile tile) {
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

    private String getTileClass(Tile tile) {
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
}

