package com.bkav.watch.notification;

import android.os.Message;
import android.util.Log;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.component.Component;
import com.bkav.home.data.Data;
import com.bkav.home.data.DataParser;
import com.bkav.ui.watchactivity.WatchActivity;

public class NotificationManager extends Component {

	public NotificationManager(Component parent, String name) {
		super(parent, name);
		add(new NotifyMethod());
		saveAttributes(); 
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
		return change;
	}

	// @Override
	// protected Component createComponent(String name) {
	// if (name.startsWith("notify.")) {
	// return new Notify(this, name);
	// } else {
	// return null;
	// }
	// }

	private static class NotifyMethod implements Element, Importable {
		@Override
		public void importData(Data data) {
			 Log.e("Notify MAnager", "nhan duoc du lieu"); 
			 WatchActivity.sysManager.setIdNotify(data.getString("id"));
			 WatchActivity.sysManager.setContentNotify(data.getString("content"));
			 WatchActivity.sysManager.setBitmapNotify(data.getString("bitmap"));

			 Message msg = new Message();
			 msg.what = WatchActivity.COMING_NOTIFY;
			 WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(msg);
		}

		@Override
		public String getName() {
			return "NotifyMethod";
		}
	}
}
