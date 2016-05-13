package com.test.root.test;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Keymouse extends Activity implements SensorEventListener {

    Socket socket = null,socketkey = null;
    EditText key;
    SharedPreferences sp;
    DataOutputStream dataOutputStream = null,dataOutputStreamkey = null;
    DataInputStream dataInputStream = null,dataInputStreamkey = null;
    String dstAddress = "192.168.43.88";
    int dstPort = 5520,dstPortkey = 5521,ckey=0,x,y,p=0,sx,sy,cnt=0,ms=0;
    Button bd, bl, br;
    TextView tracker;
    boolean last=false;

    Sensor acc;
    SensorManager sm;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;
        sp=getSharedPreferences("file", 0);
      dstAddress=sp.getString("ipadd","none");
        tracker=(TextView) findViewById(R.id.tvtracker);

        bl = (Button) findViewById(R.id.btnleft);
        br = (Button) findViewById(R.id.btnright);
        key = (EditText) findViewById(R.id.etkeyb);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);


        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
                socket = new Socket(dstAddress, dstPort);
                socketkey = new Socket(dstAddress, dstPortkey);

            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //        fun(String.valueOf(sx) + "x" + String.valueOf(sy)+ "x");

        tracker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sx = (int) (x / 2 - event.getX());
                sy = (int) (y / 2 - event.getY());

                fun(String.valueOf(sx) + "x" + String.valueOf(sy) + "x");
                ms = 0;
                //"Touch coordinates : " + String.valueOf(x / 2 - event.getX()) + "x" + String.valueOf(y / 2 - event.getY())+ "x");
                tracker.setText("Touch coordinates : " + String.valueOf(x / 2 - event.getX()) + "x" + String.valueOf(y / 2 - event.getY()) + "x");
                // mc.fun(String.valueOf((int) (x / 2 - event.getX())) + "x" + String.valueOf((int) (y / 2 - event.getY())));
                // mc.fun(String.valueOf((int) event.getX())+"x"+String.valueOf((int) event.getX()));

                return true;
            }
        });

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun("left");
            }
        });
        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun("right");
            }
        });



        key.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method
                if (count != 0) {
                    ms = 1;
                    br.setText(String.valueOf(count));
                    bl.setText(String.valueOf(cnt));
                    if (count <= cnt) {
                        if (count != cnt) {
                            if (count == 1 && cnt != 2)
                                key("enter");
                            else {
                                key("back");
                                cnt = count;
                            }
                        }
                    } else {
                        cnt = count;
                        char ch = s.charAt(start + count - 1);
                        String st = Character.toString(ch);
                        if (!st.equals("")) {
                            key(st);
                        }

                    }
                }

            }
        });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        x=tracker.getWidth();
        y=tracker.getHeight();
    }


    void fun(String s) {
      if(socket!=null)
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            if(s!=null)
               dataOutputStream.writeUTF(s);
            //textIn.setText(s);
        } catch ( IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      }
    void key(String s) {

        try {
            dataOutputStreamkey = new DataOutputStream(socketkey.getOutputStream());
            dataInputStreamkey = new DataInputStream(socketkey.getInputStream());
            dataOutputStreamkey.writeUTF(s);
            //textIn.setText(s);
        } catch (IOException e){
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
        if(socketkey != null) {
            try {
                socketkey.close();
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

           float x=event.values[0];
           float  y=event.values[1];
            int a=(int)(x*100);
            int b=(int)(y*100);
        if(ms==1) {
            // fun(String.valueOf(a) + "x" + String.valueOf(b)+ "x");
            // ac.setText("X:" + event.values[0] + "\n y:" + event.values[1] + "\n z:" + event.values[2]);
        }
          //  fun(String.valueOf(i));
        }

    }



