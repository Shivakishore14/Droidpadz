package com.test.root.test;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class test extends Activity implements SensorEventListener {
    Sensor acc;
    SensorManager sm;
    TextView ac,ac1;
    int m=0,i=-1,c=-1;
    float x=0,y=0;
    Button reset;
    String dir[]={"up","down","right","left"};


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ac=(TextView)findViewById(R.id.tvtest);
        ac1=(TextView)findViewById(R.id.tvtest1);
        reset=(Button)findViewById(R.id.btntest);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,acc,SensorManager.SENSOR_DELAY_NORMAL);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m=1;
            }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(m==1){
            x=event.values[0];
            y=event.values[1];
            m=0;
        }
        ac.setText("X:"+event.values[0]+ "\n y:"+event.values[1] +"\n z:"+event.values[2]);
        if(event.values[0]-x>2){
            i=1;
            //dir[1];
        }else if(event.values[0]-x<-2){
            i=0;
            //dir[0];
        }else if(event.values[1]-y>2){
            i=2;
            // dir[2];
        }else if(event.values[1]-y<-2){
            i=3;
            //dir[3;]
        }else{
            ac1.setText("none");
        }
        if(i != c){
            ac1.setText(dir[i]);
            c=i;
        }

    }
}