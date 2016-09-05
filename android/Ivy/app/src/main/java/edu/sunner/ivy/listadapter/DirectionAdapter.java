package edu.sunner.ivy.listadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * The defined fragment page adapter which would be used in DirectionActivity
 *
 * @author sunner
 * @since 9/3/16.
 */
public class DirectionAdapter extends FragmentPagerAdapter {
    // The list store the fragments
    private final List<Fragment> fragmentlist = new ArrayList<>();

    // The list store the fragment titles
    private final List<String> fragmenttitlelist = new ArrayList<>();

    public DirectionAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmenttitlelist.size();
    }

    /**
     * Add new fragment to the structure.
     *
     * @param fragment the fragment object that want to add
     * @param title    the fragment title
     */
    public void addFrag(Fragment fragment, String title) {
        fragmentlist.add(fragment);
        fragmenttitlelist.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmenttitlelist.get(position);
    }
}
