package sk.tacademy.gamestudio.service;


import sk.tacademy.gamestudio.entity.Player;
import sk.tacademy.gamestudio.entity.Words;

import java.util.List;

public interface WordService {

    boolean getWord(String word);

    List<Words> getWords(int length);
    void addWord(Words word);

}
