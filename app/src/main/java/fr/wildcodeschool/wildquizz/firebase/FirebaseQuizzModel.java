package fr.wildcodeschool.wildquizz.firebase;

/**
 * Created by wilder on 19/04/18.
 */

public class FirebaseQuizzModel {

    private FirebaseQcmModel qcm1;
    private FirebaseQcmModel qcm2;
    private FirebaseQcmModel qcm3;
    private FirebaseQcmModel qcm4;
    private FirebaseQcmModel qcm5;
    private String id;
    private long datetime;



    // constructeur vide pour firebase

    public FirebaseQuizzModel(String s, long time) {
    }

    public FirebaseQuizzModel(String id) {
        this.id = id;
    }

    public FirebaseQuizzModel(String id,FirebaseQcmModel qcm1, FirebaseQcmModel qcm2, FirebaseQcmModel qcm3, FirebaseQcmModel qcm4, FirebaseQcmModel qcm5, long datetime) {
        this.qcm1 = qcm1;
        this.qcm2 = qcm2;
        this.qcm3 = qcm3;
        this.qcm4 = qcm4;
        this.qcm5 = qcm5;
        this.id = id;
        this.datetime = datetime;

    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public FirebaseQcmModel getQcm5() {
        return qcm5;
    }

    public void setQcm5(FirebaseQcmModel qcm5) {
        this.qcm5 = qcm5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FirebaseQcmModel getQcm1() {
        return qcm1;
    }

    public void setQcm1(FirebaseQcmModel qcm1) {
        this.qcm1 = qcm1;
    }

    public FirebaseQcmModel getQcm2() {
        return qcm2;
    }

    public void setQcm2(FirebaseQcmModel qcm2) {
        this.qcm2 = qcm2;
    }

    public FirebaseQcmModel getQcm3() {
        return qcm3;
    }

    public void setQcm3(FirebaseQcmModel qcm3) {
        this.qcm3 = qcm3;
    }

    public FirebaseQcmModel getQcm4() {
        return qcm4;
    }

    public void setQcm4(FirebaseQcmModel qcm4) {
        this.qcm4 = qcm4;
    }
}
