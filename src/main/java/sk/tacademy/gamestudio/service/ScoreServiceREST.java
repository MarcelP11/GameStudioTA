package sk.tacademy.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceREST implements ScoreService{
    private String url="http://localhost:8080/api";

    //@Value("${remote.server.api}")  //do url sa automaticky vlozi hodnota parametra z aplication.properties
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url+"/score", score,Score.class);
    }

    @Override
    public List<Score> getBestScores(String game) {
        return Arrays.asList(restTemplate.getForEntity(url+"/score/"+game,Score[].class).getBody());
    }

    @Override
    public void reset() {
throw new UnsupportedOperationException("Not supported via web.");
    }
}
