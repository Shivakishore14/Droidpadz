package com.test.root.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    Button bkey,bjoy;
    TextView tvip;
    EditText etip;
    Intent i;
    public static String file="shareddata";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bjoy=(Button)findViewById(R.id.btnmainjoy);
        bkey=(Button)findViewById(R.id.btnmainkey);
        tvip=(TextView)findViewById(R.id.tvipadd);
        etip=(EditText)findViewById(R.id.etip);
        sp=getSharedPreferences("file", 0);
        final String sip =sp.getString("ipadd",null);
        if( sip != null)
            etip.setText(sip);
        bjoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip=etip.getText().toString();

                if(ip.length()>6) {
                    //start
                    sp = getSharedPreferences("file", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("ipadd", ip);
                    editor.commit();
                    //end
                    i = new Intent(MainActivity.this, Client.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip=etip.getText().toString();
                if(ip.length()>6) {
                    //start
                    sp = getSharedPreferences("file", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("ipadd", ip);
                    editor.commit();
                    //end
                    i = new Intent(MainActivity.this,Keymouse.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}