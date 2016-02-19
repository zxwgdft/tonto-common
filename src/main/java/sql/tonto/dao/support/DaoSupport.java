package sql.tonto.dao.support;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sql.tonto.dao.support.annotation.Find;
import sql.tonto.dao.support.annotation.FindTable;


/**
 * <h2>DAO层辅助支持类</h2>
 * 
 * <h3>问题</h3>
 * <p>
 * 由于SQL语句中AND OR执行时候是有优先级的，而该支持类不支持 括号写法，所以有些关系无法表达。例如(A=3 or B=4) and C=0 和A=3
 * or B=4 and C=0 是完全不同的，所以在使用OR的时候需要 注意添加条件的顺序，如果必须使用括号的则应该在相应DAO中写HQL语句
 * </p>
 * 
 * <p>
 * 如果要符合所有情况，引入括号或者其他SQL上的写法势必会令这个类复杂和难用
 * ，而在系统中大多数SQL语句都是AND关系的多条件比较，所以可以考虑放弃复杂情况 从而让该类尽量简单。
 * </p>
 * 
 * @author xwzhou
 * @date 2014-12-5
 */
@SuppressWarnings("rawtypes")
public abstract class DaoSupport {
	
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	//----------------------------------
	//
	//----------------------------------
	
	@SuppressWarnings("unchecked")
	public <T> T findOne(Class<T> clazz, Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <T> T findOne(Class<T> clazz, QueryFilter queryFilter) {
		String table = clazz.getSimpleName();
		return (T) findUniqueObject(table, queryFilter);
	}

	private Object findUniqueObject(String table, QueryFilter queryFilter) {
		String hql = buildSortHql(table, queryFilter);
		Query query = getSession().createQuery(hql);
		setFilterParameter(queryFilter, query);
		return query.uniqueResult();
	}

	/**
	 * <p>
	 * 通过Annotation注释的类作为搜索类
	 * </p>
	 * <p>
	 * 通过{@link FindTable}注释查找要搜索的表，通过{@link Find}注释产生查询过滤条件
	 * </p>
	 * 
	 * @param searchDto
	 * @return
	 */

	public List search(Object searchDto) {
		Class clazz = searchDto.getClass();
		String table = getTableByAnnotation(clazz);
		QueryFilter queryFilter = getQueryFilterByAnnotation(searchDto);
		return findAllObjects(table, queryFilter);
	}

	/**
	 * <p>
	 * 通过Annotation注释的类作为搜索类
	 * </p>
	 * <p>
	 * 通过{@link FindTable}注释查找要搜索的表，通过{@link Find}注释产生查询过滤条件
	 * </p>
	 * 
	 * @param searchDto
	 * @param sorts
	 *            没有则设为null
	 * @return
	 */
	public List search(Object searchDto, List<PropertySort> sorts) {
		Class clazz = searchDto.getClass();
		String table = getTableByAnnotation(clazz);
		QueryFilter queryFilter = getQueryFilterByAnnotation(searchDto);
		queryFilter.addPropertySort(sorts);
		return findAllObjects(table, queryFilter);
	}

	/**
	 * <p>
	 * 通过Annotation注释的类作为搜索类
	 * </p>
	 * <p>
	 * 通过{@link FindTable}注释查找要搜索的表，通过{@link Find}注释产生查询过滤条件
	 * </p>
	 * 
	 * @param searchDto
	 * @param sort
	 *            没有则设为null
	 * @return
	 */
	public List search(Object searchDto, PropertySort sort) {
		Class clazz = searchDto.getClass();
		String table = getTableByAnnotation(clazz);
		QueryFilter queryFilter = getQueryFilterByAnnotation(searchDto);
		queryFilter.addPropertySort(sort);
		return findAllObjects(table, queryFilter);
	}

	/**
	 * <p>
	 * 通过Annotation注释的类作为搜索类
	 * </p>
	 * <p>
	 * 通过{@link FindTable}注释查找要搜索的表，通过{@link Find}注释产生查询过滤条件
	 * </p>
	 * 
	 * @param searchDto
	 * @param pageFilter
	 * @return
	 */
	public PageResult search(Object searchDto, PageFilter pageFilter) {
		Class clazz = searchDto.getClass();
		String table = getTableByAnnotation(clazz);
		QueryFilter queryFilter = getQueryFilterByAnnotation(searchDto);
		return findAllObjects(table, queryFilter, pageFilter);
	}

	/**
	 * <p>
	 * 通过Annotation注释的类作为搜索类
	 * </p>
	 * <p>
	 * 通过{@link FindTable}注释查找要搜索的表，通过{@link Find}注释产生查询过滤条件
	 * </p>
	 * 
	 * @param searchDto
	 * @param pageFilter
	 * @param sorts
	 *            没有则设为null
	 * @return
	 */
	public PageResult search(Object searchDto, List<PropertySort> sorts,
			PageFilter pageFilter) {
		Class clazz = searchDto.getClass();
		String table = getTableByAnnotation(clazz);
		QueryFilter queryFilter = getQueryFilterByAnnotation(searchDto);
		queryFilter.addPropertySort(sorts);
		return findAllObjects(table, queryFilter, pageFilter);
	}

	/**
	 * <p>
	 * 通过Annotation注释的类作为搜索类
	 * </p>
	 * <p>
	 * 通过{@link FindTable}注释查找要搜索的表，通过{@link Find}注释产生查询过滤条件
	 * </p>
	 * 
	 * @param searchDto
	 * @param pageFilter
	 * @param sort
	 *            没有则设为null
	 * @return
	 */
	public PageResult search(Object searchDto, PropertySort sort,
			PageFilter pageFilter) {
		Class clazz = searchDto.getClass();
		String table = getTableByAnnotation(clazz);
		QueryFilter queryFilter = getQueryFilterByAnnotation(searchDto);
		queryFilter.addPropertySort(sort);
		return findAllObjects(table, queryFilter, pageFilter);
	}
	
	/**
	 * 查找所有记录
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz) {
		return getSession().createQuery("from " + clazz.getSimpleName()).list();
	}
	
	/**
	 * 查找所有符合条件记录
	 * @param clazz
	 * @param queryFilter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz, QueryFilter queryFilter) {
		String table = clazz.getSimpleName();
		return findAllObjects(table, queryFilter);
	}

	private List findAllObjects(String table, QueryFilter queryFilter) {
		String hql = buildSortHql(table, queryFilter);
		Query query = getSession().createQuery(hql);
		setFilterParameter(queryFilter, query);
		return query.list();
	}
	
	/**
	 * 分页查找
	 * @param clazz
	 * @param queryFilter
	 * @param pageFilter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> findAll(Class<T> clazz, QueryFilter queryFilter,
			PageFilter pageFilter) {
		String table = clazz.getSimpleName();
		return findAllObjects(table, queryFilter, pageFilter);
	}
	
	/**
	 * 分页查找
	 * @param clazz
	 * @param pageFilter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> findAll(Class<T> clazz, PageFilter pageFilter) {
		String hql = "from" + clazz.getSimpleName();
		
		Session session = getSession();
		Query countQuery = session.createQuery("select count(*) "+hql);
		int rowcount = ((Long) countQuery.uniqueResult()).intValue();

		pageFilter.setRowCount(rowcount);
		Query query = session.createQuery(hql);
		query.setFirstResult(pageFilter.firstPosition);
		query.setMaxResults(pageFilter.pageSize);
		List<T> data = query.list();
		return PageResult.build(pageFilter, data);
	}
	
	@SuppressWarnings("unchecked")
	private PageResult findAllObjects(String table, QueryFilter queryFilter,
			PageFilter pageFilter) {
		Session session = getSession();

		String hql = buildBaseHql(table, queryFilter);

		// 获取记录数
		String countHql = buildCount2Hql(hql);
		Query countQuery = session.createQuery(countHql);
		setFilterParameter(queryFilter, countQuery);
		int rowcount = ((Long) countQuery.uniqueResult()).intValue();

		pageFilter.setRowCount(rowcount);

		String resultHql = buildSort2Hql(hql, queryFilter);
		Query query = session.createQuery(resultHql);
		setFilterParameter(queryFilter, query);
		query.setFirstResult(pageFilter.firstPosition);
		query.setMaxResults(pageFilter.pageSize);

		List data = query.list();
		return PageResult.build(pageFilter, data);
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public Serializable save(Object object) {
		return getSession().save(object);
	}
	
	/**
	 * 
	 * @param object
	 */
	public void update(Object object) {
		getSession().update(object);
	}
	
	/**
	 * 
	 * @param object
	 */
	public void delete(Object object) {
		getSession().delete(object);
	}

	/**
	 * 删除表中符合查询条件的记录
	 * 
	 * @param clazz
	 * @param queryFilter
	 * @return 删除记录数
	 */
	public int delete(Class<?> clazz, QueryFilter queryFilter) {
		return delete(clazz.getSimpleName(), queryFilter);
	}

	/**
	 * 删除表中符合查询条件的记录
	 * 
	 * @param table
	 * @param queryFilter
	 * @return 删除记录数
	 */
	public int delete(String table, QueryFilter queryFilter) {
		// 获取记录数
		String deleteHql = buildDeleteHql(table, queryFilter);
		Query query = getSession().createQuery(deleteHql);
		setFilterParameter(queryFilter, query);
		return query.executeUpdate();
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public Object merge(Object object) {
		return getSession().merge(object);
	}
	
	/**
	 * 是否存在
	 * @param clazz
	 * @param id
	 * @return
	 */
	public boolean exists(Class<?> clazz, Serializable id) {
		Object object = findOne(clazz, id);
		return object != null;
	}
	
	/**
	 * 获取存在的记录数
	 * @param clazz
	 * @param queryFilter
	 * @return
	 */
	public int getRowCount(Class<?> clazz, QueryFilter queryFilter) {
		String countHql = buildCountHql(clazz.getSimpleName(), queryFilter);
		Session session = getSession();
		return ((Long) session.createQuery(countHql).uniqueResult()).intValue();
	}

	private String buildSortHql(String table, QueryFilter queryFilter) {
		String hql = buildBaseHql(table, queryFilter);
		return buildSort2Hql(hql, queryFilter);
	}

	private String buildSort2Hql(String hql, QueryFilter queryFilter) {
		if (queryFilter.havePropertySort()) {
			List<PropertySort> sorts = queryFilter.propertySorts;
			StringBuilder sb = new StringBuilder(hql);
			boolean isFirst = true;

			for (PropertySort sort : sorts) {
				if (isFirst) {
					sb.append(" order by ");
					isFirst = false;
				} else {
					sb.append(" and ");
				}

				sb.append(sort.name);
				int sortType = sort.sort;

				if (sortType == PropertySort.SORT_ASC) {
					sb.append(" asc");
				} else if (sortType == PropertySort.SORT_DESC) {
					sb.append(" desc");
				} else {
					throw new RuntimeException("排序方式不存在：" + sortType);
				}

			}

			return sb.toString();
		} else {
			return hql;
		}

	}

	private String buildCountHql(String table, QueryFilter queryFilter) {
		return buildCount2Hql(buildBaseHql(table, queryFilter));
	}

	private String buildCount2Hql(String hql) {
		return "select count(*) " + hql;
	}

	private String buildDeleteHql(String table, QueryFilter queryFilter) {
		return "delete " + buildBaseHql(table, queryFilter);
	}

	private String buildBaseHql(String table, QueryFilter queryFilter) {
		StringBuilder sb = new StringBuilder();

		sb.append("from ").append(table);

		if (queryFilter.havePropertyFilter()) {
			boolean isFirst = true;
			List<PropertyFilter> propertyFilters = queryFilter.propertyFilters;

			for (PropertyFilter propertyFilter : propertyFilters) {

				String name = propertyFilter.getName();
				String pname = propertyFilter.getPname();
				int relation = propertyFilter.relation;

				if (isFirst) {
					sb.append(" where ");
					isFirst = false;
				} else {

					if (relation == PropertyFilter.RELATION_AND) {
						sb.append(" and ");
					} else if (relation == PropertyFilter.RELATION_OR) {
						sb.append(" or ");
					} else {
						throw new RuntimeException("查询过滤条件关系不存在：" + relation);
					}
				}
				sb.append(name);

				int operation = propertyFilter.getOperation();

				switch (operation) {
				case PropertyFilter.OPERATION_EQUAL:
					sb.append(" =:").append(pname);
					break;
				case PropertyFilter.OPERATION_NOT_EQUAL:
					sb.append(" <>:").append(pname);
					break;
				case PropertyFilter.OPERATION_GREATER:
					sb.append(" >:").append(pname);
					break;
				case PropertyFilter.OPERATION_GREATER_OR_EQUAL:
					sb.append(" >=:").append(pname);
					break;
				case PropertyFilter.OPERATION_LESS:
					sb.append(" <:").append(pname);
					break;
				case PropertyFilter.OPERATION_LESS_OR_EQUAL:
					sb.append(" <=:").append(pname);
					break;
				case PropertyFilter.OPERATION_LIKE:
					sb.append(" like :").append(pname);
					break;
				case PropertyFilter.OPERATION_LIKE_FRONT:
					sb.append(" like :").append(pname);
					break;
				case PropertyFilter.OPERATION_LIKE_BACK:
					sb.append(" like :").append(pname);
					break;
				case PropertyFilter.OPERATION_IN:
					sb.append(" in (:").append(pname).append(")");
					break;
				case PropertyFilter.OPERATION_IS_NULL:
					sb.append(" is null");
					break;
				case PropertyFilter.OPERATION_IS_NOT_NULL:
					sb.append(" is not null");
					break;
				default: {
					throw new RuntimeException("查询过滤条件关系不存在：" + operation);
				}
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private void setFilterParameter(QueryFilter queryFilter, Query query) {
		if (queryFilter.havePropertyFilter()) {
			List<PropertyFilter> propertyFilters = queryFilter.propertyFilters;
			for (PropertyFilter propertyFilter : propertyFilters) {
				if (propertyFilter != null) {
					Object value = propertyFilter.value;
					String pname = propertyFilter.pname;
					if (value instanceof Collection) {
						Collection coll = (Collection) value;
						Collection pList = null;
						if (coll.size() > max_list_no) {
							pList = new ArrayList(max_list_no);
							Iterator iterator = coll.iterator();
							int i = 0;
							while (iterator.hasNext()) {
								pList.add(iterator.next());
								if (++i >= max_list_no)
									break;
							}

						} else {
							pList = coll;
						}
						query.setParameterList(pname, pList);
					}

					else
						query.setParameter(pname, value);

				}
			}
		}
	}

	private String getTableByAnnotation(Class<?> clazz) {
		FindTable table = (FindTable) clazz.getAnnotation(FindTable.class);

		if (table == null)
			throw new RuntimeException("该类没有FindTable注解，无法解析并查找数据库："
					+ clazz.getName());

		String value = table.value();
		// 没有设置值，则根据class名字判断，如果以Dto结尾的则去除Dto结尾
		if ("".equals(value)) {
			String className = clazz.getSimpleName();
			value = className.endsWith("Dto") ? className.substring(0,
					className.length() - 3) : className;
		}

		return value;
	}

	private QueryFilter getQueryFilterByAnnotation(Object searchDto) {
		try {
			Class clazz = searchDto.getClass();
			QueryFilter queryFilter = new QueryFilter();
			// 父类属性也扫描
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Find find = field.getAnnotation(Find.class);
				if (find != null) {
					// 暴力反射
					field.setAccessible(true);
					Object value = field.get(searchDto);

					if (value == null || "".equals(value))
						continue;

					String name = find.name();
					if ("".equals(name)) {
						name = field.getName();
					}
					queryFilter.addPropertyFilter(name, value,
							find.operation(), find.relation());
				}
			}

			return queryFilter;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("无法解析Find注解获取Field的值");
		}
	}

	/** 最大列表数，SQL中in的参数最多为1000个，当in的参数过多时我们截取前面一部分 */
	private static final int max_list_no = 200;


}
