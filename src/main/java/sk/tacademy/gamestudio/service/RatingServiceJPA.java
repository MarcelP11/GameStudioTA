package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {

    }

    @Override
    public int getAverageRating(String name) {
        return (int) entityManager
                .createQuery("select avg(r.rating) from Rating r where r.game = :myGame")
                .setParameter("myGame", name)
                .getSingleResult();
    }

    @Override
    public int getRating(String name, String username) {
        return (int) entityManager
                .createQuery("select r.rating from Rating r where r.username = :myUsername and r.game = :myGame")
                .setParameter("myGame", name)
                .setParameter("myUsername", username)
                .getSingleResult();

    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
