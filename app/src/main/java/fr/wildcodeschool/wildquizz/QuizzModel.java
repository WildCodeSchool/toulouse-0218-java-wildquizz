package fr.wildcodeschool.wildquizz;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilder on 23/04/18.
 */

public class QuizzModel {

    private String id;
    private long datetime;
    private HashMap<String, QcmModel> qcmList;
    private boolean isFinished;


   public QuizzModel() {}

    public QuizzModel(String id, long datetime, HashMap<String, QcmModel> qcmList, boolean isFinished) {
        this.id = id;
        this.datetime = datetime;
        this.qcmList = qcmList;
        this.isFinished = isFinished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public HashMap<String, QcmModel> getQcmList() {
        return qcmList;
    }

    public void setQcmList(HashMap<String, QcmModel> qcmList) {
        this.qcmList = qcmList;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
