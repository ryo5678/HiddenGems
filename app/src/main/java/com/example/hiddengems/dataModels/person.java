package com.example.hiddengems.dataModels;

import java.io.Serializable;

public class person implements Serializable{
    String firstName;
    String lastName;
    String email;
    String password;

    public person (String firstname,String lastname,String Email, String Password) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = Email;
        this.password = Password;
    }

}

