package com.minimakerlabrobo.voice;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.minimakerlabrobo.CodeArduinoActivity;
import com.minimakerlabrobo.R;
import com.minimakerlabrobo.UploadHexActivity;
import com.minimakerlabrobo.serial.BlinkLedActivity;
import com.minimakerlabrobo.serial.TurnONActivity;
import com.minimakerlabrobo.valueObject.MessageVO;
import com.physicaloid.lib.Physicaloid;

import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class MainActivity extends Activity implements OnClickListener {

    public ListView mList;
    public ImageButton speakButton;
    public ImageButton onButton;
    public ImageButton offButton;

    public ImageButton felizButton;
    public  static BluetoothSPP bt;

    private Switch switchBluetooth=null;
    private Physicaloid mPhysicaloid=null;

    private Button buttonEnd= null;


    public static BluetoothSPP getInstanciaBluetoothSPP(){
        return MainActivity.bt;
    }

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        speakButton = (ImageButton) findViewById(R.id.btn_speak);

        onButton = (ImageButton) findViewById(R.id.btn_on);

        offButton = (ImageButton) findViewById(R.id.btn_off);

        felizButton = (ImageButton) findViewById(R.id.btn_feliz);


        speakButton.setOnClickListener(this);
        buttonEnd= (Button) findViewById(R.id.buttonEnd);
        buttonEnd.setText(getResources().getText(R.string.buttonEndActivity));






        voiceinputbuttons();





    }

    @Override
    public void onStart() {
        super.onStart();

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startVoiceRecognitionActivity();

            }
        });
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] buf;



                    buf = "L".getBytes();





                Physicaloid mPhysicaloid = new Physicaloid(MainActivity.this);
                if (mPhysicaloid.open()) {

                    mPhysicaloid.write(buf, buf.length);
                    mPhysicaloid.close();
                }

            }
        });
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] buf;

                    buf = "D".getBytes();





                Physicaloid mPhysicaloid = new Physicaloid(MainActivity.this);
                if (mPhysicaloid.open()) {

                    mPhysicaloid.write(buf, buf.length);
                    mPhysicaloid.close();
                }

            }
        });


        felizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] buf;

                buf = "F".getBytes();





                Physicaloid mPhysicaloid = new Physicaloid(MainActivity.this);
                if (mPhysicaloid.open()) {

                    mPhysicaloid.write(buf, buf.length);
                    mPhysicaloid.close();
                }

            }
        });




        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                    MainActivity.this.finish();


                }




                //   CodeArduinoActivity.this.finish();



        });


    }

    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }

    public void voiceinputbuttons() {

        mList = (ListView) findViewById(R.id.list);
    }

    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub



                    //   CodeArduinoActivity.this.finish();



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, matches));

            String s = (String) matches.get(0);
            byte[] buf;
            if(s.toLowerCase().equals("ligar")){


                buf = "L".getBytes();


            } else if (s.toLowerCase().equals("desligar")){
                buf = "D".getBytes();



            } else if (s.toLowerCase().equals("feliz")){
                buf = "F".getBytes();



            }else {
                buf = "D".getBytes();
            }


            Physicaloid mPhysicaloid = new Physicaloid(MainActivity.this);
            if (mPhysicaloid.open()) {

                mPhysicaloid.write(buf, buf.length);
                mPhysicaloid.close();
            }

            // matches is the result of voice input. It is a list of what the
            // user possibly said.
            // Using an if statement for the keyword you want to use allows the
            // use of any activity if keywords match
            // it is possible to set up multiple keywords to use the same
            // activity so more than one word will allow the user
            // to use the activity (makes it so the user doesn't have to
            // memorize words from a list)
            // to use an activity from the voice input information simply use
            // the following format;
            // if (matches.contains("keyword here") { startActivity(new
            // Intent("name.of.manifest.ACTIVITY")

            if (matches.contains("information")) {
                informationMenu();
            }
        }
    }
}
