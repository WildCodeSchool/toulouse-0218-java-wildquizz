package fr.wildcodeschool.wildquizz;

import android.provider.BaseColumns;

/**
 * Created by wilder on 04/04/18.
 */

public class DbContract {
    public static class QcmEntry implements BaseColumns {
        public static final String TABLE_NAME = "qcm";
        public static final String COLUMN_NAME_QCM = "number";

    }
}
