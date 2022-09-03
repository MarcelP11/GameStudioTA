package sk.tacademy.gamestudio.service;


import sk.tacademy.gamestudio.entity.Words;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class WordServiceJPA implements WordService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean getWord(String word) {   //metoda ktora bude vracat true alebo false ak je alebo nie je dane slovo v databaze
        if (entityManager
                .createQuery("select w from Word w where w.word = :myWord")
                .setParameter("myWord", word)
                .getSingleResult() == null) {
            return false;
        }
        return true;
    }

    public List<Words> getWords(int length) {
        return entityManager
                .createQuery("select w.word from Words w where w.length= :length")
                .setParameter("length", length)
                .getResultList();
    }

    public void addWord(Words word){
        entityManager.persist(word);
    };
}
