package com.example.readsensordata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        inisialisasi
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        nampung sensor, tipe data : object sensor
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        menyimpan data sensor
//        untuk mengetahui namanya, diubah menjadi string
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);
    }
}