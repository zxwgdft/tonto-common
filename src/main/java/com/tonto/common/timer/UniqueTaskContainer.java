package com.tonto.common.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;


/**
 * 
 * 唯一性任务容器
 * <p>根据唯一KEY保存任务，当创建相同KEY任务时，之前的任务会被取消</p>
 * <p>线程完全</p>
 * @author TontoZhou
 *
 */
public class UniqueTaskContainer {
		
	/**
	 * 任务集合
	 */
	private Map<Object,TimerTaskWrap> taskMap=new HashMap<>();
	
	/**
	 * 创建唯一性的任务
	 * @param uniqueKey
	 * @param task
	 * @return
	 */
	public TimerTask createTask(final Object uniqueKey,final Task task)
	{		
		if(uniqueKey==null)
			throw new NullPointerException();
		
		synchronized(taskMap)
		{
			TimerTaskWrap timerTask=taskMap.get(uniqueKey);
			
		    if(timerTask!=null)
		    	timerTask.cancel();
					
			timerTask=new TimerTaskWrap(task){
	
				@Override
				public void run() {
					task.doTask();
					taskMap.remove(uniqueKey);
				}
				
			};
			
			taskMap.put(uniqueKey, timerTask);
			
			return timerTask;
		}
		
	}

	/**
	 * 获取任务
	 * @param key
	 * @return
	 */
	public TimerTaskWrap getTask(Object key){
		synchronized(taskMap)
		{
			return taskMap.get(key);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Map<Object, TimerTaskWrap> getTaskMap() {
		return taskMap;
	}
	
	
}


