package com.loftydevelopment.oneminutepaper;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;


import com.loftydevelopment.oneminutepaper.persistence.PaperDao;
import com.loftydevelopment.oneminutepaper.persistence.PaperDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class PaperDatabaseTest {

    private PaperDatabase paperDatabase;

    public PaperDao getPaperDao(){
        return paperDatabase.paperDao();
    }

    @Before
    public void init() {
        paperDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                PaperDatabase.class
        ).build();

    }

    @After
    public void finish() {
        paperDatabase.close();
    }

}
