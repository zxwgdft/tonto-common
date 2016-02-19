package sql.tonto.dao.support;

import java.util.Collection;

/**
 * 查询过滤条件
 * 
 * @author xwzhou
 * @date 2014-12-5
 */
@SuppressWarnings("rawtypes")
public class PropertyFilter {

	private static int num = 0;

	private static int getNumber2Name() {
		if (num > 0xffff)
			num = 0;
		return num++;
	}

	/**
	 * 
	 * @param name
	 *            过滤的属性名
	 * @param value
	 *            过滤的值
	 * @param operation
	 *            过滤操作
	 * @param relation
	 *            过滤关系 OR/AND
	 */
	public PropertyFilter(String name, Object value, int operation, int relation) {
		this.name = name;
		// 去除.，防止hql语句错误
		this.pname = name.replaceAll("\\.", "") + getNumber2Name();

		this.relation = relation;
		this.operation = operation;

		if (operation == OPERATION_IS_NULL
				|| operation == OPERATION_IS_NOT_NULL) {
			this.value = null;
			return;
		}

		// 在不是判断null或者not null操作下value为null，则该过滤条件无意义
		if (value == null || "".equals(value.toString().trim())) {
			this.relation = RELATION_NULL;
			return;
		}

		// 如果为集合，则集合size为0则会报错，是否在这里判断
		if (value instanceof Collection) {
			Collection collValue = (Collection) value;
			if (collValue.size() == 0) {
				this.relation = RELATION_NULL;
				return;
			}
		}

		switch (operation) {
		case OPERATION_LIKE:
			value = "%" + value + "%";
			break;
		case OPERATION_LIKE_FRONT:
			value = "%" + value;
			break;
		case OPERATION_LIKE_BACK:
			value = value + "%";
			break;
		}

		this.value = value;
	}

	String name; // 参数名
	Object value; // 参数值
	int operation = OPERATION_EQUAL; // 操作
	String pname; // hql语句中变量名

	int relation; // 过滤关系

	/** 与关系 */
	public static final int RELATION_AND = 1;
	/** 或关系 */
	public static final int RELATION_OR = 2;
	/** 无关系，忽略该过滤条件 */
	public static final int RELATION_NULL = 3;

	/** 等于 = */
	public static final int OPERATION_EQUAL = 1;
	/** 不等于 <>或者!= */
	public static final int OPERATION_NOT_EQUAL = 2;
	/** 大于 > */
	public static final int OPERATION_GREATER = 3;
	/** 小于 < */
	public static final int OPERATION_LESS = 4;
	/** 大于等于 >= */
	public static final int OPERATION_GREATER_OR_EQUAL = 5;
	/** 小于等于 <= */
	public static final int OPERATION_LESS_OR_EQUAL = 6;
	/** 前后模糊查询 */
	public static final int OPERATION_LIKE = 7;
	/** 前模糊查询, like '%内容' */
	public static final int OPERATION_LIKE_FRONT = 8;
	/** 后模糊查询, like '内容%' */
	public static final int OPERATION_LIKE_BACK = 9;
	/** 在里面 in */
	public static final int OPERATION_IN = 10;
	/** 为空 is null */
	public static final int OPERATION_IS_NULL = 11;
	/** 为空 is not null */
	public static final int OPERATION_IS_NOT_NULL = 12;

	public String getName() {
		return name;
	}

	public int getOperation() {
		return operation;
	}

	public Object getValue() {
		return value;
	}

	public String getPname() {
		return pname;
	}

	public int getRelation() {
		return relation;
	}
}
