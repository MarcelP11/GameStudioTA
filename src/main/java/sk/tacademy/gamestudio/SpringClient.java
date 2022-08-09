package sk.tacademy.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import sk.tacademy.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tacademy.gamestudio.service.ScoreService;
import sk.tacademy.gamestudio.service.ScoreServiceJDBC;

@SpringBootConfiguration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);   //povieme ze ideme spustat nieco cez Spring Framework teda triedu SpringClient
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI console){  //sluzi na to aby vytvoril objekt na spustenie konzoloveho rozhrania
        return s -> console.play();
    }
    @Bean
    public ConsoleUI console(){   //factory metoda ktora vytvara produkt resp komponenty
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJDBC();
    }
}
