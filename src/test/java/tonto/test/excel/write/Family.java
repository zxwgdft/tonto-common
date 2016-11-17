package tonto.test.excel.write;

import com.tonto2.common.excel.write.annotation.WriteProperty;

public class Family {
	
	@WriteProperty(cellIndex = 7,name="是否已婚",width=5)
	private boolean hasMarried;
	
	@WriteProperty(cellIndex = 6,name="电话",width=15)
	private String phoneNumber;
	
	@WriteProperty(cellIndex = 5,name="地址",width=30)
	private String address;

	public boolean isHasMarried() {
		return hasMarried;
	}

	public void setHasMarried(boolean hasMarried) {
		this.hasMarried = hasMarried;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
