package com.example.contextlist;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracking extends Service implements LocationListener{

	private final Context mContext;
	public boolean has_location=false;
	boolean isGPSEnabled=false;
	boolean isNetworkEnabled=false;
	boolean canGetLocation=false;
	Location location=null;
	private static final long MIN_DISTANCE_FOR_UPDATE=0;
	//private static final long MIN_TIME_BW_UPDATE=0;
	protected LocationManager locationManager;
	public GPSTracking(Context context){
		this.mContext=context;
		getLocation();
	}
	double longitude,latitude;
	
	public boolean canGetLocation(){
		return this.canGetLocation;
	}
	
	public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
      
        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");
  
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
	
	public Location getLocation(){
		try{
			locationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);
						
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!isNetworkEnabled){
			}
			else{
				this.canGetLocation=true;
				//First try to get location from network provider
				if(isNetworkEnabled){
					locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            10,
                            MIN_DISTANCE_FOR_UPDATE, this);
					Log.d("Network", "Network");
					if(locationManager!=null){
						location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(location==null){
						Log.d("Location", "NULL");
						}
					}
					if(location!=null){
						latitude=location.getLatitude();
						longitude=location.getLongitude();
					}
				}
				
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return location;
	}
	
	 public void stopUsingGPS(){
	        if(locationManager != null){
	            locationManager.removeUpdates(GPSTracking.this);
	        }       
	    }
	
	 public double getLatitude(){
	        if(location != null){
	            latitude = location.getLatitude();
	        }else{
	        	Log.d("Location NULL","Location NULL");
	        }
	         
	        // return latitude
	        return latitude;
	    }
	 
	 public double getLongitude(){
	        if(location != null){
	            longitude = location.getLongitude();
	        }
	         
	        // return longitude
	        return longitude;
	    }
	
	 @Override
	    public void onLocationChanged(Location location) {
		 
	    }
	 
	    @Override
	    public void onProviderDisabled(String provider) {
	    	
	    }
	 
	    @Override
	    public void onProviderEnabled(String provider) {
	    	
	    }
	 
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	
	    }
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
