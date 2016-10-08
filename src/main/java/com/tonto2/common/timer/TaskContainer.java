package com.tonto2.common.timer;

public class TaskContainer extends Thread {

	private Task[] tasks = new Task[100];
	private int i = 0;

	private Object lock = new Object();

	public void addTask(Task task) {
		
		synchronized (lock) {

			if (i >= tasks.length) {
				Task[] newTasks = new Task[tasks.length * 2];
				System.arraycopy(tasks, 0, newTasks, 0, i);
				tasks = newTasks;
			}

			tasks[i++] = task;

			lock.notifyAll();
		}

	}

	public void run() {

		Task[] ts = null;

		while (true) {
			
			synchronized (lock) {

				if (i <= 0)
					try {
						lock.wait();
					} catch (InterruptedException e) {
						continue;
					}

				ts = new Task[i];

				System.arraycopy(tasks, 0, ts, 0, i);

				i = 0;
			}

			if (ts != null && ts.length > 0) {
				for (Task t : ts)
					t.doTask();
			}

		}

	}

}
