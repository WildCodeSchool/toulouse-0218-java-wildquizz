package fr.wildcodeschool.wildquizz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by wilder on 03/04/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
    public final static int TAB_INFOS = 0;


    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case TAB_INFOS :
                TabInfosFragment tab1 = new TabInfosFragment();
                return tab1;

            default :
                return null;
        }


    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
