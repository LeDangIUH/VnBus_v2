package fitiuh.edu.vn.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.cu;

import fitiuh.edu.vn.common.Common;
import fitiuh.edu.vn.common.SqlQuery;
import fitiuh.edu.vn.model.BusAllID;
import fitiuh.edu.vn.model.BusCountLocation;
import fitiuh.edu.vn.model.BusInfo;
import fitiuh.edu.vn.model.BusLngLat;
import fitiuh.edu.vn.model.BusLngLatAddress;
import fitiuh.edu.vn.model.BusLocationGPS;
import fitiuh.edu.vn.model.BusTime;
import fitiuh.edu.vn.model.SwitchMarker;
import fitiuh.edu.vn.vnbus.FN0001;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatabaseActivity extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	String DB_PATH = null;

	private SQLiteDatabase myDataBase;

	private final Context myContext;
	Common common = new Common();
	FN0001 fn0001 = new FN0001();
	
	SwitchMarker switchMarker = new SwitchMarker();

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public BaseDatabaseActivity(Context context) {
		super(context, Common.DB_NAME, null, 1);
		this.myContext = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
	}

	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		

		if (dbExist) {
			
			if (common.getBusCountLocations() == null) {
				common.setBusLocationGPS(getListBusGPS());
			}
			
			if (common.getBusTimes() == null) {
				common.setBusTimes(getListBusTime());
			}
			
			if (common.getBusAllID() == null) {
				common.setBusAllID(getListBusID());
			}
			
			if (common.getBusCountLocations() == null) {
				common.setBusCountLocations(getcountLocation());
			}
			
			
			//Log.e("danglc", "***********************" + getListBusTime().size());
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				Log.e("====", e.getMessage());
				throw new Error("Error copying database");

			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + Common.DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(Common.DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + Common.DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + Common.DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}
	
	@Override
	public synchronized void close() {
		if(myDataBase != null)
		    myDataBase.close();

	    super.close();
	}
	
	//connect db and create List<BusLocationGPS>
	public List<BusLocationGPS> getListBusGPS() {
		
		List<BusLocationGPS> busLocationGPS = new ArrayList<BusLocationGPS>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SqlQuery.sqlFN000101, null);
		
		BusLocationGPS locationGPS = null;
		if (cursor.moveToFirst()) {
			do {
				
				locationGPS = new BusLocationGPS();
				locationGPS.setBusID(cursor.getString(0));
				locationGPS.setLocationGPS(cursor.getString(1));
				locationGPS.setSortNum(cursor.getInt(2));
				
				busLocationGPS.add(locationGPS);
			} while (cursor.moveToNext());
		}
		
		return busLocationGPS;	
	}
	
	//connect db and create List<Bustime>
	public List<BusTime> getListBusTime() {
		
		List<BusTime> busTimes = new ArrayList<BusTime>();
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SqlQuery.sqlFN000102, null);
		
		BusTime time = null;
		if (cursor.moveToFirst()) {
			do {
			time = new BusTime();
			time.setBusID(cursor.getString(0));
			time.setTimeStart(cursor.getString(1));
			time.setTimeEnd(cursor.getString(2));
			
			busTimes.add(time);
			} while (cursor.moveToNext());
		}
		
		return busTimes;
	}
	
	public List<BusAllID> getListBusID() {
		
		List<BusAllID> busAllID = new ArrayList<BusAllID>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SqlQuery.sqlFN000103, null);
		
		BusAllID allID = null;
		if (cursor.moveToFirst()) {
			do {
				allID = new BusAllID();
				allID.setBusID(cursor.getString(0));
				
				busAllID.add(allID);
			}while (cursor.moveToNext());
		}
		
		return busAllID;
	}
	
	
	public List<BusCountLocation> getcountLocation(){
		
		List<BusAllID> allID = Common.getBusAllID();
		List<BusCountLocation> busCountLocations = new ArrayList<BusCountLocation>();
		BusCountLocation countLocation = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		for (BusAllID busAllID : allID ){
			Cursor cursor = db.rawQuery("SELECT COUNT(BusStopID) FROM BusLocation WHERE BusID = '" + busAllID.getBusID() + "'" +";", null);
			if (cursor.moveToFirst()) {
				do {
					countLocation = new BusCountLocation();
					countLocation.setBusID(busAllID.getBusID());
					countLocation.setCountLocation(cursor.getInt(0));
					
					busCountLocations.add(countLocation);
				}while (cursor.moveToNext());
			}
		}
		return busCountLocations;
	}
	
	public List<BusInfo> getBusInfo(String busID) {
		
		List<BusInfo> busInfos = new ArrayList<BusInfo>();
		BusInfo info = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT _id,BusName,ActivityType,Distance,RuningTime,SpacingTime,BusType,Uptime,NTrip,PathLuotDi," +
									"PathLuotVe FROM BusInfo WHERE _id = " + switchMarker.choosenBusIDInfo(busID) + ";",null);
		if (cursor.moveToFirst()) {
			do {
				
				info = new BusInfo();
				
				info.setBusID(cursor.getInt(0));
				info.setBusName(cursor.getString(1));
				info.setActivityType(cursor.getString(2));
				info.setDistance(cursor.getString(3));
				info.setRuningTime(cursor.getString(4));
				info.setSpacingTime(cursor.getString(5));
				info.setBusType(cursor.getString(6));
				info.setUpTime(cursor.getString(7));
				info.setnTrip(cursor.getString(8));
				info.setPathOutward(cursor.getString(9));
				info.setPathHomeward(cursor.getString(10));
				
				busInfos.add(info);
				
			}while (cursor.moveToNext());
		}
		
		return busInfos;
		
	}
	
	public List<BusLngLat> getStartEndPoint(String busID) {
		
		List<BusLngLat> busLngLats = new ArrayList<BusLngLat>();
		BusLngLat lngLat = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		for (BusCountLocation busCountLocation : common.getBusCountLocations()) {
			if (busID.equals(busCountLocation.getBusID())) {
				Cursor cursor = db.rawQuery("SELECT Location " +
											"FROM BusLocation, BusStop " +
											"WHERE  BusLocation.BusID = '" + busID+ "'" +
												"AND BusLocation.BusStopID = BusStop._id " +
												"AND (BusLocation.SortNum = 0 OR BusLocation.SortNum = " + subtractV(busCountLocation.getCountLocation()) + ")" +";", null);
				if (cursor.moveToFirst()) {
					do {
						lngLat = new BusLngLat();
						
						lngLat.setLatitude(fn0001.getLatitude(cursor.getString(0)));
						lngLat.setLongitude(fn0001.getLongitude(cursor.getString(0)));
						
						busLngLats.add(lngLat);
						
					}while (cursor.moveToNext());
				}
			}
		}
		
		
		return busLngLats;
	}
	
	public int subtractV(int a) {
		
		int value = 0;
		value = a -1;
		return value;
	}
	
	public List<BusLngLat> getLocation(String busID) {
		
		List<BusLngLat> busLngLats = new ArrayList<BusLngLat>();
		BusLngLat lngLat = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		for (BusCountLocation busCountLocation : common.getBusCountLocations()) {
			if (busID.equals(busCountLocation.getBusID())) {
				Cursor cursor = db.rawQuery("SELECT Location " +
											"FROM BusLocation, BusStop " +
											"WHERE  BusLocation.BusID = '" + busID+ "'" +
												"AND BusLocation.BusStopID = BusStop._id " + ";", null);
				if (cursor.moveToFirst()) {
					do {
						lngLat = new BusLngLat();
						
						lngLat.setLatitude(fn0001.getLatitude(cursor.getString(0)));
						lngLat.setLongitude(fn0001.getLongitude(cursor.getString(0)));
						
						busLngLats.add(lngLat);
						
					}while (cursor.moveToNext());
				}
			}
		}
		return busLngLats;
	}
	
	public String getBusName(int id){
		
		String busName = null;	
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT BusName FROM BusInfo WHERE _id = "+ id + ";",null);
		if (cursor.moveToFirst()) {
			do {
				busName = cursor.getString(0);
				
			}while (cursor.moveToNext());
		}
		return busName;
	}
	
	public List<BusLngLatAddress> getLocationAddress(String busID) {
		
		List<BusLngLatAddress> busLngLatAddresses = new ArrayList<BusLngLatAddress>();
		BusLngLatAddress lngLatAddress = null;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		for (BusCountLocation busCountLocation : common.getBusCountLocations()) {
			if (busID.equals(busCountLocation.getBusID())) {
				Cursor cursor = db.rawQuery("SELECT Address, Location " +
											"FROM BusLocation, BusStop " +
											"WHERE  BusLocation.BusID = '" + busID+ "'" +
												"AND BusLocation.BusStopID = BusStop._id " + ";", null);
				if (cursor.moveToFirst()) {
					do {
						
						lngLatAddress = new BusLngLatAddress();

						lngLatAddress.setBusAddress(cursor.getString(0));
						lngLatAddress.setLocation(cursor.getString(1));
						
						busLngLatAddresses.add(lngLatAddress);
						
					}while (cursor.moveToNext());
				}
			}
		}
		return busLngLatAddresses;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
