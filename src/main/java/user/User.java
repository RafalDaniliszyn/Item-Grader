package com.example.demo.user;

import com.example.demo.items.Items;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(
        name = "users"
)
public class User {

    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "users_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    Long id;

    @Column(
            name = "user_ip",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true
    )
    String userIp;

    @Column(
            name = "password",
            columnDefinition = "TEXT",
            unique = true
    )
    String password;

    @Column(
            name = "email",
            columnDefinition = "TEXT",
            unique = true
    )
    String email;

    @Column(
            name = "confirmationKey",
            columnDefinition = "TEXT"
    )
    String confirmationKey;

    @Column(
            name = "isConfirmated",
            columnDefinition = "boolean default false",
            nullable = false
    )
    boolean isConfirmated;


    @ManyToMany(mappedBy = "enrolledUser")
    private Set<Items> items= new HashSet<Items>();


    public User() {
    }

    public User(String userIp) {
        this.userIp = userIp;
    }


    public User(String userIp, String password, String email) {
        this.userIp = userIp;
        this.password = password;
        this.email = email;
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public boolean isConfirmated() {
        return isConfirmated;
    }

    public void setConfirmated(boolean confirmated) {
        isConfirmated = confirmated;
    }

    public String getConfirmationKey() {
        return confirmationKey;
    }

    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Items> getItems() {
        return items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", isConfirmated=" + isConfirmated +
                ", itemsRated=" + items.size() +
                '}';
    }
}
