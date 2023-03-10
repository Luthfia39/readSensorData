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
    private Sensor mSensorTemp;
    private Sensor mSensorPressure;
    private Sensor mSensorHumidity;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorTemp;
    private TextView mTextSensorPressure;
    private TextView mTextSensorHumidity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        inisialisasi
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        menampung sensor, tipe data : object sensor
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

//        cek nama sensor, diubah menjadi string, kemudian disimpan
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

//        menampilkan daftar sensor
        TextView sensorTextView = findViewById(R.id.sensor_list2);
        sensorTextView.setText(sensorText);

//        inisialisasi
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorTemp = findViewById(R.id.label_temperature);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorHumidity = findViewById(R.id.label_humidity);

//        untuk mendeteksi jarak objek yg ada di sekitar. digunakan ketika bertelepon
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//        untuk mengatur kecerahan cahaya pada layar/mengubah mode kecerahan layar ke otomatis
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        untuk mendeteksi suhu perangkat dan baterai
        mSensorTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
//        untuk mengecek apakah perangkat berada di dalam air atau tidak
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
//        untuk mengetahui kelembaban udara sekitar
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

//        cek sensor apakah ada atau tidak
        String sensor_error = "No Sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorTemp == null){
            mTextSensorTemp.setText(sensor_error);
        }
        if (mSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorHumidity == null){
            mTextSensorHumidity.setText(sensor_error);
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
        if (mSensorTemp != null){
            mSensorManager.registerListener(this, mSensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPressure != null){
            mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorHumidity != null){
            mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
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
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorTemp.setText(String.format("Temperature Sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure Sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity Sensor : %1$.2f", currentValue));
                break;
            default:
        };

//        mengganti background berdasarkan sensor light
//        if (currentValue >= 20000 && currentValue <= 40000){
//            findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.red));
//        }
//        else if(currentValue >= 0 && currentValue < 20000){
//            findViewById(R.id.layout).setBackgroundColor(getResources().getColor(R.color.teal_700));
//        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}