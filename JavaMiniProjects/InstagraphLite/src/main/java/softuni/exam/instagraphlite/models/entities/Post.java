package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String caption;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Picture picture;

    public Post() {

    }

    public Post(String caption, User user, Picture picture) {
        this.caption = caption;
        this.user = user;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("Successfully imported Post, made by %s", this.user.getUsername());
    }
}
