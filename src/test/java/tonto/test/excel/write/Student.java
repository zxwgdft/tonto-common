package tonto.test.excel.write;

import com.tonto2.common.excel.write.annotation.WriteProperty;

public class Student {
	
	private Integer id;
	
	@WriteProperty(cellIndex = 3,name="学生姓名",width=20)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
