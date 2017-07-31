package com.bkav.watch.health;

import android.os.Message;
import android.util.Log;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;
import com.bkav.home.data.Data;
import com.bkav.ui.pedometer.PedometerScreen;
import com.bkav.ui.watchactivity.WatchActivity;

public class HealthManager extends Component {

	public HealthManager(Component parent, String name) {
		super(parent, name);
		add(new GetWeightMethod());
		add(new GetHeightMethod());
	}

	private static class GetHeightMethod implements Element, Importable {

		@Override
		public void importData(Data data) {
			String arg = data.toString();
			PedometerScreen.height = Double.parseDouble(arg);
			Log.e("", "set height:" + Double.parseDouble(arg));
			Message msg = new Message();
			msg.what = WatchActivity.UPDATE_PERSON;
			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(msg);
		}

		@Override
		public String getName() {
			return "GetHeightMethod";
		}
	}

	private static class GetWeightMethod implements Element, Importable {

		@Override
		public void importData(Data data) {
			String arg = data.toString();
			PedometerScreen.weight = Double.parseDouble(arg);
			Log.e("", "set weight" + Double.parseDouble(arg));
			Message msg = new Message();
			msg.what = WatchActivity.UPDATE_PERSON;
			WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(msg);
		}

		@Override
		public String getName() {
			return "GetWeightMethod";
		}
	}
}
