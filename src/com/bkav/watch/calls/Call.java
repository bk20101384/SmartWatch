package com.bkav.watch.calls;

import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class Call extends Component{

	public Call(Component parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
		this.contactName = new StringAttribute(this, "contactName");
		this.number = new StringAttribute(this, "number");
		this.time = new StringAttribute(this, "time");
		this.state = new StringAttribute(this, "state");
		this.duration = new StringAttribute(this, "duration");
		this.isIncoming =  new StringAttribute(this, "isIncoming");
		this.callId = new StringAttribute(this, "callId");
		
		add(this.contactName);  
		add(this.number);
		add(this.time);
		add(this.state);
		add(this.duration);
		add(this.callId);
		add(this.isIncoming);
		
	}
	
	public StringAttribute getContactName() {
		return contactName;
	}
	
	public StringAttribute getNumber() {
		return number;
	}
	public StringAttribute getTime() {
		return time;
	}
	public StringAttribute getState() {
		return state;
	}
	public StringAttribute getDuration() {
		return duration;
	}

	public StringAttribute getCallId() {
		return callId;
	}

	public void setCallId(StringAttribute callId) {
		this.callId = callId;
	}

	public StringAttribute getIsIncomming() {
		return isIncoming;
	}

	public void setIsInComming(StringAttribute isInComming) {
		this.isIncoming = isInComming;
	}

	private StringAttribute contactName;
	private StringAttribute number;
	private StringAttribute time;
	private StringAttribute state;
	private StringAttribute duration;
	private StringAttribute callId;
	private StringAttribute isIncoming;
}
