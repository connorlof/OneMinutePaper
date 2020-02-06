package com.loftydevelopment.oneminutepaper.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.loftydevelopment.oneminutepaper.model.Paper;

import java.util.List;

@Dao
public interface PaperDao {

    @Query("SELECT * FROM paper ORDER BY ID")
    List<Paper> loadAllPapers();

    @Insert
    void insertPaper(Paper paper);

    @Update
    void updatePaper(Paper paper);

    @Delete
    void deletePaper(Paper paper);

    @Query("SELECT * FROM paper WHERE id = :id")
    Paper loadPaperById(int id);

    @Query("DELETE FROM paper")
    void deleteAllPapers();
}