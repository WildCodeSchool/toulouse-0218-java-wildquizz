package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 09/04/18.
 */

public class ResultatsModel {

    private String nomQCM;
    private String reponse;
    private int logoValidation;
    private boolean isSuccess = true;

    public ResultatsModel(String nomQCM, String reponse, int logoValidation){
        this.nomQCM = nomQCM;
        this.reponse = reponse;
        this.logoValidation = logoValidation;
    }


    private String vraiReponse;


    public ResultatsModel(String nomQCM, String reponse, int logoValidation, String vraiReponse){
        this(nomQCM, reponse, logoValidation);
        this.vraiReponse = vraiReponse;
        this.isSuccess = false;
    }

    public void setNomQCM(String nomQCM) {
        this.nomQCM = nomQCM;
    }
    public String getNomQCM() {
        return nomQCM;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    public String getReponse() {
        return reponse;
    }

    public void setLogoValidation(int logoValidation) {
        this.logoValidation = logoValidation;
    }
    public int getLogoValidation() {
        return logoValidation;
    }


    public void setVraiReponse(String vraiReponse) {
        this.vraiReponse = vraiReponse;
    }
    public String getVraiReponse() {
        return vraiReponse;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
