package edu.sunner.ivy.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
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
import edu.sunner.ivy.listadapter.MainListAdapter;


/**
 * This is the main fragment that would be used in the main activity
 *
 * @author sunner
 * @since 9/3/16.
 */
public class MainFragment extends Fragment {
    // List view object
    private ListView list;
    private View view;

    // The mode explaination text
    private String[] modes = {
        "\n\n\tFundamental Mode",
        "\n\n\tAdvance Mode",
        "\n\n\tStrengthen Mode",
        "\n\n\tListen Mode"
    };

    // The list image id array
    private Integer[] imageId = {
        R.drawable.ic_directions_walk_black_big_24dp,
        R.drawable.ic_school_black_big_24dp,
        R.drawable.ic_trending_up_black_big_24dp,
        R.drawable.ic_hearing_black_big_24dp
    };

    // Silent setting flag
    static boolean isSilent = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main, container, false);
        list = (ListView) view.findViewById(R.id.mainList);

        MainListAdapter adapter = new
            MainListAdapter(view, MainFragment.this, modes, imageId);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Fragment fragment = null;
                Bundle bundle = new Bundle();

                switch (position) {
                    case 0:
                        fragment = new ChooseFromFourFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.FUNDAMENTAL);
                        getActivity().setTitle(R.string.fundamental_mode);
                        break;
                    case 1:
                        fragment = new ChooseFromFourFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.ADVANCE);
                        getActivity().setTitle(R.string.advance_mode);
                        break;
                    case 2:
                        fragment = new ChooseFromFourFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                        getActivity().setTitle(R.string.strengthen_mode);
                        break;
                    case 3:
                        fragment = new ListenFragment();
                        bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                        getActivity().setTitle(R.string.listen_mode);
                        break;
                    default:
                        Log.e(Constant.MFT_TAG, "error mode");
                }
                fragment.setArguments(bundle);

                // Jump to the fragment
                if (!isSilent || position != 1) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                } else {
                    getActivity().setTitle(R.string.app_name);
                    Toast.makeText(getActivity(), "Silent setting cannot enter Advance Mode",
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Read silent setting
        final SharedPreferences setting = view.getContext()
            .getSharedPreferences(Constant.PRE_NAME, 0);
        if (setting.getInt(Constant.SETTING_SILENT, Constant.YES) == Constant.YES) {
            isSilent = true;
        } else {
            isSilent = false;
        }

        // Set image resource and listener
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.silent);
        if (isSilent) {
            fab.setImageResource(R.drawable.ic_volume_off_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSilent) {      // Set as normal
                    isSilent = false;
                    fab.setImageResource(R.drawable.ic_volume_up_black_24dp);
                    setting.edit().putInt(Constant.SETTING_SILENT, Constant.NO).commit();
                } else {             // Set as silent
                    isSilent = true;
                    fab.setImageResource(R.drawable.ic_volume_off_black_24dp);
                    setting.edit().putInt(Constant.SETTING_SILENT, Constant.YES).commit();
                }
            }
        });
    }
}
