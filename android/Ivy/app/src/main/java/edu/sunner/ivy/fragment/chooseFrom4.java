package edu.sunner.ivy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.sunner.ivy.fragment.Setting;

/**
 * Created by sunner on 9/3/16.
 */
public class chooseFrom4 extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_practice, container, false);                      // get view object
        setHasOptionsMenu(true);                                                                    // Set menu button
        showMode(getArguments().getInt(Constant.MODE_KEY));                                         // Show the mode parameter

        return view;
    }

    public void showMode(int mode){
        switch (mode){
            case Constant.FUNDAMENTAL:
                Log.d("??", "基礎");
                break;
            case Constant.ADVANCE:
                Log.d("??", "進階");
                break;
            case Constant.STRENGTHEN:
                Log.d("??", "強化");
                break;
            default:
                Log.e("??", "未知參數");
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.practice_setting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        //Log.d("??", String.valueOf(item.getOrder()));
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (item.getOrder()) {
            case 0:
                fragment = new Setting();
                bundle.putInt(Constant.MODE_KEY, getArguments().getInt(Constant.MODE_KEY));
                break;
        }
        fragment.setArguments(bundle);

        // Jump to the fragment
        getActivity().setTitle(item.getTitle());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        return true;
    }
}
