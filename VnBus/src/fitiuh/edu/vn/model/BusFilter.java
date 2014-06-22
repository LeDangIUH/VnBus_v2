package fitiuh.edu.vn.model;

public class BusFilter {

	private boolean selected = true;
	private String code = null;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
