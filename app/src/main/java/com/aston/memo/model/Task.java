package com.aston.memo.model;

import java.util.concurrent.ThreadLocalRandom;

public class Task {

    private long dateCreation;
    private String title;
    private int priority;
    private String description;
    private boolean done;
    private long dateModif;
    private boolean isDeleted;
    private long deadLine;
    private int id;

    public Task(String title, int priority) {
        this.title = title;
        this.priority = priority;
        this.dateCreation = System.currentTimeMillis();
        this.id = ThreadLocalRandom.current().nextInt();
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getDateModif() {
        return dateModif;
    }

    public void setDateModif(long dateModif) {
        this.dateModif = dateModif;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public long getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(long deadLine) {
        this.deadLine = deadLine;
    }

    public int getId() {
        return id;
    }
}
