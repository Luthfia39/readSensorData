package com.example.readsensordata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
// SensorEventListener : memberi tahu kalo ada perubahan data sensor
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

//    mengambil atribut sensor
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        inisialisasi
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        menampung sensor, tipe data : object sensor
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        menyimpan data sensor
//        untuk mengetahui namanya, diubah menjadi string
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

//        inisialisasi
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

//        cek sensor apakah ada atau tidak
        String sensor_error = "No Sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
    }

//    register sensor
    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

//    unregister
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        cari tahu jenis sensor yang berubah
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light Sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity Sensor : %1$.2f", currentValue));
                break;
            default:
        };
        if (currentValue >= 20000 && currentValue <= 40000){
            findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(currentValue >= 0 && currentValue < 20000){
            findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.teal_700));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}