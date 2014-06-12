package fitiuh.edu.vn.vnbus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.List;
import fitiuh.edu.vn.radialmenu.*;

import com.google.android.gms.internal.gt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.ui.IconGenerator;

import fitiuh.edu.vn.base.BaseDatabaseActivity;
import fitiuh.edu.vn.base.BaseMapActivity;
import fitiuh.edu.vn.base.BaseServiceActivity;
import fitiuh.edu.vn.common.Common;
import fitiuh.edu.vn.model.BusAllID;
import fitiuh.edu.vn.model.BusCountLocation;
import fitiuh.edu.vn.model.BusGPSRealtime;
import fitiuh.edu.vn.model.BusLngLat;
import fitiuh.edu.vn.model.BusLngLatAddress;
import fitiuh.edu.vn.model.BusLocationGPS;
import fitiuh.edu.vn.model.BusTime;
import fitiuh.edu.vn.model.BusTimeSpace;
import fitiuh.edu.vn.model.BusTimeSpaceIndex;
import fitiuh.edu.vn.model.SwitchMarker;
import fitiuh.edu.vn.radialmenu.RadialMenuWidget;
import fitiuh.edu.vn.radialmenu.RadialMenuWidget.RadialMenuEntry;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import fitiuh.edu.vn.base.*;

public class FN0001 extends BaseMapActivity {
	
	/*SwitchMarker switchMarker = new SwitchMarker();
	List<BusGPSRealtime> busGPSRealtimes = Common.getBusGPSRealtimes();*/
	private static RadialMenuWidget PieMenu;
	private LinearLayout ll;
	
	List<BusCountLocation> busCountLocations = null;
	List<BusLocationGPS> busLocationGPSs = null;
	List<BusTime> busTimes = null;
	List<BusAllID> busAllIDs = null;
	List<BusTimeSpace> busTimeSpaces = null;
	List<BusTimeSpaceIndex> busTimeSpaceIndexs = null;
	List<BusGPSRealtime> busGPSRealtimes = null;
	List<BusLngLat> busLngLats = null;
	
	SwitchMarker switchMarker = new SwitchMarker();
	Common common = new Common();
	DirectionsJSONParser directionsJSONParser = new DirectionsJSONParser();
	BaseDatabaseActivity baseDatabaseActivity;
	FN0003 fn0003 = new FN0003();
	
	private View mViewGroup;
	private View mViewGroup2;
	private TextView txtMyLocation;
	private TextView txtbus;
	private TextView txtTimeSpace;
	private TextView txtBusName;	
	
	@Override
	protected void startDemo() {
		
		/*// run and get google map 
		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.773223266971607, 106.70639541003383), 10)); 	
		
		if (isMyServiceRunning(getApplicationContext())) {
			Log.e("------>", "Service is running");
		} else {
			Log.e("------>", "Service isn't running");
			startService(new Intent(getApplicationContext(),BaseServiceActivity.class));
		}
		
		
		// Start service using AlarmManager
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 2);

		Intent intent = new Intent(this, BaseServiceActivity.class);

		PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int i;
		i = 15;
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				i * 1000, pintent);
		
		addMarker(busGPSRealtimes);*/
		
		/**
		 * 
		 */
		// run and get google map 
		getMap().moveCamera(
				CameraUpdateFactory.newLatLngZoom(new LatLng(
						10.773223266971607, 106.70639541003383), 10));
		// database basic
		BaseDatabaseActivity baseDatabaseActivity = new BaseDatabaseActivity(
				getApplicationContext());
		try {
			baseDatabaseActivity.createDataBase();
		} catch (Exception e) {
			// Log.e("====", e.getMessage());
			throw new Error("Unable to create database");

		}
		try {
			baseDatabaseActivity.openDataBase();

		} catch (Exception e) {
			// TODO: handle exception
		}
		busLocationGPSs = Common.getBusLocationGPS();
		busTimes = Common.getBusTimes();
		busAllIDs = Common.getBusAllID();
		busCountLocations = Common.getBusCountLocations();

		busTimeSpaces = timeSpace();
		busTimeSpaceIndexs = getIndexBusTime(busTimeSpaces);
		// get location realtime auto
		busGPSRealtimes = getLocationRealtime(busTimeSpaceIndexs,
				busLocationGPSs);
		// display loacation realtime into map
		addMarker(busGPSRealtimes);
		
				
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.map);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean isMyServiceRunning(Context mContext) {
	    ActivityManager manager = (ActivityManager) mContext
	            .getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager
	            .getRunningServices(Integer.MAX_VALUE)) {
	        if (BaseServiceActivity.class.getName().equals(
	                service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public void addMarker(final List<BusGPSRealtime> busGPSRealtimes) {
		
		getMap().addMarker(new MarkerOptions()
		.position(new LatLng(getLatitude("10.77475321,106.7044759"), getLongitude("10.77475321,106.7044759")))
		.title("A_01")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable._marker1)));
		
		/*getMap().addMarker(new MarkerOptions()
		.position(new LatLng(getLatitude("10.77406813,106.7052054"), getLongitude("10.77406813,106.7052054")))
		.title("A_02")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable._marker1)));
		
		getMap().addMarker(new MarkerOptions()
		.position(new LatLng(getLatitude("10.77324603,106.7060959"), getLongitude("10.77324603,106.7060959")))
		.title("A_01")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable._marker1)));
		
		getMap().addMarker(new MarkerOptions()
		.position(new LatLng(getLatitude("10.77308793,106.7046154"), getLongitude("10.77308793,106.7046154")))
		.title("A_02")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable._marker1)));*/
		
		
		
		//checkRoutine();
		
		for (BusGPSRealtime gpsRealtime : busGPSRealtimes) {
			getMap().addMarker(new MarkerOptions()
					.position(new LatLng(getLatitude(gpsRealtime.getLocation()), getLongitude(gpsRealtime.getLocation())))
					.title(gpsRealtime.getBusID())
					.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(gpsRealtime.getBusID()))));
		}
		
		//event for click on marker
		getMap().setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub		
				
				//set busID into unil class
				common.setBusIDFN001(arg0.getTitle());
				common.setLatitude(arg0.getPosition().latitude);
				common.setLongitude(arg0.getPosition().longitude);
				
				openRadialMenu();
				return true;
			}
		});
	}
	
	public void checkRoutine(){
		
		double currentLocationLa = 10.77308793;
		double currentLocationLo = 106.7046154;
		
		double polylinePointLa = 10.77324603;
		double polylinePointLo = 106.7060959;
		
		/*double angle = Math.acos(
		        (currentLocationLa*polylinePointLa+currentLocationLo+polylinePointLo) / norm(currentLocationLa,currentLocationLo)*norm(polylinePointLa,currentLocationLo));
		
		Log.e("==========DangLC", String.valueOf(angle));*/
		
		double dLon = (polylinePointLo - currentLocationLo);

	    double y = Math.sin(dLon) * Math.cos(polylinePointLa);
	    double x = Math.cos(currentLocationLa) * Math.sin(polylinePointLa) - Math.sin(currentLocationLa)
	            * Math.cos(polylinePointLa) * Math.cos(dLon);

	    double brng = Math.atan2(y, x);

	    brng = Math.toDegrees(brng);
	    brng = (brng + 360) % 360;
	    brng = 360 - brng;
	    
	    Log.e("==========DangLC", String.valueOf(brng));
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
	
	//convert string to int and second value
	public int convertTime(String time)
	{
		int timeCon = 0;
		if (time.length() == 7) {
			timeCon = Integer.parseInt(time.substring(0, 1)) * 3600 + Integer.parseInt(time.substring(2, 4)) * 60 ;
		}
		if (time.length() == 8) {
			timeCon = Integer.parseInt(time.substring(0, 2)) * 3600 + Integer.parseInt(time.substring(3, 5)) * 60 ;
		}

		return timeCon;
	}

	// find time space detail from timestart and time end
	public List<Integer> timeSpaceDeatail(String timeStart, String timeEnd, int countLocation) {

		List<Integer> timeSpaceDetail = new ArrayList<Integer>();

		int timeS = convertTime(timeStart);
		int timeE = convertTime(timeEnd);
		int timeES = timeE - timeS;
		int timeSpace = timeES / countLocation;

		for (int i = 0; i < countLocation; i++) {
			timeSpaceDetail.add(timeS + timeSpace * i);
		}

		return timeSpaceDetail;
	}



	//List <BusTimeSpace> from List<BusTime>
	public List<BusTimeSpace> timeSpace(){

		List<Integer> timeSpaceDetail = null;
		List<BusTimeSpace> busTimeSpaces = new ArrayList<BusTimeSpace>();
		for(BusTime busTime : busTimes){
			for (BusCountLocation busCountLocation : busCountLocations) {
				if ((busTime.getBusID()).equals(busCountLocation.getBusID())) {
					BusTimeSpace bTime = new BusTimeSpace();
					bTime.setBusID(busTime.getBusID());
					timeSpaceDetail = timeSpaceDeatail(busTime.getTimeStart(),busTime.getTimeEnd(),busCountLocation.getCountLocation());
					bTime.setTimeSpaceDetail(timeSpaceDetail);
					busTimeSpaces.add(bTime);
					break;
				}
			}	

		}

		return busTimeSpaces;
	}

	public String addValueTime(int time) {

		String timeV = String.valueOf(time);
		if (time < 10) {
			timeV = "0"+String.valueOf(time);
		}
		return timeV;

	}

	//Time format HH:MM:SS
	public String getCurrentTime() {

	    final Calendar c = Calendar.getInstance();

	    return(new StringBuilder()
	            .append(addValueTime(c.get(Calendar.HOUR_OF_DAY))).append(":")
	            .append(addValueTime(c.get(Calendar.MINUTE))).append(":")
	            .append(addValueTime(c.get(Calendar.SECOND))).append(" ")).toString();
	}

	//get time
	public int getTimeNow() {

		int timeNow = 0;
		timeNow = convertTime(getCurrentTime().trim());
		return timeNow;
	}

	//create List<BusTimeSpaceIndex> from List<BusTimeSpace> 
	public List<BusTimeSpaceIndex> getIndexBusTime(List<BusTimeSpace> busTimeSpaces) {

		int timeNow = getTimeNow();
		List<Integer> timeDetail = null;
		int indexTemporary = 0;
		int valueTemporary = 0;

		List<BusTimeSpaceIndex> busTimeSpaceIndexs = new ArrayList<BusTimeSpaceIndex>();


		for (BusTimeSpace timeSpace : busTimeSpaces){

			timeDetail = timeSpace.getTimeSpaceDetail();
			int numTimeDetail = timeDetail.size();

			for (int i = 0; i < numTimeDetail; i++) {
				BusTimeSpaceIndex timeSpaceIndex = new BusTimeSpaceIndex();

				if (timeNow == timeDetail.get(i)) {
					timeSpaceIndex.setIndexBus(i);
					timeSpaceIndex.setBusID(timeSpace.getBusID());
					busTimeSpaceIndexs.add(timeSpaceIndex);
					break;

				} else {
					//set value is -1 if timenow not scope of time for bus start
					if (timeNow < timeDetail.get(0) || timeNow > timeDetail.get(numTimeDetail-1)) {
						timeSpaceIndex.setIndexBus(-1);
						timeSpaceIndex.setBusID(timeSpace.getBusID());
						busTimeSpaceIndexs.add(timeSpaceIndex);
						break;
					} else {
						indexTemporary = i;
						valueTemporary = timeDetail.get(i);

						/**
						 * if value time larger a value get(i) 
						 * then index = i-1;
						 */
						if (valueTemporary > timeNow) {
							indexTemporary = i -1;
							timeSpaceIndex.setIndexBus(indexTemporary);
							timeSpaceIndex.setBusID(timeSpace.getBusID());
							busTimeSpaceIndexs.add(timeSpaceIndex);
							break;
						}
					}
				}
			}		
		}

		return busTimeSpaceIndexs;
	}

	public List<BusGPSRealtime> getLocationRealtime(List<BusTimeSpaceIndex> busTimeSpaceIndexs, List<BusLocationGPS> busLocationGPSs) {

		List<BusGPSRealtime> busGPSRealtimes = new ArrayList<BusGPSRealtime>();
		BusGPSRealtime busGPSRealtime = null;
		for (BusTimeSpaceIndex timeSpaceIndex : busTimeSpaceIndexs) {
			for (BusLocationGPS locationGPS : busLocationGPSs) {
				if (timeSpaceIndex.getBusID().equals(locationGPS.getBusID())) {
					if (timeSpaceIndex.getIndexBus() == locationGPS.getSortNum()){
						busGPSRealtime = new BusGPSRealtime();
						busGPSRealtime.setBusID(timeSpaceIndex.getBusID());
						busGPSRealtime.setLocation(locationGPS.getLocationGPS());

						busGPSRealtimes.add(busGPSRealtime);
						break;
					}
				}
			}

		}

		return busGPSRealtimes;

	}
	
	//open radial menu
	public void openRadialMenu() {

		ll = new LinearLayout(this);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( 
	             LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT); 
		addContentView(ll, params);

		PieMenu = new RadialMenuWidget(getBaseContext());

		PieMenu.setAnimationSpeed(0L);

		int xLayoutSize = ll.getWidth();
		int yLayoutSize = ll.getHeight();				
		PieMenu.setSourceLocation(xLayoutSize,yLayoutSize);
		PieMenu.setIconSize(15, 30);
		PieMenu.setTextSize(13);				
		
		PieMenu.setCenterCircle(new Close());
		PieMenu.addMenuEntry(new BusInfo());
		PieMenu.addMenuEntry(new SpaceTimeToYou());
		PieMenu.addMenuEntry(new DemoTrip());
		PieMenu.addMenuEntry(new SocialNetwork());
		
		ll.addView(PieMenu);
	}
	
	 public class Close implements RadialMenuEntry
	   {

		  public String getName() { return "Close"; } 
		  public String getLabel() { return null; } 
	      public int getIcon() { return android.R.drawable.ic_menu_close_clear_cancel; }
	      public List<RadialMenuEntry> getChildren() { return null; }
	      public void menuActiviated()
	      {
	  
	    	  System.out.println( "Close Menu Activated");
	    	  ((LinearLayout)PieMenu.getParent()).removeView(PieMenu);   
	      }
	   }	 
	
	   public class BusInfo implements RadialMenuEntry
	   {
	      public String getName() { return ""; } 
		  public String getLabel() { return ""; } 
		  public int getIcon() { return R.drawable.collections_view_as_list; }
	      public List<RadialMenuEntry> getChildren() { return null; }
	      public void menuActiviated()
	      {
	    	  ((LinearLayout)PieMenu.getParent()).removeView(PieMenu);
	    	  new FN0002().dialogDisplay(FN0001.this,common.getBusIDFN001());
	      }
	   }	
	   
	   public class SpaceTimeToYou implements RadialMenuEntry
	   {
	      public String getName() { return ""; } 
		  public String getLabel() { return ""; } 
		  public int getIcon() { return R.drawable.social_person; }
	      public List<RadialMenuEntry> getChildren() { return null; }
	      public void menuActiviated()
	      {
	    	  
	    	  double latGps = fn0003.getLatitude(FN0001.this);
	    	  double longGps = fn0003.getLongitude(FN0001.this);
	    	  
	    	  baseDatabaseActivity = new BaseDatabaseActivity(FN0001.this);
	    	  
	    	  getMap().clear();
	    	  
	    	  busLngLats = baseDatabaseActivity.getStartEndPoint(common.getBusIDFN001());
	    	  
	    	  ((LinearLayout)PieMenu.getParent()).removeView(PieMenu);
	    	  
	    	  //draw line from gps loaction to bus location current
	    	  drawPolylineGPS(latGps,longGps);
	    	  
	    	  //common.setColorPolyline(0);
	    	  
	    	  //draw polyline with bus id is bus location current
	    	  drawPolyline(common.getBusIDFN001());
	    	  
	    	  //add marker start, end and bus location current
	    	  //add marker current
	    	  getMap().addMarker(new MarkerOptions()
		  		.position(new LatLng(common.getLatitude(),common.getLongitude()))
		  		.title(common.getBusIDFN001())
		  		.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(common.getBusIDFN001()))));
	    	  
	    	  //add marker start and end of direction
	    	  for (BusLngLat busLL : busLngLats) {
	    		  getMap().addMarker(new MarkerOptions()
			  		.position(new LatLng(busLL.getLatitude(),busLL.getLongitude()))
			  		.title(common.getBusIDFN001())
			  		.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(common.getBusIDFN001()))));
	    	  }
	    	  
	    	  //add marker of gps
	    	  getMap().addMarker(new MarkerOptions()
		  		.position(new LatLng(common.getLatitudeGPS(),common.getLonitudeGPS()))
		  		.title(common.getBusIDFN001())
		  		.icon(BitmapDescriptorFactory.fromResource(R.drawable.checkloction)));
	    	  
	    	  //get diraction
	    	  
	    	  Handler handler = new Handler();
	    	  handler.postDelayed(new Runnable(){
	    	  @Override
	    	        public void run(){
	    		  	
	    		  	//Toast.makeText(getApplicationContext(), common.getDirection() + "== " + common.getTimeDirection(), Toast.LENGTH_LONG).show();
	    		  	
	    		  	mViewGroup = findViewById(R.id.layoutDirection);
	    			mViewGroup.setVisibility(View.VISIBLE);
	    			
	    			mViewGroup2 = findViewById(R.id.layoutInfo);
	    			mViewGroup2.setVisibility(View.VISIBLE);
	    			
	    			txtMyLocation = (TextView) findViewById(R.id.txtnamelmylocation);
	    			txtMyLocation.setText("Vi tri ban dang dung");
	    			
	    			txtbus = (TextView) findViewById(R.id.txtbus);
	    			txtbus.setText("Tuyen xe buyt so "+ common.getBusIDFN001());
	    			
	    			txtTimeSpace = (TextView) findViewById(R.id.txtTimeSpace);
	    			txtTimeSpace.setText("Khoang cach: "+common.getDirection() + "\n" +"Thoi gian toi ban:" + common.getTimeDirection());
	    			
	    			txtBusName = (TextView) findViewById(R.id.txtBusName);
	    			txtBusName.setText(baseDatabaseActivity.getBusName(switchMarker.choosenBusIDInfo(common.getBusIDFN001())));	    			
	    	  }
	    	  }, 2000);
	      }
	   }
	   
	   public class DemoTrip implements RadialMenuEntry
	   {
	      public String getName() { return ""; } 
		  public String getLabel() { return ""; } 
		  public int getIcon() { return R.drawable.av_shuffle; }
	      public List<RadialMenuEntry> getChildren() { return null; }
	      public void menuActiviated()
	      {
	    	  
	    	  List<BusLngLat> busLngLats = null;
	    	  List<BusLngLatAddress> busLngLatAddresses = null;
	    	  String busID = common.getBusIDFN001();
	    	  baseDatabaseActivity = new BaseDatabaseActivity(FN0001.this);
	    	  
	    	  ((LinearLayout)PieMenu.getParent()).removeView(PieMenu);
	    	  
	    	  busLngLats = baseDatabaseActivity.getLocation(busID);
	    	  common.setBusLngLats(busLngLats);
	    	  common.setMap(getMap());
	    	  common.setContext(FN0001.this);
	    	  
	    	  busLngLatAddresses = baseDatabaseActivity.getLocationAddress(busID);
	    	  common.setBusLngLatAddresses(busLngLatAddresses);
	    	  
	    	  new FN0004().animationMarker();
			  
	    	  
	      }
	   }
	   
	   public static class SocialNetwork implements RadialMenuEntry
	   {
	      public String getName() { return ""; } 
		  public String getLabel() { return ""; } 
		  public int getIcon() { return R.drawable.social_share; }
	      public List<RadialMenuEntry> getChildren() { return null; }
	      public void menuActiviated()
	      {
	    	  ((LinearLayout)PieMenu.getParent()).removeView(PieMenu);
	      }
	   }
	   
	   public void drawPolyline(String busID) {
		   
		   //List<LatLng> latLngs = PolyUtil.decode(common.getBusIDFN001());
		   List<LatLng> latLngs = directionsJSONParser.decodePoly(common.ecodeBus_1);
		   getMap().addPolyline(new PolylineOptions()
		   							.addAll(latLngs)
		   							.color(Color.RED)
		   							.width(5));
		   /*common.setFlag(2);
		   
		   int x = 0 ;
		   int y = 0;
		  List<BusLngLat> busLngLats = baseDatabaseActivity.getLocation(busID);
		  for (int i = 0 ; i < busLngLats.size() ; i++) {
			  x = y;
			  y = x + 1;
			  BusLngLat lngLatS = busLngLats.get(x);
			  BusLngLat lngLatN = busLngLats.get(y);
			  getDirection(FN0001.this,lngLatS.getLatitude(),lngLatS.getLongitude(),lngLatN.getLatitude(),lngLatN.getLongitude());
			  x = y;
			  if (y == busLngLats.size() -1) {
				  break;
			  }
		  }*/
		   
	   }
	   
	public void drawPolylineGPS(double latGps, double longGps ) {
		
		//common.setFlag(1);
		
		getDirection(FN0001.this,latGps,longGps,common.getLatitude(),common.getLongitude());
	}
}
