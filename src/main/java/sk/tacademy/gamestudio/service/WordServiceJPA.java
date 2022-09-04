package sk.tacademy.gamestudio.service;


import sk.tacademy.gamestudio.entity.Words;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class WordServiceJPA implements WordService {
    @PersistenceContext
    EntityManager entityManager;   //nemoze byt private

    @Override
    public boolean getWord(String word) {   //metoda ktora bude vracat true alebo false ak je alebo nie je dane slovo v databaze
        if (entityManager
                .createQuery("select w from Words w where w.word = :myWord")
                .setParameter("myWord", word)
                .getSingleResult() == null) {
            return false;
        }
        return true;
    }
@Override
    public List<Words> getWords(int length) {
        return entityManager
                .createQuery("select w from Words w where w.length= :length")
                .setParameter("length", length)
                .getResultList();
    }

    @Override
    public List<Words> getWordsByChar(int length, char character) {
        return entityManager
                .createQuery("select w from Words w where w.length= :length and w.word like ':char%'")
                .setParameter("length", length)
                .setParameter("char", character)
                .getResultList();
    }
@Override
    public void addWord(Words word){
        entityManager.persist(word);
    };
}
