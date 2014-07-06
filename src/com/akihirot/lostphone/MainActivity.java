package com.akihirot.lostphone;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.*;

public class MainActivity extends Activity {
	
	public static final String ACTION_RECEIVED = "com.akihirot.lostphone.ACTION_RECEIVED";
	public static final String LOCATE_RECEIVED = "com.akihirot.lostphone.LOCATE_RECEIVED";
	
	MyBroadcastReceiver receiver = null;
	LocateBroadcastReceiver locateReceiver = null;
	IntentFilter intentFilter;
//	TextView logTextView;
	String address = "";
	String body = "";
	GpsLocationListener GLL;
	TextView SetPassButton;
	TextView HowToButton;
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        logTextView = (TextView) findViewById(R.id.logTextView);
        
		SetPassButton = (TextView)findViewById(R.id.SetPassButton_id);
		SetPassButton.setOnClickListener(new OnClickListener(){
	     @Override
	       public void onClick(View v) {
	           Intent setPassIntent = new Intent(MainActivity.this, SetPassActivity.class);

	           int requestCode = 101;
	           startActivityForResult(setPassIntent, requestCode);
	      }
	    });
		
		HowToButton = (TextView)findViewById(R.id.HowToButton_id);
		HowToButton.setOnClickListener(new OnClickListener(){
		     @Override
		       public void onClick(View v) {
		    	 Intent howToIntent = new Intent(MainActivity.this, HowToActivity.class);
		    	 startActivity(howToIntent);
		      }
		    });
    }
    
    public void onActivityResult( int requestCode, int resultCode, Intent argintent ) {
     if( requestCode == 101 ){
     	if( resultCode == Activity.RESULT_OK ){
      	String stpass = argintent.getStringExtra("pass");
      	System.out.println(stpass);
      	savePass(stpass);
      }
     }
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	receiver = new MyBroadcastReceiver();
    	intentFilter = new IntentFilter();
    	intentFilter.addAction(ACTION_RECEIVED);
    	registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // get view size
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_view_id);
        
        SetPassButton.setHeight(rl.getHeight()/2);
        HowToButton.setHeight(rl.getHeight()/2);
    }
    
    public class MyBroadcastReceiver extends BroadcastReceiver {
    	
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		address = intent.getStringExtra("address");
    		body = intent.getStringExtra("body");
    		
//    		logTextView.setText(address + "\n" + intent.getStringExtra("body") + "&"+ getPass());
    		
    		String[] spBody = body.split(" ", 2);
    		spBody[0] = toMD5.encodePassdigiest(spBody[0]);
    		if(!spBody[0].equals(getPass())){
    			return;
    		}
    		if(spBody[1].equals("@sound")) {
    			setRing();
    		}else if(spBody[1].equals("@locate")) {
    			setLocateListener();
    		}
    		
    		return;
    	}
    }
    
    public class LocateBroadcastReceiver extends BroadcastReceiver {
    	
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		String locate = intent.getStringExtra("locate");
    		sendLocate(address, locate);
    		
    		unregisterReceiver(LocateBroadcastReceiver.this);
    		return;
    	}
    }
    
    public void setRing() {
    	AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    	am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        am.setStreamVolume(AudioManager.STREAM_RING, am.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
    }

    public static void sendLocate(String address, String locate) {
    	SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(address, null, locate, null, null);
    }
    
    public void setLocateListener() {
    	startService(new Intent(MainActivity.this, GpsLocationListener.class));
    	locateReceiver = new LocateBroadcastReceiver();
    	IntentFilter locatefilter = new IntentFilter(LOCATE_RECEIVED);
        registerReceiver(locateReceiver, locatefilter);
    	return;
    }
    

	// save password for Preference
	 public void savePass(String pass) {
		 SharedPreferences pref = getPreferences(MODE_PRIVATE);
		 Editor e = pref.edit();
		 e.putString("PassWord", pass);
		 e.commit();
	 }
	 
    // get password from Preference
 	 private String getPass() {
 		SharedPreferences pref = getPreferences(MODE_PRIVATE);
 		return pref.getString("PassWord",null);
 	 }
    
}