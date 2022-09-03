package sk.tacademy.gamestudio.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import sk.tacademy.gamestudio.SpringClient;
import sk.tacademy.gamestudio.minesweeper.PlaygroundJPA;
import sk.tacademy.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tacademy.gamestudio.service.*;

@SpringBootApplication   //obsahuje v sebe controller ktora hovori ze tu na budu beany
@EntityScan(basePackages = "sk.tacademy.gamestudio.entity")  //pretoze balik entity je na inej urovni tak sa dala tato anotacia EntityScan
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);   //povieme ze ideme spustat nieco cez Spring Framework teda triedu SpringClient
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
    @Bean
    public StudentServiceJPA studentServiceJPA(){
        return new StudentServiceJPA();
    }
    @Bean
    public StudentGroupServiceJPA studentGroupServiceJPA(){
        return new StudentGroupServiceJPA();
    }
    @Bean
    public CountryService countryService(){
        return new CountryServiceJPA();
    }
    @Bean
    public OccupationService occupationService(){
        return new OccupationServiceJPA();
    }
    @Bean
    public PlayerService playerService(){
        return new PlayerServiceJPA();
    }
    @Bean
    public WordService wordService(){
        return new WordServiceJPA();
    }

    //spustame z triedy SpringClient. Inicializaciu v console UI sme zrusili resp zakomentovali a premenne dali na globalnu uroven
}
