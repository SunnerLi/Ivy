package edu.sunner.ivy;

import android.app.Fragment;
import android.util.Log;
import android.util.Pair;

import java.util.Stack;

/**
 * This class wouldn't be used since it would increase the complexity of the bach button
 * On the other hand, it should cost memory to store the fragment object
 *
 * @author sunner
 * @since 9/3/16.
 */
public class FragmentStack extends Stack {
    // Stack object to store each fragment that user has visited
    private static Stack<Pair<Fragment, CharSequence>> FS = null;

    // The number of the fragments in the stack
    private int ssize = 0;

    public FragmentStack() {
        FS = new Stack<>();
    }

    /**
     * Push the new fragment into the stack.
     *
     * @param fragment the fragment object which want to push
     * @param title    the fragment title which want to push
     */
    public void push(Fragment fragment, CharSequence title) {
        FS.push(new Pair<>(fragment, title));
        ssize++;
    }

    /**
     * Return the number of fragment in the stack.
     *
     * @return number
     */
    public int size() {
        return ssize;
    }

    /**
     * Pop the top fragment and return.
     *
     * @return the pair of fragment and the title
     */
    public Pair<Fragment, CharSequence> pop() {
        if (FS.empty()) {
            Log.d(Constant.FS_TAG, "已經空了");
            return null;
        } else {
            Log.d(Constant.FS_TAG, "還沒空");
            ssize--;
            return FS.pop();
        }
    }
}
