package tonto.test.excel.read;

import com.tonto2.common.excel.read.annotation.ReadProperty;

public class Address {

	@ReadProperty(cellIndex = 2, maxLength = 10)
	private String city;

	@ReadProperty(cellIndex = 3)
	private String district;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String toString() {
		return "City:" + city + "/District:" + district;

	}

}
