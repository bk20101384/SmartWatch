package com.bkav.watch.sms;

import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class Sms extends Component {

	public Sms(Component parent, String name) {
		super(parent, name);
		this.contactName = new StringAttribute(this, "contactName");
		this.number = new StringAttribute(this, "number");
		this.content = new StringAttribute(this, "content");
		this.time = new StringAttribute(this, "time");
		this.state = new StringAttribute(this, "state");
		this.type = new StringAttribute(this, "type");
		this.smsId = new StringAttribute(this, "smsId");
		this.isIncoming = new StringAttribute(this, "isIncoming");

		add(this.contactName);
		add(this.number);
		add(this.content);
		add(this.time);
		add(this.state);
		add(this.type);
		add(this.isIncoming);
		add(this.smsId);
	}

	public StringAttribute getContactName() {
		return this.contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName.setValue(contactName);
	}

	public StringAttribute getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number.setValue(number);
	}

	public void setContent(String content) {
		this.content.setValue(content);
	}

	public StringAttribute getContent() {
		return this.content;
	}

	public StringAttribute getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time.setValue(time);
	}

	public StringAttribute getState() {
		return this.state;
	}

	public StringAttribute getType() {
		return this.type;
	}

	public StringAttribute getSmsId() {
		return smsId;
	}

	public void setSmsId(StringAttribute smsId) {
		this.smsId = smsId;
	}

	public StringAttribute getIsIncoming() {
		return isIncoming;
	}

	public void setIsIncoming(StringAttribute isIncoming) {
		this.isIncoming = isIncoming;
	}
 
	private StringAttribute contactName;
	private StringAttribute number;
	private StringAttribute content;
	private StringAttribute time;
	private StringAttribute state;
	private StringAttribute type;
	private StringAttribute smsId;
	private StringAttribute isIncoming;
}
