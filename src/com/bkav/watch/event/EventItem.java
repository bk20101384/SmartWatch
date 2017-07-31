package com.bkav.watch.event;

import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class EventItem extends Component {

	public EventItem(Component parent, String name) {
		super(parent, name);
		this.content = new StringAttribute(parent, "content");
		this.startTime = new StringAttribute(parent, "startTime");
		this.endTime = new StringAttribute(parent, "endTime");
		
		add(this.content);
		add(this.startTime);
		add(this.endTime);
	}
	
	public StringAttribute getContent() {
		return content;
	}
	
	public void setContent(StringAttribute content) {
		this.content = content;
	}
	

	public StringAttribute getStartTime() {
		return startTime;
	}

	public void setStartTime(StringAttribute startTime) {
		this.startTime = startTime;
	}


	public StringAttribute getEndTime() {
		return endTime;
	}

	public void setEndTime(StringAttribute endTime) {
		this.endTime = endTime;
	}


	private StringAttribute content;
	private StringAttribute startTime;
	private StringAttribute endTime;
}
