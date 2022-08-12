package sk.tacademy.gamestudio.entity;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private long ident;  //primarny kluc pridame pomocou anotacie
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name="StudyGroup.ident", nullable = false)
    private StudyGroup studyGroup;

    public Student() {
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, StudyGroup studyGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studyGroup = studyGroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ident=" + ident +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studyGroup=" + studyGroup +
                '}';
    }
}
