package fr.wildcodeschool.wildquizz;

/**
 * Created by wilder on 24/04/18.
 */

public class SingletonClass {

    private static SingletonClass INSTANCE = null;

    private SingletonClass() {

    }
    public static SingletonClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonClass();
        }
        return(INSTANCE);
    }


}
