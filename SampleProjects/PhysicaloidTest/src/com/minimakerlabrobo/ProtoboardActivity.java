package com.minimakerlabrobo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.minimakerlabrobo.arduino.ProtoboardArduinoActivity;
import com.minimakerlabrobo.mmlbox.ProtoboardMMLBoxActivity;
import com.minimakerlabrobo.util.Util;
import com.minimakerlabrobo.valueObject.ActivityBoxValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class ProtoboardActivity extends Activity {


    private boolean its_detail=false;


    //private EditText editTextCode;

    private Button buttonNext= null;

    private Button buttonArduino;

    private Button buttonMMLBox;


    protected String file=null;


    private TextView textViewName= null;


    VideoView video_player_view; DisplayMetrics dm; SurfaceView sur_View; MediaController media_Controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protoboard);


        Intent myIntent = getIntent(); // gets the previously created intent



        file = myIntent.getStringExtra("file");

        ActivityBoxValue activityBoxValue= (ActivityBoxValue) ActivityBoxSingleton.getInstancia().getMap().get(myIntent.getStringExtra("file"));
        file = activityBoxValue.getLabel();


        textViewName =(TextView) findViewById(R.id.textViewName);

        textViewName.setText(activityBoxValue.getLabel());



       // editTextCode = (EditText) findViewById(R.id.editTextCode);
       // if (file.equals("piscarLae.ino.standard.hex")){

        //editTextCode.append(activityBoxValue.getLabel()+"\n");

        this.readFileProt(activityBoxValue.getDirectory()+"/"+activityBoxValue.getProt());


        //}else if (file.equals("ligarledserial.ino.standard.hex")){

          //  editTextCode.append("Ligar Led Celular\n");

        //}
      //  editTextCode.append("\n");
      //  editTextCode.append(" 3-83 (Led(-) to GND)\n");
       // editTextCode.append(" 4-15 (Led(+) to Resistor)\n");
      //  editTextCode.append("16-L12 (Resistor to Porta12)\n");


        //buttonNext= (Button) findViewById(R.id.buttonNext);

       // buttonNext.setText(getResources().getText(R.string.buttonNext));



        /*buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 Intent iuploadHexActivity = new Intent(ProtoboardActivity.this,UploadHexActivity.class);

                iuploadHexActivity.putExtra("file",ProtoboardActivity.this.file);

                startActivity(iuploadHexActivity);

                its_detail=true;




            }
        });*/


        buttonArduino= (Button) findViewById(R.id.buttonArduino);

        buttonMMLBox= (Button) findViewById(R.id.buttonMMLBox);

      //  buttonArduino.setText(getResources().getText(R.string.buttonNext));



        buttonArduino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent iuploadHexActivity = new Intent(ProtoboardActivity.this,ProtoboardArduinoActivity.class);

                iuploadHexActivity.putExtra("file",ProtoboardActivity.this.file);

                startActivity(iuploadHexActivity);

                its_detail=true;




            }
        });

        buttonMMLBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent iuploadHexActivity = new Intent(ProtoboardActivity.this,ProtoboardMMLBoxActivity.class);

                iuploadHexActivity.putExtra("file",ProtoboardActivity.this.file);

                startActivity(iuploadHexActivity);

                its_detail=true;




            }
        });


        getInit();


    }

    @Override
    public void onResume(){
        super.onResume();
        if(its_detail == true){
            finish();
        }
    }
private void readFileProt(String file){
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(
                new InputStreamReader(getAssets().open(file), "UTF-8"));

        // do reading, usually loop until end of file reading
        String mLine;
        while ((mLine = reader.readLine()) != null) {
            //process line
           // editTextCode.append(mLine+"\n");

        }
    } catch (IOException e) {
        //log the exception
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                //log the exception
            }
        }
    }
}

    public void getInit() {

        try {

            video_player_view = (VideoView) findViewById(R.id.VideoView);


            media_Controller = new MediaController(this);

            dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;
            int width = dm.widthPixels;
            video_player_view.setMinimumWidth(width);
            video_player_view.setMinimumHeight(height);
            video_player_view.setMediaController(media_Controller);



            //   Util.ResourceToUri(this.getBaseContext(), R.raw.video_splash)


            //     Uri uri = Uri.parse(  "android.resource://your.package.name/" + R.raw.video_splash);

            Log.i("teste", Util.ResourceToUri(this.getBaseContext(), R.raw.piscar_led4).getEncodedPath());
            video_player_view.setVideoURI(Util.ResourceToUri(this.getBaseContext(), R.raw.piscar_led4));

            // AssetFileDescriptor afd=getAssets().openFd(Util.ResourceToUri ( this.getApplicationContext(),R.raw.video_splash).getEncodedPath());

            // video_player_view.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            video_player_view.start();

            video_player_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
