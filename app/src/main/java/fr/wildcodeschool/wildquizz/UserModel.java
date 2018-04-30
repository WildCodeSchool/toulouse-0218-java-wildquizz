package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 24/04/18.
 */

public class UserModel {

    private String username;
    private String avatar;
    private int nbQcm;
    private int score = 0;

    public UserModel() {

    }

    public UserModel(String username, String avatar, int nbQcm, int score) {
        this.username = username;
        this.avatar = avatar;
        this.nbQcm = nbQcm;
        this.score = score;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getNbQcm() {
        return nbQcm;
    }

    public void setNbQcm(int nbQcm) {
        this.nbQcm = nbQcm;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
