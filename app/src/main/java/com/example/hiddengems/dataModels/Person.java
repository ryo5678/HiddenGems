package com.example.hiddengems.dataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Person {
    private static final HashMap<String, ArrayList<Users>> people = new HashMap<String, ArrayList<Users>>() {{
        put("Users", new ArrayList<Users>() {{
            add(new Users("JohnDoe", "John Doe", "johndoe@gmail.com", "jdoe", ""));
            add(new Users("JaneDoe", "Jane Love", "janelove@gmail.com", "jlove", ""));
        }});
    }};


    public static ArrayList<Users> getUsers(String type){
        if(people.containsKey(type)) {
            return people.get(type);
        }
        return new ArrayList<Users>();
    }


    public static class Users implements Serializable {
        String displayName;
        String name;
        String email;
        String password;
        String profilePic;

        public Users(String display, String name, String Email, String Password, String ProfilePic) {
            this.displayName = display;
            this.name = name;
            this.email = Email;
            this.password = Password;
            this.profilePic = ProfilePic;
        }

        @Override
        public String toString() {
            return "Users{" +
                    "displayName=" + displayName +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", profilePic='" + profilePic + '\'' +
                    '}';
        }
        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String display) {
            this.displayName = display;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }
}
