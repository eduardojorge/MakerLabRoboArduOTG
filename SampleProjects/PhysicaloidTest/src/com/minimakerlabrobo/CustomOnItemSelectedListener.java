package com.minimakerlabrobo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private UploadHexActivity uploadHexActivity=null;
    public CustomOnItemSelectedListener(UploadHexActivity uploadHexActivity){

        this.uploadHexActivity = uploadHexActivity;



    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
     //   Toast.makeText(parent.getContext(),
       //         "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
         //       Toast.LENGTH_SHORT).show();
        this.uploadHexActivity.setBorder(pos);


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}