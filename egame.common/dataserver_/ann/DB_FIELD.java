package cn.egame.common.dataserver.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DB_FIELD {
	public boolean isDBField() default true;  //是否数据库字段
	public String fieldName();                //对应的数据库字段名字
}
