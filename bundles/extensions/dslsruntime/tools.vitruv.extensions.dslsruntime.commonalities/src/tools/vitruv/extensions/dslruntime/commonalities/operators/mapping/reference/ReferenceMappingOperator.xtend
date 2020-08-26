package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import java.lang.annotation.Target
import java.lang.annotation.Retention

@Target(TYPE)
@Retention(RUNTIME)
annotation ReferenceMappingOperator {

	String name
	boolean isMultiValued
	boolean isAttributeReference = false
}
