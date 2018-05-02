package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 28/03/18.
 */

public class DisplayQuizzModel {

    private String idQuizz;
    private int score;
    private float note;


    public DisplayQuizzModel() {

    }

    public DisplayQuizzModel(String idQuizz,int score, float note) {
        this.idQuizz = idQuizz;
        this.score = score;
        this.note = note;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getIdQuizz() {
        return idQuizz;
    }

    public int getScore() {
        return score;
    }


    public void setIdQuizz(String idQuizz) {
        this.idQuizz = idQuizz;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
