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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.kokalabs.svg.SvgPath;
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
        private SvgPath from = Char.from;
        private SvgPath to = Char.to;

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
//            objectAnimator = tweeningView.animate(Char.from, Char.to);
//            objectAnimator.setDuration(DURATION);

            Spinner fromSpinner = (Spinner) root.findViewById(R.id.fromSpinner);
            Spinner toSpinner = (Spinner) root.findViewById(R.id.toSpinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.from_numbers_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromSpinner.setAdapter(adapter);
            toSpinner.setAdapter(adapter);
            fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    from = Char.at(position);
                    objectAnimator = tweeningView.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    to = Char.at(position);
                    objectAnimator = tweeningView.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

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
