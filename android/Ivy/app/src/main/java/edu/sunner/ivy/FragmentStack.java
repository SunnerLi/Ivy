package edu.sunner.ivy;

import android.app.Fragment;
import android.util.Log;
import android.util.Pair;

import java.util.Stack;

/**
 * Created by sunner on 9/3/16.
 */
public class FragmentStack extends Stack {
    static Stack<Pair<Fragment, CharSequence>> fs = null;
    int ssize = 0;

    public FragmentStack(){
        fs = new Stack<>();
    }

    public void push(Fragment fragment, CharSequence title){
        fs.push(new Pair<>(fragment, title));
        ssize++;
    }

    public int Size(){
        return ssize;
    }

    public Pair<Fragment, CharSequence> pop(){
        if (fs.empty()) {
            Log.d("??", "已經空了");
            return null;
        }
        else {
            Log.d("??", "還沒空");
            ssize--;
            return fs.pop();
        }
    }
}
