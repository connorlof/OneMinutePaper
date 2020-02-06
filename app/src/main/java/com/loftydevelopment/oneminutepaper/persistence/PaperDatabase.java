package com.loftydevelopment.oneminutepaper.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.loftydevelopment.oneminutepaper.model.Paper;

@Database(entities = {Paper.class}, version = 1, exportSchema = false)
public abstract class PaperDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "paper_db";
    private static PaperDatabase instance;

    public abstract PaperDao paperDao();

    public static PaperDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        PaperDatabase.class, PaperDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return instance;
    }

}