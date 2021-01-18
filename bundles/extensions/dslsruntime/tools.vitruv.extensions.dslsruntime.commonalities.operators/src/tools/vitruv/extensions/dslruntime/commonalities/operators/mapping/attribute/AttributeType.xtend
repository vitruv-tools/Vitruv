package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute

import java.lang.annotation.Retention
import java.lang.annotation.Target

@Target(TYPE)
@Retention(RUNTIME)
annotation AttributeType {

	boolean multiValued
	Class<?> type
}
