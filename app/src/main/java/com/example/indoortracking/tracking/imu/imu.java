package com.example.indoortracking.tracking.imu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import com.example.indoortracking.R;

import java.util.List;
import java.lang.Math;

public class imu extends AppCompatActivity {
    SensorManager sm = null;
    TextView xValue = null;
    TextView yValue = null;
    TextView zValue = null;
    List list;
    float prevtime=System.nanoTime();
    float[] last_values = new float[3];
    float[] velocity = new float[3];
    float[] position= new float[3];
    Button zeroButton;
    int i = 0;
    float[] offset = new float[3];
    float[] values = new float[3];
    float[] accel = new float[3];

    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            values = sensorEvent.values;
            float time = System.nanoTime();
            float delta = ((prevtime - time)/(float)Math.pow(10, 9));
            prevtime = time;
            i++;
            getDistance(delta, values);
            if (i%2 == 0 && offset[0] != 0) {
                xValue.setText("x: " + position[0]);
                yValue.setText("y: " + position[1]);
                zValue.setText("z: " + position[2]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imu);
//        Arrays.fill(offset, 0);
        zeroButton = (Button)findViewById(R.id.zeroButton);
        TextView offsetText = (TextView)findViewById(R.id.offset);
        zeroButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                position[0] = 0;
                position[1] = 0;
                position[2] = 0;
                String offsetValues = "";
                for(i=0; i<3; i++){
                    position[i] = 0;
                    offset[i] = values[i];
                    Float value =  Math.round(offset[i]*1000)/(float) 1000;
                    offsetValues += value.toString();
                    offsetValues += " | ";
                }
                offsetText.setText(offsetValues);
            }
        });


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        xValue = (TextView)findViewById(R.id.xCoordinate);
        yValue = (TextView)findViewById(R.id.yCoordinate);
        zValue = (TextView)findViewById(R.id.zCoordinate);

        list = sm.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
        if(list.size()>0){
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }

    protected void onStop() {
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }

    private void getDistance(float dt, float[] accel){
        for(int index = 0 ; index < 3 ; ++index){
            accel[index] -= offset[index];
            velocity[index] += accel[index]*dt;
//            position[index] += velocity[index]*dt;
            position[index] = velocity[index];
        }
    }
}