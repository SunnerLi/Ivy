package edu.sunner.ivy.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();

            }
        });
        return view;
    }

}
