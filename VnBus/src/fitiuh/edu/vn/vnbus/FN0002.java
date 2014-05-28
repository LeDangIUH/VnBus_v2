package fitiuh.edu.vn.vnbus;

import java.util.List;

import fitiuh.edu.vn.base.BaseDatabaseActivity;
import fitiuh.edu.vn.model.BusInfo;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class FN0002 extends Activity {
	
	 BaseDatabaseActivity baseDatabaseActivity ;
	 List<BusInfo> busInfos = null;
	 TextView textView ;
	 
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//dialogDisplay();
		
	}
	
	public  void dialogDisplay(Context context, String busID) {
		
		
		baseDatabaseActivity = new BaseDatabaseActivity(context);
				
		busInfos = baseDatabaseActivity.getBusInfo(busID);
		
		Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.fn0002);
		dialog.setCancelable(true);

		textView = new TextView(context);
		textView = (TextView) dialog.findViewById(R.id.txtBusInfo);
		textView.setMovementMethod(new ScrollingMovementMethod());
		
		for (BusInfo bus : busInfos) {
			//textView.setText(bus.getBusName());
			dialog.setTitle("Thong Tin Buyt so " + String.valueOf(bus.getBusID()));
			textView.setText(String.valueOf(bus.getBusID())+"\n" 
							+ bus.getBusName() + "\n"
							+ bus.getActivityType() + "\n"
							+ bus.getDistance() + "\n"
							+ bus.getRuningTime() + "\n"
							+ bus.getSpacingTime() + "\n"
							+ bus.getBusType() + "\n"
							+ bus.getUpTime() + "\n"
							+ bus.getnTrip() + "\n"
							+ bus.getPathOutward() + "\n"
							+ bus.getPathHomeward());
		}
		
		dialog.show();	
		
		
	}
	
}
