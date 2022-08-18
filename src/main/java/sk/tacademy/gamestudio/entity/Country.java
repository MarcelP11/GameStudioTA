package sk.tacademy.gamestudio.entity;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(uniqueConstraints =
//        {@UniqueConstraint(name = "UniqueCountry", columnNames = {"country"})})
public class Country {
    @Id
    @GeneratedValue
    private long ident;

    @Column(nullable = false, length = 128,unique = true)
    private String country;
    @OneToMany(mappedBy = "ident")
    private List<Player> players;

    public Country() {
    }

    public Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public long getIdent() {
        return ident;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }
}
