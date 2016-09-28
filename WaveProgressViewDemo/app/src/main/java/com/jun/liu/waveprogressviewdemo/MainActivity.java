package com.jun.liu.waveprogressviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MyWaveProgressView waveProgressbar;
    SeekBar mSeekBarProgress;
    SeekBar mSeekBarWaveHeight;
    SeekBar mSeekBarWaveSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waveProgressbar = (MyWaveProgressView) findViewById(R.id.waveProgressbar);
        mSeekBarProgress = (SeekBar) findViewById(R.id.seekBarProgress);
        mSeekBarWaveHeight = (SeekBar) findViewById(R.id.seekBarWaveHight);
        mSeekBarWaveSpeed = (SeekBar) findViewById(R.id.seekBarWaveSpeed);
        init();
    }

    private void init() {
        mSeekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveProgressbar.setCurrent(progress, progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarWaveHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                waveProgressbar.setWaveHeight((progress+80)/2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarWaveSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveProgressbar.setWaveSpeedContral((progress+40)/2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarWaveHeight.setMax(90);
    }
}
