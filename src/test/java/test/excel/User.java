package test.excel;

import java.util.Date;

import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.Property;
import com.tonto.common.base.annotation.PropertyType;

public class User{
	@Property(name="姓名",type=PropertyType.STRING)
	String name;
	
	@Property(name="年龄")
	@Convert(convert=TestConvert.class)
	Integer age;
	@Property(name="是否结婚",type=PropertyType.BOOLEAN)
	Boolean ismarried;
	@Property(name="出生年月",type=PropertyType.DATE)
	Date birthday;
	
	
}