package com.bkav.watch.contact;

import com.bkav.home.component.Component;

public class ContactManager extends Component{

	public ContactManager(Component parent, String name) {
		super(parent, name);
	} 
	
	public void addContact(String contactName, String contactID) {
		Contact objectContact = (Contact) this.getElement(contactName + "." + contactID);
		
		if (objectContact == null) { 
			objectContact = new Contact(this, contactName + "." + contactID);
			objectContact.getContactName().setValue(contactName);
			objectContact.getContactID().setValue(contactID);
			safeAdd(objectContact);
			objectContact.saveAttributes();    
		}
	}
	
//	public void process() {
//		super.process();
////		String change = exportChange();
////		if (change != null)
////			Log.e("contectmanager change:", change);
////			System.out.println(change);
//	}
//	
	@Override
	protected Component createComponent(String name) {
		return new Contact(this, name);
	}
}
