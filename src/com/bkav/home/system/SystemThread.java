package com.bkav.home.system;

import java.util.Date;
import java.util.LinkedList;

import android.util.Log;

import com.bkav.home.component.Component;
import com.bkav.home.common.Process;

public class SystemThread extends Thread {
	public SystemThread(SystemContext context, Component system) {
		this.context = context;
		this.system = system;
		this.pendingProcesses = new LinkedList<Process>();
	}

	public synchronized void notify(Process process) {
		if (process != null)
			this.pendingProcesses.add(process);
		notify();
	}

	public synchronized void check(Process process) {
		this.pendingProcesses.add(process);
	}

	@Override
	public void run() {
		try {
			this.context.update();
			while (!isInterrupted()) {
				synchronized (this) {
					this.context.update();
					this.system.process();

					while (!this.pendingProcesses.isEmpty()) {
						Process process = this.pendingProcesses.remove();
						process.process();
					}

					long next = this.context.getNext();
					if (next < 0)
						wait();
					else if (next == 0)
						wait(1);
					else {
						long current = new Date().getTime();
						if (next > current)
							wait(next - current);
						else
							wait(1);
					}
				}
			}
		} catch (InterruptedException e) {
			Log.e("Interrupt1", "aaaaa");
		}
	}

	private SystemContext context;
	private Component system;
	private LinkedList<Process> pendingProcesses;
}
