package com.akihirot.lostphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		SmsMessage message = null;
		
		if(bundle == null) {
			return;
		}
		
		String address = new String();
		String body = new String();
		Object[] pdus = (Object[]) bundle.get("pdus");
		
		message = SmsMessage.createFromPdu((byte[])pdus[0]);
		address = message.getOriginatingAddress();
		body = message.getMessageBody();
		
		System.out.println("Received messages");
		
		
		
		// Send Broadcast
		Intent broadcastIntent = new Intent();
		broadcastIntent.putExtra("address", address);
		broadcastIntent.putExtra("body", body);
		broadcastIntent.setAction(MainActivity.ACTION_RECEIVED);
		context.sendBroadcast(broadcastIntent);
	}
	
	

}
