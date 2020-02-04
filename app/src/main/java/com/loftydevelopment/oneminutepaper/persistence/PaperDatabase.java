package com.loftydevelopment.oneminutepaper.persistence;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.loftydevelopment.oneminutepaper.model.Paper;

@Database(entities = {Paper.class}, version = 1, exportSchema = false)
public abstract class PaperDatabase extends RoomDatabase {
    private static final String LOG_TAG = PaperDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "paper_db";
    private static PaperDatabase sInstance;

    public static PaperDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PaperDatabase.class, PaperDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract PaperDao personDao();
}