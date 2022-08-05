package com.minimakerlabrobo;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;


import com.minimakerlabrobo.util.Util;
import com.minimakerlabrobo.valueObject.ActivityBoxValue;

public class SplashScreenActivity extends Activity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    VideoView video_player_view; DisplayMetrics dm; SurfaceView sur_View; MediaController media_Controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {



                Intent intent = new Intent(SplashScreenActivity.this,com.minimakerlabrobo.MainActivity.class);

                startActivity(intent);

                SplashScreenActivity.this.finish();


            }
        }, SPLASH_TIME_OUT);
        setContentView(R.layout.activity_splash_screen);
        getInit();


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

            Log.i("teste", Util.ResourceToUri(this.getBaseContext(), R.raw.minimaker_6).getEncodedPath());
            video_player_view.setVideoURI(Util.ResourceToUri(this.getBaseContext(), R.raw.minimaker_6));

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
