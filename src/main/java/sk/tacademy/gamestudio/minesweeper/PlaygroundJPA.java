package sk.tacademy.gamestudio.minesweeper;

import sk.tacademy.gamestudio.entity.Comment;
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
        entityManager.persist(new Score("Minesweeper", "Marcelo", 100, new Date()));
        String game = "Minesweeper";
        List<Score> bestScores = entityManager
                .createQuery("select sc from Score sc where sc.game = :myGame order by sc.points desc")
                .setParameter("myGame",game)
                .getResultList();

        System.out.println(bestScores);

//        entityManager.persist(new Comment("Minesweeper", "Marcel", "dasfdasf", new Date()));  //persist metoda nam ulozi objekt do databazy
//        entityManager.persist(new Comment("Minesweeper", "Marcelo", "dsafasf", new Date()));
//
//        List<Comment> topComments = entityManager
//                .createQuery("select cm from comment cm where cm.game = :myGame order by cm.commented_on")
//                .setParameter("myGame",game)
//                .setMaxResults(5)
//                .getResultList();
//        System.out.println(topComments);

        System.out.println("Closing JPA playground");
    }

    public void play2(){
        String game="Minesweeper";
        entityManager.persist(new Comment("Minesweeper", "Marcel", "dasfdasf", new Date()));  //persist metoda nam ulozi objekt do databazy
        entityManager.persist(new Comment("Minesweeper", "Marcelo", "dsafasf", new Date()));

        List<Comment> topComments = entityManager
                .createQuery("select cm from comment cm where cm.game = :myGame order by cm.commented_on")
                .setParameter("myGame",game)
                .setMaxResults(5)
                .getResultList();
        System.out.println(topComments);
    }
}
