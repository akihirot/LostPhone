package com.akihirot.lostphone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HowToActivity extends Activity {
	Button backButton;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new OnClickListener(){
	     @Override
	       public void onClick(View v) {
	    	 finish();
	      }
	    });
	}
}
