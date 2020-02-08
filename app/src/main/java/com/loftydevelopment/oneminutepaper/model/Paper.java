package com.loftydevelopment.oneminutepaper.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "paper")
public class Paper implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "subject")
    private String subject;
    @ColumnInfo(name = "main_ideas")
    private String mainIdeas;
    @ColumnInfo(name = "questions")
    private String questions;

    public Paper(@NonNull String subject, String mainIdeas, String questions) {
        this.subject = subject;
        this.mainIdeas = mainIdeas;
        this.questions = questions;
    }

    @Ignore
    public Paper(int id, @NonNull String subject, String mainIdeas, String questions) {
        this.id = id;
        this.subject = subject;
        this.mainIdeas = mainIdeas;
        this.questions = questions;
    }

    @Ignore
    public Paper() {
    }

    @Ignore
    public Paper(Paper paper) {
        id = paper.id;
        subject = paper.subject;
        mainIdeas = paper.mainIdeas;
        questions = paper.questions;
    }

    protected Paper(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        mainIdeas = in.readString();
        questions = in.readString();
    }

    public static final Parcelable.Creator<Paper> CREATOR = new Parcelable.Creator<Paper>() {
        @Override
        public Paper createFromParcel(Parcel in) {
            return new Paper(in);
        }

        @Override
        public Paper[] newArray(int size) {
            return new Paper[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subject);
        dest.writeString(mainIdeas);
        dest.writeString(questions);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        Paper paper = (Paper) obj;
        return paper.getId() == getId() && paper.getSubject().equals(getSubject())
                && paper.getMainIdeas().equals(getMainIdeas()) && paper.getQuestions().equals(getQuestions());
    }
}
