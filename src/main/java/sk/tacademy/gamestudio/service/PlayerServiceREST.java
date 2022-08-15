package sk.tacademy.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerServiceREST implements PlayerService{
    private String url = "http://localhost:8080/api";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addPlayer(Player player) {
        restTemplate.postForEntity(url + "/player", player, Player.class);
    }

    @Override
    public List<Player> getPlayersByUserName(String uName) {
        return Arrays.asList(restTemplate.getForEntity(url+"/player/"+uName,Player[].class).getBody());
    }
}
