package com.bkav.watch;

import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.util.Log;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.component.Component;
import com.bkav.home.component.ComponentException;
import com.bkav.home.component.StringAttribute;
import com.bkav.home.data.Data;
import com.bkav.home.lib.FileSystem;
import com.bkav.home.system.Platform;
import com.bkav.ui.sms.QuickSmsChoiceDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.CallsManager;
import com.bkav.watch.contact.ContactManager;
import com.bkav.watch.email.EmailManager;
import com.bkav.watch.event.EventManager;
import com.bkav.watch.health.HealthManager;
import com.bkav.watch.notification.NotificationManager;
import com.bkav.watch.sms.SmsManager;

public class Watch extends Component {

	public Watch() {
		super(null, "watch");
		ensureComponent("contact");
		ensureComponent("calls");
		ensureComponent("sms");
		ensureComponent("email");
		ensureComponent("health");
		ensureComponent("notify");
		ensureComponent("event");

		this.listClockStyles = new StringAttribute(this, "listClockStyles");
		listClockStyles.setValue("0%1%2%3");
		add(this.listClockStyles);
		this.saveAttributes();
		add(new UpdateMethod());
		add(new UpdateClockStyles());
		add(new GetAddress());
		add(new GetArrangeIcon());
	}

	public void resetData() {
		ensureComponent("contact");
		ensureComponent("calls");
		ensureComponent("sms");
		ensureComponent("email");
		ensureComponent("health");
		ensureComponent("notify");
		ensureComponent("event");
	}

	@Override
	public String getPath() {
		return "";
	}

	@Override
	public String getDirectory() {
		return FileSystem.getDataDirectory() + "/" + getName();
	}

	@Override
	public void process() {
		super.process();
//		 String change = exportChange();
	}

	public void build() {
		Platform.connection.write("{get:data}");
		System.out.println("build");

		Message messageUpdate = new Message();
		messageUpdate.what = WatchActivity.REQUEST_UPDATE;
		WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
				messageUpdate);
	}

	@Override
	protected Component createComponent(String name) {
		try {
			if ("calls".equals(name))
				return new CallsManager(this, name);
			else if ("sms".equals(name))
				return new SmsManager(this, name);
			else if ("contact".equals(name))
				return new ContactManager(this, name);
			else if ("health".equals(name))
				return new HealthManager(this, name);
			else if ("email".equals(name))
				return new EmailManager(this, name);
			else if ("notify".equals(name))
				return new NotificationManager(this, name);
			else if ("event".equals(name))
				return new EventManager(this, name);
			else
				throw new ComponentException("Unknown component " + name);

		} catch (ComponentException e) {
			return null;
		}
	}

	public StringAttribute getListClockStyles() {
		return listClockStyles;
	}

	public void setListClockStyles(String listClocksStyles) {
		this.listClockStyles.setValue(listClocksStyles);
	}

	private StringAttribute listClockStyles;

	private static class UpdateMethod implements Element, Importable {
		public UpdateMethod() {

		}

		@Override
		public String getName() {
			return "UpdateMethod";
		}

		@Override
		public void importData(Data data) {
			String arg = data.toString();

			System.out.println("TestMethod invoke: " + arg);
			if ("UPDATE_CALL".equals(arg)) {
				Log.e("Watch", "Receive UPDATE_CALL");
				Message messageUpdateCall = new Message();
				messageUpdateCall.what = WatchActivity.UPDATE_CALL;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateCall);
			} else if ("UPDATE_SMS".equals(arg)) {
				Log.e("Watch", "Receive UPDATE_SMS");
				Message messageUpdateSms = new Message();
				messageUpdateSms.what = WatchActivity.UPDATE_SMS;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateSms);
			} else if ("UPDATE_EMAIL".equals(arg)) {
				Log.e("Watch", "Receive UPDATE_EMAIL");
				Message messageUpdateCall = new Message();
				messageUpdateCall.what = WatchActivity.UPDATE_EMAIL;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateCall);
			} else if ("UPDATE_MISS_CALL".equals(arg)) {
				Log.e("Watch", "Receive UPDATE_MISS_CALL");
				Message messageUpdateCall = new Message();
				messageUpdateCall.what = WatchActivity.UPDATE_MISS_CALL_ICON;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateCall);
			} else if ("UPDATE_NEW_SMS".equals(arg)) {
				Log.e("Watch", "Receive UPDATE_NEW_SMS");
				Message messageUpdateCall = new Message();
				messageUpdateCall.what = WatchActivity.UPDATE_NEW_SMS_ICON;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateCall);
			} else if ("UPDATE_QUICK_SMS".equals(arg)) {
				Log.e("message handler", "Receive UPDATE_QUICK_SMS");
				Message messageUpdateQuickSms = new Message();
				messageUpdateQuickSms.what = QuickSmsChoiceDialog.UPDATE_QUICK_SMS;
				if (WatchActivity.sysManager.getMessageHandlerQuickSms() != null) {
					WatchActivity.sysManager.getMessageHandlerQuickSms()
							.sendMessage(messageUpdateQuickSms);
				} else {
					Log.e("Watch", "null");
				}
			} else if ("UPDATE_TIME".equals(arg)) {
				// Log.e("Watch", "Receive UPDATE_TIME");
				// Message messageUpdateClockStyles = new Message();
				// messageUpdateClockStyles.what = WatchActivity.UPDATE_TIME;
				// WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
				// messageUpdateClockStyles);
			} else if ("UPDATE_EVENTS".equals(arg)) {
				Log.e("Watch", "Receive UPDATE_EVENTS");
				Message messageUpdateEvent = new Message();
				messageUpdateEvent.what = WatchActivity.UPDATE_EVENTS;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateEvent);
			} else if ("NOTIFY".equals(arg)) {
				Log.e("", "");
			}
		}
	}

	private static class UpdateClockStyles implements Element, Importable {
		public UpdateClockStyles() {

		}

		@Override
		public String getName() {
			return "UpdateClockStyles";
		}

		@Override
		public void importData(Data data) {
			String arg = data.toString();
			Log.e("update list clock styles:", arg);
			WatchActivity.sysManager.setListStyles(arg);
			Message messageUpdateClockStyles = new Message();
			messageUpdateClockStyles.what = WatchActivity.UPDATE_CLOCK_STYLES;
			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
					messageUpdateClockStyles);
		}
	}

	private static class GetAddress implements Element, Importable {
		public GetAddress() {

		}

		@Override
		public String getName() {
			return "GetAddress";
		}

		@Override
		public void importData(Data data) {
			String arg = data.toString();
			Log.e("Watch", "address:" + arg);
			Log.e("Watch", WatchActivity.sysManager.getPreferences().getString("address", ""));
			if (!arg.equals(WatchActivity.sysManager.getPreferences().getString("address", ""))) {
				Editor editor = WatchActivity.sysManager.getPreferences()
						.edit();
				editor.putString("address", arg);
				editor.commit();
				Message messageReset= new Message();
				messageReset.what = WatchActivity.RESET_DATA;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageReset);
			}
		}
	}
	
	private static class GetArrangeIcon implements Element, Importable {
		public GetArrangeIcon() {
			
		}

		@Override
		public String getName() {
			return "GetArrangeIcon";
		}

		@Override
		public void importData(Data data) {
			String arg = data.toString();
			Log.e("Watch", "ArrangeIcon:" + arg);
			WatchActivity.sysManager.setmThumbIds(arg);
			Message messageUpdateArrangeIcon = new Message();
			messageUpdateArrangeIcon.what = WatchActivity.UPDATE_ARRANGE_ICON;
			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
					messageUpdateArrangeIcon);
			
		}
	}
}
