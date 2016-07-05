package com.tonto.process;

import java.util.List;


/**
 * 处理调用类
 * @author TontoZhou
 *
 */
public abstract class ProcessInvocation<T> {
	
	/**
	 * 处理流程列表
	 * <p>暂时定为顺序列表，又需要可改为树状，网状</p>
	 */
	private List<Process<T>> processes;
	
	/**
	 * 当前处理节点序号
	 */
	private int currentProcessIndex;
	
	
	/**
	 * 处理流程调用
	 * @return
	 */
	public T invoke(){
		
		if(currentProcessIndex < processes.size())
		{
			Process<T> process = processes.get(currentProcessIndex++);
			return process.process(this);
		}
		
		return null;
	}

	public List<Process<T>> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process<T>> processes) {
		this.processes = processes;
	}
	
	
}
