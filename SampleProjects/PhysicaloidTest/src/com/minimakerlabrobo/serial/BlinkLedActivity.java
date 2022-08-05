package com.minimakerlabrobo.serial;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.minimakerlabrobo.MainActivity;
import com.minimakerlabrobo.R;
import com.minimakerlabrobo.valueObject.MessageVO;
import com.physicaloid.lib.Physicaloid;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Created by saba on 25/02/17.
 */
public class BlinkLedActivity extends Activity {


    Button butttonTurnON;
    String descButtonON;
    String descButtonOFF;

    private  static BluetoothSPP bt;

    private TextView textViewConnect= null;

    private Switch switchBluetooth=null;



   private TextView textView;

   private EditText editText;


    public static BluetoothSPP getInstanciaBluetoothSPP(){
        return BlinkLedActivity.bt;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blink_led_main);




        Spinner spinnerDelay = findViewById(R.id.spinnerDelay);
//create a list of items for the spinner.
        String[] itemsDelay = new String[]{"","50", "2000", "4000"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterDelay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDelay);
//set the spinners adapter to the previously created one.
        spinnerDelay.setAdapter(adapterDelay);





        Spinner spinnerGate = findViewById(R.id.spinnerGate);
//create a list of items for the spinner.
        String[] itemsGate = new String[]{"","L02", "L03", "L04", "L05","L06", "L07", "L08","L09", "L10","L11", "L12","L13"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterGate = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsGate);
//set the spinners adapter to the previously created one.
        spinnerGate.setAdapter(adapterGate);

        spinnerGate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                String select = ((String) parent.getItemAtPosition(position));

                byte[] bufGate= "M".getBytes(); ;

                if(select.equals("L02")){

                    bufGate = "A".getBytes();


                }else if (select.equals("L03")){

                    bufGate = "B".getBytes();

                } else if(select.equals("L04")){
                    bufGate = "C".getBytes();
                }else if(select.equals("L05")) {
                    bufGate = "D".getBytes();
                }   else if(select.equals("L06")) {
                    bufGate = "E".getBytes();
                }else if(select.equals("L07")) {
                    bufGate = "F".getBytes();
                }else if(select.equals("L08")) {
                    bufGate = "G".getBytes();
                } else if(select.equals("L09")) {
                    bufGate = "H".getBytes();
                }else if(select.equals("L10")) {
                    bufGate = "I".getBytes();
                }else if(select.equals("L11")) {
                    bufGate = "J".getBytes();
                }else if(select.equals("L12")) {
                    bufGate = "L".getBytes();
                }else if(select.equals("L13")) {
                    bufGate = "M".getBytes();
                }





                // Physicaloid mPhysicaloid = new Physicaloid(TurnONActivity.this);
              //  if (MainActivity.mPhysicaloid.open()) {


                    Toast.makeText(BlinkLedActivity.this, "Open", Toast.LENGTH_SHORT).show();

                    MainActivity.mPhysicaloid.write(bufGate, bufGate.length);
                    //MainActivity.mPhysicaloid.close();
               // }else{

                 //   Toast.makeText(BlinkLedActivity.this, "Close", Toast.LENGTH_SHORT).show();

                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinnerDelay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                String select = ((String) parent.getItemAtPosition(position));

                byte[] bufDelay= "N".getBytes(); ;

                if(select.equals("50")){

                    bufDelay = "N".getBytes();


                }else if (select.equals("2000")){

                    bufDelay = "O".getBytes();

                } else if(select.equals("4000")){
                    bufDelay = "P".getBytes();

                }





                // Physicaloid mPhysicaloid = new Physicaloid(TurnONActivity.this);
               // if (MainActivity.mPhysicaloid.open()) {


                    Toast.makeText(BlinkLedActivity.this, "Open", Toast.LENGTH_SHORT).show();

                    MainActivity.mPhysicaloid.write(bufDelay, bufDelay.length);


                   /// MainActivity.mPhysicaloid.close();
                //}else{
                   // MainActivity.mPhysicaloid.

                  //  Toast.makeText(BlinkLedActivity.this, "Close", Toast.LENGTH_SHORT).show();


                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        bt = new BluetoothSPP(BlinkLedActivity.this);


        switchBluetooth = (Switch) findViewById(R.id.switchbluetooth);



      //  textViewConnect = (TextView) findViewById(R.id.textViewConnect);

    /*  bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }*/


     //   this.butttonTurnON= (Button) this.findViewById(R.id.btnTurnONLed);

        BlinkLedActivity.getInstanciaBluetoothSPP().setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {


                MessageVO.getInstancia().setMessage(message);


                Toast.makeText(BlinkLedActivity.this, "Bluetooth:"+message, Toast.LENGTH_SHORT).show();


                byte[] buf;
                if (message.equals("L")) {

                    buf = "L".getBytes();



                    //   ligar = false;
                } else {
                    buf = "D".getBytes();
                    // ligar = true;


                }


               // if (MainActivity.mPhysicaloid==null){
                 //   MainActivity.mPhysicaloid = new Physicaloid(BlinkLedActivity.this);
               // }


               // Physicaloid mPhysicaloid = new Physicaloid(TurnONActivity.this);
                if (MainActivity.mPhysicaloid.open()) {


                    Toast.makeText(BlinkLedActivity.this, "Open", Toast.LENGTH_SHORT).show();

                    MainActivity.mPhysicaloid.write(buf, buf.length);
                  //  MainActivity.mPhysicaloid.close();


                    //   CodeArduinoActivity.this.finish();


                }else{
                    Toast.makeText(BlinkLedActivity.this, "Not Open", Toast.LENGTH_SHORT).show();

                }







            }
        });




    }

    public void onStart() {
        super.onStart();

        try {

            // if (MainActivity.mPhysicaloid == null) {
            // MainActivity.mPhysicaloid = new Physicaloid(BlinkLedActivity.this);
            //}


            // Physicaloid mPhysicaloid = new Physicaloid(TurnONActivity.this);
            if (MainActivity.mPhysicaloid.open()) {

                Toast.makeText(getApplicationContext()
                        , "Open..."
                        , Toast.LENGTH_SHORT).show();




            }else{

                Toast.makeText(getApplicationContext()
                        , "Close..."
                        , Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e){
            e.printStackTrace();
        }


      //   MyTask task = new MyTask(MessageVO.getInstancia());
       // task.execute("Hello world!");


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

                bt = new BluetoothSPP(BlinkLedActivity.this);

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

            //descButtonON = getResources().getString(R.string.turnOnled);
           // descButtonOFF = getResources().getString(R.string.turnOffled);

          //  BlinkLedActivity.this.butttonTurnON.setText("Parametros");







       /* this.butttonTurnON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] bufGate;

                byte[] bufDelay;



                bufGate = "13;".getBytes();

                bufDelay = "2000;".getBytes();









                if (BlinkLedActivity.this.mPhysicaloid==null){
                    BlinkLedActivity.this.mPhysicaloid = new Physicaloid(BlinkLedActivity.this);
                }

                if (BlinkLedActivity.this.mPhysicaloid.open()) {

                    bufGate =BlinkLedActivity.this.editText.getText().toString().getBytes();

                   // bufGate = "L".getBytes();

                    //  bufGate = BlinkLedActivity.this.editText.toString().getBytes();

                    BlinkLedActivity.this.mPhysicaloid.write(bufGate, bufGate.length);



                    //  bufGate = "\0".getBytes();

                    //BlinkLedActivity.this.mPhysicaloid.write(bufGate, bufGate.length);


                    byte[] buf = new byte[256];
                    BlinkLedActivity.this.mPhysicaloid.read(buf, buf.length);
                    String str = new String(buf);

                    BlinkLedActivity.this.mPhysicaloid.close();

                    Toast.makeText(BlinkLedActivity.this, "Send Gate 1"+ str, Toast.LENGTH_SHORT).show();

                    Log.i("Teste"," Get ="+str);

                    BlinkLedActivity.this.textView.setText(str);

                }





                   // BlinkLedActivity.this.mPhysicaloid.write(bufDelay, bufDelay.length);

                  //  BlinkLedActivity.this.mPhysicaloid.close();




                    //   CodeArduinoActivity.this.finish();




                if (BlinkLedActivity.this.mPhysicaloid.open()) {

                   // BlinkLedActivity.this.mPhysicaloid.write(bufGate, bufGate.length);
                    BlinkLedActivity.this.mPhysicaloid.write(bufDelay, bufDelay.length);

                    BlinkLedActivity.this.mPhysicaloid.close();

                    Toast.makeText(BlinkLedActivity.this, "Delay", Toast.LENGTH_SHORT).show();


                    //   CodeArduinoActivity.this.finish();


                }

            }
        });*/

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









                // ClickActivity.this.imageView.setBackgroundColor(Color.BLUE);




            return "OK";
        }

        @Override
        protected void onPostExecute(String result){
            //this._callback.Executed(result);
        }


    }




}
