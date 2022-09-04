package sk.tacademy.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.entity.Words;

import java.util.Arrays;
import java.util.List;

public class WordServiceREST implements WordService {
    private String url = "http://localhost:8080/api";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean getWord(String word) {
        return false;  //DOPLNIT KOD
    }

    @Override
    public List<Words> getWords(int length) {
        return Arrays.asList(restTemplate.getForEntity(url+"/word/"+length,Words[].class).getBody());
    }

    @Override
    public void addWord(Words word) {
        restTemplate.postForEntity(url + "/word", word, Words.class);
    }

    @Override
    public List<Words> getWordsByChar(int length, char character) {
        return null;  //doplnit
    }
}
