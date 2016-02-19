package sql.tonto.dao.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import sql.tonto.dao.support.PropertyFilter;

/**
 * <h2>注解查询条件</h2>
 * <p>name:数据表中的字段名</p>
 * <p>operation:对该字段做什么查询操作,值应从{@link PropertyFilter}中得到，缺省为PropertyFilter.OPERATION_EQUAL</p>
 * <p>name:查询关系,{@link PropertyFilter},缺省为PropertyFilter.RELATION_AND</p>
 * @author	xwzhou
 * @date	2014-12-11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Find {
	public String name() default "";
	public int operation() default PropertyFilter.OPERATION_EQUAL;
	public int relation() default PropertyFilter.RELATION_AND;
}
