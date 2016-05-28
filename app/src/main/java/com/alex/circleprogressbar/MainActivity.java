package com.alex.circleprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.socks.library.KLog;

import github.alex.circleprogressbar.CircleProgressBar;

public class MainActivity extends Activity {
    private CircleProgressBar circleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();
    }

    private void intView() {
        circleProgressBar = (CircleProgressBar) findViewById(R.id.cpb);
        circleProgressBar.setDotDiameter(20);
        circleProgressBar.setMaxProgressWidth(5);
        circleProgressBar.setFirstProgressWidth(8);
        circleProgressBar.setSecondProgressWidth(10);
        circleProgressBar.setFirstProgress(80, 1000);
        circleProgressBar.setSecondProgress(40, 1000);
        SeekBar sbFirst = (SeekBar) findViewById(R.id.sb_first);
        SeekBar sbSecond = (SeekBar) findViewById(R.id.sb_second);
        sbFirst.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        sbSecond.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
    }
    private final class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener
    {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            KLog.e("progress = "+progress);
            if(R.id.sb_first == seekBar.getId()){
                circleProgressBar.setFirstProgress(progress);
            }else if(R.id.sb_second == seekBar.getId()){
                circleProgressBar.setSecondProgress(progress);
            }
            if(progress > 90){
                circleProgressBar.setCanDisplayDot(false);
            }else{
                circleProgressBar.setCanDisplayDot(true);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
