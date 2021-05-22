package com.mola.mola.domain;

public class User {

    @Id
    private Long id;
    private String email;
    private String password;
    private int point;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.point = 0;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
