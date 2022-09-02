package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class WordServiceJPA {

    public List<Word> getWord(String word) {
        return entityManager
                .createQuery("select c from Comment c where c.game = :myGame order by c.commentedOn desc")
                .setParameter("myGame",game)
                .setMaxResults(5)
                .getResultList();
    }
}
