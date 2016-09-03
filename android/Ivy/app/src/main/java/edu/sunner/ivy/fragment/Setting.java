package edu.sunner.ivy.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.ListAdapter.SettingListAdapter;
import edu.sunner.ivy.R;
import edu.sunner.ivy.chooseFrom4;

/**
 * Created by sunner on 9/3/16.
 */
public class Setting extends Fragment {
    View view;
    ListView list;
    String[] setting_texts = {
            "\n\tShow the text",
            "\n\tOpen Speaking",
            "\n\tPractice Mode"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        list = (ListView) view.findViewById(R.id.settingList);

        SettingListAdapter settingListAdapter = new SettingListAdapter(view, Setting.this, setting_texts);
        //ArrayAdapter<String> settingListAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_expandable_list_item_1, setting_texts);
        list.setAdapter(settingListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

        FloatingActionButton back = (FloatingActionButton)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Bundle bundle = new Bundle();

                // Jump back
                switch (getArguments().getInt(Constant.MODE_KEY)){
                    case Constant.FUNDAMENTAL:
                        fragment = new chooseFrom4();
                        bundle.putInt(Constant.MODE_KEY, Constant.FUNDAMENTAL);
                        break;
                    case Constant.ADVANCE:
                        fragment = new chooseFrom4();
                        bundle.putInt(Constant.MODE_KEY, Constant.ADVANCE);
                        break;
                    case Constant.STRENGTHEN:
                        fragment = new chooseFrom4();
                        bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                        break;
                    default:
                        Log.e("??", "error mode");
                }
                fragment.setArguments(bundle);

                // Jump to the fragment
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        return view;
    }


}
