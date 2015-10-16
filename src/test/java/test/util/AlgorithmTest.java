package test.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tonto.common.util.AlgorithmUtil;

public class AlgorithmTest {
	@Test
	public void sorttset()
	{
		String[] strs=new String[]{"第一","蝶儿","课啊"};
		
		List<String[]> sorts=AlgorithmUtil.getAllSortArray(strs);
		Assert.assertTrue(sorts!=null);
	}
}	
