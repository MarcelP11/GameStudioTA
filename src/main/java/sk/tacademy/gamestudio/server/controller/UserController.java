package sk.tacademy.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tacademy.gamestudio.entity.*;
import sk.tacademy.gamestudio.minesweeper.core.Tile;
import sk.tacademy.gamestudio.service.*;

import java.util.Date;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)   //obmedzenie na jedno sedenie na jednu session
public class UserController {

    private String loggedUser;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;
    @Autowired
    private CountryService countryService;

    @Autowired
    private OccupationService occupationService;
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/register")
    public String register(Model model) {   //registracia noveho pouzivatela
        prepareModel(model);  //pripravime model s krajinami a occupation aby to bolo v zoznamoch
        return "register";  //vratime spat stranku s formularom kde budu aj krajiny aj occupation na vyber
    }

    @RequestMapping("/newplayer")
    public String newPlayer(String username, String fullname, String evaluation, String country, String occupation) {
        if (isValidUsername(username) && isValidFullname(fullname) && isValidEvaluation(evaluation)&&isValidCountry(country)&&isValidOccupation(occupation)) {
            List<Country> countries = countryService.getCountries();  // country a occupation musia byt perzistentne
            int idCountry = Integer.parseInt(country);  //z formulara sa natiahnu cisla
            List<Occupation> occupations = occupationService.getOccupations(); // country a occupation musia byt perzistentne
            int idOccupation = Integer.parseInt(occupation);  //z formulara sa natiahnu cisla
            Player newPlayer = new Player(username, fullname, Integer.parseInt(evaluation), countries.get(idCountry), occupations.get(idOccupation)); //vytovrenie noveho hraca
            playerService.addPlayer(newPlayer);  //pridanie hraca do databazy
            return "redirect:/gamestudio";  //po registracii sa presmeruje na prihlasovaciu stranku
        } else {
            return "redirect:/registration";  //ak su nejake udaje nespravne vrati sa spat na registracnu stranku
        }
    }

    private boolean isValidUsername(String username) {  //vsalidacia dlzky mena
        return (username.trim().length()) > 0 && (username.trim().length()) <= 32;  //vracia true ak je username v pozdaovvanej dlzke
    }

    private boolean isValidFullname(String fullname) {  //vsalidacia dlzky celeho mena
        return (fullname.trim().length()) > 0 && (fullname.trim().length()) <= 128;  //vracia true ak je fullname v pozdaovvanej dlzke
    }

    private boolean isValidEvaluation(String evaluation) {  //vsalidacia spravneho cisla pri sebahodnoteni
        return (Integer.parseInt(evaluation.trim())) > 0 && (Integer.parseInt(evaluation.trim())) <= 10;  //vracia true ak je evaluation v rozmedzi od 1 do 10
    }

 private boolean isValidCountry(String country){  //validacia country
        int sizeOfCountriesList=countryService.getCountries().size();  //vytiahnem si velkost listu
        return Integer.parseInt(country)>=0&&Integer.parseInt(country)<=sizeOfCountriesList;  //porovnavam ci cislo ktore pride z formulara je v danom rozmedzi
   }

    private boolean isValidOccupation(String occupation){  //validacia occupation
        int sizeOfOccupationsList=occupationService.getOccupations().size();  //vytiahnem si velkost listu
        return Integer.parseInt(occupation)>=0&&Integer.parseInt(occupation)<=sizeOfOccupationsList;  //porovnavam ci cislo ktore pride z formulara je v danom rozmedzi

    }
    @RequestMapping("/login")
    public String login(String login, String password) {   //metoda pre prihlasenie, nazvy paramatrov su nazvy tagov atribut name
        this.loggedUser = login.trim();  //osetrenie zadania prihlasovacieho mena bez medzier
        if (isRegistered(loggedUser)) {   //ak je zadany pouzivatel registrovany v databaze tak pokracujeme na dalsie overovanie
            if (("heslo").equals(password) && (this.loggedUser.length() > 0 && this.loggedUser.length() <= 32)) { //ak je heslo heslo a dlzka je v rozmedzi
                return "redirect:/gamestudio";  //vrati sa spat na sablonu ako prihlaseny
            } else {
                this.loggedUser = null;  //ak nie tak sa nastavi ze nie je nikto prihlaseny a sa vrati spat na prihlasovaciu stranku
                return "redirect:/gamestudio";
            }
        } else {
            this.loggedUser = null;  //ak nie je v databaze tak nemoze byt prihlaseny teda aby sa nezobrazovalo: prihlaseny ako:
            return "redirect:/register";  //ak sa nenajde uzivatel v databaze tak sa rovno presmeruje na registracnu strnanku
        }
    }

    @RequestMapping("/logout")
    public String logout() {   //metoda pre odhlasenie
        this.loggedUser = null;
        return "redirect:/gamestudio";  //neide na sablonu ale na predpripravenu stranku
    }

    @RequestMapping("/comment")
    public String comment(String commentIn) {
        if (this.loggedUser != null) {
            Comment newComment = new Comment("Minesweeper", this.loggedUser, commentIn, new Date());
            commentService.addComment(newComment);
        }
        return "redirect:/minesweeper";
    }

    @RequestMapping("/rating")
    public String rating(String rateIn) {
        if (this.loggedUser != null) {
            Rating newRating = new Rating("Minesweeper", this.loggedUser, Integer.parseInt(rateIn), new Date());
            ratingService.setRating(newRating);
        }
        return "redirect:/minesweeper";
    }


    public String getLoggedUser() {  //metoda ktory hrac je prihlaseny
        return loggedUser;
    }    //funkcia vracia meno prihlaseneho hraca

    public boolean isLogged() {   //metoda ktora vracia true alebo false ak je alebo nie je nejaky hrac prihlaseny
        return loggedUser != null;
    }   //funkcia vracia ci je alebo nie je nejaky hrac prihlaseny

    public boolean isRegistered(String username) {  //metoda vracia true alebo false ak je alebo nie je pouzzivatel registrovany
        List<Player> players = playerService.getPlayersByUserName(username);
        int noOfFoundPlayers = players.size();
        if (noOfFoundPlayers == 0) {
            return false;
        }
        return true;
    }

    public String getCountry(Country country) {
        return String.valueOf(country);  //metoda vracia text krajiny
    }

    public String getOccupation(Occupation occupation) {
        return String.valueOf(occupation);  //metoda vracia text profesii
    }


    private void prepareModel(Model model) {
        model.addAttribute("countries", countryService.getCountries());   //pridame do modelu zoznam s krajinami
        model.addAttribute("occupations", occupationService.getOccupations());   //pridame do modelu zoznam s profesiami
    }
}


