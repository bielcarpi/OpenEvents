package com.grup8.OpenEvents.model.entities;

public class User {
    private int id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String image;
    private float avgScore;
    private int numComments;
    private float percentageCommentersBelow;

    public User(int id, String name, String lastName, String email, String image){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.image = image;
    }

    public User(String name, String lastName, String email, String password, String image){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(float avgScore, int numComments, float percentageCommentersBelow){
        this.avgScore = avgScore;
        this.numComments = numComments;
        this.percentageCommentersBelow = percentageCommentersBelow;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getImage() {
        return image;
    }
    public float getAvgScore() {
        return avgScore;
    }
    public int getNumComments() {
        return numComments;
    }
    public float getPercentageCommentersBelow() {
        return percentageCommentersBelow;
    }
}
