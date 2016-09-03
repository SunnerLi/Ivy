package edu.sunner.ivy.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import edu.sunner.ivy.ListAdapter.ListenListAdapter;
import edu.sunner.ivy.R;

/**
 * Created by sunner on 9/3/16.
 */
public class ListenFragment extends Fragment {
    View view;
    ListView list;
    String[] setting_des = {
            "\n\tPlay Now",
            "\n\tSpeaking Speed"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_listen, container, false);

        list = (ListView) view.findViewById(R.id.settingList);
        ListenListAdapter adapter = new
                ListenListAdapter(view, ListenFragment.this, setting_des);
        list.setAdapter(adapter);

        return view;
    }
}
