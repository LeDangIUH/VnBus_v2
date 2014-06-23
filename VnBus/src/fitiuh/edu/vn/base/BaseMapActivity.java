package fitiuh.edu.vn.base;

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
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import fitiuh.edu.vn.common.Common;
import fitiuh.edu.vn.model.BusFilter;
import fitiuh.edu.vn.model.BusGPSRealtime;
import fitiuh.edu.vn.model.SwitchMarker;
import fitiuh.edu.vn.vnbus.FN0001;
import fitiuh.edu.vn.vnbus.FN0003;
import fitiuh.edu.vn.vnbus.R;
import android.R.color;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public abstract class BaseMapActivity extends FragmentActivity {
	
	private GoogleMap mMap;
	ArrayList<LatLng> markerPoints;
	
	private boolean viewGroupIsVisible = true;  
    private View mViewGroup;
    private View mViewGroup2;
    private View mViewLeft;
    private View mViewRight;
	
	FN0003 fn0003 = new FN0003();
	//FN0001 fn0001 = new FN0001();
	Common common = new Common();
	Dialog dialog;
	MyCustomAdapter dataAdapter = null;
	SwitchMarker switchMarker = new SwitchMarker();
	
	protected int getLayoutId() {
        return R.layout.map;
    }
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		//setContentView(R.layout.vnb_001);
		setContentView(getLayoutId());
		mViewGroup = findViewById(R.id.layoutDirection);
		mViewGroup.setVisibility(View.GONE);
		
		mViewGroup2 = findViewById(R.id.layoutInfo);
		mViewGroup2.setVisibility(View.GONE);
		
		mViewLeft = findViewById(R.id.loutLeft);
		mViewLeft.setVisibility(View.VISIBLE);
		
		mViewRight = findViewById(R.id.loutRight);
		mViewRight.setVisibility(View.VISIBLE);
		
		setUpMapIfNeeded();
		
		//event for image filter click
		ImageButton imageButtonFilter = (ImageButton) findViewById(R.id.btnFilter);
		imageButtonFilter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				displayListViewdialog();
			}
		});
		
		// event for image refresh
		ImageButton imageButtonRefresh = (ImageButton) findViewById(R.id.btnRestart);
		imageButtonRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				addMarker();
			}			
		});
		
		//event for get gps
		ImageButton imageButtonGps = (ImageButton) findViewById(R.id.btnGps);
		imageButtonGps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				// TODO Auto-generated method stub
				double laitude = fn0003.getLatitude(getApplicationContext());
				double longitude = fn0003.getLongitude(getApplicationContext());
				
				//set latitude and longitude to common
				common.setLatitudeGPS(laitude);
				common.setLonitudeGPS(longitude);
				
				fn0003.addGpsLocationMarker(getMap(), longitude, laitude, getApplicationContext());

			}
		});
		
		// hide mViewGroup layout
		getMap().setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				mViewGroup.setVisibility(View.GONE);
				mViewGroup2.setVisibility(View.GONE);
				mViewLeft.setVisibility(View.VISIBLE);
				mViewRight.setVisibility(View.VISIBLE);
				
				addMarker();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpMapIfNeeded();
	}
	
	private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap != null) {
            startDemo();
        }
    }
	
	protected abstract void startDemo();

    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }
    
    /**
     * Map dicrection
     */
    
    public void getDirection(Context context, double LatCheck1, double LongCheck1, double LatCheck2, double LongCheck2 ) {
		
		double latitude = LatCheck1;
		double longitude = LongCheck1;
		//double la = 10.76988383;
		//double lo = 106.6969872;
		
		markerPoints = new ArrayList<LatLng>();
		markerPoints.add(new LatLng(latitude, longitude));
		markerPoints.add(new LatLng(LatCheck2, LongCheck2));
		//markerPoints.add(new LatLng(la, lo));
		
		LatLng origin = markerPoints.get(0);
		LatLng dest = markerPoints.get(1);
		
		// Getting URL to the Google Directions API
		String url =getDirectionsUrl(origin, dest);				
		
		DownloadTask downloadTask = new DownloadTask();
		
		// Start downloading json data from Google Directions API
		downloadTask.execute(url);		
		
	}
    
    public String getDirectionsUrl(LatLng origin,LatLng dest){
		
		// Origin of route
		String str_origin = "origin="+origin.latitude+","+origin.longitude;
		
		// Destination of route
		String str_dest = "destination="+dest.latitude+","+dest.longitude;		
		
					
		// Sensor enabled
		String sensor = "sensor=false";			
					
		// Building the parameters to the web service
		String parameters = str_origin+"&"+str_dest+"&"+sensor;
					
		// Output format
		String output = "json";
		
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		
		
		return url;
	}
	
	/** A method to download json data from url */
    public String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url 
                urlConnection.connect();

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }
                
                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }
        return data;
     }

	// Fetches data from url passed
	public class DownloadTask extends AsyncTask<String, Void, String>{			
				
		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
				
			// For storing data from web service
			String data = "";
					
			try{
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			}catch(Exception e){
				Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);			
			
			ParserTask parserTask = new ParserTask();
			
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
				
		}		
	}
	
	/** A class to parse the Google Places in JSON format */
    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
    	
    	// Parsing the data in non-ui thread    	
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			
			JSONObject jObject;	
			List<List<HashMap<String, String>>> routes = null;			           
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	DirectionsJSONParser parser = new DirectionsJSONParser();
            	
            	// Starts parsing data
            	routes = parser.parse(jObject);    
            }catch(Exception e){
            	e.printStackTrace();
            }
            return routes;
		}
		
		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			
			//Intent intent = new Intent(getApplicationContext(), Common.class);
			
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();
			String distance = "";
			String duration = "";	
			
			if(result.size()<1){
				//Toast.makeText(this, "No Points", Toast.LENGTH_SHORT).show();
				return;
			}
	
			// Traversing through all the routes
			for(int i=0;i<result.size();i++){
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();
				
				
				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);
				
				// Fetching all the points in i-th route
				for(int j=0;j<path.size();j++){
					HashMap<String,String> point = path.get(j);	
					
					if(j==0){	// Get distance from the list
						distance = (String)point.get("distance");						
						continue;
					}else if(j==1){ // Get duration from the list
						duration = (String)point.get("duration");
						continue;
					}
					
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);	
					
					points.add(position);						
				}
				
				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(5);
				lineOptions.color(Color.RED);
				
			}
			
			
			//Toast.makeText(getApplicationContext(), distance, Toast.LENGTH_LONG).show();
			//intent.putExtra("distance", distance);
			//startActivity(intent);
			
			// Drawing polyline in the Google Map for the i-th route
			mMap.addPolyline(lineOptions);
			
			BaseMapActivity.this.setvalueAAA(distance, duration);			
		}	
    }
    
    public void setvalueAAA(String a, String b){
    	common.setDirection(a);
    	common.setTimeDirection(b);
    }
     
    //display dialog choose bus number display
    public void displayListViewdialog() {

		final Dialog dialog = new Dialog(common.getContext());
        dialog.setContentView(R.layout.fn0001_filter);
        dialog.setCancelable(true);
        dialog.setTitle("Hiển thị buýt");
        
        List<BusFilter> busFilters = common.getBusFilters();
			
		// Array list of countries
		ArrayList<BusFilter> stateList = new ArrayList<BusFilter>();

		BusFilter busFilter = null;
		for (BusFilter filter : busFilters) {
			busFilter = new BusFilter();
			busFilter.setCode(filter.getCode());
			busFilter.setSelected(false);
			
			stateList.add(busFilter);
		}

		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(common.getContext(), R.layout.fn0001_filter_checkbox, stateList);
		ListView listView = (ListView)dialog.findViewById(R.id.lvCheckbox);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				BusFilter state = (BusFilter) parent.getItemAtPosition(position);
				/*Toast.makeText(getApplicationContext(),
						"Clicked on : " + state.getCode(), Toast.LENGTH_LONG)
						.show()*/;
			}
		});
		
		Button button = (Button) dialog.findViewById(R.id.findSelected);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				List<BusFilter> busFilters = new ArrayList<BusFilter>();
				BusFilter filter = null;
				
				//StringBuffer responseText = new StringBuffer();
				
				ArrayList<BusFilter> stateList = dataAdapter.stateList;

				for (int i = 0; i < stateList.size(); i++) {
					BusFilter state = stateList.get(i);

					if (state.isSelected()) {
						
						filter = new BusFilter();
						filter.setCode(state.getCode());
						filter.setSelected(true);
						//responseText.append("\n" + state.getCode());
						
						busFilters.add(filter);
					}
				}

				/*Toast.makeText(getApplicationContext(), responseText,
						Toast.LENGTH_LONG).show();*/
				common.setBusFilters(busFilters);
				dialog.dismiss();
				getMap().clear();
				
				List<BusFilter> busFilt = common.getBusFilters();
				List<BusGPSRealtime> busGPSRealtimes = common.getBusGPSRealtimes();
				
				for (BusGPSRealtime gpsRealtime : busGPSRealtimes) {
					for (BusFilter busF : busFilters) {
						if (busF.getCode().equals(String.valueOf(switchMarker.choosenBusIDInfo(gpsRealtime.getBusID())))) {
							getMap().addMarker(new MarkerOptions()
							.position(new LatLng(getLatitude(gpsRealtime.getLocation()), getLongitude(gpsRealtime.getLocation())))
							.title(gpsRealtime.getBusID())
							.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(gpsRealtime.getBusID()))));
						}
					}			
				}
			}
		});
		
		dialog.show();
	}
    
  //get longitude gps
  	public double getLongitude(String location) {

  		double longitudeBus = 0.0;
  		String[] aLocation = location.trim().split(",");
  		longitudeBus = Double.parseDouble(aLocation[1]);

  		return longitudeBus;
  	}

  	//get latitude gps
  	public double getLatitude(String location) {

  		double latitudeBus = 0.0;
  		String[] aLocation = location.trim().split(",");
  		latitudeBus = Double.parseDouble(aLocation[0]);

  		return latitudeBus;
  	}
	
	private class MyCustomAdapter extends ArrayAdapter<BusFilter> {

		private ArrayList<BusFilter> stateList;

		public MyCustomAdapter(Context context, int textViewResourceId,

		ArrayList<BusFilter> stateList) {
			super(context, textViewResourceId, stateList);
			this.stateList = new ArrayList<BusFilter>();
			this.stateList.addAll(stateList);
		}

		private class ViewHolder {
			TextView code;
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = vi.inflate(R.layout.fn0001_filter_checkbox, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView.findViewById(R.id.code);
				holder.name = (CheckBox) convertView
						.findViewById(R.id.cbChoose);

				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						BusFilter _state = (BusFilter) cb.getTag();

						/*Toast.makeText(
								common.getContext(),
								"Checkbox: " + cb.getText() + " -> "
										+ cb.isChecked(), Toast.LENGTH_LONG)
								.show();*/

						_state.setSelected(cb.isChecked());
					}
				});

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			BusFilter state = stateList.get(position);

			holder.code.setText(state.getCode());
			holder.name.setChecked(state.isSelected());

			holder.name.setTag(state);

			return convertView;
		}

	}
	
	public void addMarker() {
		
		getMap().clear();
		
		List<BusGPSRealtime> busGPSRealtimes = common.getBusGPSRealtimes();
		
		for (BusGPSRealtime gpsRealtime : busGPSRealtimes) {
					getMap().addMarker(new MarkerOptions()
					.position(new LatLng(getLatitude(gpsRealtime.getLocation()), getLongitude(gpsRealtime.getLocation())))
					.title(gpsRealtime.getBusID())
					.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(gpsRealtime.getBusID()))));
		}
	}
	    
}
