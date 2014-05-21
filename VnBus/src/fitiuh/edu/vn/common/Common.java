package fitiuh.edu.vn.common;

import java.util.List;

import fitiuh.edu.vn.model.BusAllID;
import fitiuh.edu.vn.model.BusCountLocation;
import fitiuh.edu.vn.model.BusLocationGPS;
import fitiuh.edu.vn.model.BusTime;
import fitiuh.edu.vn.model.BusTimeSpace;

public class Common {
	
	public static String DB_NAME = "DB_VnBus";
	public static String TABLE_BUSLOCATION = "BusLocation";
	public static String TABLE_BUSSTOP = "BusStop";
	public static String TABLE_BUSTIME = "BusTime";
	public static String busID;
	
	public static List<BusLocationGPS> busLocationGPS;
	public static List<BusTime> busTimes;
	public static List<BusTimeSpace> busTimeSpaces;
	public static List<BusAllID> busAllID;
	public static List<BusCountLocation> busCountLocations;
	
	public static List<BusLocationGPS> getBusLocationGPS() {
		return busLocationGPS;
	}

	public static void setBusLocationGPS(List<BusLocationGPS> busLocationGPS) {
		Common.busLocationGPS = busLocationGPS;
	}

	public static List<BusTime> getBusTimes() {
		return busTimes;
	}

	public void setBusTimes(List<BusTime> busTimes) {
		Common.busTimes = busTimes;
	}

	public static List<BusTimeSpace> getBusTimeSpaces() {
		return busTimeSpaces;
	}

	public static void setBusTimeSpaces(List<BusTimeSpace> busTimeSpaces) {
		Common.busTimeSpaces = busTimeSpaces;
	}

	public static List<BusAllID> getBusAllID() {
		return busAllID;
	}

	public static void setBusAllID(List<BusAllID> busAllID) {
		Common.busAllID = busAllID;
	}

	public static String getBusID() {
		return busID;
	}

	public static void setBusID(String busID) {
		Common.busID = busID;
	}

	public static List<BusCountLocation> getBusCountLocations() {
		return busCountLocations;
	}

	public static void setBusCountLocations(List<BusCountLocation> busCountLocations) {
		Common.busCountLocations = busCountLocations;
	}

}
