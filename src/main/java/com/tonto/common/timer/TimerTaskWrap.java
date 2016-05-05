package com.tonto.common.timer;

import java.util.TimerTask;

/**
 * 唯一任务定时任务，继承此类来与{@link java.util.Timer}兼容
 * @author TontoZhou
 *
 */
public abstract class TimerTaskWrap extends TimerTask{
	
	private Task task;
	
	public TimerTaskWrap(Task task){
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
}
