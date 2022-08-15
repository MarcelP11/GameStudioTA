package sk.tacademy.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.minesweeper.PlaygroundJPA;
import sk.tacademy.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tacademy.gamestudio.service.*;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "sk.tacademy.gamestudio.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);   //povieme ze ideme spustat nieco cez Spring Framework teda triedu SpringClient

   //new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);   //aby nespustal so spustenim hry aj server
    }
    //@Bean
    public CommandLineRunner runnerJPA(PlaygroundJPA console){  //sluzi na to aby vytvoril objekt na spustenie konzoloveho rozhrania
        return s -> console.play();
    }  //rovnaka metoda ale lisi sa menom a typom
    @Bean
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
       // return new ScoreServiceREST();
    }  //vytvorime beany pre vsetky sluzby

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
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
    public PlayerService playerService(){
        return new PlayerServiceJPA();
    }
    @Bean
    public OccupationService occupationService(){
        return new OccupationServiceJPA();
    }
    @Bean
    public CountryService countryService(){
        return new CountryServiceJPA();
    }

    //spustame z triedy SpringClient. Inicializaciu v console UI sme zrusili resp zakomentovali a premenne dali na globalnu uroven
}
