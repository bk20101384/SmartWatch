package com.bkav.watch.calls;

import android.os.Message;
import android.util.Log;

import com.bkav.home.common.Element;
import com.bkav.home.common.Importable;
import com.bkav.home.component.Component;
import com.bkav.home.component.StringAttribute;
import com.bkav.home.data.Data;
import com.bkav.ui.call.CallDialog;
import com.bkav.ui.watchactivity.WatchActivity;

public class CallsManager extends Component {
	
	public CallsManager(Component parent, String name) {
		super(parent, name);
		this.missCallCount = new StringAttribute(this, "missCallCount");
//		missCallCount.setValue("3");
		add(this.missCallCount);
		this.saveAttributes();
		add(new CallMethod());  
		incomingCall = new Call(this, "incomingCall");
		incomingCall.getContactName().setValue("SubcriberTest");
		incomingCall.getNumber().setValue("01674599999");
		incomingCall.getState().setValue("1");
		incomingCall.getTime().setValue("12h00");
		incomingCall.getDuration().setValue("60s");
		incomingCall.getCallId().setValue("0"); 
		incomingCall.getIsIncomming().setValue("true");
		add(incomingCall);
		incomingCall.saveAttributes();
	}

	@Override
	public void process() {
		super.process();
	}

	@Override
	public String exportChange() {
		String change = super.exportChange();
		if (change != null) {
			Log.e("", "export change call manager");
		}
		return change;
	}

	public StringAttribute getMissCallCount() {
		return missCallCount;
	}

	public Call getIncomingCall() {
		return incomingCall;
	}

	@Override
	protected Component createComponent(String name) {
		if (name.startsWith("call.")) {
			return new Call(this, name);
		} else {
			return null;
		}
	}

	private StringAttribute missCallCount;
	private Call incomingCall;

	private static class CallMethod implements Element, Importable {
 
		@Override
		public void importData(Data data) {
			String arg = data.toString();
			if ("COMING_CALL".equals(arg)) {
				Log.e("Calls Manager", "Receive COMING_CALL");
				Message message = new Message();
				message.what = WatchActivity.COMING_CALL;
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(message);
			} else if ("END_CALL".equals(arg)) {
				Log.e("Calls Manager", "Receive END_CALL");  
				Message message = new Message();
				message.what = CallDialog.END_CALL;
				if (WatchActivity.sysManager.getMessageHandlerCallDialog() != null) {
					WatchActivity.sysManager.getMessageHandlerCallDialog().sendMessage(message);
				}
			}
		}

		@Override
		public String getName() {
			return "CallMethod";
		}
	}
}
