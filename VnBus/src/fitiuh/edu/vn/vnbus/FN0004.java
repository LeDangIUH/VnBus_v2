package fitiuh.edu.vn.vnbus;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.internal.fo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import fitiuh.edu.vn.base.DirectionsJSONParser;
import fitiuh.edu.vn.common.Common;
import fitiuh.edu.vn.model.BusLngLat;
import fitiuh.edu.vn.model.BusLngLatAddress;
import fitiuh.edu.vn.model.SwitchMarker;

public class FN0004 {
	
	Common common = new Common();
	int currentPt;
	private List<LatLng> listPoint = null;
	private List<BusLngLat> busLngLats = null;
	GoogleMap myMap = common.getMap();
	SwitchMarker switchMarker = new SwitchMarker();
	DirectionsJSONParser directionsJSONParser = new DirectionsJSONParser();
	IconGenerator iconFactory ;
	FN0001 fn0001 = new FN0001();
	
	public void animationMarker() {
		
		listPoint = getvalueListLnglat() ;
		
		myMap.animateCamera(
				CameraUpdateFactory.zoomTo(15), 
				5000,
				MyCancelableCallback);						
		
		currentPt = 0-1;
		
	};
	
	public List<LatLng> getvalueListLnglat() {
		
		List<LatLng> latLngs = new ArrayList<LatLng>() ;
		LatLng lng = null;
		
		busLngLats = common.getBusLngLats();
		
		for (BusLngLat busL : busLngLats) {
			lng = new LatLng(busL.getLatitude(), busL.getLongitude());
			latLngs.add(lng);
		}
		
		return latLngs;
	};
	
	
	CancelableCallback MyCancelableCallback = new CancelableCallback() {
		
		@Override
		public void onCancel() {
			
		}
		
		@Override
		public void onFinish() {
			
			if(++currentPt < listPoint.size()){
				
				myMap.clear();
				
				myMap.animateCamera(
						CameraUpdateFactory.newLatLng(listPoint.get(currentPt)), 
						5000,
						MyCancelableCallback);
				
				Marker marker = myMap.addMarker(new MarkerOptions()
								.position(listPoint.get(currentPt))
								.title(getTitleMarkerMap(common.getBusLngLatAddresses(),listPoint.get(currentPt)))
								.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(common.getBusIDFN001()))));
				marker.showInfoWindow();
				
			}else{
				myMap.clear();
				drawPolyline(common.getBusIDFN001());
			}
			
		}
		
		
	};
	
	public void drawPolyline(String busID) {
		
			for (BusLngLat busLL : busLngLats) {
	  		  myMap.addMarker(new MarkerOptions()
			  		.position(new LatLng(busLL.getLatitude(),busLL.getLongitude()))
			  		.title(common.getBusIDFN001())
			  		.icon(BitmapDescriptorFactory.fromResource(switchMarker.chooseMarker(common.getBusIDFN001()))));
	  	  }
		   
		   //List<LatLng> latLngs = PolyUtil.decode(common.getBusIDFN001());
		   List<LatLng> latLngs = directionsJSONParser.decodePoly(common.ecodeBus_1);
		   myMap.addPolyline(new PolylineOptions()
		   							.addAll(latLngs)
		   							.color(Color.RED)
		   							.width(5));
		   
	   }
	
	public String getTitleMarkerMap(List<BusLngLatAddress> busLngLatAddresses, LatLng listPoint) {
		
		String title = null;
		
		for (BusLngLatAddress address : busLngLatAddresses) {
			if (getLat(address.getLocation()) == listPoint.latitude
					&& getLng(address.getLocation()) == listPoint.longitude) {
				title = address.getBusAddress();
				break;
			}
		}
		
		return title;
	}
	
	public double getLat(String a){
		
		double lat = 0;
		lat = fn0001.getLatitude(a);
		
		return lat;
	}
	
	public double getLng(String a){
		
		double lng = 0;
		lng = fn0001.getLongitude(a);
		
		return lng;
	}
}
