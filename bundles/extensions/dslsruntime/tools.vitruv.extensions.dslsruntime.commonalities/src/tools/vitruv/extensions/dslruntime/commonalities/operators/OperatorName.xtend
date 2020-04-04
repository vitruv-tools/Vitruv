package tools.vitruv.extensions.dslruntime.commonalities.operators

import java.lang.annotation.Target
import java.lang.annotation.Retention

@Target(TYPE)
@Retention(RUNTIME)
annotation OperatorName {

	String value
}
