package sk.tacademy.gamestudio.minesweeper;

import sk.tacademy.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class PlaygroundJPA {  //v ramci kazdej jednej metody tvoria jednu transakciu

    @PersistenceContext
    private EntityManager entityManager;

    public void play() {
        System.out.println("Opening JPA playground");
        entityManager.persist(new Score("Minesweeper", "Marcel", 10, new Date()));  //persist metoda nam ulozi objekt do databazy

        String game = "Minesweeper";
        List<Score> bestScores = entityManager
                .createQuery("select s from Score where s.name = :myGame order by s.points desc")
                .setParameter("myGame",game)
                .getResultList();

        System.out.println(bestScores);

        System.out.println("Closing JPA playground");
    }
}
