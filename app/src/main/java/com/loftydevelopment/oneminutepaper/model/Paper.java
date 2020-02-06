package com.loftydevelopment.oneminutepaper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "paper")
public class Paper {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "subject")
    private String subject;
    @ColumnInfo(name = "main_ideas")
    private String mainIdeas;
    @ColumnInfo(name = "questions")
    private String questions;

    public Paper(String subject, String mainIdeas, String questions) {
        this.subject = subject;
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
                ", mainIdeas='" + mainIdeas + '\'' +
                ", questions='" + questions + '\'' +
                '}';
    }
}
