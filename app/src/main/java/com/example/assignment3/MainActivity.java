package com.example.assignment3;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    private static final int POLL_INTERVAL = 500;
    private final Handler hdr = new Handler();
    SensorInfo sensor_info = new SensorInfo();
    Boolean shown_dialog = false;
    private static final int shake_threshold = 15;

    private final Runnable pollTask = new Runnable() {
        public void run() {
            showDialog();
            hdr.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            sensor_info.accX=event.values[0];
            sensor_info.accY=event.values[1];
            sensor_info.accZ=event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void showDialog() {
        if( (Math.abs(sensor_info.accX)>shake_threshold) || (Math.abs(sensor_info.accY)>shake_threshold) || (Math.abs(sensor_info.accZ)>shake_threshold) ) {
            if(!shown_dialog) {
                shown_dialog = true;
                final AlertDialog.Builder viewDialog = new AlertDialog.Builder(this);
                viewDialog.setIcon(android.R.drawable.btn_star_big_on);
                switch (randomNumber()) {
                    case 1:
                        viewDialog.setTitle(R.string.no1);
                        viewDialog.setMessage(R.string.info1);
                        break;
                    case 2:
                        viewDialog.setTitle(R.string.no2);
                        viewDialog.setMessage(R.string.info2);
                        break;
                    case 3:
                        viewDialog.setTitle(R.string.no3);
                        viewDialog.setMessage(R.string.info3);
                        break;
                    case 4:
                        viewDialog.setTitle(R.string.no4);
                        viewDialog.setMessage(R.string.info4);
                        break;
                    case 5:
                        viewDialog.setTitle(R.string.no5);
                        viewDialog.setMessage(R.string.info5);
                        break;
                    case 6:
                        viewDialog.setTitle(R.string.no6);
                        viewDialog.setMessage(R.string.info6);
                        break;
                    case 7:
                        viewDialog.setTitle(R.string.no7);
                        viewDialog.setMessage(R.string.info7);
                        break;
                    case 8:
                        viewDialog.setTitle(R.string.no8);
                        viewDialog.setMessage(R.string.info8);
                        break;
                    case 9:
                        viewDialog.setTitle(R.string.no9);
                        viewDialog.setMessage(R.string.info9);
                        break;
                    case 10:
                        viewDialog.setTitle(R.string.no10);
                        viewDialog.setMessage(R.string.info10);
                        break;
                }

                viewDialog.setPositiveButton("OK",
                        (dialog, which) -> {
                            dialog.dismiss();
                            shown_dialog = false;
                        });
                viewDialog.show();
            }//end if
        }
    }

    private int randomNumber(){
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    @SuppressLint("WakelockTimeout")
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        hdr.removeCallbacks(pollTask);
    }

    static class SensorInfo{
        float accX, accY, accZ;
    }
}