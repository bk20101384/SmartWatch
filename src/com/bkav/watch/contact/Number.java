package com.bkav.watch.contact;

import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class Number extends Component{

	public Number(Component parent, String name) {
		super(parent, name);
		
		this.number = new StringAttribute(this, "number");
		this.type = new StringAttribute(this, "type");
		
		add(this.number);
		add(this.type);
	}
	
	public StringAttribute getNumber() {
		return this.number;
	}
	
	public StringAttribute getType() {
		return this.type;
	}
	
	private StringAttribute number;
	private StringAttribute type;

}
