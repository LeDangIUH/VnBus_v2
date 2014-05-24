package fitiuh.edu.vn.vnbus;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

public class VNB_F_001 extends FragmentActivity {
	
	 private GoogleMap VNB_MAP;
	 private final static String LINE = "uxw`Ac_hjS`T{A~FbAcO~PsCnIrG~FpG|FjIjA~KpOxGrFpHfG|JpIrIvGtPlN~JdJdI|Rv@hEnBrKfBdKdBpJzApIxBrPCpR|AvLb@fN^tIpCrR~AfBc@lSa@xN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        //setContentView(R.layout.vnb_001);
        
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
    	List<LatLng>decodedPath=PolyUtil.decode(LINE);
    	
        if (VNB_MAP == null) {
        	VNB_MAP = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.VNBUS_MAP)).getMap();
        	VNB_MAP.addPolyline(new PolylineOptions().addAll(decodedPath));
        	VNB_MAP.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.8256, 151.2395), 12));
        	
        }
        if (VNB_MAP == null) {
            return;
        }
        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    
}
