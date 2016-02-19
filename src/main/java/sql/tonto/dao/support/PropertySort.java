package sql.tonto.dao.support;

/**
 * 排序条件
 * 
 * @author xwzhou
 * @date 2014-12-5
 */
public class PropertySort {

	/** 降序 */
	public static final int SORT_DESC = 1;
	/** 升序 */
	public static final int SORT_ASC = 2;

	/**
	 * @param name
	 *            排序属性
	 * @param sort
	 *            排序方式 ASC/DESC
	 */
	public PropertySort(String name, int sort) {
		this.name = name;
		this.sort = sort;
	}

	String name;
	int sort;

	public String getName() {
		return name;
	}

	public int getSort() {
		return sort;
	}

}
