package edu.sunner.ivy.ListAdapter;

import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import edu.sunner.ivy.Parser;
import edu.sunner.ivy.R;
import edu.sunner.ivy.fragment.ListenFragment;

/**
 * Created by sunner on 9/3/16.
 */
public class ListenListAdapter extends ArrayAdapter {
    private View view;
    private Fragment fragment;
    private String[] setting_texts;
    private String[][] arr = {{"   Yes   ", "   No   "}, {"Slower", "Normal"}};

    // Text to speech object
    static TextToSpeech tts;

    Parser p = new Parser();

    // Control flag
    public static boolean stop = false;
    public static boolean slower = false;
    public static int startIndex = 0;

    TextView txtTitle;
    Switch sswitch;

    // Speak Runnable
    Runnable speakRunnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < p.length() && !stop; i++) {
                if (tts == null)
                    Log.e("??", "tts為空");
                ListenFragment.t.speak(p.getEnglish(i), TextToSpeech.QUEUE_ADD, null);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    // Constructor
    public ListenListAdapter(View view, Fragment f, String[] setting_texts, TextToSpeech t) {
        super(view.getContext(), R.layout.list_listen, setting_texts);
        this.fragment = f;
        this.view = view;
        this.setting_texts = setting_texts;
        this.tts = t;
        if (this.tts == null)
            Log.e("??", "tts為空");
        p.readWork();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_listen, null, true);

        setView(rowView, position);
        setListener(position);

        return rowView;
    }

    public void setView(View rowView, int position) {
        txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(setting_texts[position]);

        sswitch = (Switch) rowView.findViewById(R.id.switch1);
        sswitch.setTextOn(arr[position][0]);
        sswitch.setTextOff(arr[position][1]);
    }

    public void setListener(final int position) {
        sswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (position) {
                    case 0:
                        if (b)
                            new Thread(speakRunnable).start();
                        else
                            stop = true;
                        break;
                    case 1:
                        if (b)
                            tts.setSpeechRate(0.8f);
                        else
                            tts.setSpeechRate(1);
                        break;
                    default:
                        Log.d("??", "未知list row");
                        break;
                }
            }
        });
    }
}
