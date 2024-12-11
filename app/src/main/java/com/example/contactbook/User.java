package com.example.contactbook;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    //private String id;
    private String name;
    private String number;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User(){

    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public User(String name, String number) {
        this.name = name;
        this.number = number;
       // this.id = id;
    }

    public String getNumber() {
        return number;
    }


    public String getName() {
        return name;
    }

}
