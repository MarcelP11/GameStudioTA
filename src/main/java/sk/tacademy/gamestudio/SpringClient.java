package sk.tacademy.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.tacademy.gamestudio.minesweeper.PlaygroundJPA;
import sk.tacademy.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tacademy.gamestudio.service.*;

@SpringBootApplication
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);   //povieme ze ideme spustat nieco cez Spring Framework teda triedu SpringClient
    }
    @Bean
    public CommandLineRunner runnerJPA(PlaygroundJPA console){  //sluzi na to aby vytvoril objekt na spustenie konzoloveho rozhrania
        return s -> console.play();
    }  //rovnaka metoda ale lisi sa menom a typom
    // @Bean
    public CommandLineRunner runner(ConsoleUI console){  //sluzi na to aby vytvoril objekt na spustenie konzoloveho rozhrania
        return s -> console.play();
    }

    @Bean
    public PlaygroundJPA consoleJPA(){   //factory metoda ktora vytvara produkt resp komponenty
        return new PlaygroundJPA();
    }
    @Bean
    public ConsoleUI console(){   //factory metoda ktora vytvara produkt resp komponenty
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }  //vytvorime beany pre vsetky sluzby
    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }

    //spustame z triedy SpringClient. Inicializaciu v console UI sme zrusili resp zakomentovali a premenne dali na globalnu uroven
}
