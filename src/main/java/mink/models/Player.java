package mink.models;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "labdarugo")
public class Player {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "mezszam")
    private int  jersey;

    @Column(name = "klubid")
    private int clubID;

    @Column(name = "posztid")
    private int postID;

    @Column(name = "utonev")
    private String firstName;

    @Column(name = "vezeteknev")
    private String lastName;

    @Column(name = "szulido")
    private Date birthDate;

    @Column(name = "magyar")
    private int isHungarian;

    @Column(name = "ertek")
    private int value;

    @OneToOne
    @JoinColumn(name = "klubid", referencedColumnName = "id", insertable = false, updatable = false)
    private Club club;

    @OneToOne
    @JoinColumn(name = "posztid", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getId() {
        return id;
    }

    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getIsHungarian() {
        return isHungarian;
    }

    public void setIsHungarian(int hungarian) {
        isHungarian = hungarian;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
