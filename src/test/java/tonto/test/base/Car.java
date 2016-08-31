package tonto.test.base;

import java.util.Map;

public class Car extends Vehicle{

	private Integer seatNum;
	
	private Map<String, Object> datas;

	public Integer getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
	
}
