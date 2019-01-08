package com.example.izmai.aceelerometr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;
    Sensor mAccelerometerSensor;
    TextView mAzimuthTextView,mPitchTextView,mRollTextView,textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAzimuthTextView = findViewById(R.id.textViewAzimuth);
        mPitchTextView = findViewById(R.id.textViewPitch);
        mRollTextView = findViewById(R.id.textViewRoll);
        textView = findViewById(R.id.textView);
    }
        @Override
       protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this);
        }


        @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mAccelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void onSensorChanged(SensorEvent event) {
        float valueAzimuth = event.values[0];
        float valuePitch = event.values[1];
        float valueRoll = event.values[2];
        mAzimuthTextView.setText("Azimuth: " + String.valueOf(valueAzimuth));
        mPitchTextView.setText("Pitch: " + String.valueOf(valuePitch));
        mRollTextView.setText("Roll: " + String.valueOf(valueRoll));
        if(valueRoll>0){textView.setText("vverh");}
        else {textView.setText("vniz");}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
