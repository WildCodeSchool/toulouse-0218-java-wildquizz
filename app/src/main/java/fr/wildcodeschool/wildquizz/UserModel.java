package fr.wildcodeschool.wildquizz;

import android.graphics.drawable.Drawable;

/**
 * Created by wilder on 23/04/18.
 */

public class UserModel {

    private String idUser;
    private String username;
    private String urlImageProfile;
    private int average;
    private int score;

    public UserModel(String idUser, String username, String urlImageProfile, int average, int score) {
        this.idUser = idUser;
        this.username = username;
        this.urlImageProfile = urlImageProfile;
        this.average = average;
        this.score = score;
    }

    public String getId() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getImageProfile() {
        return urlImageProfile;
    }

    public int getAverage() {
        return average;
    }

    public int getScore() {
        return score;
    }
}
