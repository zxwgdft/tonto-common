package com.tonto.common.util.statistics;

import java.lang.reflect.Field;


/**
 * 字段统计工具类
 * 
 * @author TontoZhou
 * 
 */
public class FieldStatistics{
	
	/**统计空属性*/
	private final static StatisticsRule statisticsEmpty;
	
	static{
		
		statisticsEmpty=new StatisticsRule(){

			@Override
			public boolean compare(Object fieldValue, Object object) {
				return fieldValue == null || "".equals(fieldValue);
			}
			
		};
		
	}
	
	
	/**
	 * 统计空的属性个数
	 * <p>
	 * NULL，空字符串都算空属性
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	public static StatisticsResult countEmpty(Object obj) {
		return countCustomized(obj,statisticsEmpty);		
	}
	
	/**
	 * 自定义规则统计
	 * @param obj
	 * @param rule
	 * @return
	 */
	public static StatisticsResult countCustomized(Object obj, StatisticsRule rule) {
		
		if (obj == null)
			return new StatisticsResult();

		Class<?> clazz = obj.getClass();

		int nullNum = 0;
		int totalNum = 0;

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.getAnnotation(NotStatistics.class) == null) {
				totalNum++;

				try {
					field.setAccessible(true);
					Object value = field.get(obj);
					if (rule.compare(value, obj))
						nullNum++;
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}

			}
		}

		return new StatisticsResult(nullNum, totalNum);
	}

	public static class StatisticsResult {

		// 空属性个数
		private int nullNum;

		// 总个数
		private int totalNum;

		public StatisticsResult() {
			
		}

		public StatisticsResult(int nullNum, int totalNum) {
			this.nullNum = nullNum;
			this.totalNum = totalNum;
		}

		public int getNullNum() {
			return nullNum;
		}

		public void setNullNum(int nullNum) {
			this.nullNum = nullNum;
		}

		public int getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(int totalNum) {
			this.totalNum = totalNum;
		}

	}
}
