package com.bkav.home.component;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.data.Data;

public class ComponentList extends Component {

	public ComponentList(Component parent, String name) {
		super(parent, name);
		add(new RemoveMethod());
	}

	private class RemoveMethod implements Element, Importable {
		public RemoveMethod() {
			
		}

		@Override
		public String getName() {
			return "remove";
		}

		@Override
		public void importData(Data data) {
			while (data != null) {
				String name = data.toString();
				Element element = getElement(name);
				if (element != null) {
					safeRemove(element);
				}				
				data = data.getNext();
			}
		}
	}
}
