package fitiuh.edu.vn.common;

import fitiuh.edu.vn.model.SwitchMarker;

public class SqlQuery {
	
	public static String sqlFN000101 = "SELECT BusID,Location,SortNum FROM BusLocation, BusStop WHERE BusLocation.BusStopID = BusStop._id;";
	public static String sqlFN000102 = "SELECT BusID,TimeStart,TimeEnd FROM BusTime;";
	public static String sqlFN000103 = "SELECT BusID FROM BusLocation GROUP BY BusID";
	public static String sqlFN000104 = "SELECT COUNT(BusStopID) FROM BusLocation WHERE BusID = '" + Common.getBusID() + "'" +";";
	
	public static String sqlFN000201 = "SELECT _id,BusName,ActivityType,Distance,RuningTime,SpacingTime,BusType,Uptime,NTrip,PathLuotDi" +
										 ",PathLuotVe FROM BusInfo WHERE _id = "+ Common.getbID() + ";";
}
