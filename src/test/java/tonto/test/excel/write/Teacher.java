package tonto.test.excel.write;

import java.util.List;

import com.tonto2.common.excel.write.annotation.WriteBean;
import com.tonto2.common.excel.write.annotation.WriteProperty;

public class Teacher {
	
	@WriteProperty(cellIndex = 1,name="教师ID",width=20)
	private int id;
	
	@WriteProperty(cellIndex = 2,name="教师姓名",width=20)
	private String name;
	
	@WriteBean
	private List<Student> students;
	
	@WriteBean
	private Family family;

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	
	
}
