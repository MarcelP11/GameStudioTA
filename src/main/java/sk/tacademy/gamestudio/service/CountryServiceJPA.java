package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CountryServiceJPA implements CountryService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCountry(Country country) {
        entityManager.persist(country);
    }

    @Override
    public List<Country> getCountries() {
        return entityManager
                .createQuery("select c from Country c")
                .getResultList();
    }
}
