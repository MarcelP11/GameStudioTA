package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Player;

import java.util.List;

public interface PlayerService {
    void addPlayer(Player player);
    List<Player> getPlayersByUserName(String uName);
}
