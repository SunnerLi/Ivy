package edu.sunner.ivy.listadapter;

import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.Parser;
import edu.sunner.ivy.R;

/**
 * This class had been merge into the ListenFragment since it cannot call the tts object
 * (This class is not maintained)
 *
 * @author sunner
 * @since 9/3/16.
 * @deprecated ( not maintain )
 */
public class ListenListAdapter extends ArrayAdapter {
    // The View object
    private View view;
    private Fragment fragment;
    private TextView txtTitle;
    private Switch sswitch;

    // The setting text array
    private String[] settingtexts;

    // The setting option text
    private String[][] arr = {{"   Yes   ", "   No   "}, {"Slower", "Normal"}};

    // Text to speech object
    private static TextToSpeech TTS;

    // Parser object
    private Parser parser = new Parser();

    // Control flag
    private static boolean STOP = false;
    private static boolean SLOWER = false;
    private static int STARTINDEX = 0;

    /**
     * Speak Runnable to speak each words.
     * Notice since this class isn't maintain, the tts object might be null !!!
     */
    private Runnable speakRunnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < parser.length() && !STOP; i++) {
                if (TTS == null) {
                    Log.e(Constant.LLAR_TAG, "tts為空");
                }
                TTS.speak(parser.getEnglish(i), TextToSpeech.QUEUE_ADD, null);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
            }
        }
    };

    /**
     * Constructor.
     *
     * @param view          view object
     * @param fragment      fragment object
     * @param settingTexts  setting text descriptions
     * @param ts             TTS object
     */
    public ListenListAdapter(View view, Fragment fragment, String[] settingTexts, TextToSpeech ts) {
        super(view.getContext(), R.layout.list_listen, settingTexts);
        this.fragment = fragment;
        this.view = view;
        this.settingtexts = settingTexts;
        this.TTS = ts;
        if (this.TTS == null) {
            Log.e(Constant.LLAR_TAG, "tts為空");
        }
        parser.readWork();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_listen, null, true);

        setView(rowView, position);
        setListener(position);

        return rowView;
    }

    /**
     * Get the view object and set the text in the list.
     *
     * @param rowView  the view object
     * @param position the position index in the list
     */
    public void setView(View rowView, int position) {
        txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(settingtexts[position]);

        sswitch = (Switch) rowView.findViewById(R.id.switch1);
        sswitch.setTextOn(arr[position][0]);
        sswitch.setTextOff(arr[position][1]);
    }

    /**
     * Set the switch listener.
     *
     * @param position the position index in the list
     */
    public void setListener(final int position) {
        sswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                switch (position) {
                    case 0:
                        if (bool) {
                            new Thread(speakRunnable).start();
                        } else {
                            STOP = true;
                        }
                        break;
                    case 1:
                        if (bool) {
                            TTS.setSpeechRate(0.8f);
                        } else {
                            TTS.setSpeechRate(1);
                        }
                        break;
                    default:
                        Log.d(Constant.LLAR_TAG, "未知list row");
                        break;
                }
            }
        });
    }
}
