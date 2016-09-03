package edu.sunner.ivy;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sunner on 9/3/16.
 */
public class MainListAdapter extends ArrayAdapter<String> {

    private String[] web;
    private Integer[] imageId;
    private Fragment fragment;

    public MainListAdapter(View view, Fragment f, String[] web, Integer[] imageId) {
        super(view.getContext(), R.layout.list_main, web);
        this.fragment = f;
        this.web = web;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_main, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}

/*
    public class CustomList extends ArrayAdapter<String> {

        private final View view;
        private final String[] web;
        private final Integer[] imageId;

        public CustomList(View view, String[] web, Integer[] imageId) {
            super(view.getContext(), R.layout.list_main, web);
            this.view = view;
            this.web = web;
            this.imageId = imageId;

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_main, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web[position]);

            imageView.setImageResource(imageId[position]);
            return rowView;
        }
    }
    */