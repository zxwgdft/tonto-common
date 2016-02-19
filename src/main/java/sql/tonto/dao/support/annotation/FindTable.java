package sql.tonto.dao.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注解需要查找的数据表名，缺省值为类名，如果类名以Dto结尾则除去Dto
 * @author	xwzhou
 * @date	2014-12-11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FindTable {
	public String value() default "";
}
