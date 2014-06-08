package fitiuh.edu.vn.common;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

import fitiuh.edu.vn.base.BaseMapActivity;
import fitiuh.edu.vn.model.BusAllID;
import fitiuh.edu.vn.model.BusCountLocation;
import fitiuh.edu.vn.model.BusGPSRealtime;
import fitiuh.edu.vn.model.BusLocationGPS;
import fitiuh.edu.vn.model.BusTime;
import fitiuh.edu.vn.model.BusTimeSpace;
import fitiuh.edu.vn.vnbus.FN0001;
import fitiuh.edu.vn.vnbus.R.string;

public class Common extends Activity {
	
	public static String DB_NAME = "DB_VnBus";
	public static String TABLE_BUSLOCATION = "BusLocation";
	public static String TABLE_BUSSTOP = "BusStop";
	public static String TABLE_BUSTIME = "BusTime";
	public static String busID;
	public static int bID;
	public static String busIDFN001;
	public static double latitude;
	public static double longitude;
	public static GoogleMap map;
	private static double latitudeGPS;
	private static double lonitudeGPS;
	public static String direction;
	private static String timeDirection;
	public static int colorPolyline ;
	public static int flag;
	
	public String ecodeBus_1 = "uxw`Ac_hjS`T{A~FbAcO~PsCnIrG~FpG|FjIjA~KpOxGrFpHfG|JpIrIvGtPlN~JdJdI|Rv@hEnBrKfBdKdBpJzApIxBrPCpR|AvLb@fN^tIpCrR~AfBc@lSa@xN";
	
	
	public static List<BusLocationGPS> busLocationGPS;
	public static List<BusTime> busTimes;
	public static List<BusTimeSpace> busTimeSpaces;
	public static List<BusAllID> busAllID;
	public static List<BusCountLocation> busCountLocations;
	public static List<BusGPSRealtime> busGPSRealtimes;
	
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

	public static List<BusGPSRealtime> getBusGPSRealtimes() {
		return busGPSRealtimes;
	}

	public static void setBusGPSRealtimes(List<BusGPSRealtime> busGPSRealtimes) {
		Common.busGPSRealtimes = busGPSRealtimes;
	}

	public static int getbID() {
		return bID;
	}

	public static void setbID(int bID) {
		Common.bID = bID;
	}

	public static String getBusIDFN001() {
		return busIDFN001;
	}

	public static void setBusIDFN001(String busIDFN001) {
		Common.busIDFN001 = busIDFN001;
	}

	public static double getLongitude() {
		return longitude;
	}

	public static void setLongitude(double longitude) {
		Common.longitude = longitude;
	}

	public static double getLatitude() {
		return latitude;
	}

	public static void setLatitude(double latitude) {
		Common.latitude = latitude;
	}

	public static double getLonitudeGPS() {
		return lonitudeGPS;
	}

	public static void setLonitudeGPS(double lonitudeGPS) {
		Common.lonitudeGPS = lonitudeGPS;
	}

	public static double getLatitudeGPS() {
		return latitudeGPS;
	}

	public static void setLatitudeGPS(double latitudeGPS) {
		Common.latitudeGPS = latitudeGPS;
	}

	public static String getDirection() {
		return direction;
	}

	public static void setDirection(String direction) {
		Common.direction = direction;
	}

	public static int getColorPolyline() {
		return colorPolyline;
	}

	public static void setColorPolyline(int colorPolyline) {
		Common.colorPolyline = colorPolyline;
	}

	public static int getFlag() {
		return flag;
	}

	public static void setFlag(int flag) {
		Common.flag = flag;
	}

	public static String getTimeDirection() {
		return timeDirection;
	}

	public static void setTimeDirection(String timeDirection) {
		Common.timeDirection = timeDirection;
	}
}
