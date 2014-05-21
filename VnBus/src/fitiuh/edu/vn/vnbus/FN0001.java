package fitiuh.edu.vn.vnbus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fitiuh.edu.vn.base.BaseDatabaseActivity;
import fitiuh.edu.vn.common.Common;
import fitiuh.edu.vn.model.BusAllID;
import fitiuh.edu.vn.model.BusCountLocation;
import fitiuh.edu.vn.model.BusLocationGPS;
import fitiuh.edu.vn.model.BusTime;
import fitiuh.edu.vn.model.BusTimeSpace;
import fitiuh.edu.vn.model.BusTimeSpaceIndex;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class FN0001 extends Activity {
	
	List<BusCountLocation> busCountLocations = null;
	List<BusLocationGPS> busLocationGPSs = null;
	List<BusTime> busTimes = null;
	List<BusAllID> busAllIDs = null;
	List<BusTimeSpace> busTimeSpaces = null;
	List<BusTimeSpaceIndex> busTimeSpaceIndexs = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fn0001);	
		
		BaseDatabaseActivity baseDatabaseActivity = new BaseDatabaseActivity(getApplicationContext());
		try {
			baseDatabaseActivity.createDataBase();
		} catch (Exception e) {
			//Log.e("====", e.getMessage());
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
		
		busTimeSpaces= timeSpace();
		busTimeSpaceIndexs = getIndexBusTime(busTimeSpaces);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		
		List<BusTimeSpace> busTimeSpaces = new ArrayList<BusTimeSpace>();
		BusTimeSpace bTime = new BusTimeSpace();
		
		for(BusTime busTime : busTimes){
			bTime.setBusID(busTime.getBusID());
			for (BusCountLocation busCountLocation : busCountLocations) {
				if ((busTime.getBusID()).equals(busCountLocation.getBusID())) {
					bTime.setTimeSpaceDetail(timeSpaceDeatail(busTime.getTimeStart(),busTime.getTimeEnd(),busCountLocation.getCountLocation()));
					break;
				}
			}
			
			busTimeSpaces.add(bTime);
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
		BusTimeSpaceIndex timeSpaceIndex = new BusTimeSpaceIndex();
		
		for (BusTimeSpace timeSpace : busTimeSpaces){
			
			timeSpaceIndex.setBusID(timeSpace.getBusID());
			
			timeDetail = timeSpace.getTimeSpaceDetail();
			for (int i = 0; i < timeDetail.size(); i++) {
				if (timeNow == timeDetail.get(i)) {
					timeSpaceIndex.setIndexBus(i);
				} else {
					//set value is -1 if timenow not scope of time for bus start
					if ((timeNow < timeDetail.get(0)) || (timeNow > timeDetail.get(timeDetail.size()))) {
						timeSpaceIndex.setIndexBus(-1);
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
							break;
						}
					}
				}
			}
			
			busTimeSpaceIndexs.add(timeSpaceIndex);
		}
		
		return busTimeSpaceIndexs;
	}
}
