package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 28/03/18.
 */

public class DisplayQuizzModel {

    private int  idQuizz;
    private int score;
    private double note;

    public DisplayQuizzModel(int idQuizz,int score, double note) {
        this.idQuizz = idQuizz;
        this.score = score;
        this.note = note;
    }

    public int getIdQuizz() {
        return idQuizz;
    }

    public int getScore() {
        return score;
    }

    public double getNote() {
        return note;
    }

    public void setIdQuizz(int idQuizz) {
        this.idQuizz = idQuizz;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
