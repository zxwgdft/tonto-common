package sql.tonto.dao.support;

public class PageFilter {

	/* 默认每页记录数 */
	private static final int DEFAULT_PAGESIZE = 10;

	int pageNo;
	int totalPage;
	int pageSize;

	int rowCount;
	int firstPosition;

	private PageFilter(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * 设置记录数，并计算总页数和检测当前页数是否适当
	 * 
	 * @param count
	 */
	public void setRowCount(int count) {
		rowCount = count;
		totalPage = rowCount % pageSize == 0 ? rowCount / pageSize : rowCount
				/ pageSize + 1;
		if (pageNo > totalPage)
			pageNo = totalPage;
		firstPosition = (pageNo - 1) * pageSize;
	}

	/**
	 * 构建一个默认PageSize的页过滤条件
	 * 
	 * @param pageNo
	 * @return
	 */
	public static PageFilter build(int pageNo) {
		return build(pageNo, DEFAULT_PAGESIZE);
	}

	/**
	 * 构建一个页过滤条件
	 * 
	 * @param currentPage
	 *            查询页
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	public static PageFilter build(int pageNo, int pageSize) {
		PageFilter filter = new PageFilter(pageNo, pageSize);
		return filter;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getFirstPosition() {
		return firstPosition;
	}

}
