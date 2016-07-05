package com.tonto.process;

/**
 * 处理
 * @author TontoZhou
 *
 */
public interface Process<T> {
	
	/**
	 * 处理
	 * @param invocation 调用上下文
	 */
	public T process(ProcessInvocation<T> invocation);
	
}
