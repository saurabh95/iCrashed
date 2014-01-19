package com.example.contextlist;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class speedit extends Service implements SensorEventListener{
	public SensorManager sensormgr;
	public Sensor mAccelerometer;
	private boolean mInitialised;
	private float mLastX, mLastY, mLastZ;
	double threshold;
	String num1,num2,num3;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		long curTime = System.currentTimeMillis();
		long lastUpdate = 0;
		if ((curTime - lastUpdate) > 1) {
		      long diffTime = (curTime - lastUpdate);
		      lastUpdate = curTime;
		//TextView tvx = (TextView) findViewById(R.id.x);
		//TextView tvy = (TextView) findViewById(R.id.y);
		//TextView tvz = (TextView) findViewById(R.id.z);
		//tvx.setKeyListener(null);
	//	tvy.setKeyListener(null);
		//tvz.setKeyListener(null);
		// TODO Auto-generated method stub
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if(!mInitialised){
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			//tvx.setText("0.0");
			//tvy.setText("0.0");
			//tvz.setText("0.0");
			mInitialised = true;
		}
		else{
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
			mLastX = x;
			mLastY = y;
			mLastZ = z;			
			double speedx = deltaX/diffTime *1000;
			double speedy = deltaY/diffTime *1000;
			double speedz = deltaZ/diffTime *1000;
			double net_speed = Math.sqrt((speedx*speedx+speedy*speedy+speedz*speedz));
			double lat,lng;
			
			if(net_speed>threshold)
			{
				Log.d("hog","sensor");
				GPSTracking gps = new GPSTracking(this);
				@SuppressWarnings("unused")
				boolean a=gps.canGetLocation();
				try{
				Location l ;
				l = gps.getLocation();
				lat = l.getLatitude();
				lng = l.getLongitude();
				//String phoneNumber = ((EditText) findViewById(R.id.editView1)).getText().toString();
				Log.d("lat",Double.toString(lat));
				//String phoneNumber="08790529404";
				String lati=Double.toString(lat);
				String longi=Double.toString(lng);
				String messagetext="Help! I crashed at latitude: "+lati+" longitude: "+longi;
				messagetext=" "+messagetext+"\nhttp://maps.google.com/maps?z=12&t=m&q=loc:"+lati+"+"+longi;
				try {
					Log.d("sent","yes");
					
					SmsManager.getDefault().sendTextMessage(num1, null, messagetext, null, null);
				}
				catch (Exception e) {
					Log.d("sent","no");
				}
				long t1 = System.currentTimeMillis();
					long t2 = System.currentTimeMillis();
					while((t2-t1)<=1000){
						t2 =System.currentTimeMillis();
					}
					try{
					SmsManager.getDefault().sendTextMessage(num2, null, messagetext, null, null);
					}
					catch (Exception e) {
						Log.d("sent","no");
					}
					t1 =System.currentTimeMillis();
					while((t1-t2)<=1000)
					{
						t1 =System.currentTimeMillis();
					}
					try{
					SmsManager.getDefault().sendTextMessage(num3, null, messagetext, null, null);
					}
					catch (Exception e) {
						Log.d("sent","no");
					}
					Toast.makeText(getApplicationContext(), "Messages Sent", Toast.LENGTH_LONG).show();
					
					
				 
				//Log.d(Float.toString((float) lng), "Longitude");
				}catch(Exception e){
					Log.d("sent","nolocation");
					e.printStackTrace();
				}

				onDestroy();
			}
			//tvx.setText(Double.toString(net_speed));
			//tvy.setText(Double.toString(speedy));
			//tvz.setText(Double.toString(speedz));
			
		}
		
		// TODO Auto-generated method stub
		
	}
	}	
	@Override
	public void onCreate() {
	Log.d("shit","man");
		sensormgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = sensormgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensormgr.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mInitialised = false;
		SharedPreferences sel_num = getSharedPreferences("numbers",0);
		num1=sel_num.getString("no1", "");
		num2=sel_num.getString("no2", "");
		num3=sel_num.getString("no3", "");
		
	}
	@Override
	public void onStart(Intent intent, int startId) {
		
	    Bundle extras = intent.getExtras();
	    threshold=(Double) extras.get("threshold");
	    Log.d("shit",Double.toString(threshold));
	    //threshold=1E-9;
	}
	@Override
	public void onDestroy() {
		threshold=42;
		Log.d("band","ho gayi");
	}
	
}

