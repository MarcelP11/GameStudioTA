package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Rating;
import sk.tacademy.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingWebServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping    //bud sa urobi post alebo put pre update preto musia byt obidva anotacie
    @PutMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    @GetMapping("/avg/{game}")
    public int getAverageRating(@PathVariable String game){
        return ratingService.getAverageRating(game);
    }

    @GetMapping("/{game}/{username}")
    public int getRating(@PathVariable String game, @PathVariable String username){
        return ratingService.getRating(game,username);
    }

}
