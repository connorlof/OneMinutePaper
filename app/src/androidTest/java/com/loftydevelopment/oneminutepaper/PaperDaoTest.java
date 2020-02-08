package com.loftydevelopment.oneminutepaper;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.loftydevelopment.oneminutepaper.model.Paper;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class PaperDaoTest extends PaperDatabaseTest{

    public static final String TEST_SUBJECT = "Science";
    public static final String TEST_MAIN_IDEAS = "Gravity is only a theory.";
    public static final String TEST_QUESTION = "Why can I not fly?";

    public static final Paper TEST_PAPER_1 =
            new Paper("Math", "You cannot divide by 0.", "Why can you multiply by 0?");


    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    /*
        Insert, read, delete
     */
    @Test
    public void insertReadDelete() throws Exception{

        Paper paper = new Paper(TEST_PAPER_1);

        //insert
        getPaperDao().insertPaper(paper);

        //read
        List<Paper> insertedPapers = getPaperDao().loadAllPapers();

        Assert.assertNotNull(insertedPapers);

        Assert.assertEquals(paper.getSubject(), insertedPapers.get(0).getSubject());
        Assert.assertEquals(paper.getMainIdeas(), insertedPapers.get(0).getMainIdeas());
        Assert.assertEquals(paper.getQuestions(), insertedPapers.get(0).getQuestions());

        paper.setId(insertedPapers.get(0).getId());
        Assert.assertEquals(paper, insertedPapers.get(0));

        //delete
        getPaperDao().deletePaper(paper);

        //confirm the database is empty
        insertedPapers = getPaperDao().loadAllPapers();
        Assert.assertEquals(0, insertedPapers.size());

    }

    /*
        Insert, read, update, read, delete
     */
    @Test
    public void insertReadUpdateReadDelete() throws Exception{

        Paper paper = new Paper(TEST_PAPER_1);

        //insert
        getPaperDao().insertPaper(paper);

        //read
        List<Paper> insertedPapers = getPaperDao().loadAllPapers();

        Assert.assertNotNull(insertedPapers);

        Assert.assertEquals(paper.getSubject(), insertedPapers.get(0).getSubject());
        Assert.assertEquals(paper.getMainIdeas(), insertedPapers.get(0).getMainIdeas());
        Assert.assertEquals(paper.getQuestions(), insertedPapers.get(0).getQuestions());

        paper.setId(insertedPapers.get(0).getId());
        Assert.assertEquals(paper, insertedPapers.get(0));

        //update
        paper.setSubject(TEST_SUBJECT);
        paper.setMainIdeas(TEST_MAIN_IDEAS);
        paper.setQuestions(TEST_QUESTION);
        getPaperDao().updatePaper(paper);

        //read
        insertedPapers = getPaperDao().loadAllPapers();

        Assert.assertEquals(TEST_SUBJECT, insertedPapers.get(0).getSubject());
        Assert.assertEquals(TEST_MAIN_IDEAS, insertedPapers.get(0).getMainIdeas());
        Assert.assertEquals(TEST_QUESTION, insertedPapers.get(0).getQuestions());

        paper.setId(insertedPapers.get(0).getId());
        Assert.assertEquals(paper, insertedPapers.get(0));

        //delete
        getPaperDao().deletePaper(paper);

        //confirm the database is empty
        insertedPapers = getPaperDao().loadAllPapers();
        Assert.assertEquals(0, insertedPapers.size());

    }

}
