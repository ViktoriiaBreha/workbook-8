package com.pluralsight;

public class Actor {
    private int actorID;
    private String firstName;
    private String lastName;

    public Actor(int actorID, String firstName, String lastName) {
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getActorID() {
        return actorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString (){
        return String.format("ID: %d | %s %s", actorID, firstName, lastName);
    }
}
