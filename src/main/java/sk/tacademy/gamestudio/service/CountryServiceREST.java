package sk.tacademy.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tacademy.gamestudio.entity.Country;

import java.util.Arrays;
import java.util.List;

public class CountryServiceREST implements CountryService {
    private String url = "http://localhost:8080/api";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addCountry(Country country) {
        restTemplate.postForEntity(url + "/country", country, Country.class);
    }

    @Override
    public List<Country> getCountries() {
        return Arrays.asList(restTemplate.getForEntity(url+"/country/",Country[].class).getBody());
    }
}
