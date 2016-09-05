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
import android.widget.ListView;
import android.widget.Toast;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.R;
import edu.sunner.ivy.listadapter.SettingListAdapter;

/**
 * The setting fragment which would be called in practice function
 *
 * @author sunner
 * @since 9/3/16.
 */
public class SettingFragment extends Fragment {
    // View object
    private View view;
    private ListView list;

    // The setting text array
    private String[] settingtexts = {
        "\n\tShow the text",
        "\n\tOpen Speaking",
        "\n\tPractice Mode"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        list = (ListView) view.findViewById(R.id.settingList);

        SettingListAdapter ad = new SettingListAdapter(view, SettingFragment.this, settingtexts);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });

        FloatingActionButton back = (FloatingActionButton) view.findViewById(R.id.back);
        back.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Bundle bundle = new Bundle();

                // Jump back
                switch (getArguments().getInt(Constant.MODE_KEY)) {
                    case Constant.FUNDAMENTAL:
                        fragment = new ChooseFromFourFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.FUNDAMENTAL);
                        getActivity().setTitle(R.string.fundamental_mode);
                        break;
                    case Constant.ADVANCE:
                        fragment = new ChooseFromFourFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.ADVANCE);
                        getActivity().setTitle(R.string.advance_mode);
                        break;
                    case Constant.STRENGTHEN:
                        fragment = new ChooseFromFourFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                        getActivity().setTitle(R.string.strengthen_mode);
                        break;
                    default:
                        Log.e(Constant.SFT_TAG, "error mode");
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

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments().getInt(Constant.MODE_KEY) == Constant.ADVANCE) {
            Toast.makeText(getActivity(), "The setting in advance mode is random",
                Toast.LENGTH_LONG).show();
        }
    }
}
