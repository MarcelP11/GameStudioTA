package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Country;
import sk.tacademy.gamestudio.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryWebServiceRest {
    @Autowired
    private CountryService countryService;
    @GetMapping
    public List<Country> getCountries() {
        return countryService.getCountries();
    }
    @PostMapping
    public void addCountry(@RequestBody Country country){
        countryService.addCountry(country);
    }
}
