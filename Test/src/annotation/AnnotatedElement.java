package annotation;

import java.text.Annotation;

public interface AnnotatedElement {
	    boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);
	    <T extends Annotation> T getAnnotation(Class<T> annotationClass);
	    Annotation[] getAnnotations();
	    Annotation[] getDeclaredAnnotations();
}