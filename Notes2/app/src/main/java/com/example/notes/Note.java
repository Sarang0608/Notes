package com.example.notes;

import com.google.firebase.Timestamp;

public class Note {

    private String id,heading,body;
    private boolean isImportant,isCompleted;
    private Timestamp creationTime;

    public Note() {
    }

    public Note(String id, String heading, String body, boolean isImportant,boolean isCompleted,Timestamp creationTime) {
        this.id = id;
        this.heading = heading;
        this.body = body;
        this.isImportant = isImportant;
        this.isCompleted = isCompleted;
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(boolean important) {
        isImportant = important;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }




    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", heading='" + heading + '\'' +
                ", body='" + body + '\'' +
                ", isImportant=" + isImportant +
                ", creationTime=" + creationTime +
                '}';
    }
}
