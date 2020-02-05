package com.loftydevelopment.oneminutepaper.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "paper")
public class Paper {

    @PrimaryKey(autoGenerate = true)
    int id;
    String subject;
    String date;
    String mainIdeas;
    String questions;

    @Ignore
    public Paper(String subject, String date, String mainIdeas, String questions) {
        this.subject = subject;
        this.date = date;
        this.mainIdeas = mainIdeas;
        this.questions = questions;
    }

    public Paper(int id, String subject, String date, String mainIdeas, String questions) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.mainIdeas = mainIdeas;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMainIdeas() {
        return mainIdeas;
    }

    public void setMainIdeas(String mainIdeas) {
        this.mainIdeas = mainIdeas;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", mainIdeas='" + mainIdeas + '\'' +
                ", questions='" + questions + '\'' +
                '}';
    }
}
