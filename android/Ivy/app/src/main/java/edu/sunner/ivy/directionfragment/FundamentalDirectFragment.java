package edu.sunner.ivy.directionfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.sunner.ivy.R;

/**
 * Ths one fragment that would be used in DirectionActivity.
 * It shows the used of the fundamental mode.
 *
 * @author sunner
 * @since 9/4/16.
 */
public class FundamentalDirectFragment extends Fragment {

    // The view object
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_direction_fundamental, container, false);

        return view;
    }
}
