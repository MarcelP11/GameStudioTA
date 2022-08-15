package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Player;
import sk.tacademy.gamestudio.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerWebServiceRest {
    @Autowired
    private PlayerService playerService;
    @GetMapping("/{uName}")
    public List<Player> getPlayersByUserName(@PathVariable String uName) {
        return playerService.getPlayersByUserName(uName);
    }
    @PostMapping
    public void addPlayer(@RequestBody Player player){
        playerService.addPlayer(player);
    }
}
