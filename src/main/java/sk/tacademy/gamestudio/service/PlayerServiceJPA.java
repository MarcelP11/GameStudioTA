package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PlayerServiceJPA implements PlayerService {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addPlayer(Player player) {
        entityManager.persist(player);
    }

    @Override
    public List<Player> getPlayersByUserName(String uName) {
        return entityManager.createQuery("select p from Player p where p.userName= :username")
                .setParameter("username", uName)
                .getResultList();
    }
}
