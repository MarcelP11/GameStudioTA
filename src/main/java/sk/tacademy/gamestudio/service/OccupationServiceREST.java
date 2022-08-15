package sk.tacademy.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.entity.Occupation;

import java.util.Arrays;
import java.util.List;

public class OccupationServiceREST implements OccupationService{
    private String url = "http://localhost:8080/api";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addOccupation(Occupation occupation) {
        restTemplate.postForEntity(url + "/occupation", occupation, Occupation.class);
    }

    @Override
    public List<Occupation> getOccupations() {
        return Arrays.asList(restTemplate.getForEntity(url+"/occupation/",Occupation[].class).getBody());
    }
}
