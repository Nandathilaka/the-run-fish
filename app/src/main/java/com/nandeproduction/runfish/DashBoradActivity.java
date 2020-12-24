package com.nandeproduction.runfish;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class DashBoradActivity extends AppCompatActivity {
    private AdView mAdViewBottom;
    private AdView mAdViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_borad);

        /* Removed Admob Adds in Load Page
        //AdMob Bottom Add Display Start
        mAdViewBottom = findViewById(R.id.adViewBottom);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdViewBottom.loadAd(adRequest);
        //AdMob Bottom Add Display End

        //AdMob Bottom Add Display Start
        mAdViewTop = findViewById(R.id.adViewTop);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequestTop = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdViewTop.loadAd(adRequestTop);
        //AdMob Bottom Add Display End
        */



        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                    Intent mainIntent = new Intent(DashBoradActivity.this, MainActivity.class);
                    startActivity(mainIntent);

                }
            }
        };
        thread.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}