package com.touchmenotapps.graph3r.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Graph3rSplashActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setContentView(R.layout.layout_splash);
        
        /** Run thread to change screen once the display time is over */
		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(new Intent(Graph3rSplashActivity.this, Graph3rMainActivity.class));
				finish();  
            }
		}, getResources().getInteger(R.integer.splash_timeout)); //Set Time
    }
}
