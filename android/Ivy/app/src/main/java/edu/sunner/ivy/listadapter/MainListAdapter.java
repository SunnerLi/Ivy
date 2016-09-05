package edu.sunner.ivy.listadapter;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.sunner.ivy.R;

/**
 * This class is used to show the function list in the MainActivity
 *
 * @author sunner
 * @since 9/3/16.
 */
public class MainListAdapter extends ArrayAdapter<String> {
    // The function name of each mode
    private String[] functionname;

    // The integer array that store the hash number of the image source
    private Integer[] imageId;

    // The fragment object
    private Fragment fragment;

    /**
     * Constructor.
     *
     * @param view view object
     * @param fragment fragment object
     * @param functionname the name of each function
     * @param imageId the position index array
     */
    public MainListAdapter(View view, Fragment fragment, String[] functionname, Integer[] imageId) {
        super(view.getContext(), R.layout.list_main, functionname);
        this.fragment = fragment;
        this.functionname = functionname;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_main, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(functionname[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}