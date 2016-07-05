package com.tonto.process;

/**
 * 处理节点
 * @author TontoZhou
 *
 */
public abstract class ProcessNode<T extends ProcessContext,E> {
	
	
	public abstract void init();
	
	public abstract E process(T context);
	
}
