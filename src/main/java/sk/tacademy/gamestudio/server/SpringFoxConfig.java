package sk.tacademy.gamestudio.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration  //definujeme aby nam to bralo beany, teda aby sa ukladali do kontajnera
public class SpringFoxConfig {

    @Bean   //aby nam nebralo vsetko baliky ale iba webservice
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()   //ziskame
                .apis(RequestHandlerSelectors.basePackage("sk.tacademy.gamestudio.server.webservice"))   //zakladny balik je ten v ktorom mame dane services
                .paths(PathSelectors.any())
                .build();
    }
}
