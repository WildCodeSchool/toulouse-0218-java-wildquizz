package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 09/04/18.
 */

public class ResultsModel {

    private String question;
    private int validateLogo;
    private int unvalidateLogo;
    private boolean isSuccess = true;
    private int score;

    public ResultsModel() {

    }


    public ResultsModel(String question, int validateLogo, int score) {
        this.question = question;
        this.validateLogo = validateLogo;
        this.score = score;
    }
    public ResultsModel(String question, int unvalidateLogo, int score){
        this.question = question;
        this.unvalidateLogo = unvalidateLogo;
        this.score;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getValidateLogo(int logo_check1) {
        return validateLogo;
    }

    public void setValidateLogo(int validateLogo) {
        this.validateLogo = validateLogo;
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
