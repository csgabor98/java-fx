package mink.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "klub")
public class Club {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "csapatnev")
    private String teamName;

    @OneToMany(mappedBy = "clubID")
    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return teamName;
    }
}
