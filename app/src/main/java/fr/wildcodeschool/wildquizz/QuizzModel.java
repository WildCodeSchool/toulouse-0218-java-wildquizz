package fr.wildcodeschool.wildquizz;

import java.util.ArrayList;

/**
 * Created by wilder on 23/04/18.
 */

public class QuizzModel {

    private String id;
    private long datetime;
    private ArrayList<QcmModel> qcmList;
    private boolean isFinished;


   public QuizzModel() {}

    public QuizzModel(String id, long datetime, ArrayList<QcmModel> qcmList, boolean isFinished) {
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

    public ArrayList<QcmModel> getQcmList() {
        return qcmList;
    }

    public void setQcmList(ArrayList<QcmModel> qcmList) {
        this.qcmList = qcmList;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
