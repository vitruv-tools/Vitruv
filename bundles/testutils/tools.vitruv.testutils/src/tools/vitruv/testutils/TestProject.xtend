package tools.vitruv.testutils

import java.lang.annotation.Target
import java.lang.annotation.Retention

@Target(PARAMETER, FIELD)
@Retention(RUNTIME)
annotation TestProject {
	String variant = ""
}
