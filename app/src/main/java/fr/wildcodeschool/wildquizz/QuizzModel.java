package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 23/04/18.
 */

public class QuizzModel {

    private String id;
    private long datetime;
    private String idQcm1;
    private String idQcm2;
    private String idQcm3;
    private String idQcm4;
    private String idQcm5;


   public QuizzModel() {}

    public QuizzModel(String id, long datetime, String idQcm1, String idQcm2, String idQcm3, String idQcm4, String idQcm5) {
        this.id = id;
        this.datetime = datetime;
        this.idQcm1 = idQcm1;
        this.idQcm2 = idQcm2;
        this.idQcm3 = idQcm3;
        this.idQcm4 = idQcm4;
        this.idQcm5 = idQcm5;
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

    public String getIdQcm1() {
        return idQcm1;
    }

    public void setIdQcm1(String idQcm1) {
        this.idQcm1 = idQcm1;
    }

    public String getIdQcm2() {
        return idQcm2;
    }

    public void setIdQcm2(String idQcm2) {
        this.idQcm2 = idQcm2;
    }

    public String getIdQcm3() {
        return idQcm3;
    }

    public void setIdQcm3(String idQcm3) {
        this.idQcm3 = idQcm3;
    }

    public String getIdQcm4() {
        return idQcm4;
    }

    public void setIdQcm4(String idQcm4) {
        this.idQcm4 = idQcm4;
    }

    public String getIdQcm5() {
        return idQcm5;
    }

    public void setIdQcm5(String idQcm5) {
        this.idQcm5 = idQcm5;
    }
}
