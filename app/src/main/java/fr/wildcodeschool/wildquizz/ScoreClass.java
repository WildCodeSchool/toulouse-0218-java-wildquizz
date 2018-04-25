package fr.wildcodeschool.wildquizz;

import java.util.List;

/**
 * Created by wilder on 24/04/18.
 */

public class ScoreClass {

    public static int foundAverage(int[] tab) {
        int total = 0;
        for (int i = 0; i < tab.length; i++) {
            total = tab[i] + total;
        }
        return total / tab.length;
    }

    public static int foundQuizzScore(int[] scoreQcm) {
        int totalScore = 0;
        for (int i = 0; i < scoreQcm.length; i++) {
            totalScore = totalScore + scoreQcm[i];
        }
        return totalScore;
    }

    public static int foundUserScore(int score, int[] scoreQcm) {
        int totalUser = 0;
        totalUser = score + foundQuizzScore(scoreQcm);
        return totalUser;
    }

    public static int getMedal(int totalUser) {
        if (totalUser >= 200) {
            return 4;
        } else if (totalUser >= 150) {
            return 3;
        } else if (totalUser >= 100) {
            return 2;
        } else if (totalUser >= 50) {
            return 1;
        }
        return 0;


    }


}
