package com.example.izmai.practic_4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private ListView listCountSensors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listCountSensors  = findViewById(R.id.list_view);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        HashMap<String,Object> sensorTypeList;
        for (int i = 0;i<sensorList.size();i++){
            sensorTypeList = new HashMap<>();
            sensorTypeList.put("Name", sensorList.get(i).getName());
            sensorTypeList.put("Value",sensorList.get(i).getMaximumRange());
            arrayList.add(sensorTypeList);
        }
        SimpleAdapter mHistory = new SimpleAdapter(this,arrayList,android.R.layout.simple_list_item_2,new String[]{"Name","Value"},new int[]{android.R.id.text2});
        listCountSensors.setAdapter(mHistory);
    }
}
