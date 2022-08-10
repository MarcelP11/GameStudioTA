package sk.tacademy.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;
@Entity  //toto anotaciou davame informaciu
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private long ident;  //primarny kluc pridame pomocou anotacie
    private String game;
    private String username;
    private int points;
    private Date playedOn;

    public Score() {
    }

    public Score(String game, String username, int points, Date playedOn) {
        this.game = game;
        this.username = username;
        this.points = points;
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        Formatter f = new Formatter();
        f.format("%-40s%-40s%s\n", username, points,playedOn);
        return f.toString();

//        return "Score{" +
//                "game='" + game + '\'' +
//                ", username='" + username + '\'' +
//                ", points=" + points +
//                ", playedOn=" + playedOn +
//                '}';
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }
}