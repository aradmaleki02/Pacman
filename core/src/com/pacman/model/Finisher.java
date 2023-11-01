package com.pacman.model;


import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class Finisher {

    public static void finish() throws IOException {
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user.getUsername());
            Gson gson = new Gson();
            String fileAddress = "users/" + user.getUsername() + ".json";

            try (FileWriter writer = new FileWriter(fileAddress)) {
                gson.toJson(user, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
