package com.example.demo.items;

import com.example.demo.Category;
import com.example.demo.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(
        name = "items"
)
public class Items {

    @Id
    @SequenceGenerator(
            name = "items_sequence",
            sequenceName = "items_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "items_sequence"
    )
    @Column(
        name = "id",
        updatable = false
    )
    Long id;

    @Column(
        name = "name",
        columnDefinition = "TEXT",
        nullable = false
    )
    String name;

    @Column(
            name = "rate"
    )
    int rate;

    @Column(
            name = "rate_counter"
    )
    int rateCounter;

    @Enumerated(
            EnumType.STRING
    )
    Category category;

    @ManyToMany
    @JoinTable(
            name = "items_user",
            inverseJoinColumns = {@JoinColumn(name = "id")}
    )
    private Set<User> enrolledUser = new HashSet<User>();


    public Items(String name) {
        this.name = name;
    }

    public Items() {

    }

    public Set<User> getEnrolledUser() {
        return enrolledUser;
    }

    public void setEnrolledUser(Set<User> enrolledUser) {
        this.enrolledUser = enrolledUser;
    }

    public void enrollUser(User user){
        enrolledUser.add(user);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRateCounter() {
        return rateCounter;
    }

    public void setRateCounter(int rateCounter) {
        this.rateCounter = rateCounter;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", rateCounter=" + rateCounter +
                ", category=" + category +
                ", enrolledUser=" + enrolledUser +
                '}';
    }
}
