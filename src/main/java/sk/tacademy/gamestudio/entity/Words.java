package sk.tacademy.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Words implements Serializable {
    @Id
    @GeneratedValue
    private long ident;  //id
    private String word;  //slovo
    private int length;

    public Words(String word, int length) {
        this.word = word;
        this.length = length;
    }

    public Words() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Words{" +
                "ident=" + ident +
                ", word='" + word + '\'' +
                ", length=" + length +
                '}';
    }
}
