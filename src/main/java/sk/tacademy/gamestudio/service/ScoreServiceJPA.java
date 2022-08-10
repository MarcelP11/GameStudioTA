package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@PersistenceContext
public class ScoreServiceJPA implements ScoreService{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getBestScores(String game) {
        return entityManager
                .createQuery("select s from Score where s.name = :myGame order by s.points desc")
                .setParameter("myGame",game)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset() {
        //vyuzijeme nativnu query teda klasicky sql
        entityManager.createNativeQuery("DELETE FROM score").executeUpdate();

    }
}
