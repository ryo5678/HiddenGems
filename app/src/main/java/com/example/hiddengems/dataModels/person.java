package com.example.hiddengems.dataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class person {
    private final HashMap<String, ArrayList<Users>> apps = new HashMap<String, ArrayList<Users>>() {{
        put("Users", new ArrayList<Users>() {{
            add(new Users("John", "Doe", "johndoe@gmail.com", "jdoe", "https://freesvg.org/img/abstract-user-flat-4.png"));
            add(new Users("Jane", "Love", "janelove@gmail.com", "jlove", "https://freesvg.org/img/abstract-user-flat-4.png"));
        }});
    }};


    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<String>(apps.keySet());
        Collections.sort(users);
        return users;
    }

    public class Users implements Serializable {
        String firstName;
        String lastName;
        String email;
        String password;
        String profilePic;

        public Users(String firstname, String lastname, String Email, String Password, String ProfilePic) {
            this.firstName = firstname;
            this.lastName = lastname;
            this.email = Email;
            this.password = Password;
            this.profilePic = ProfilePic;
        }

        @Override
        public String toString() {
            return "Users{" +
                    "firstName=" + firstName +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", profilePic='" + profilePic + '\'' +
                    '}';
        }
    }
}
