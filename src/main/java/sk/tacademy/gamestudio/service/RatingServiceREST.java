package sk.tacademy.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.entity.Rating;
import sk.tacademy.gamestudio.entity.Score;

public class RatingServiceREST implements RatingService{
    private String url="http://localhost:8080/api";

    //@Value("${remote.server.api}")  //do url sa automaticky vlozi hodnota parametra z aplication.properties
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url+"/rating", rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        return restTemplate.getForObject(url+"/rating/avg/"+game,Rating.class).getRating();
    }

    @Override
    public int getRating(String game, String username) {
        return restTemplate.getForObject(url+"/rating/"+game+"/"+username,Rating.class).getRating();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web.");
    }
}
