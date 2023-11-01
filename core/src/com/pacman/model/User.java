package com.pacman.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class User {
    private static User loggedInUser = null;
    private static ArrayList<User> allUsers = new ArrayList<>();

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void deleteUser(User user) {
        allUsers.remove(user);
    }

    private String username;
    private String password;
    private int maxScore;
    private Map unFinishedGame;

    public User(String username, String password, Map unFinishedGame, int maxScore) {
        this.username = username;
        this.password = password;
        this.maxScore = maxScore;
        this.unFinishedGame = unFinishedGame;
        allUsers.add(this);
    }

    public static Comparator<User> comparator = (user1, user2) -> {
        return user2.maxScore - user1.maxScore;
    };

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
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

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public Map getUnFinishedGame() {
        return unFinishedGame;
    }

    public void setUnFinishedGame(Map unFinishedGame) {
        this.unFinishedGame = unFinishedGame;
    }

    public static void addToAllUsers(User user) {
        allUsers.add(user);
    }
}
