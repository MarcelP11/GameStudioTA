package sk.tacademy.gamestudio.entity;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(uniqueConstraints =
//        {@UniqueConstraint(name = "UniqueCountry", columnNames = {"country"})})
public class Occupation {
    @Id
    @GeneratedValue
    private long ident;

    @Column(nullable = false, length = 32,unique = true)
    private String occupation;

    @OneToMany(mappedBy = "ident")
    private List<Player> players;

    public Occupation() {
    }

    public Occupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return occupation;
    }
}
