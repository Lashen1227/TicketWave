package com.oop.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private boolean isSimulated;
    private String password;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.isSimulated = false;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isSimulated = false;
    }

    public User(String name, String email, boolean isSimulated) {
        this.name = name;
        this.email = email;
        this.isSimulated = isSimulated;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSimulated() {
        return this.isSimulated;
    }

    // ... getters and setters ...
}