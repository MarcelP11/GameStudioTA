package sk.tacademy.gamestudio.minesweeper;

import sk.tacademy.gamestudio.entity.Comment;
import sk.tacademy.gamestudio.entity.Rating;
import sk.tacademy.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
//        entityManager.persist(new Score("Minesweeper", "Marcel", 10, new Date()));  //persist metoda nam ulozi objekt do databazy
//        entityManager.persist(new Score("Minesweeper", "Marcelo", 100, new Date()));
//        String game = "Minesweeper";
//        List<Score> bestScores = entityManager
//                .createQuery("select sc from Score sc where sc.game = :myGame order by sc.points desc")
//                .setParameter("myGame",game)
//                .getResultList();
//
//        System.out.println(bestScores);
//
//        entityManager.persist(new Comment("Minesweeper", "Marcel", "dasfdasf", new Date()));  //persist metoda nam ulozi objekt do databazy
//       entityManager.persist(new Comment("Minesweeper", "Marcelo", "dsafasf", new Date()));
//
//       List<Comment> topComments = entityManager
//                .createQuery("select c from Comment c where c.game = :myGame order by c.commentedOn")
//                .setParameter("myGame",game)
//                .setMaxResults(5)
//                .getResultList();
//        System.out.println(topComments);

        String game = "Minesweeper";
        String user = "Ferko";
        int ratingValue = 4;

        //entityManager.persist(new Rating(game, user, ratingValue, new Date()));

        Rating rating2Write = null;

        try {
            rating2Write = (Rating) entityManager.createQuery("select r from Rating r where r.username= :user and r.game = :game")
                    .setParameter("user", user)
                    .setParameter("game", game)
                    .getSingleResult();
            rating2Write.setRating(ratingValue);
            rating2Write.setRatedOn(new Date());

        } catch (NoResultException e) {
            rating2Write = new Rating(game, user, ratingValue, new Date());
            entityManager.persist(rating2Write);
            rating2Write.setRating(1);
        }

        System.out.println(rating2Write);
        rating2Write = (Rating) entityManager.createQuery("select r from Rating r where r.username= :user and r.game = :game")
                .setParameter("user", user)
                .setParameter("game", game)
                .getSingleResult();


        System.out.println("Closing JPA playground");
    }

}
