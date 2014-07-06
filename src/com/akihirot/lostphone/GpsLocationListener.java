package com.akihirot.lostphone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GpsLocationListener extends Service implements LocationListener {

	LocationManager mLocationManager;
	Criteria criteria;
	private static Context context;
	String provider, latitude, longitude;
	
	public String getProvider() {
		return provider;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		latitude = "" + location.getLatitude();
		longitude = "" + location.getLongitude();
		mLocationManager.removeUpdates(this);
		sendBroadcast(getString());
		stopSelf();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	public String getString() {
		String url = "https://maps.google.co.jp/maps?ll=" + latitude + "," + longitude;
		return url;
	}
	
	public void checkGPS() {
		 if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			 mLocationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
		 }
	}
	
	public void sendBroadcast(String stLocate) {
		// Send Broadcast
			Intent broadcastIntent = new Intent();
			broadcastIntent.putExtra("locate", stLocate);
			broadcastIntent.setAction(MainActivity.LOCATE_RECEIVED);
			context.sendBroadcast(broadcastIntent);
	}
	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onCreate() {
		context = getBaseContext();

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

       // create Criteria Object
       criteria = new Criteria();

       // Accuracy( low precision )
       criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        
       // PowerRequirement( low power )
       criteria.setPowerRequirement(Criteria.POWER_LOW);
        
       // get Location Provider
       provider = mLocationManager.getBestProvider(criteria, true);

       // set LocationListener
       mLocationManager.requestLocationUpdates(provider, 100, 0, this);
       
       latitude = null;
       longitude = null;
       checkGPS();
	}
	
}
