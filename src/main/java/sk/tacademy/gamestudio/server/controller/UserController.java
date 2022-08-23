package sk.tacademy.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)   //obmedzenie na jedno sedenie na jednu session
public class UserController {

    private String loggedUser;

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

    public String getLoggedUser(){  //metoda ktory hrac je prihlaseny
        return loggedUser;
    }

    public boolean isLogged(){   //metoda ktora vracia true alebo false ak je alebo nie je nejaky hrac prihlaseny
        return loggedUser!=null;
    }
}
