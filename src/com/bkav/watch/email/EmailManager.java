package com.bkav.watch.email;

import android.os.Message;
import android.util.Log;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;
import com.bkav.home.data.Data;
import com.bkav.ui.watchactivity.WatchActivity;

public class EmailManager extends Component{

	public EmailManager(Component parent, String name) {
		super(parent, name);
		this.numberOfUnreadMsg = new StringAttribute(this, "numberOfUnreadsg");
		this.numberOfUnreadMsg.setValue("0");
		saveAttributes(); 
		add(new UpdateNumberEmail());
	}
	
	public StringAttribute getNumberOfUnreadMsg() {
		return numberOfUnreadMsg;
	}
	
	public void addEmail(String address, String time,
			String subject, String type, String idEmail) {
		Email email = (Email) this.getElement("emailName");
		if (email == null) {
			email = new Email(this, "email." + idEmail);
			email.getAddress().setValue(address);
			email.getTime().setValue(time);
			email.getSubject().setValue(subject);
			email.getMailType().setValue(type);
			email.getEmailID().setValue(idEmail);
			
			add(email);
			email.saveAttributes();
		} else {
			email.getAddress().setValue(address);
			email.getTime().setValue(time);
			email.getSubject().setValue(subject);
			email.getMailType().setValue(type);
			email.getEmailID().setValue(idEmail);
			email.saveAttributes();
		}
	}
	
	public void updateIncomingEmail(String emailName, String address, String time,
			String subject, String type, String idEmail){
		
	}
	
	public void setNumberOfUnreadMsg(int value) {
		numberOfUnreadMsg.setValue(String.valueOf(value));
		this.saveAttributes();
	}
	
	@Override
	protected Component createComponent(String name) {
		// TODO Auto-generated method stub
		if (name.startsWith("email.")) {
			return new Email(this, name);
		} else {
			return null;
		}
	}

	private StringAttribute numberOfUnreadMsg;
	
	private static class UpdateNumberEmail implements Element, Importable {
		 
		@Override
		public void importData(Data data) {
			String arg = data.toString();
			WatchActivity.sysManager.setNumberEmail(Integer.parseInt(arg));
			
			Message message = new Message();
			message.what = WatchActivity.UPDATE_EMAIL;
			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(message);
		}

		@Override
		public String getName() {
			return "UpdateNumberEmail";
		}
	}
}
