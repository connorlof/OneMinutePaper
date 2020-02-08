package com.loftydevelopment.oneminutepaper.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaperTest {

    /*
        Compare two equal Papers
     */
    @Test
    void isPapersEqual_identicalProperties_returnTrue() throws Exception {
        // Arrange
        Paper paper1 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper1.setId(1);

        Paper paper2 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper2.setId(1);

        // Act

        // Assert
        Assertions.assertEquals(paper1, paper2);
        System.out.println("The papers are equal!");

    }

    /*
        Compare two Papers with different ids
     */
    @Test
    void isPapersEqual_differentIds_returnFalse() throws Exception {
        // Arrange
        Paper paper1 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper1.setId(1);

        Paper paper2 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper2.setId(2);

        // Act

        // Assert
        Assertions.assertNotEquals(paper1, paper2);
        System.out.println("The papers are not equal!");
    }

    /*
    Compare two papers with different subjects
    */
    @Test
    void isPapersEqual_differentSubjects_returnFalse() throws Exception {
        // Arrange
        Paper paper1 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper1.setId(1);

        Paper paper2 = new Paper("Math", "Gravity is only a theory.", "Why can I not fly?");
        paper2.setId(1);

        // Act

        // Assert
        Assertions.assertNotEquals(paper1, paper2);
        System.out.println("The papers are not equal!");
    }

    /*
    Compare two papers with different main ideas
    */
    @Test
    void isPapersEqual_differentMainIdeas_returnFalse() throws Exception {
        // Arrange
        Paper paper1 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper1.setId(1);

        Paper paper2 = new Paper("Science", "The sky is blue.", "Why can I not fly?");
        paper2.setId(1);

        // Act

        // Assert
        Assertions.assertNotEquals(paper1, paper2);
        System.out.println("The papers are not equal!");
    }

    /*
    Compare two papers with different questions
    */
    @Test
    void isPapersEqual_differentQuestions_returnFalse() throws Exception {
        // Arrange
        Paper paper1 = new Paper("Science", "Gravity is only a theory.", "Why can I not fly?");
        paper1.setId(1);

        Paper paper2 = new Paper("Science", "Gravity is only a theory.", "Is that why people float on the moon?");
        paper2.setId(1);

        // Act

        // Assert
        Assertions.assertNotEquals(paper1, paper2);
        System.out.println("The papers are not equal!");
    }


}
