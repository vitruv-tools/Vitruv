package tools.vitruv.testutils

import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Annotation allowing to inject the test workspace created by {@link WorkpaceManager} 
 */
 @Target(PARAMETER, FIELD)
 @Retention(RUNTIME)
annotation TestWorkspace {
}
