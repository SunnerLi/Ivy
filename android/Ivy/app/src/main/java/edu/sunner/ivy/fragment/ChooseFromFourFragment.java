package edu.sunner.ivy.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.Parser;
import edu.sunner.ivy.R;

import java.util.Locale;


/**
 * This fragment is the main fragment that do the fundamental, advance and strengthen function.
 * It might be called by MainActivity and MainFragment
 *
 * @author sunner
 * @since 9/3/16.
 */
public class ChooseFromFourFragment extends Fragment implements TextToSpeech.OnInitListener {
    // The view object
    private View view;

    // Parser object
    private Parser parser = new Parser();

    // Setting parameter to know the function mode.
    private int mode;

    // Setting parameter
    private int settingShowText = Constant.YES;
    private int settingSpeaking = Constant.YES;
    private int settingMode = Constant.FOUR_CHOOSE_ONE;

    // Options button view objects
    private AppCompatButton[] optionsbtns = new AppCompatButton[4];

    // Card View object that show the information / the score
    private TextView card;
    private TextView score;

    // Floating action button objects
    private FloatingActionButton show;
    private FloatingActionButton speak;

    // The index of the options in the button.
    private int[] options = new int[4];

    // The actual answer and the answer which user choose
    private int phoneAnswer;
    private int userAnswer;

    // The array to record the rest option expect the phoneanswer.
    private int[] candicates = new int[3];

    // Score integer
    private int numberOfRight = 0;
    private int numberOfAll = 0;

    // Text to speech object.
    private TextToSpeech tts;

    // Accent counter
    private static int accent = 0;

    // Control flags
    private static boolean hasPress = false;
    private static boolean hasWatch = false;

    // Message
    private Message right;
    private Message wrong;

    /**
     * The runnable to define the action of giving question.
     */
    private Runnable quesRunnable = new Runnable() {
        @Override
        public void run() {
            // Advance mode shuffle the setting parameter first
            if (mode == Constant.ADVANCE) {
                int pat = (int) (Math.random() * 6);
                settingMode = (pat >= 3 ? Constant.FOUR_CHOOSE_ONE : Constant.FOUR_DELETE_THREE);
                settingShowText = (pat % 3 == 2 ? Constant.NO : Constant.YES);
                settingSpeaking = (pat % 3 == 1 ? Constant.NO : Constant.YES);
            }

            // Each 10 times update the weak list during strengthen mode
            if (mode == Constant.STRENGTHEN) {
                if (accent == 3) {
                    parser.buildWeak(2);
                }

                // If no weak Word, jump to main fragment
                if (parser.weaklength() == 0) {
                    finishStrengthen.sendEmptyMessage(0);
                    return;
                }
            }

            // Determine the four option's index
            if (mode != Constant.STRENGTHEN) {
                for (int i = 0; i < 4; i++) {
                    options[i] = (int) (Math.random() * (parser.length()));
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    options[i] = (int) (Math.random() * (parser.weaklength()));
                }
            }

            // Determine the question's index and candicate
            int target = (int) (Math.random() * 4);
            phoneAnswer = options[target];
            for (int i = 0, j = 0; i < 4; i++) {
                if (i != target) {
                    candicates[j++] = options[i];
                }
            }

            // Show the result
            Log.v(Constant.CFFFT_TAG, "choose1: " + options[0] + "\nchoose2: " + options[1]
                + "\nchoose3: " + options[2] + "\nchoose4: " + options[3]);
            Log.v(Constant.CFFFT_TAG, "ans: " + phoneAnswer);
            Log.v(Constant.CFFFT_TAG, "candi[0]: " + candicates[0] + "\ncandi[1]: " + candicates[1]
                + "\ncandi[2]: " + candicates[2]);

            // Say the question
            if (settingSpeaking == Constant.YES) {
                if (settingMode == Constant.FOUR_CHOOSE_ONE) {
                    if (mode != Constant.STRENGTHEN) {
                        speak(parser.getEnglish(phoneAnswer));
                    } else {
                        speak(parser.weakgetEnglish(phoneAnswer));
                    }
                } else {
                    if (mode != Constant.STRENGTHEN) {
                        for (int i = 0; i < 3; i++) {
                            speak(parser.getEnglish(candicates[i]));
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            speak(parser.weakgetEnglish(candicates[i]));
                        }
                    }
                }
            }

            // Change the view
            Message message = new Message();
            message.what = settingShowText;
            message.arg1 = settingMode;
            quesHandler.sendMessage(message);
        }
    };

    /**
     * The handler to define the action of giving question which related to UI.
     */
    private Handler quesHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // Set button description
            if (mode != Constant.STRENGTHEN) {
                for (int i = 0; i < 4; i++) {
                    optionsbtns[i].setText(parser.getChinese(options[i]));
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    optionsbtns[i].setText(parser.weakgetChinese(options[i]));
                }
            }

            // Set score description
            score.setText("\n [Y/All] : " + numberOfRight + " / " + numberOfAll);

            // Set question description
            if (msg.what == Constant.YES) {
                if (msg.arg1 == Constant.FOUR_CHOOSE_ONE) {
                    if (mode != Constant.STRENGTHEN) {
                        card.setText("\tQ :\n\t" + parser.getEnglish(phoneAnswer));
                    } else {
                        card.setText("\tQ :\n\t" + parser.weakgetEnglish(phoneAnswer));
                    }
                } else {
                    String quesString = "\tQ : \n";
                    if (mode != Constant.STRENGTHEN) {
                        for (int i = 0; i < 3; i++) {
                            quesString = quesString + parser.getEnglish(candicates[i]) + "\n";
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            quesString = quesString + parser.weakgetEnglish(candicates[i]) + "\n";
                        }
                    }
                    card.setText(quesString);
                }
            } else {
                card.setText("\tPlease listen the speech carefully !");
            }
        }
    };

    /**
     * The runnable to define the action after the user press the answer.
     */
    private Runnable ansRunnable = new Runnable() {
        @Override
        public void run() {
            // Judge the answer
            if (mode != Constant.STRENGTHEN) {
                if (parser.getChinese(phoneAnswer).equals(parser.getChinese(userAnswer))) {
                    sendRightMsg();
                } else {
                    sendWrongMsg();
                }
            } else {
                if (parser.weakgetChinese(phoneAnswer).equals(parser.weakgetChinese(userAnswer))) {
                    sendRightMsg();
                    winInStrengthenMode();
                } else {
                    sendWrongMsg();
                }
            }

            // Sleep to know the result (Change the accent simultaneously)
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    // Change the country for each 5 times
                    if (accent == 0) {
                        tts.setLanguage(new Locale("en", "GB"));
                    } else if (accent == 5) {
                        tts.setLanguage(new Locale("en", "US"));
                    } else if (accent == 10) {
                        tts.setLanguage(new Locale("en", "AS"));
                    }
                    accent = (accent + 1) % 15;
                }
            }.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException err) {
                err.printStackTrace();
            }

            // Release the flags
            hasPress = false;       // Recover the button lock
            hasWatch = false;       // Recover the increment lock

            // Next Question
            numberOfAll++;
            new Thread(quesRunnable).start();
        }
    };

    /**
     * The runnable to define the action after the user press the answer (related to UI).
     */
    private Handler ansHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String info;
            if (msg.what == 1) {
                info = "\tYou are Right !!!\n\n";
            } else {
                info = "\tYou are Wrong ...\n\n";
            }
            if (settingMode == Constant.FOUR_CHOOSE_ONE) {
                if (mode != Constant.STRENGTHEN) {
                    info = info + "\tQ : " + parser.getEnglish(phoneAnswer) + "\n\n";
                    info = info + "\tA : " + parser.getChinese(phoneAnswer) + "\n\n";
                } else {
                    info = info + "\tQ : " + parser.weakgetEnglish(phoneAnswer) + "\n\n";
                    info = info + "\tA : " + parser.weakgetChinese(phoneAnswer) + "\n\n";
                }
            } else {
                if (mode != Constant.STRENGTHEN) {
                    for (int i = 0; i < 3; i++) {
                        info = info + "\tQ : " + parser.getEnglish(candicates[i]) + "\n";
                    }
                    for (int i = 0; i < 3; i++) {
                        info = info + "\tA : " + parser.getChinese(candicates[i]) + "\n";
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        info = info + "\tQ : " + parser.weakgetEnglish(candicates[i]) + "\n";
                    }
                    for (int i = 0; i < 3; i++) {
                        info = info + "\tA : " + parser.weakgetChinese(candicates[i]) + "\n";
                    }
                }
            }
            card.setText(info);
        }
    };

    /**
     * The runnable to define the action after finish the strengthen function (related to UI).
     */
    private Handler finishStrengthen = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getActivity(),
                "No word you should strengthen, please enter higher level function !",
                Toast.LENGTH_LONG).show();
            Fragment fragment = new MainFragment();
            getActivity().setTitle(R.string.app_name);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    };

    /**
     * Make TTS object speak the sentence.
     *
     * @param string the sentence want to say
     */
    public void speak(String string) {
        tts.speak(string, TextToSpeech.QUEUE_ADD, null);
    }

    /**
     * Send the right message to handler.
     * Notice strengthen mode should do the enhencement process later
     */
    public void sendRightMsg() {
        Log.v(Constant.CFFFT_TAG, "You are right !!!!!!!!!!!");
        numberOfRight++;
        ansHandler.sendMessage(right);
    }

    /**
     * Send the wrong message to handler.
     */
    public void sendWrongMsg() {
        // increase the wrong counter
        Log.v(Constant.CFFFT_TAG, "You are wrong ...........");
        parser.increaseNumberOfWrong(phoneAnswer);
        ansHandler.sendMessage(wrong);

        // information changed, update the file
        parser.write();
    }

    /**
     * 50% probability to decrease the number of wrong and number of watch.
     */
    public void winInStrengthenMode() {
        boolean pro = (int) (Math.random() * 2) == 1 ? true : false;
        boolean hasChange = false;
        if (pro) {
            hasChange = true;
            parser.decreaseNumberOfWrong(phoneAnswer);
            Log.v(Constant.CFFFT_TAG,
                "word ( " + parser.getEnglish(phoneAnswer) + " ) decrease wrong");
        }
        pro = (int) (Math.random() * 2) == 1 ? true : false;
        if (pro) {
            hasChange = true;
            parser.decreaseNumberOfWatch(phoneAnswer);
            Log.v(Constant.CFFFT_TAG,
                "word ( " + parser.getEnglish(phoneAnswer) + " ) decrease watch");
        }
        if (hasChange) {
            parser.write();     // information changed, update the file
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // get view object
        view = inflater.inflate(R.layout.fragment_practice, container, false);

        // Set menu button
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Show the mode parameter
        showMode(getArguments().getInt(Constant.MODE_KEY));

        // Get the previous setting parameter
        SharedPreferences settings = getActivity().getSharedPreferences(Constant.PRE_NAME, 0);
        settingShowText = settings.getInt(Constant.SETTING_SHOWTEXT_KEY, Constant.YES);
        settingSpeaking = settings.getInt(Constant.SETTING_SPEAKING_KEY, Constant.NO);
        settingMode = settings.getInt(Constant.SETTING_MODE_KEY, Constant.FOUR_CHOOSE_ONE);
        Log.d(Constant.CFFFT_TAG, "complete get setting from shared preference");
        Log.d(Constant.CFFFT_TAG, "setting_showText: " + settingShowText + "\t\tsetting_speaking: "
            + settingSpeaking + "\t\tsetting_mode: " + settingMode);

        // Get the message object
        right = new Message();
        wrong = new Message();
        right.what = 1;
        wrong.what = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
        new Thread() {
            @Override
            public void run() {
                super.run();
                parser.readWork();
                if (mode == Constant.STRENGTHEN) {
                    parser.buildWeak(1);
                    Log.d(Constant.CFFFT_TAG, "重建");
                }
                loadTtsEngine();
            }
        }.start();
    }

    /**
     * Set the view object listener and get the object.
     */
    public void setView() {
        // Get button object
        optionsbtns[0] = (AppCompatButton) view.findViewById(R.id.choose1);
        optionsbtns[1] = (AppCompatButton) view.findViewById(R.id.choose2);
        optionsbtns[2] = (AppCompatButton) view.findViewById(R.id.choose3);
        optionsbtns[3] = (AppCompatButton) view.findViewById(R.id.choose4);

        // Cancel the capital setting
        for (int i = 0; i < 4; i++) {
            optionsbtns[i].setTransformationMethod(null);
        }

        // Get cardview object
        card = (TextView) view.findViewById(R.id.info_text);
        score = (TextView) view.findViewById(R.id.score_text);

        // Set the button listener
        optionsbtns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressN(0);
            }
        });
        optionsbtns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressN(1);
            }
        });
        optionsbtns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressN(2);
            }
        });
        optionsbtns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressN(3);
            }
        });

        show = (FloatingActionButton) view.findViewById(R.id.hint);
        speak = (FloatingActionButton) view.findViewById(R.id.speaking);
        if (mode != Constant.ADVANCE) {
            show.setImageResource(R.drawable.ic_search_black_24dp);
            speak.setImageResource(R.drawable.ic_volume_up_black_24dp);
        } else {
            show.setImageResource(R.drawable.ic_clear_black_24dp);
            speak.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }


        show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mode != Constant.ADVANCE) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (mode != Constant.STRENGTHEN) {
                                for (int i = 0; i < 4; i++) {
                                    optionsbtns[i].setText(parser.getChinese(options[i])
                                        + '\n' + parser.getEnglish(options[i]));
                                }
                            } else {
                                for (int i = 0; i < 4; i++) {
                                    optionsbtns[i].setText(parser.weakgetChinese(options[i])
                                        + '\n' + parser.weakgetEnglish(options[i]));
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (mode != Constant.STRENGTHEN) {
                                for (int i = 0; i < 4; i++) {
                                    optionsbtns[i].setText(parser.getChinese(options[i]));
                                }
                            } else {
                                for (int i = 0; i < 4; i++) {
                                    optionsbtns[i].setText(parser.weakgetChinese(options[i]));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    if (!hasWatch) {        // Increase the number of watch
                        hasWatch = true;
                        for (int i = 0; i < 4; i++) {
                            parser.increaseNumberOfWatch(i);
                        }

                        // The correct index should double the increment
                        parser.increaseNumberOfWatch(phoneAnswer);

                        // information changed, update the file
                        parser.write();
                    }
                } else {
                    if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                        Toast.makeText(getActivity(), "Master don't need hint !",
                            Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode != Constant.ADVANCE) {
                    if (settingSpeaking == Constant.YES) {
                        if (settingMode == Constant.FOUR_CHOOSE_ONE) {
                            if (mode != Constant.STRENGTHEN) {
                                tts.speak(parser.getEnglish(phoneAnswer),
                                    TextToSpeech.QUEUE_ADD, null);
                            } else {
                                tts.speak(parser.weakgetEnglish(phoneAnswer),
                                    TextToSpeech.QUEUE_ADD, null);
                            }
                        } else {
                            if (mode != Constant.STRENGTHEN) {
                                for (int i = 0; i < 3; i++) {
                                    tts.speak(parser.getEnglish(candicates[i]),
                                        TextToSpeech.QUEUE_ADD, null);
                                }
                            } else {
                                for (int i = 0; i < 3; i++) {
                                    tts.speak(parser.weakgetEnglish(candicates[i]),
                                        TextToSpeech.QUEUE_ADD, null);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(),
                            "You should enable the speaking setting first",
                            Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Master don't need hear again !",
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Press the option n, launch the judgement process.
     *
     * @param number the pressing index
     */
    public void pressN(int number) {
        if (!hasPress) {
            hasPress = true;
            userAnswer = options[number];
            new Thread(ansRunnable).start();
        }
    }

    /**
     * Show the mode text.
     *
     * @param mode the mode Constant
     */
    public void showMode(int mode) {
        this.mode = mode;
        switch (mode) {
            case Constant.FUNDAMENTAL:
                Log.d(Constant.CFFFT_TAG, "基礎");
                break;
            case Constant.ADVANCE:
                Log.d(Constant.CFFFT_TAG, "進階");
                break;
            case Constant.STRENGTHEN:
                Log.d(Constant.CFFFT_TAG, "強化");
                break;
            default:
                Log.e(Constant.CFFFT_TAG, "未知參數");
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.practice_setting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (item.getOrder()) {
            case 0:
                fragment = new SettingFragment();
                bundle.putInt(Constant.MODE_KEY, getArguments().getInt(Constant.MODE_KEY));
                break;
            default:
                break;
        }
        fragment.setArguments(bundle);

        // Jump to the fragment
        getActivity().setTitle(item.getTitle());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        return true;
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
                tts = new TextToSpeech(getActivity(), this);
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
            new Thread(quesRunnable).start();
        }
    }
}
