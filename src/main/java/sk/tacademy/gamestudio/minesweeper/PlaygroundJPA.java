package sk.tacademy.gamestudio.minesweeper;

import sk.tacademy.gamestudio.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

@Transactional
public class PlaygroundJPA {  //v ramci kazdej jednej metody tvoria jednu transakciu

    @PersistenceContext
    private EntityManager entityManager;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {  //metoda pre vstup od pouzivatela
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

;

    public void play() {
        System.out.println("Opening JPA playground");
        //priklad uloha so zadanim od pouzivatela

//        entityManager.persist(new StudyGroup("zakladna"));
//        entityManager.persist(new StudyGroup("mierne pokrocila"));
//        entityManager.persist(new StudyGroup("pokrocila"));
        List<StudyGroup> studyGroups = entityManager.createQuery("select g from StudyGroup g")
                .getResultList();

        System.out.println("Zadaj meno:");
        String firstName = readLine();
        System.out.println("Zadaj priezvisko:");
        String lastName = readLine();
        System.out.println("Zadaj cislo studijnej skupiny:");
        int noOfGroups = studyGroups.size();
        for (int i = 0; i < noOfGroups; i++) {
            System.out.println(i + " " + studyGroups.get(i));
        }
        int group = Integer.parseInt(readLine());
        entityManager.persist(new Student(firstName, lastName, studyGroups.get(group)));
        //kedze v premennej studyGroups mame ziskany pomocou createQuery a getResult list zoznam
        //tak je to v stave managed a teda je to akoby stale otovrene cize cislo skupiny mozeme rovno
        //pouzit pri vytvarani studenta

        List<Student> students = entityManager.createQuery("select s from Student s")
                .getResultList();
        System.out.println(students);


        //priklad s rucnym vlozenim
//        entityManager.persist(new StudyGroup("zakladna"));
//        entityManager.persist(new StudyGroup("mierne pokrocila"));
//        entityManager.persist(new StudyGroup("pokrocila"));
//
//        String firstName = "Raweel";
//        String lastName = "Powick";
//        int group = 1;
//
//        List<StudyGroup> studyGroups=entityManager.createQuery("select g from StudyGroup g")
//                .getResultList();
//
//        int noOfGroups = studyGroups.size();
//        for(int i =0; i<noOfGroups; i++){
//            System.out.println(i+" " +studyGroups.get(i));
//        }
//
//        entityManager.persist(new Student(firstName,lastName,studyGroups.get(group)));
//
//        List<Student> students=entityManager.createQuery("select s from Student s")
//                .getResultList();
//        System.out.println(students);


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
//
//        String game = "Minesweeper";
//        String user = "Ferko";
//        int ratingValue = 4;
//
//        //entityManager.persist(new Rating(game, user, ratingValue, new Date()));
//
//        Rating rating2Write = null;
//
//        try {
//            rating2Write = (Rating) entityManager.createQuery("select r from Rating r where r.username= :user and r.game = :game")
//                    .setParameter("user", user)
//                    .setParameter("game", game)
//                    .getSingleResult();
//            rating2Write.setRating(ratingValue);
//            rating2Write.setRatedOn(new Date());
//
//        } catch (NoResultException e) {
//            rating2Write = new Rating(game, user, ratingValue, new Date());
//            entityManager.persist(rating2Write);
//            rating2Write.setRating(1);
//        }
//
//        System.out.println(rating2Write);
//        rating2Write = (Rating) entityManager.createQuery("select r from Rating r where r.username= :user and r.game = :game")
//                .setParameter("user", user)
//                .setParameter("game", game)
//                .getSingleResult();
//

        System.out.println("Closing JPA playground");
    }

}
