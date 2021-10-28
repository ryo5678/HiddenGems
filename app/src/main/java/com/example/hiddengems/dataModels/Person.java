package com.example.hiddengems.dataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Person {
    private static final HashMap<String, ArrayList<Users>> people = new HashMap<String, ArrayList<Users>>() {{
        put("Users", new ArrayList<Users>() {{
            add(new Users("John", "Doe", "johndoe@gmail.com", "jdoe", "https://freesvg.org/img/abstract-user-flat-4.png"));
            add(new Users("Jane", "Love", "janelove@gmail.com", "jlove", "https://freesvg.org/img/abstract-user-flat-4.png"));
        }});
    }};


    public static ArrayList<Users> getUsers(String type){
        if(people.containsKey(type)) {
            return people.get(type);
        }
        return new ArrayList<Users>();
    }


    public static class Users implements Serializable {
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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
