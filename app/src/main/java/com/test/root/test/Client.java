package com.test.root.test;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Client extends Activity implements SensorEventListener {

    Sensor acc;
    SensorManager sm;
    int m=0,i=-1,c=-1;
    float x=0,y=0;
    Button reset,bX,bO,bSq,bTr;
    String dir[]={"up","down","right","left","down right","down left","up right","up left","none"};


    Socket socket = null,socketb=null,socketk=null;
    DataOutputStream dataOutputStream = null,dataOutputStreamb = null,dataOutputStreamk = null;
    DataInputStream dataInputStream = null,dataInputStreamb = null,dataInputStreamk = null;
    String dstAddress = "192.168.43.88";
    int dstPort = 5522,dstPortb = 5523,dstPortk = 5520,x1,y1;
    SharedPreferences sp;
    TextView tracker;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        reset=(Button)findViewById(R.id.btntest);
        tracker=(TextView)findViewById(R.id.tvmouse);
        bO=(Button)findViewById(R.id.btnO);
        bX=(Button)findViewById(R.id.btnX);
        bTr=(Button)findViewById(R.id.btnTri);
        bSq=(Button)findViewById(R.id.btnSq);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
        sp=getSharedPreferences("file", 0);
        dstAddress =sp.getString("ipadd",null);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = 1;
            }
        });
        bO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun1("B");
            }
        });
        bX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fun1("Y");
            }
        });
        bTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fun1("X");
            }
        });
        bSq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun1("A");
            }
        });
        tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               int sx = (int) (x1 / 2 - event.getX());
               int sy = (int) (y1 / 2 - event.getY());
                fun2(String.valueOf(sx) + "x" + String.valueOf(sy) + "x");
                tracker.setText("Touch coordinates : " + String.valueOf(x1 / 2 - event.getX()) + "x" + String.valueOf(y1 / 2 - event.getY()) + "x");
                // mc.fun(String.valueOf((int) (x / 2 - event.getX())) + "x" + String.valueOf((int) (y / 2 - event.getY())));
                // mc.fun(String.valueOf((int) event.getX())+"x"+String.valueOf((int) event.getX()));

                return true;
            }
        });

        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                socket = new Socket(dstAddress, dstPort);
                socketb = new Socket(dstAddress, dstPortb);
                socketk = new Socket(dstAddress, dstPortk);

            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        x1=tracker.getWidth();
        y1=tracker.getHeight();
    }

    void fun(String s) {

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(s);
            dataOutputStream.flush();
            //textIn.setText(s);
        } catch (IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    void fun2(String s) {

        try {
            dataOutputStreamk = new DataOutputStream(socketk.getOutputStream());
            dataInputStreamk = new DataInputStream(socketk.getInputStream());
            dataOutputStreamk.writeUTF(s);
            dataOutputStreamk.flush();
            //textIn.setText(s);
        } catch (IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    void fun1(String s) {

        try {
            dataOutputStreamb = new DataOutputStream(socketb.getOutputStream());
            dataInputStreamb = new DataInputStream(socketb.getInputStream());
            dataOutputStreamb.writeUTF(s);
            dataOutputStreamb.flush();
            //textIn.setText(s);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(socketb != null) {
            try {
                socketb.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(socketk != null) {
            try {
                socketk.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
        //ac.setText("X:" + event.values[0] + "\n y:" + event.values[1] + "\n z:" + event.values[2]);
        if(event.values[0]-x>2 && event.values[1]-y>2){
            i=4;
            //dir[1]; down right
        }else if(event.values[0]-x>2 && event.values[1]-y<-2){
            i=5;
            //dir[1]; down left
        }else if(event.values[0]-x<-2 && event.values[1]-y>2){
            i=6;
            //dir[0]; up right
        }else if(event.values[0]-x<-2 && event.values[1]-y<-2){
            i=7;
            //dir[0]; up left
        }else if(event.values[1]-y>2){
            i=2;
            // dir[2]; right
        }else if(event.values[1]-y<-2){
            i=3;
            //dir[3;]left
        }else if(event.values[0]-x>2){
            i=1;
            //dir[1]; down
        }else if(event.values[0]-x<-2){
            i=0;
            //dir[0]; up
        }else{
            i=8;
        }
        if(i != c){
           // ac1.setText(dir[i]);
            c=i;
            fun(String.valueOf(i));
        }
    }
}

