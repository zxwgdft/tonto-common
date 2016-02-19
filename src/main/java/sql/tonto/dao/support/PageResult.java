package sql.tonto.dao.support;

import java.util.List;

@SuppressWarnings("rawtypes")
public class PageResult<T> {

	private PageResult(PageFilter pageFilter, List<T> data) {
		resultData = data;
		pageNo = pageFilter.pageNo;
		totalPage = pageFilter.totalPage;
		pageSize = pageFilter.pageSize;
		if (pageNo == 0) {
			startNo = 0;
			endNo = 0;
		} else {
			startNo = (pageNo - 1) * pageSize + 1;
			endNo = startNo + pageSize - 1;
		}
		rowCount = pageFilter.rowCount;
	}

	private PageResult() {

	}

	public static <E> PageResult<E> build(PageFilter pageFilter, List<E> data) {
		return new PageResult<E>(pageFilter, data);
	}

	List<T> resultData;
	int pageNo;
	int totalPage;
	int pageSize;
	int rowCount;
	int startNo;
	int endNo;

	// 结果的说明信息
	String message;

	public List getResultData() {
		return resultData;
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

	public int getStartNo() {
		return startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public int getRowCount() {
		return rowCount;
	}

	public final static PageResult EMPTY_RESULT;
	static {
		EMPTY_RESULT = new PageResult();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
