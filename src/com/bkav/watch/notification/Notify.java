package com.bkav.watch.notification;

import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;

public class Notify extends Component {

	public Notify(Component parent, String name) {
		super(parent, name);
		this.id = new StringAttribute(this, "id");
		this.content = new StringAttribute(this, "content");
		add(this.id);
		add(this.content);
	}

	public StringAttribute getId() {
		return id;
	}

	public void setId(StringAttribute id) {
		this.id = id;
	}

	public StringAttribute getContent() {
		return content;
	}

	public void setContent(StringAttribute content) {
		this.content = content;
	}

	private StringAttribute id;
	private StringAttribute content;
	
}
