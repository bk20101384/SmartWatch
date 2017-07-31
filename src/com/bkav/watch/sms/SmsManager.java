package com.bkav.watch.sms;

import android.os.Message;
import android.util.Log;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;
import com.bkav.home.data.Data;
import com.bkav.ui.watchactivity.WatchActivity;

public class SmsManager extends Component {

	public SmsManager(Component parent, String name) {
		super(parent, name);
		this.missCountSms = new StringAttribute(this, "NumberOfUnreadSMS");
		missCountSms.setValue("0");
		this.incomingSms =  new Sms(this, "inComingSms");
		
		incomingSms.getContactName().setValue("SubcriberTest");
		incomingSms.getNumber().setValue("01674599999");
		incomingSms.getState().setValue("1");
		incomingSms.getTime().setValue("12h00");
		incomingSms.getContent().setValue("Test có tin nhắn mới.");
		incomingSms.getSmsId().setValue("0"); 
		incomingSms.getIsIncoming().setValue("true");
		
		this.listQuickSms = new StringAttribute(this, "listQuickSms");		
		add(this.missCountSms); 
		add(this.incomingSms);
		add(this.listQuickSms);
		add(new SmsMethod());
		add(new UpdateNumberSms());
	}

	@Override
	public void process() {
		super.process();
	}
	
	@Override
	public String exportChange() {
		String change = super.exportChange();
		if (change != null) {
			Log.e("", "export change sms manager");
		}
//		if (change != null) {   
//			Log.e("Sms Manager", "UPDATE_SMS");
//			Message msgUpdateSms = new Message();
//			msgUpdateSms.what = WatchActivity.UPDATE_SMS;
//			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(msgUpdateSms);
//		}
		return change;
	}

	@Override
	protected Component createComponent(String name) {
		if (name.startsWith("sms.")) {
			return new Sms(this, name);
		} else {
			return null;
		}
	}

	public StringAttribute getMissCountSms() {
		return missCountSms;
	}

	public Sms getIncomingSms() {
		return incomingSms;
	}
	
	public StringAttribute getListQuickSms() {
		return listQuickSms;
	}

	private StringAttribute missCountSms; 
	private Sms incomingSms;
	private StringAttribute listQuickSms;
	
	private static class SmsMethod implements Element, Importable {
 
		@Override
		public void importData(Data data) {
			String arg = data.toString();
			if ("COMING_SMS".equals(arg)) {
				Log.e("SmsManager", "Receive COMING_SMS");
				Message message = new Message();
				message.what = WatchActivity.COMING_SMS;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(message);
			}
		}

		@Override
		public String getName() {
			return "SmsMethod";
		}
	}
	
	private static class UpdateNumberSms implements Element, Importable {
		 
		@Override
		public void importData(Data data) {
			String arg = data.toString();
			WatchActivity.sysManager.setNumberSms(Integer.parseInt(arg));
			
			Message messageUpdateSms = new Message();
			messageUpdateSms.what = WatchActivity.UPDATE_SMS;
			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
					messageUpdateSms);
		}

		@Override
		public String getName() {
			return "UpdateNumberSms";
		}
	}
}
