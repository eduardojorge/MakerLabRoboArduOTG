package com.minimakerlabrobo.arduino;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ZoomControls;

import com.minimakerlabrobo.ActivityBoxSingleton;

import com.minimakerlabrobo.R;
import com.minimakerlabrobo.UploadHexActivity;
import com.minimakerlabrobo.util.Util;
import com.minimakerlabrobo.valueObject.ActivityBoxValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProtoboardArduinoActivity extends Activity {

   float mx;
   float my;


    private boolean its_detail=false;

    private ImageView imageView;


    //private EditText editTextCode;

    private Button buttonNext= null;

    protected String file=null;

    private TextView textViewName= null;


    VideoView video_player_view; DisplayMetrics dm; SurfaceView sur_View; MediaController media_Controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protoboard_arduino);


        Intent myIntent = getIntent(); // gets the previously created intent



        file = myIntent.getStringExtra("file");

        ActivityBoxValue activityBoxValue= (ActivityBoxValue) ActivityBoxSingleton.getInstancia().getMap().get(myIntent.getStringExtra("file"));
        file = activityBoxValue.getLabel();


        textViewName =(TextView) findViewById(R.id.textViewName);

        textViewName.setText(activityBoxValue.getLabel());


        this.imageView = (ImageView) findViewById(R.id.imageView);



        String uri = "@drawable/"+ activityBoxValue.getDirectory().toLowerCase()+"_arduino";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        //imageview= (ImageView)findViewById(R.id.imageView);
        Drawable res = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);



       // this.imageView.setImageResource(R.drawable.piscar_led_arduino);

     //   imageView.setImageDrawable(image);

       // int id = getResources().getIdentifier("com.minimakerlab:drawable/piscar_led_arduino.png", null, null);

        //this.imageView.setImageResource(id);

        //editTextCode = (EditText) findViewById(R.id.editTextCode);
       // if (file.equals("piscarLae.ino.standard.hex")){

       // editTextCode.append(activityBoxValue.getLabel()+"\n");

        this.readFileProt(activityBoxValue.getDirectory()+"/"+activityBoxValue.getProt());


        //}else if (file.equals("ligarledserial.ino.standard.hex")){

          //  editTextCode.append("Ligar Led Celular\n");

        //}
      //  editTextCode.append("\n");
      //  editTextCode.append(" 3-83 (Led(-) to GND)\n");
       // editTextCode.append(" 4-15 (Led(+) to Resistor)\n");
      //  editTextCode.append("16-L12 (Resistor to Porta12)\n");

        buttonNext= (Button) findViewById(R.id.buttonNext);

       buttonNext.setText(getResources().getText(R.string.buttonNext));



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 Intent iuploadHexActivity = new Intent(ProtoboardArduinoActivity.this,UploadHexActivity.class);

                iuploadHexActivity.putExtra("file",ProtoboardArduinoActivity.this.file);

                startActivity(iuploadHexActivity);

                its_detail=true;




            }
        });

        //getInit();

        ZoomControls zoom = (ZoomControls) findViewById(R.id.zoomControls1);


        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                float x = imageView.getScaleX();
                float y = imageView.getScaleY();

                imageView.setScaleX((float) (x+0.2));
                imageView.setScaleY((float) (y+0.2));
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                float x = imageView.getScaleX();
                float y = imageView.getScaleY();

                imageView.setScaleX((float) (x-0.2));
                imageView.setScaleY((float) (y-0.2));
            }
        });


        imageView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent event) {

                float curX, curY;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mx = event.getX();
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getX();
                        curY = event.getY();
                        imageView.scrollBy((int) (mx - curX), (int) (my - curY));
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        curY = event.getY();
                        imageView.scrollBy((int) (mx - curX), (int) (my - curY));
                        break;
                }

                return true;
            }
        });




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
         //   editTextCode.append(mLine+"\n");

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


}
