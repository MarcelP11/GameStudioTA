package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Score;
import sk.tacademy.gamestudio.service.ScoreService;

import java.util.List;

//ovladac na ovladanie restovskeho rozhrania
@RestController
@RequestMapping("/api/score")
public class ScoreWebServiceRest {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{game}")
    public List<Score> getBestScores(@PathVariable String game) {   //pathvariable ze sa nacita z cesty
        return scoreService.getBestScores(game);
    }

    @PostMapping
    public void addScore(@RequestBody Score score){
        scoreService.addScore(score);
    }

//    @GetMapping
//    public List<Score> getBestScores(String game) {
//        return scoreService.getBestScores(game);
//    }
//    @GetMapping
//    public String someSimpleService(){
//        return ("Ahoj");
//    }
}
