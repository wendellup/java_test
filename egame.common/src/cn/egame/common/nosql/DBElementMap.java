package cn.egame.common.nosql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DBElementMap {

	String name() default "";

	boolean needGet() default true;

	boolean needSet() default true;

	String[] primaryKey() default {};
}
