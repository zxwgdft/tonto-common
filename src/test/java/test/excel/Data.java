package test.excel;

import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;

import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.ExcelColumn;
import com.tonto.common.base.annotation.PropertyConvert;

public class Data {
	@ExcelColumn(index=0,name="姓名",defaultValue="无",width=20)
	String name;
	
	@ExcelColumn(index=1,name="出生日期",defaultValue="无",width=20,format="yyyy/MM/dd")
	Date birthday;
	
	
	@ExcelColumn(index=2,name="性格",defaultValue="无",width=20,alignment=CellStyle.ALIGN_CENTER)
	@Convert(convert=SexConvert.class)
	Integer sex;
	
	public static class SexConvert implements PropertyConvert<String>{

		@Override
		public String convert(Object obj)  {	
			Integer i=(Integer)obj;
			if(i==1)
				return "男";
			else
				return "女";
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
