package com.example.picapp;

import java.util.List;

public class FirebaseHelper {

    private String email;
    private String username;
    private String password;
    private List friends;

    public FirebaseHelper(String email, String username, String password, List friends) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.friends = friends;
    }

    public FirebaseHelper() {}

    public List getFriends() {
        return friends;
    }

    public void setFriends(List friends) {
        this.friends = friends;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
