package com.minimakerlabrobo.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minimakerlabrobo.R;
import com.minimakerlabrobo.serial.TurnONActivity;
import com.minimakerlabrobo.valueObject.MessageVO;
import com.physicaloid.lib.Physicaloid;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class MainActivity extends Activity {

    private  static BluetoothSPP bt;

    public static String LED="LED";

    public static String BEEP="BEEP";

    MenuItem menuActivity_led;
    MenuItem menuActivity_beep;
    MenuItem menuActivity_click;

    Button conectButton = null;

    Button btnSend = null;
    TextView textViewConnect= null;

    boolean meunVisible=false;






    public static BluetoothSPP getInstanciaBluetoothSPP(){
           return MainActivity.bt;
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bluetooth);

        conectButton = (Button) findViewById(R.id.btnConnect);

        btnSend = (Button) findViewById(R.id.btnTeste);

        textViewConnect = (TextView) findViewById(R.id.textViewConnect);

        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        MainActivity.getInstanciaBluetoothSPP().setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {


                MessageVO.getInstancia().setMessage(message);


                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();


                byte[] buf;
                if (message.equals("L")) {

                    buf = "L".getBytes();

                 //   ligar = false;
                } else {
                    buf = "D".getBytes();
                   // ligar = true;


                }


                Physicaloid mPhysicaloid = new Physicaloid(MainActivity.this);
                if (mPhysicaloid.open()) {

                    mPhysicaloid.write(buf, buf.length);
                    mPhysicaloid.close();


                    //   CodeArduinoActivity.this.finish();


                }







            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();

                MainActivity.this.textViewConnect.setText(getResources().getString(R.string.btnConnect)+ " "+ name);

                MainActivity.this.meunVisible =true;

                MessageVO.getInstancia().setMessage("teste");


                invalidateOptionsMenu();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();

                MainActivity.this.meunVisible =false;

                MainActivity.this.textViewConnect.setText(getResources().getString(R.string.btnDisconnect));
                invalidateOptionsMenu();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();

                MainActivity.this.meunVisible =false;

                MainActivity.this.textViewConnect.setText(getResources().getString(R.string.btnDisconnect));
                invalidateOptionsMenu();



            }
        });

        Button btnConnect = (Button)findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

                MessageVO.getInstancia().setMessage("teste");

                MyTask task = new MyTask(MessageVO.getInstancia());
             //   MainActivity.
                task.execute("Hello world!");
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){



                 Toast.makeText(getApplicationContext()
                          , "teste"
                        , Toast.LENGTH_SHORT).show();


                bt.send("L", true);

            }
        });


    }



    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
    }

    public void setup() {
       // Button btnSend = (Button)findViewById(R.id.btnSend);
      //  btnSend.setOnClickListener(new View.OnClickListener(){
        //    public void onClick(View v){
             //   bt.send("L", true);
          //  }
        //});


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
       // menuActivity_led.setEnabled(meunVisible);
        //menuActivity_beep.setEnabled(meunVisible);
        //menuActivity_click.setEnabled(meunVisible);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                Toast.makeText(getApplicationContext()
                        , "Connect."
                        , Toast.LENGTH_SHORT).show();
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
/*
        menuActivity_led = (MenuItem) menu.findItem(R.id.activity_led);
        menuActivity_beep = (MenuItem) menu.findItem(R.id.activity_beep);
        menuActivity_click = (MenuItem) menu.findItem(R.id.activity_click);*/
        return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       int id = item.getItemId();

/*
        if (id == R.id.activity_led) {
            Intent intent = new Intent(this, TurnONActivity.class);
            intent.putExtra("tipo",LED );
            startActivity(intent);
            return true;
        }

        if (id == R.id.activity_beep) {
            Intent intent = new Intent(this, TurnONActivity.class);
            intent.putExtra("tipo",BEEP );
            startActivity(intent);
            return true;
        }

        if (id == R.id.activity_click) {
            Intent intent = new Intent(this, ClickActivity.class);
            startActivity(intent);
            return true;
        }*/
        return super.onOptionsItemSelected(item);


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

              /*  Toast.makeText(MainActivity.this.getApplicationContext()
                        , messageVO.getMessage()
                       , Toast.LENGTH_SHORT).show();*/

            }
            return "OK";
        }

        @Override
        protected void onPostExecute(String result){
            //this._callback.Executed(result);
        }


    }


}