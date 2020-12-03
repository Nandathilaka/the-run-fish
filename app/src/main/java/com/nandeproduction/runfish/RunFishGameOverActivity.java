package com.nandeproduction.runfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class RunFishGameOverActivity extends AppCompatActivity {

    private Button playAgain;
    private TextView displayScore;
    private String score;
    private AdView mAdViewBottom;
    private AdView mAdViewTop;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_fish_game_over);

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

        //AdMob Top Add Display Start
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
        //AdMob Top Add Display End


        //AdMob InterstitialAd ads Start
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4566432493079281/9964320082");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //AdMob InterstitialAd ads End
        

        //Initialized Button
        playAgain = (Button) findViewById(R.id.playAgainButton);
        //Initialized Score Text View
        displayScore = (TextView) findViewById(R.id.scoreTextView);
        //Get score from passed out game
        score = getIntent().getExtras().get("score").toString();

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Internet or Wi-Fi ON
                if(checkInternetOn()){
                    //Load Adds when click Play Again button
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }else{
                    Intent mainIntent = new Intent(RunFishGameOverActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }

            }
        });



        mInterstitialAd.setAdListener(new AdListener(){

            //Close Add
            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Intent mainIntent = new Intent(RunFishGameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }

            /*
            //Fail Add
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Intent mainIntent = new Intent(RunFishGameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
            */
        });


        displayScore.setText("Score = " + score);

    }

    //Check Internet Connection Available at this time
    private boolean checkInternetOn(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }else{
            return false;
        }
    }
}