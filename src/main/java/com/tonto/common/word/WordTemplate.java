package com.tonto.common.word;

import java.io.OutputStream;

public interface WordTemplate {
	/**
	 * 输出word模板
	 * @param out
	 */
	public void write(OutputStream out);
	
}
