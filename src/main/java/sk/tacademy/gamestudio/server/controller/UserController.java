package sk.tacademy.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tacademy.gamestudio.entity.Comment;
import sk.tacademy.gamestudio.entity.Rating;
import sk.tacademy.gamestudio.service.CommentService;
import sk.tacademy.gamestudio.service.RatingService;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)   //obmedzenie na jedno sedenie na jednu session
public class UserController {

    private String loggedUser;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/login")
    public String login(String login,String password){   //metoda pre prihlasenie
        if(("heslo").equals(password)){
            this.loggedUser=login.trim();  //osetrenie zadania prihlasovacieho mena
            if (this.loggedUser.length()>0){
                return "redirect:/minesweeper";  //neide na sablonu ale na predpripravenu stranku
            }
        }
        this.loggedUser=null;
        return "redirect:/gamestudio";
    }

    @RequestMapping("/logout")
    public String logout(){   //metoda pre odhlasenie
            this.loggedUser=null;
            return "redirect:/minesweeper";  //neide na sablonu ale na predpripravenu stranku
    }

    @RequestMapping("/comment")
    public String comment(String commentIn){
        if(this.loggedUser!=null){
Comment newComment = new Comment("Minesweeper",this.loggedUser,commentIn,new Date());
commentService.addComment(newComment);
        }
        return "redirect:/minesweeper";
    }

    @RequestMapping("/rating")
    public String rating(String rateIn){
        if(this.loggedUser!=null){
            Rating newRating = new Rating("Minesweeper",this.loggedUser,Integer.parseInt(rateIn),new Date());
            ratingService.setRating(newRating);
        }
        return "redirect:/minesweeper";
    }

    public String getLoggedUser(){  //metoda ktory hrac je prihlaseny
        return loggedUser;
    }

    public boolean isLogged(){   //metoda ktora vracia true alebo false ak je alebo nie je nejaky hrac prihlaseny
        return loggedUser!=null;
    }
}
