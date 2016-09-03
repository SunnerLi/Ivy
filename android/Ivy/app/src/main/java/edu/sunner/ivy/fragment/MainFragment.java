package edu.sunner.ivy;

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

import edu.sunner.ivy.MainListAdapter;
import edu.sunner.ivy.chooseFrom4;
import edu.sunner.ivy.fragment.ListenFragment;


/**
 * Created by sunner on 9/3/16.
 */
public class MainFragment extends Fragment {
    ListView list;
    String[] modes = {
            "\n\n\tFundamental Mode",
            "\n\n\tAdvance Mode",
            "\n\n\tStrengthen Mode",
            "\n\n\tListen Mode"
    };
    Integer[] imageId = {
            R.drawable.icon_fundamental_black_small,
            R.drawable.icon_advance_black_small,
            R.drawable.icon_strengthen_black_small,
            R.drawable.icon_listen_black_small
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                        fragment = new chooseFrom4();
                        bundle.putInt(Constant.MODE_KEY, Constant.FUNDAMENTAL);
                        break;
                    case 1:
                        fragment = new chooseFrom4();
                        bundle.putInt(Constant.MODE_KEY, Constant.ADVANCE);
                        break;
                    case 2:
                        fragment = new chooseFrom4();
                        bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                        break;
                    case 3:
                        fragment = new ListenFragment();
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
