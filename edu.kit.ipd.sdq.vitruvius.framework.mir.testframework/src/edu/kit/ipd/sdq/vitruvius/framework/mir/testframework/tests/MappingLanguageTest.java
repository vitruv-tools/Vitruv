package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappingLanguageTest {
	String value() default "";
}