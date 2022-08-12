package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        Rating rating2Write = null;
        try {
            rating2Write = (Rating) entityManager.createQuery("select r from Rating r where r.username= :user and r.game = :game")
                    .setParameter("user", rating.getUsername())
                    .setParameter("game", rating.getGame())
                    .getSingleResult();
            rating2Write.setRating(rating.getRating());
            rating2Write.setRatedOn(new Date());

        } catch (NoResultException e) {
            rating2Write = new Rating(rating.getGame(), rating.getUsername(), rating.getRating(), new Date());
            entityManager.persist(rating2Write);
        }
    }

    @Override
    public int getAverageRating(String name) {
        return (int)(double) entityManager
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
