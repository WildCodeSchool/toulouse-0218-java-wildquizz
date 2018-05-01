package fr.wildcodeschool.wildquizz;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wilder on 23/04/18.
 */

public class QuizzModel implements Parcelable{

    private String id;
    private long datetime;
    private HashMap<String, QcmModel> qcmList;
    private boolean isFinished;

    public QuizzModel(){

    }

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

    //MEHODE QUI RETOURNE LES VALEURS DES ATTRIBUTS DE LA CLASSE
    @Override
    public int describeContents() {
        return Integer.parseInt(getId()+ getDatetime() + getQcmList() + isFinished());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(String.valueOf(datetime));
        dest.writeString(String.valueOf(qcmList));
        dest.writeInt(isFinished ? 1 : 0);

    }

    //Création d'un objet CREATOR avec le constructeur Parcel pour reconstruire l'objet à partir d'un parcel. Indique comment l'objet est créé
    public static final Creator<QuizzModel> CREATOR = new Creator<QuizzModel>() {
        @Override
        public QuizzModel createFromParcel(Parcel in) {
            return new QuizzModel(in);
        }
        @Override
        public QuizzModel[] newArray(int size) {
            return new QuizzModel[size];
        }
    };

    protected QuizzModel(Parcel in) {
        id = in.readString();
        datetime = Long.parseLong(in.readString());
        isFinished = in.readInt() == 1;

    }

}
