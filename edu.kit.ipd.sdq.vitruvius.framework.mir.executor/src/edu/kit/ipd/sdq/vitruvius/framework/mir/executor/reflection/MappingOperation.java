package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD})
@Retention(RUNTIME)
public @interface MappingOperation {
	String value();
	MappingType type() default MappingType.UNSPECIFIED;
	String source() default "";
	String target() default "";
}
