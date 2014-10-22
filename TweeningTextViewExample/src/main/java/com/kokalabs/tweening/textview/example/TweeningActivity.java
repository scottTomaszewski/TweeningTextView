package com.kokalabs.tweening.textview.example;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.kokalabs.tweening.textview.TweeningTextView;

public class TweeningActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweening);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweening, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public static final int DURATION = 1000;

        private TweeningTextView tweeningView;
        private SeekBar seekBar;
        private volatile ObjectAnimator objectAnimator;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tweening, container, false);
            tweenStuff(rootView);
            return rootView;
        }

        private void tweenStuff(View root) {
//        setContentView(R.layout.fragment_tweening);
            tweeningView = (TweeningTextView) root.findViewById(R.id.textView1);
            seekBar = (SeekBar) root.findViewById(R.id.seekBar);
            seekBar.setMax(DURATION);
            objectAnimator = tweeningView.animate(Char.from, Char.to);
            objectAnimator.setDuration(DURATION);
            SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (objectAnimator != null) {
                        objectAnimator.setCurrentPlayTime(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            };
            seekBar.setOnSeekBarChangeListener(listener);
        }

    }
}
