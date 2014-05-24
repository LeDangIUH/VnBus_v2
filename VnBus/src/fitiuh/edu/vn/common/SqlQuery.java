package fitiuh.edu.vn.common;

public class SqlQuery {
	
	public static String sqlFN000101 = "SELECT BusID,Location,SortNum FROM BusLocation, BusStop WHERE BusLocation.BusStopID = BusStop._id;";
	public static String sqlFN000102 = "SELECT BusID,TimeStart,TimeEnd FROM BusTime;";
	public static String sqlFN000103 = "SELECT BusID FROM BusLocation GROUP BY BusID";
	public static String sqlFN000104 = "SELECT COUNT(BusStopID) FROM BusLocation WHERE BusID = '" + Common.getBusID() + "'" +";";
}
