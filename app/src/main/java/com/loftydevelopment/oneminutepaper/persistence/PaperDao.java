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

    @Query("SELECT * FROM PAPER ORDER BY ID")
    List<Paper> loadAllPapers();

    @Insert
    void insertPaper(Paper paper);

    @Update
    void updatePaper(Paper paper);

    @Delete
    void delete(Paper paper);

    @Query("SELECT * FROM PAPER WHERE id = :id")
    Paper loadPaperById(int id);
}