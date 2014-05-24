package fitiuh.edu.vn.base;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import fitiuh.edu.vn.vnbus.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseMapActivity extends FragmentActivity {
	
	private GoogleMap mMap;
	
	protected int getLayoutId() {
        return R.layout.map;
    }
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		//setContentView(R.layout.vnb_001);
		setContentView(getLayoutId());
		
		setUpMapIfNeeded();
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
}
