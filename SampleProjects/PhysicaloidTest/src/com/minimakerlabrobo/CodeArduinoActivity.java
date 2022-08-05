package com.minimakerlabrobo;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;


import com.minimakerlabrobo.serial.BlinkLedActivity;
import com.minimakerlabrobo.serial.TurnONActivity;
import com.minimakerlabrobo.valueObject.ActivityBoxValue;
import com.physicaloid.lib.Physicaloid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeArduinoActivity extends Activity {

    private boolean its_detail=false;


    private EditText editTextCode;

    private Button buttonEnd= null;

    private Button buttonTurnOn= null;

    private boolean ligar=true;

    private String file;

    private ActivityBoxValue activityBoxValue=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_arduino);

        editTextCode = (EditText) findViewById(R.id.editTextCode);

        Intent myIntent = getIntent(); // gets the previously created intent
        file = myIntent.getStringExtra("file");


        file = myIntent.getStringExtra("file");

         activityBoxValue= (ActivityBoxValue) ActivityBoxSingleton.getInstancia().getMap().get(myIntent.getStringExtra("file"));

        file = activityBoxValue.getHex();

        editTextCode.append(activityBoxValue.getLabel()+"\n");

        editTextCode.append("______________________________\n");


        buttonEnd= (Button) findViewById(R.id.buttonEnd);




        //     buttonTurnOn= (Button) findViewById(R.id.buttonTurnoOn);

          if (activityBoxValue.getIno().equals("serial.ino")) {


              buttonEnd = (Button) findViewById(R.id.buttonEnd);

              buttonEnd.setText(getResources().getText(R.string.buttonNext));
          }else if (activityBoxValue.getIno().equals("Piscar_LED_Serial.ino")){

              buttonEnd = (Button) findViewById(R.id.buttonEnd);

              buttonEnd.setText(getResources().getText(R.string.buttonNext));

        }else{
              buttonEnd.setText(getResources().getText(R.string.buttonEndActivity));

          }



        if (activityBoxValue.getIno().equals("ardu.ino")) {


         //   MainActivity.getPhysicaloid().upload(mSelectedBoard, fileInputStream, mUploadCallback);
            this.readFileIno(activityBoxValue.getDirectory()+"/"+ activityBoxValue.getIno(),false);
        }else{

            this.readFileIno(activityBoxValue.getDirectory()+"/"+ activityBoxValue.getIno(),true);

            }





















        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activityBoxValue.getIno().equals("serial.ino")) {

                    Intent iuploadHexActivity = new Intent(CodeArduinoActivity.this, com.minimakerlabrobo.voice.MainActivity.class);

                    iuploadHexActivity.putExtra("file", CodeArduinoActivity.this.file);

                    startActivity(iuploadHexActivity);

                }


                if (CodeArduinoActivity.this.activityBoxValue.getIno().equals("ligarledserial.ino")) {


                    Intent iuploadHexActivity = new Intent(CodeArduinoActivity.this, TurnONActivity.class);

                    iuploadHexActivity.putExtra("file", CodeArduinoActivity.this.file);

                    startActivity(iuploadHexActivity);

                    its_detail = true;

                }else if (CodeArduinoActivity.this.activityBoxValue.getIno().equals("Piscar_LED_Serial.ino")) {


                        Intent iuploadHexActivity = new Intent(CodeArduinoActivity.this,BlinkLedActivity.class);

                        iuploadHexActivity.putExtra("file",CodeArduinoActivity.this.file);

                        startActivity(iuploadHexActivity);

                        its_detail=true;
                }else{

                    its_detail=true;

                    CodeArduinoActivity.this.finish();


                }




             //   CodeArduinoActivity.this.finish();


            }
        });


        /*buttonTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] buf;
                if (ligar) {

                    buf = "o".getBytes();

                    ligar = false;
                } else {
                    buf = "f".getBytes();
                    ligar = true;


                }


                Physicaloid mPhysicaloid = new Physicaloid(CodeArduinoActivity.this);
                if (mPhysicaloid.open()) {

                    mPhysicaloid.write(buf, buf.length);
                    mPhysicaloid.close();


                    //   CodeArduinoActivity.this.finish();


                }
            }
        });*/


        editTextCode.requestFocus();

        editTextCode.scrollTo(0,0);


    }



    private void readFileIno(String file, boolean asset ){
        BufferedReader reader = null;
        try {
            if (asset) {
                reader = new BufferedReader(
                        new InputStreamReader(getAssets().open(file), "UTF-8"));
            }else{
                File f2 = new File(Environment.getExternalStorageDirectory(), "/roboardu/ardu.ino");

                FileInputStream fileInputStream = new FileInputStream(f2);

                reader = new BufferedReader(
                        new InputStreamReader(fileInputStream, "UTF-8"));

            }
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                editTextCode.append(mLine+"\n");

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
