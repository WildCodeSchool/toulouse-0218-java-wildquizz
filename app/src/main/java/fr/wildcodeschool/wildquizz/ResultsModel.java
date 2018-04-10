package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 09/04/18.
 */

public class ResultsModel {

    private String nameQcm;
    private String answer;
    private int validateLogo;
    private boolean isSuccess = true;

    public ResultsModel(String nameQcm, String answer, int validateLogo){
        this.nameQcm = nameQcm;
        this.answer = answer;
        this.validateLogo = validateLogo;
    }


    private String goodAnswer;


    public ResultsModel(String nameQcm, String answer, int validateLogo, String goodAnswer){
        this(nameQcm, answer, validateLogo);
        this.goodAnswer = goodAnswer;
        this.isSuccess = false;
    }

    public void setNameQcm(String nameQcm) {
        this.nameQcm = nameQcm;
    }
    public String getNameQcm() {
        return nameQcm;
    }

    public void setAnswer(String reponse) {
        this.answer = reponse;
    }
    public String getAnswer() {
        return answer;
    }

    public void setValidateLogo(int validateLogo) {
        this.validateLogo = validateLogo;
    }
    public int getValidateLogo() {
        return validateLogo;
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer = goodAnswer;
    }
    public String getGoodAnswer() {
        return goodAnswer;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
