package edu.sunner.ivy;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import edu.sunner.ivy.fragment.Setting;

/**
 * Created by sunner on 9/3/16.
 */
public class chooseFrom4 extends Fragment implements TextToSpeech.OnInitListener {
    View view;

    // Parser object
    Parser parser = new Parser();

    // Setting parameter
    int Mode;
    int setting_showText = Constant.YES;
    int setting_speaking = Constant.YES;
    int setting_mode = Constant.FOUR_CHOOSE_ONE;

    // View object
    Button[] optionsBtn = new Button[4];
    TextView card, score;
    FloatingActionButton show, speak;

    // The both side answer index & button's index
    int[] options = new int[4];
    int PhoneAns, UserAns;
    int[] candicate = new int[3];

    // Score
    int numberOfRight = 0, numberOfAll = 0;

    // Text to speech object
    TextToSpeech t;

    // Accent counter
    int accent = 0;

    Runnable QRunnable = new Runnable() {
        @Override
        public void run() {
            // Advance mode shuffle the setting parameter first
            if (Mode == Constant.ADVANCE) {
                int _pat = (int) (Math.random() * 6);
                Log.d("??", "pat: " + _pat);
                setting_mode = (_pat >= 3 ? Constant.FOUR_CHOOSE_ONE : Constant.FOUR_DELETE_THREE);
                setting_showText = (_pat % 3 == 2 ? Constant.NO : Constant.YES);
                setting_speaking = (_pat % 3 == 1 ? Constant.NO : Constant.YES);
            }

            // Determine the four option's index
            if (Mode != Constant.STRENGTHEN)
                for (int i = 0; i < 4; i++)
                    options[i] = (int) (Math.random() * (parser.length()));
            else
                for (int i = 0; i < 4; i++)
                    options[i] = (int) (Math.random() * (parser.weak_length()));

            // Determine the question's index and candicate
            int target = (int) (Math.random() * 4);
            PhoneAns = options[target];
            for (int i = 0, j = 0; i < 4; i++)
                if (i != target)
                    candicate[j++] = options[i];

            // Show the result
            Log.d("??", "choose1: " + options[0] + "\nchoose2: " + options[1] + "\nchoose3: " + options[2] + "\nchoose4: " + options[3]);
            Log.d("??", "ans: " + PhoneAns);
            Log.d("??", "candi[0]: " + candicate[0] + "\ncandi[1]: " + candicate[1] + "\ncandi[2]: " + candicate[2]);

            // Say the question
            if (setting_speaking == Constant.YES) {
                if (setting_mode == Constant.FOUR_CHOOSE_ONE) {
                    if (Mode != Constant.STRENGTHEN)
                        t.speak(parser.getEnglish(PhoneAns), TextToSpeech.QUEUE_ADD, null);
                    else
                        t.speak(parser.weak_getEnglish(PhoneAns), TextToSpeech.QUEUE_ADD, null);
                } else {
                    if (Mode != Constant.STRENGTHEN)
                        for (int i = 0; i < 3; i++)
                            t.speak(parser.getEnglish(candicate[i]), TextToSpeech.QUEUE_ADD, null);
                    else
                        for (int i = 0; i < 3; i++)
                            t.speak(parser.weak_getEnglish(candicate[i]), TextToSpeech.QUEUE_ADD, null);
                }
            }

            // Change the view
            Message message = new Message();
            message.what = setting_showText;
            message.arg1 = setting_mode;
            QHandler.sendMessage(message);
        }
    };

    Handler QHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // Set button description
            if (Mode != Constant.STRENGTHEN)
                for (int i = 0; i < 4; i++)
                    optionsBtn[i].setText(parser.getChinese(options[i]));
            else
                for (int i = 0; i < 4; i++)
                    optionsBtn[i].setText(parser.weak_getChinese(options[i]));

            // Set score description
            score.setText("\n [Y/All] : " + numberOfRight + " / " + numberOfAll);

            // Set question description
            if (msg.what == Constant.YES) {
                if (msg.arg1 == Constant.FOUR_CHOOSE_ONE) {
                    if (Mode != Constant.STRENGTHEN)
                        card.setText(parser.getEnglish(PhoneAns));
                    else
                        card.setText(parser.weak_getEnglish(PhoneAns));
                } else {
                    String q = "\tQ : \n";
                    if (Mode != Constant.STRENGTHEN)
                        for (int i = 0; i < 3; i++)
                            q = q + parser.getEnglish(candicate[i]) + "\n";
                    else
                        for (int i = 0; i < 3; i++)
                            q = q + parser.weak_getEnglish(candicate[i]) + "\n";
                    card.setText(q);
                }
            } else
                card.setText("\tPlease listen the speech carefully !");
        }
    };

    Runnable ARunnable = new Runnable() {
        @Override
        public void run() {
            // Judge the answer
            if (Mode != Constant.STRENGTHEN) {
                if (parser.getChinese(PhoneAns).equals(parser.getChinese(UserAns))) {
                    Log.d("??", "You are right !!!!!!!!!!!");
                    numberOfRight++;
                    Message message = new Message();
                    message.what = 1;
                    AHandler.sendMessage(message);
                } else {
                    Log.d("??", "You are wrong ...........");
                    Message message = new Message();
                    message.what = 0;
                    AHandler.sendMessage(message);
                }
            } else {
                if (parser.weak_getChinese(PhoneAns).equals(parser.weak_getChinese(UserAns))) {
                    Log.d("??", "You are right !!!!!!!!!!!");
                    numberOfRight++;
                    Message message = new Message();
                    message.what = 1;
                    AHandler.sendMessage(message);
                } else {
                    Log.d("??", "You are wrong ...........");
                    Message message = new Message();
                    message.what = 0;
                    AHandler.sendMessage(message);
                }
            }

            // Sleep to know the result (Change the accent simultaneously)
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    if (accent == 0)                                                                 // Change the country for each 10 times
                        t.setLanguage(new Locale("en", "GB"));
                    else if (accent == 5)
                        t.setLanguage(new Locale("en", "US"));
                    else if (accent == 10)
                        t.setLanguage(new Locale("en", "AS"));
                    accent = (accent + 1) % 15;
                }
            }.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Next Question
            numberOfAll++;
            new Thread(QRunnable).start();
        }
    };

    Handler AHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String info;
            if (msg.what == 1)
                info = "\tYou are Right !!!\n\n";
            else
                info = "\tYou are Wrong ...\n\n";
            if (setting_mode == Constant.FOUR_CHOOSE_ONE) {
                if (Mode != Constant.STRENGTHEN) {
                    info = info + "\tQ : " + parser.getEnglish(PhoneAns) + "\n\n";
                    info = info + "\tA : " + parser.getChinese(PhoneAns) + "\n\n";
                } else {
                    info = info + "\tQ : " + parser.weak_getEnglish(PhoneAns) + "\n\n";
                    info = info + "\tA : " + parser.weak_getChinese(PhoneAns) + "\n\n";
                }
            } else {
                if (Mode != Constant.STRENGTHEN) {
                    for (int i = 0; i < 3; i++)
                        info = info + "\tQ : " + parser.getEnglish(candicate[i]) + "\n";
                    for (int i = 0; i < 3; i++)
                        info = info + "\tA : " + parser.getChinese(candicate[i]) + "\n";
                } else {
                    for (int i = 0; i < 3; i++)
                        info = info + "\tQ : " + parser.weak_getEnglish(candicate[i]) + "\n";
                    for (int i = 0; i < 3; i++)
                        info = info + "\tA : " + parser.weak_getChinese(candicate[i]) + "\n";
                }
            }
            card.setText(info);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_practice, container, false);                      // get view object
        setHasOptionsMenu(true);                                                                    // Set menu button

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showMode(getArguments().getInt(Constant.MODE_KEY));                                         // Show the mode parameter

        SharedPreferences settings = getActivity().getSharedPreferences(Constant.PRE_NAME, 0);
        setting_showText = settings.getInt(Constant.SETTING_SHOWTEXT_KEY, Constant.YES);
        setting_speaking = settings.getInt(Constant.SETTING_SPEAKING_KEY, Constant.NO);
        setting_mode = settings.getInt(Constant.SETTING_MODE_KEY, Constant.FOUR_CHOOSE_ONE);
        Log.d("??", "complete get setting from shared preference");
        Log.d("??", "setting_showText: " + setting_showText + "\t\tsetting_speaking: " + setting_speaking + "\t\tsetting_mode: " + setting_mode);
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
                if (Mode == Constant.STRENGTHEN) {
                    parser.buildWeak(1);
                    Log.d("??", "重建");
                }
            }
        }.start();
        loadTTSEngine();
    }

    public void setView() {
        // Get button object
        optionsBtn[0] = (Button) view.findViewById(R.id.choose1);
        optionsBtn[1] = (Button) view.findViewById(R.id.choose2);
        optionsBtn[2] = (Button) view.findViewById(R.id.choose3);
        optionsBtn[3] = (Button) view.findViewById(R.id.choose4);

        // Cancel the capital setting
        for (int i = 0; i < 4; i++)
            optionsBtn[i].setTransformationMethod(null);

        // Get cardview object
        card = (TextView) view.findViewById(R.id.info_text);
        score = (TextView) view.findViewById(R.id.score_text);

        optionsBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAns = options[0];
                new Thread(ARunnable).start();
            }
        });
        optionsBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAns = options[1];
                new Thread(ARunnable).start();
            }
        });
        optionsBtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAns = options[2];
                new Thread(ARunnable).start();
            }
        });
        optionsBtn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAns = options[3];
                new Thread(ARunnable).start();
            }
        });

        show = (FloatingActionButton) view.findViewById(R.id.hint);
        speak = (FloatingActionButton) view.findViewById(R.id.speaking);
        show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (Mode != Constant.ADVANCE) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.d("??", "按下");
                            if (Mode != Constant.STRENGTHEN)
                                for (int i = 0; i < 4; i++)
                                    optionsBtn[i].setText(parser.getChinese(options[i])
                                            + '\n' + parser.getEnglish(options[i]));
                            else
                                for (int i = 0; i < 4; i++)
                                    optionsBtn[i].setText(parser.weak_getChinese(options[i])
                                            + '\n' + parser.weak_getEnglish(options[i]));
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d("??", "談起");
                            if (Mode != Constant.STRENGTHEN)
                                for (int i = 0; i < 4; i++)
                                    optionsBtn[i].setText(parser.getChinese(options[i]));
                            else
                                for (int i = 0; i < 4; i++)
                                    optionsBtn[i].setText(parser.weak_getChinese(options[i]));
                            break;
                    }
                } else {
                    if (motionEvent.getAction() == motionEvent.ACTION_DOWN)
                        Toast.makeText(getActivity(), "Master don't need hint !", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Mode != Constant.ADVANCE) {
                    if (setting_speaking == Constant.YES) {
                        if (setting_mode == Constant.FOUR_CHOOSE_ONE) {
                            if (Mode != Constant.STRENGTHEN)
                                t.speak(parser.getEnglish(PhoneAns), TextToSpeech.QUEUE_ADD, null);
                            else
                                t.speak(parser.weak_getEnglish(PhoneAns), TextToSpeech.QUEUE_ADD, null);
                        } else {
                            if (Mode != Constant.STRENGTHEN)
                                for (int i = 0; i < 3; i++)
                                    t.speak(parser.getEnglish(candicate[i]), TextToSpeech.QUEUE_ADD, null);
                            else
                                for (int i = 0; i < 3; i++)
                                    t.speak(parser.weak_getEnglish(candicate[i]), TextToSpeech.QUEUE_ADD, null);
                        }
                    } else
                        Toast.makeText(getActivity(), "You should enable the speaking setting first"
                                , Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Master don't need hear again !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showMode(int mode) {
        Mode = mode;
        switch (mode) {
            case Constant.FUNDAMENTAL:
                Log.d("??", "基礎");
                break;
            case Constant.ADVANCE:
                Log.d("??", "進階");
                break;
            case Constant.STRENGTHEN:
                Log.d("??", "強化");
                break;
            default:
                Log.e("??", "未知參數");
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
                fragment = new Setting();
                bundle.putInt(Constant.MODE_KEY, getArguments().getInt(Constant.MODE_KEY));
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
            new Thread(QRunnable).start();
        }
    }
}
