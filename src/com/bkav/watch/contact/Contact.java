package com.bkav.watch.contact;

import com.bkav.home.common.Element;
import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class Contact extends Component{

	public Contact(Component parent, String name) {
		super(parent, name);
		
		this.contactName = new StringAttribute(this, "contactName");
		this.contactID = new StringAttribute(this, "contactID");
		
		add(this.contactName);  
		add(this.contactID);
	}
	
//	public Contact(Component parent, String name, String number, String type) {
//		this(parent, name);
//				
//		Number numberObject = new Number(this, "number." + type);
//		numberObject.getNumber().setValue(number);
//		numberObject.getType().setValue(type);
//		
//		numberObject.saveAttributes();
//		add(numberObject);		
//	}
	
	public void addNumber(String number, String type) {
		Element element = this.getElement("number." + type);
		
		if (element == null) {
			Number numberObject = new Number(this, "number." + type);
			numberObject.getNumber().setValue(number);
			numberObject.getType().setValue(type);
			add(numberObject);		
			numberObject.saveAttributes();
		}
		
	}
	
	public StringAttribute getContactName() {
		return this.contactName;
	}

	public StringAttribute getContactID() {
		return this.contactID;
	}

	@Override
	protected Component createComponent(String name) {
		if (name.startsWith("number.")) {
			return new Number(this, name);
		} else {
			return null;
		}
	}
	
	private StringAttribute contactName;
	private StringAttribute contactID;
}
