package tonto.test.excel.read;

import com.tonto2.common.excel.read.annotation.ReadBean;
import com.tonto2.common.excel.read.annotation.ReadProperty;

public class User {

	@ReadProperty(cellIndex = 0, maxLength = 10)
	private String name;

	@ReadProperty(cellIndex = 1, max = "100")
	private Integer age;

	@ReadBean
	private Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String toString() {
		return "Name:" + name + "/Age:" + age + "/Address:" + address;

	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
