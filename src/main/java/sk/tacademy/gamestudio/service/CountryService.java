package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Country;

import java.util.List;

public interface CountryService {
    void addCountry(Country country);
    List<Country> getCountries();
}
