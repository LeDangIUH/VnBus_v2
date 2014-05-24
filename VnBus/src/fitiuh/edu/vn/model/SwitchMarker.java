package fitiuh.edu.vn.model;

import fitiuh.edu.vn.vnbus.R;

public class SwitchMarker {
	
	int busID_1 = R.drawable._marker1;
	int busID_2 = R.drawable._marker2;
	int busID_3 = R.drawable._marker3;
	int busID_4 = R.drawable._marker4;
	int busID_5 = R.drawable._marker5;
	int busID_6 = R.drawable._marker6;
	int busID_7 = R.drawable._marker7;
	int busID_8 = R.drawable._marker8;
	int busID_9 = R.drawable._marker9;
	int busID_10 = R.drawable._marker10;
	
	enum BusID{A_01,A_02,A_03,A_04,A_05,A_06,A_07,A_08,A_09,A_10,B_01,B_02,B_03,B_04,B_05,B_06,B_07,B_08,B_09,B_10};
	

	/**
	 * 
	 * @param busID
	 * @return
	 */		
	
	public int chooseMarker(String busID) {
		
		BusID id = BusID.valueOf(busID);
		 
		int markerBus = 0;
		
		switch (id) {
		case A_01: {
			markerBus = busID_1;
			break;
		}
		case A_02: {
			markerBus = busID_2;
			break;
		}
		case A_03: {
			markerBus = busID_3;
			break;
		}
		case A_04: {
			markerBus = busID_4;
			break;
		}
		case A_05: {
			markerBus = busID_5;
			break;
		}
		case A_06: {
			markerBus = busID_6;
			break;
		}
		case A_07: {
			markerBus = busID_7;
			break;
		}
		case A_08: {
			markerBus = busID_8;
			break;
		}
		case A_09: {
			markerBus = busID_9;
			break;
		}
		case A_10: {
			markerBus = busID_10;
			break;
		}
		
		case B_01: {
			markerBus = busID_1;
			break;
		}
		case B_02: {
			markerBus = busID_2;
			break;
		}
		case B_03: {
			markerBus = busID_3;
			break;
		}
		case B_04: {
			markerBus = busID_4;
			break;
		}
		case B_05: {
			markerBus = busID_5;
			break;
		}
		case B_06: {
			markerBus = busID_6;
			break;
		}
		case B_07: {
			markerBus = busID_7;
			break;
		}
		case B_08: {
			markerBus = busID_8;
			break;
		}
		case B_09: {
			markerBus = busID_9;
			break;
		}
		case B_10: {
			markerBus = busID_10;
			break;
		}
		
		default:
			break;
		}
		
		
		return markerBus;
	}
}
