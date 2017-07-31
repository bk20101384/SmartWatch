package com.bkav.watch.event;

import com.bkav.home.component.Component;

public class EventManager extends Component {

	public EventManager(Component parent, String name) {
		super(parent, name);
	}

	@Override
	public void process() {
		super.process();
	}
	
	@Override
	protected Component createComponent(String name) {
		if (name.startsWith("event.")) {
			return new EventItem(this, name);
		} else {
			return null;
		}
	}
	
	
}
