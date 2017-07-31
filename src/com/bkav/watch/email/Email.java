package com.bkav.watch.email;

import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class Email extends Component{

	public Email(Component parent, String name) {
		super(parent, name);
		this.address = new StringAttribute(this, "address");
		this.time = new StringAttribute(this, "time");
		this.subject = new StringAttribute(this, "subject");
		this.mailType = new StringAttribute(this, "mailType");
		this.emailID = new StringAttribute(this, "mailID");
		
		add(this.address);
		add(this.time);
		add(this.subject);
		add(this.mailType);
		add(this.emailID);
	}

	public StringAttribute getAddress() {
		return address;
	}
	
	public StringAttribute getTime() {
		return time;
	}
	
	public StringAttribute getSubject() {
		return subject;
	}
	
	public StringAttribute getMailType() {
		return mailType;
	}
	
	public StringAttribute getEmailID() {
		return emailID;
	}

	private StringAttribute address;
	private StringAttribute time;
	private StringAttribute subject;
	private StringAttribute mailType;
	private StringAttribute emailID;
}
