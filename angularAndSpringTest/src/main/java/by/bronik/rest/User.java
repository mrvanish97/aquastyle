package by.bronik.rest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private User user;

    /**
     * Constructor for USER
     *
     * @param name
     * @param email
     * @param userss
     * @param ida
     */
    public User(String name, String email, User userss, long ida) {
        this.name = name;
        this.email = email;
        user = userss;
        this.id = ida;
        this.user = new User(){

        };
        // just for fun
        List<User> usersss = new ArrayList<>();
        usersss.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return 0;
            }
        });
        usersss.forEach(u -> System.out.close());
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
