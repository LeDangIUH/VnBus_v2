package fitiuh.edu.vn.base;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fitiuh.edu.vn.common.Common;
import fitiuh.edu.vn.model.BusAllID;
import fitiuh.edu.vn.model.BusCountLocation;
import fitiuh.edu.vn.model.BusGPSRealtime;
import fitiuh.edu.vn.model.BusLocationGPS;
import fitiuh.edu.vn.model.BusTime;
import fitiuh.edu.vn.model.BusTimeSpace;
import fitiuh.edu.vn.model.BusTimeSpaceIndex;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BaseServiceActivity extends Service {
	
	List<BusCountLocation> busCountLocations = null;
	List<BusLocationGPS> busLocationGPSs = null;
	List<BusTime> busTimes = null;
	List<BusAllID> busAllIDs = null;
	List<BusTimeSpace> busTimeSpaces = null;
	List<BusTimeSpaceIndex> busTimeSpaceIndexs = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		createUtil();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	/**
	 * 
	 */
	public void createUtil() {
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
		Common.setBusGPSRealtimes(getLocationRealtime(busTimeSpaceIndexs,busLocationGPSs));
		// display loacation realtime into map
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

}
