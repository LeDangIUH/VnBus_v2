package fitiuh.edu.vn.vnbus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fitiuh.edu.vn.base.*;
import fitiuh.edu.vn.base.BaseMapActivity.DownloadTask;

public class FN0003{
	
	GPSTracker gpsTracker;
	
	public double getLatitude(Context context) {
		
		gpsTracker = new GPSTracker(context);
		double latitude = gpsTracker.getLatitude();
		
		return latitude;
	}
	
	public double getLongitude(Context context) {
		
		gpsTracker = new GPSTracker(context);
		double longitude = gpsTracker.getLongitude();
		
		return longitude;
	}
	
	//from langitude and longtitude add one marker to google map
	public void addGpsLocationMarker(GoogleMap map,double longitude, double latitude,Context context){
		map.addMarker(new MarkerOptions()
					  .position(new LatLng(getLatitude(context), getLongitude(context)))
					  .title("Position of me")
					  .icon(BitmapDescriptorFactory.fromResource(R.drawable.checkloction)));
	}
	
}
