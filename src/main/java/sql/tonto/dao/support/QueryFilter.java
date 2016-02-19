package sql.tonto.dao.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询过滤
 * 
 * @author xwzhou
 * @date 2014-12-5
 */
public class QueryFilter {
	List<PropertyFilter> propertyFilters;
	List<PropertySort> propertySorts;

	public QueryFilter() {
		propertyFilters = new ArrayList<PropertyFilter>(2);
		propertySorts = new ArrayList<PropertySort>(1);
	}

	public QueryFilter(String name, Object value) {
		propertyFilters = new ArrayList<PropertyFilter>(2);
		addPropertyFilter(name, value);
		propertySorts = new ArrayList<PropertySort>(1);
	}

	public QueryFilter(String name, Object value, int operation, int relation) {
		propertyFilters = new ArrayList<PropertyFilter>(2);
		addPropertyFilter(new PropertyFilter(name, value, operation, relation));
		propertySorts = new ArrayList<PropertySort>(1);
	}

	public QueryFilter(String name, Object value, String sortName, int sort) {
		propertyFilters = new ArrayList<PropertyFilter>(2);
		propertySorts = new ArrayList<PropertySort>(1);

		addPropertyFilter(name, value);
		addPropertySort(new PropertySort(sortName, sort));
	}

	public QueryFilter(String name, Object value, int operation, int relation,
			String sortName, int sort) {
		propertyFilters = new ArrayList<PropertyFilter>(2);
		propertySorts = new ArrayList<PropertySort>(1);

		addPropertyFilter(new PropertyFilter(name, value, operation, relation));
		addPropertySort(new PropertySort(sortName, sort));
	}

	/**
	 * 增加一个默认操作为equal,关系为and的查询过滤条件
	 * 
	 * @param name
	 *            过滤的属性名
	 * @param value
	 *            过滤的值
	 */
	public void addPropertyFilter(String name, Object value) {
		addPropertyFilter(new PropertyFilter(name, value,
				PropertyFilter.OPERATION_EQUAL, PropertyFilter.RELATION_AND));
	}

	/**
	 * 增加一个查询过滤条件
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
	public void addPropertyFilter(String name, Object value, int operation,
			int relation) {
		addPropertyFilter(new PropertyFilter(name, value, operation, relation));
	}

	/**
	 * 增加一个查询过滤条件
	 * 
	 * @param filter
	 *            {@link PropertyFilter}
	 */
	public void addPropertyFilter(PropertyFilter filter) {
		if (filter != null && filter.relation != PropertyFilter.RELATION_NULL)
			propertyFilters.add(filter);
	}

	/**
	 * 增加多个查询过滤条件
	 * 
	 * @param filters
	 *            {@link PropertyFilter}
	 */
	public void addPropertyFilter(List<PropertyFilter> filters) {
		for (PropertyFilter filter : filters) {
			addPropertyFilter(filter);
		}
	}

	/**
	 * 增加一个排序条件
	 * 
	 * @param name
	 *            排序属性名
	 * @param sort
	 *            排序方式
	 */
	public void addPropertySort(String name, int sort) {
		addPropertySort(new PropertySort(name, sort));
	}

	/**
	 * 增加一个排序条件
	 * 
	 * @param sort
	 */
	public void addPropertySort(PropertySort sort) {
		if (sort != null)
			propertySorts.add(sort);
	}

	/**
	 * 增加多个排序条件
	 * 
	 * @param sorts
	 *            {@link PropertySort}
	 */
	public void addPropertySort(List<PropertySort> sorts) {
		if (sorts != null)
			propertySorts.addAll(sorts);
	}

	/**
	 * 是否要查询条件过滤
	 * 
	 * @return
	 */
	public boolean havePropertyFilter() {
		return propertyFilters.size() != 0;
	}

	/**
	 * 是否要排序
	 * 
	 * @return
	 */
	public boolean havePropertySort() {
		return propertySorts.size() != 0;
	}

}
