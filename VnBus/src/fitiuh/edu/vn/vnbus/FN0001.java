package fitiuh.edu.vn.vnbus;
import fitiuh.edu.vn.base.BaseDatabaseActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class FN0001 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fn0001);
		
		BaseDatabaseActivity baseDatabaseActivity = new BaseDatabaseActivity(getApplicationContext());
		try {
			baseDatabaseActivity.createDataBase();
		} catch (Exception e) {
			throw new Error("Unable to create database");
		}
		try {
			baseDatabaseActivity.openDataBase();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.w("ok", "-----OK");		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
