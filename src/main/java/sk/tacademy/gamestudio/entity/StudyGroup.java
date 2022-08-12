package sk.tacademy.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class StudyGroup {
    @Id
    @GeneratedValue
    private long ident;  //primarny kluc pridame pomocou anotacie
    private String name;
    @OneToMany(mappedBy = "ident")   //vytovrenie relacie medzi students a study group
    private List<Student> students;

    public StudyGroup() {
    }

    public StudyGroup(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }
}
