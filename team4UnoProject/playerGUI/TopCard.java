package playerGUI;

import java.io.Serializable;
import javax.swing.JToggleButton;

public class TopCard extends JToggleButton implements Serializable{
	private String color;
	private String type;
	private Integer value;
	
	
	public TopCard() {
		color = "not set";
		type = "not set";
		value = 0;
	}
	
	public TopCard(String c, String t, Integer v) {
		color = c;
		type = t;
		value = v;
	}

	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color=color;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public String getValue() {
		return Integer.toString(value);
	}
	
	public void setValue(Integer value) {
		this.value=value;
	}
	
	public String toString() {
		return color+" "+type+" "+value;
	}
	
	public String toStringNormal() {
		return color+Integer.toString(value);
	}
	
	public String toStringSpecial() {
		return color+type;
	}
	
	public String toStringVerySpecial() {
		return "all"+type;
	}
}
