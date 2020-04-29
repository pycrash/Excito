package com.t9.excito.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.orhanobut.hawk.Hawk;
import com.t9.excito.Address.LocationActivity;
import com.t9.excito.Home.HomeActivity;
import com.t9.excito.Login.LoginActivity;
import com.t9.excito.R;
import com.t9.excito.WelcomeScreen.WelcomeScreen;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    String TAG="SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "onCreate: SplashScreen started");



        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Hawk.init(SplashScreenActivity.this).build();
                if(Hawk.contains("phoneNumber")){
                    Intent intent=new Intent(SplashScreenActivity.this, HomeActivity.class);
                    String phone=Hawk.get("phoneNumber");
                    Log.d(TAG, "onCreate: SplashScreen to login activity intent started");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phoneNumber",phone);
                    startActivity(intent);

                }
                else {

                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    Log.d(TAG, "onCreate: SplashScreen to login activity intent started");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        },800);
    }
}
