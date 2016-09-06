package edu.sunner.ivy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.Parser;
import edu.sunner.ivy.R;

import java.util.Locale;

/**
 * This is the listening fragment which define the whole things in listen mode
 *
 * @author sunner
 * @since 9/3/16.
 */
public class ListenFragment extends Fragment implements TextToSpeech.OnInitListener {
    // Text to speech object
    private static TextToSpeech TTS;

    // Accent counter
    private int accent = 0;

    // List adapter object
    private ListenListAdapter1 adapter;

    // View object
    private View view;
    private ListView list;
    private Switch outSwitch;

    // Setting list view description
    private String[] settingdescriptors = {
        "\n\tPlay Now",
        "\n\tSpeaking Speed"
    };

    // Control flag to terminate the TTS
    private static boolean stop = false;

    // Recover Handler
    private Handler switchHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.OPEN:
                    Log.v(Constant.LFT_TAG, "設定藍色");
                    outSwitch.setChecked(true);
                    break;
                case Constant.CLOSE:
                    Log.v(Constant.LFT_TAG, "設定黑色");
                    outSwitch.setChecked(false);
                    break;
                default:
                    Log.e(Constant.LFT_TAG, "異常switch what");
                    break;
            }
        }
    };

    /**
     * Defined List Adapter
     * Cause We should supervise the flag that would terminate the TTS
     * Thus We don't seperate from the origin program.
     */
    public class ListenListAdapter1 extends ArrayAdapter {
        // Fragment object
        private Fragment fragment;

        // String array to store the setting descriptions
        private String[] settingtexts;

        // Switch text description
        private String[][] arr = {{"   Yes   ", "   No   "}, {"Slower", "Normal"}};

        // parser object
        private Parser parser = new Parser();


        // Start index toward parser list
        private int startIndex = 0;

        // View object
        private TextView txtTitle;
        protected Switch sswitch;

        // Launch counter
        int count = 0;
        int msg = Constant.CLOSE;

        // Speak Runnable
        private Runnable speakRunnable = new Runnable() {
            @Override
            public void run() {
                // Wait for the stop flag to tern false
                try {
                    Thread.sleep(500);
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
                if (!isSilent()) {
                    for (int i = startIndex; i < parser.length() && !stop; i++) {
                        TTS.speak(parser.getEnglish(i), TextToSpeech.QUEUE_ADD, null);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException err) {
                            err.printStackTrace();
                        }

                        if (accent == 0) {      // Change the country for each 10 times
                            TTS.setLanguage(new Locale("en", "GB"));
                        } else if (accent == 5) {
                            TTS.setLanguage(new Locale("en", "US"));
                        } else if (accent == 10) {
                            TTS.setLanguage(new Locale("en", "AS"));
                        }
                        accent = (accent + 1) % 15;
                    }
                } else {
                    Log.v(Constant.LFT_TAG, "is silent");
                }

                // Tell handler to close the button
                Message message = new Message();
                message.what = Constant.CLOSE;
                switchHandler.sendMessage(message);
            }
        };

        /**
         * Constructor.
         *
         * @param view         the view object
         * @param fragment     the fragment object
         * @param settingTexts the setting text which would be shown below
         * @param tts          ( deprecated parameter )
         */
        public ListenListAdapter1(View view, Fragment fragment, String[] settingTexts,
                                  TextToSpeech tts) {
            super(view.getContext(), R.layout.list_listen, settingTexts);
            this.fragment = fragment;
            this.settingtexts = settingTexts;
            parser.readWork();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_listen, null, true);

            /*
                With anonymous factor, the adapter migh call getView for 6 times.
                But In the list there're only 4 view object.
                At the same time, the button might lose the view control.
                So I guess the switch view might be override that cannot control directly.

                In the result, I restrict the max times that the getView can be called.
                If the list is extent in the future, you should increase the limit.
             */
            if (count < Constant.TOTAL_OBJ_IN_LISTVIEW) {
                setView(rowView, position);
                setListener(position);
            }
            count++;
            return rowView;
        }

        /**
         * Get view object and set the description.
         *
         * @param rowView  the view object
         * @param position the position index in the listView
         */
        public void setView(View rowView, int position) {
            txtTitle = (TextView) rowView.findViewById(R.id.txt);
            txtTitle.setText(settingtexts[position]);


            if (position == 0) {
                outSwitch = (Switch) rowView.findViewById(R.id.switch1);
                outSwitch.setTextOn(arr[position][0]);
                outSwitch.setTextOff(arr[position][1]);
            } else {
                sswitch = (Switch) rowView.findViewById(R.id.switch1);
                sswitch.setTextOn(arr[position][0]);
                sswitch.setTextOff(arr[position][1]);
            }
        }

        /**
         * Set the switch listener.
         *
         * @param position the position index in the listView
         */
        public void setListener(final int position) {
            if (position == 0) {
                outSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean boo) {
                        if (boo) {
                            stop = false;
                            new Thread(speakRunnable).start();
                        } else {
                            stop = true;
                        }
                    }
                });
            } else if (position == 1) {
                sswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean boo) {
                        if (boo) {
                            TTS.setSpeechRate(0.8f);
                            Log.i(Constant.LFT_TAG, "降速");
                        } else {
                            TTS.setSpeechRate(1);
                            Log.i(Constant.LFT_TAG, "正常速度");
                        }
                    }
                });
            } else {
                Log.d(Constant.LFT_TAG, "未知position");
            }

        }

        /**
         * Shuffle the start index and speak again.
         */
        public void again() {
            // Close the button first
            Message message = new Message();
            message.what = Constant.CLOSE;
            switchHandler.sendMessage(message);

            // Wait the Previous runnable terminate
            try {
                Thread.sleep(100);
            } catch (InterruptedException err) {
                err.printStackTrace();
            }

            // Then shuffle the start index and open the button
            startIndex = (int) (Math.random() * (parser.length() - 1));
            message = new Message();
            message.what = Constant.OPEN;
            switchHandler.sendMessage(message);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_listen, container, false);

        list = (ListView) view.findViewById(R.id.settingList);
        adapter = new ListenListAdapter1(view, ListenFragment.this, settingdescriptors, TTS);
        Log.d(Constant.LFT_TAG, "onCreateView");
        list.setAdapter(adapter);

        loadTtsEngine();
        return view;
    }

    /**
     * Implement loading the TTS engine.
     */
    public void loadTtsEngine() {
        Intent checkTtsIntent = new Intent();
        checkTtsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(0, 1, checkTtsIntent);
    }

    /**
     * Examine if the engine has been install.
     *
     * @param requestCode the override parameter
     * @param resultCode  the override parameter
     * @param data        the override parameter
     */
    private void startActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                TTS = new TextToSpeech(getActivity(), this);
            } else {
                Intent installTtsIntent = new Intent();
                installTtsIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTtsIntent);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.d("設定", "finish");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }

    /**
     * Check the shared paference if it's at silent setting.
     *
     * @return if it's at silent setting
     */
    public boolean isSilent() {
        if (getActivity().getSharedPreferences(Constant.PRE_NAME, 0)
            .getInt(Constant.SETTING_SILENT, Constant.YES) == Constant.YES) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.again);
        fab.setImageResource(R.drawable.ic_help_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        adapter.again();
                    }
                }.start();
            }
        });
    }
}
