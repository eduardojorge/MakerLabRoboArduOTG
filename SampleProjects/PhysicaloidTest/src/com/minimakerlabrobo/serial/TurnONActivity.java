package com.minimakerlabrobo.serial;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.minimakerlabrobo.R;
import com.minimakerlabrobo.bluetooth.MainActivity;
import com.minimakerlabrobo.valueObject.MessageVO;
import com.physicaloid.lib.Physicaloid;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Created by saba on 25/02/17.
 */
public class TurnONActivity extends Activity {


    Button butttonTurnON;
    String descButtonON;
    String descButtonOFF;

    private  static BluetoothSPP bt;

    private TextView textViewConnect= null;

    private Switch switchBluetooth=null;
   private  Physicaloid mPhysicaloid=null;


    public static BluetoothSPP getInstanciaBluetoothSPP(){
        return TurnONActivity.bt;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turnon_led_main);




        bt = new BluetoothSPP(TurnONActivity.this);


        switchBluetooth = (Switch) findViewById(R.id.switchbluetooth);


        textViewConnect = (TextView) findViewById(R.id.textViewConnect);

     bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }


        this.butttonTurnON= (Button) this.findViewById(R.id.btnTurnONLed);

        TurnONActivity.getInstanciaBluetoothSPP().setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {


                MessageVO.getInstancia().setMessage(message);


                Toast.makeText(TurnONActivity.this, "Bluetooth:"+message, Toast.LENGTH_SHORT).show();


                byte[] buf;
                if (message.equals("L")) {

                    buf = "L".getBytes();



                    //   ligar = false;
                } else {
                    buf = "D".getBytes();
                    // ligar = true;


                }


                if (TurnONActivity.this.mPhysicaloid==null){
                    TurnONActivity.this.mPhysicaloid = new Physicaloid(TurnONActivity.this);
                }


               // Physicaloid mPhysicaloid = new Physicaloid(TurnONActivity.this);
                if (TurnONActivity.this.mPhysicaloid.open()) {


                    Toast.makeText(TurnONActivity.this, "Open", Toast.LENGTH_SHORT).show();

                    TurnONActivity.this.mPhysicaloid.write(buf, buf.length);
                    TurnONActivity.this.mPhysicaloid.close();


                    //   CodeArduinoActivity.this.finish();


                }else{
                    Toast.makeText(TurnONActivity.this, "Not Open", Toast.LENGTH_SHORT).show();

                }







            }
        });




    }

    public void onStart() {
        super.onStart();


        this.switchBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!bt.isBluetoothAvailable()) {
                    Toast.makeText(getApplicationContext()
                            , "Bluetooth is not available"
                            , Toast.LENGTH_SHORT).show();
                    finish();
                }


                if (!bt.isBluetoothEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
                } else {
                    if(!bt.isServiceAvailable()) {
                        bt.setupService();
                        bt.startService(BluetoothState.DEVICE_OTHER);
                        //setup();
                    }
                }

                bt = new BluetoothSPP(TurnONActivity.this);

                if(!bt.isBluetoothAvailable()) {
                    Toast.makeText(getApplicationContext()
                            , "Bluetooth is not available"
                            , Toast.LENGTH_SHORT).show();
                    finish();
                }

                }
            });






                Intent intent = getIntent();
        String tipo = (String)     intent.getSerializableExtra("tipo");
     //   if (tipo.equals(MainActivity.LED)){

            descButtonON = getResources().getString(R.string.turnOnled);
            descButtonOFF = getResources().getString(R.string.turnOffled);

            TurnONActivity.this.butttonTurnON.setText(getResources().getString(R.string.turnOnled));







        this.butttonTurnON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] buf;






                if ( TurnONActivity.this.butttonTurnON.getText().equals(descButtonON)){

                    MainActivity.getInstanciaBluetoothSPP().send("L", true);

                  //  TurnONActivity.this.butttonTurnON.setText(descButtonOFF);
                    buf = "L".getBytes();


            }else{

                    MainActivity.getInstanciaBluetoothSPP().send("D", true);

                   // TurnONActivity.this.butttonTurnON.setText(descButtonON);

                    buf = "D".getBytes();
                }

                /*Physicaloid mPhysicaloid = new Physicaloid(TurnONActivity.this);
                if (mPhysicaloid.open()) {

                    mPhysicaloid.write(buf, buf.length);
                    mPhysicaloid.close();


                    //   CodeArduinoActivity.this.finish();


                }*/


                if (TurnONActivity.this.mPhysicaloid==null){
                    TurnONActivity.this.mPhysicaloid = new Physicaloid(TurnONActivity.this);
                }

                if (TurnONActivity.this.mPhysicaloid.open()) {

                    TurnONActivity.this.mPhysicaloid.write(buf, buf.length);
                    TurnONActivity.this.mPhysicaloid.close();


                    //   CodeArduinoActivity.this.finish();


                }

            }
        });

    }
    public class MyTask extends AsyncTask<String, Void, String> {
        MessageVO messageVO;

        public MyTask(MessageVO callback){
            this.messageVO= callback;
        }

        @Override
        protected String doInBackground(String... params){

            //    Toast.makeText(getApplicationContext()
            //          , "teste"
            //        , Toast.LENGTH_SHORT).show();

        /*

        Código que vamos a ejecutar en la tarea y que se ejecutará en segundo plano

        */   if( messageVO.getMessage().equals("turnON")){

                // ClickActivity.this.imageView.setBackgroundColor(Color.BLACK);

                //
                //
                Toast.makeText(getApplicationContext()
                        , messageVO.getMessage()
                        , Toast.LENGTH_SHORT).show();

            }else{

                // ClickActivity.this.imageView.setBackgroundColor(Color.BLUE);

                // Toast.makeText(MainActivity.this.getApplicationContext()
                //       , messageVO.getMessage()
                //     , Toast.LENGTH_SHORT).show();

            }
            return "OK";
        }

        @Override
        protected void onPostExecute(String result){
            //this._callback.Executed(result);
        }


    }




}
