package test.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class B extends A{
	private String phone;
	private Date birthday;
	
	private List<String> addresses;
	private Map<String,Date> map;
	private int[][] ts;
	
	private A a;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public List<String> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public int[][] getTs() {
		return ts;
	}
	public void setTs(int[][] ts) {
		this.ts = ts;
	}
	public Map<String, Date> getMap() {
		return map;
	}
	public void setMap(Map<String, Date> map) {
		this.map = map;
	}
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}

}
