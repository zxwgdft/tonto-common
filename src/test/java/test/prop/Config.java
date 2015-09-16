
package test.prop;

import java.util.Date;


import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.Property;
import com.tonto.common.base.annotation.PropertyType;

public class Config {
	@Property(name="name",type=PropertyType.STRING)
	String name;
	
	@Property(name="age")
	@Convert(convert=TestStrConvert.class)
	Integer age;
	@Property(name="ismarried",type=PropertyType.BOOLEAN)
	Boolean ismarried;
	@Property(name="birthday",type=PropertyType.DATE)
	Date birthday;
	
}
