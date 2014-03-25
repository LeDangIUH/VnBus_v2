package fitiuh.edu.vn.vnbus;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class VNB_F_001 extends FragmentActivity {
	
	 private GoogleMap VNB_MAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vnb_001);
        
        setUpMapIfNeeded();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void setUpMapIfNeeded() {
        if (VNB_MAP == null) {
        	VNB_MAP = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.VNBUS_MAP)).getMap();
        }
        if (VNB_MAP == null) {
            return;
        }
        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    
}
