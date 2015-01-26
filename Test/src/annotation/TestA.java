package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD,
		ElementType.CONSTRUCTOR, ElementType.PACKAGE, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TestA {
	
	String name() default "";

	int id() default 0;

	Class<Long> gid() default Long.class;
}