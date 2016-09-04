package edu.sunner.ivy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import edu.sunner.ivy.ListAdapter.ListenListAdapter;
import edu.sunner.ivy.Parser;
import edu.sunner.ivy.R;

/**
 * Created by sunner on 9/3/16.
 */
public class ListenFragment extends Fragment implements TextToSpeech.OnInitListener {
    // Text to speech object
    public static TextToSpeech t;

    // Accent counter
    int accent = 0;

    // List adapter object
    ListenListAdapter1 adapter;

    // View object
    View view;

    // setting list view
    ListView list;

    // setting list view description
    String[] setting_des = {
            "\n\tPlay Now",
            "\n\tSpeaking Speed"
    };

    /*
        Defined List Adapter
        Cause We should supervise the flag that would terminate the TTS
        Thus We don't seperate from the origin program
     */
    public class ListenListAdapter1 extends ArrayAdapter {
        private Fragment fragment;
        private String[] setting_texts;
        private String[][] arr = {{"   Yes   ", "   No   "}, {"Slower", "Normal"}};                 // Switch text description
        Parser p = new Parser();                                                                    // Defined parser object
        public boolean stop = false;                                                                // Control flag to terminate the TTS
        public int startIndex = 0;                                                                  // Start index toward parser list

        TextView txtTitle;
        Switch sswitch;

        // Speak Runnable
        Runnable speakRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = startIndex; i < p.length() && !stop; i++) {
                    t.speak(p.getEnglish(i), TextToSpeech.QUEUE_ADD, null);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Constructor
        public ListenListAdapter1(View view, Fragment f, String[] setting_texts, TextToSpeech t) {
            super(view.getContext(), R.layout.list_listen, setting_texts);
            this.fragment = f;
            this.setting_texts = setting_texts;
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

        /*
            Get view object and set the description
         */
        public void setView(View rowView, int position) {
            txtTitle = (TextView) rowView.findViewById(R.id.txt);
            txtTitle.setText(setting_texts[position]);

            sswitch = (Switch) rowView.findViewById(R.id.switch1);
            sswitch.setTextOn(arr[position][0]);
            sswitch.setTextOff(arr[position][1]);
        }

        /*
            Set the switch listener
         */
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
                            if (b) {
                                t.setSpeechRate(0.8f);
                                Log.d("??", "降訴");
                            }
                            else{
                                t.setSpeechRate(1);
                                Log.d("??", "正常速度");
                            }
                            break;
                        default:
                            Log.d("??", "未知list row");
                            break;
                    }
                }
            });
        }

        // Shuffle the start index and speak again
        public void again(){

            try {
                stop = true;
                Thread.sleep(1000);
                stop = false;
                startIndex = (int) (Math.random() * (p.length()-1));
                new Thread(speakRunnable).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_listen, container, false);

        list = (ListView) view.findViewById(R.id.settingList);
        adapter = new ListenListAdapter1(view, ListenFragment.this, setting_des, t);
        list.setAdapter(adapter);

        loadTTSEngine();
        return view;
    }

    // Implement loading the TTS engine
    public void loadTTSEngine() {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(0, 1, checkTTSIntent);
    }

    // Examine if the engine has been install
    private void startActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                t = new TextToSpeech(getActivity(), this);
            } else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.d("設定", "finish");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.again);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.again();
            }
        });
    }
}
