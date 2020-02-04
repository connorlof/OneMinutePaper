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

}
