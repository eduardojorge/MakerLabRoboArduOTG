package com.minimakerlabrobo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.physicaloidtest.PhysicaloidTestActivity;

import com.minimakerlabrobo.valueObject.ActivityBoxValue;
import com.physicaloid.lib.Boards;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.fpga.PhysicaloidFpga;
import com.physicaloid.lib.programmer.avr.UploadErrors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UploadHexActivity extends Activity {

    private boolean its_detail=false;


    private static final String TAG = UploadHexActivity.class.getSimpleName();

    private static final String ASSET_FILE_NAME_UNO = "piscarLae.ino.standard.hex";

    protected String file;







    private TextView textUpload;
    private TextView editTextMsg;
    private ArrayList<Boards> mBoardList;

    private Handler mHandler = new Handler();


    private Boards mSelectedBoard;

    private Button buttonUpload;
    private Button buttonNext;

    private  ActivityBoxValue activityBoxValue=null;

    ProgressBar progressBar;

    private Spinner spinner1;


    public void setBorder(int i){
        mSelectedBoard = mBoardList.get(i);


        SharedPreferences myPrefs = UploadHexActivity.this.getSharedPreferences("myPrefs", MODE_PRIVATE);

        SharedPreferences.Editor e = myPrefs.edit();
        e.putString("placa", i+""); // add or overwrite someValue
        e.commit();

    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener(this));

        SharedPreferences myPrefs = UploadHexActivity.this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String placa = myPrefs.getString("placa","0");

        spinner1.setSelection(new Integer(placa).intValue());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadhexprotoboard);

        addListenerOnSpinnerItemSelection();

        Intent myIntent = getIntent(); // gets the previously created intent

        activityBoxValue= (ActivityBoxValue) ActivityBoxSingleton.getInstancia().getMap().get(myIntent.getStringExtra("file"));
        file = activityBoxValue.getHex();

        editTextMsg = (TextView) findViewById(R.id.editTextMsg);

        textUpload = (TextView) findViewById(R.id.textUpload);


        textUpload.append(activityBoxValue.getLabel());






        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        buttonUpload.setText(getResources().getText(R.string.buttonUpload));


        buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonNext.setText(getResources().getText(R.string.buttonNext));


       // Dialog dialog = new Dialog (UploadHexActivity.this);

        //dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
       // dialog.setContentView (R.layout.propaganda);
        //dialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);

               progressBar = (ProgressBar) findViewById(R.id.progressbar);


        progressBar.setScaleY(3f);


        progressBar.setHorizontalScrollBarEnabled(true);

        progressBar.setMax(100);

       //progressBar.set

       // progressBar.setProgress(10);









       // MainActivity.mPhysicaloid = new Physicaloid(this);

        // Shows last selected board
        mBoardList = new ArrayList<Boards>();
        for(Boards board : Boards.values()) {
            if(board.support > 0) {
                mBoardList.add(board);
            }
        }

        mSelectedBoard = mBoardList.get(6);




        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





               // String assetFileName = ASSET_FILE_NAME_UNO;

                String assetFileName =activityBoxValue.getDirectory()+"/"+ activityBoxValue.getHex();
              //  String assetFileName = Environment.getExternalStorageDirectory().toString() + "/" +
                //        "primeiro_exemplo.ino.standard.hex";


                try {
                    Log.d("teste",file);

                    if (file.equals("ardu.ino.standard.hex")){

                        File f2 = new File(Environment.getExternalStorageDirectory(),  "/roboardu/ardu.ino.standard.hex");

                        FileInputStream fileInputStream = new FileInputStream(f2);
                        MainActivity.getPhysicaloid().upload(mSelectedBoard, fileInputStream, mUploadCallback);



                    }else{
                        MainActivity.getPhysicaloid().upload(mSelectedBoard, getResources().getAssets().open(assetFileName), mUploadCallback);
                    }

                    //File f1 = new File(assetFileName);




                  //  MainActivity.getPhysicaloid().close();


                } catch(RuntimeException e) {
                    Log.e(TAG, e.toString());
                } catch(IOException e) {
                    Log.e(TAG, e.toString());
                }




            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent iCodeArduinoActivity = new Intent(UploadHexActivity.this,CodeArduinoActivity.class);
                iCodeArduinoActivity.putExtra("file",UploadHexActivity.this.activityBoxValue.getLabel());
                startActivity(iCodeArduinoActivity);
                its_detail =true;







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



    Physicaloid.UploadCallBack mUploadCallback = new Physicaloid.UploadCallBack() {


        @Override
        public void onUploading(int value) {

            AppendMsg(editTextMsg, "Upload : " + value + " %\n");

            progressBar.setProgress(value);
        }

        @Override
        public void onPreUpload() {

            AppendMsg(editTextMsg, "Upload : Start\n");
        }

        @Override
        public void onPostUpload(boolean success) {

            if(success) {
                AppendMsg(editTextMsg, "Upload : Successful\n");
            } else {
                AppendMsg(editTextMsg, "Upload fail\n");
            }
        }

        @Override
        public void onCancel() {
            AppendMsg(editTextMsg, "Cancel uploading\n");
        }

        @Override
        public void onError(UploadErrors err) {


            if (err.getCode()==5){
                AppendMsg(editTextMsg, "Error  : " + getResources().getText(R.string.erro_MCU) + "\n");

            }else {
                AppendMsg(editTextMsg, "Error  : " + err.toString() + "\n");
            }
        }
    };
    private void AppendMsg(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;


        mHandler.post(new Runnable() {

            @Override
            public void run() {

                ftv.setText(ftext);

              //  ftv.append("\n--------------------\n");

               // ftv.append(ftext);
            }
        });
    }



}
