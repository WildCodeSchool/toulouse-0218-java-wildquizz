package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 09/04/18.
 */

public class ResultsModel {

    private String question;
    private String answer;
    private int validateLogo;
    private boolean isSuccess = true;
    public ResultsModel(){

    }

    public ResultsModel(String question, String answer, int validateLogo){
        this.question = question;
        this.answer = answer;
        this.validateLogo = validateLogo;
    }



    private String goodAnswer;


    public ResultsModel(String question, String answer, int validateLogo, String goodAnswer){
        this(question, answer, validateLogo);
        this.goodAnswer = goodAnswer;
        this.isSuccess = false;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
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
