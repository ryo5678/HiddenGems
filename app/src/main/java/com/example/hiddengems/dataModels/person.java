package com.example.hiddengems.dataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class person {
    private final HashMap<String, ArrayList<Users>> apps = new HashMap<String, ArrayList<Users>>() {{
        put("Users", new ArrayList<Users>() {{
            add(new Users("JohnDoe", "John Doe", "johndoe@gmail.com", "jdoe", "https://freesvg.org/img/abstract-user-flat-4.png"));
            add(new Users("JaneDoe", "Jane Love", "janelove@gmail.com", "jlove", "https://freesvg.org/img/abstract-user-flat-4.png"));
        }});
    }};


    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<String>(apps.keySet());
        Collections.sort(users);
        return users;
    }

    public class Users implements Serializable {
        String userName;
        String name;
        String email;
        String password;
        String profilePic;

        public Users(String UserName, String Name, String Email, String Password, String ProfilePic) {
            this.userName = UserName;
            this.name = Name;
            this.email = Email;
            this.password = Password;
            this.profilePic = ProfilePic;
        }

        @Override
        public String toString() {
            return "Users{" +
                    "userName=" + userName +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", profilePic='" + profilePic + '\'' +
                    '}';
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }
}
