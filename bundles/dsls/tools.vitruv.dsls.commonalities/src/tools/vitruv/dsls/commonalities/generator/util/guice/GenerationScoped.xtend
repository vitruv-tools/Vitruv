package tools.vitruv.dsls.commonalities.generator.util.guice

import com.google.inject.ScopeAnnotation
import java.lang.annotation.Retention
import java.lang.annotation.Target
import java.lang.annotation.Inherited

/**
 * Types annotated with this scope annotation will be instantiated once per
 * generator execution for one input resource.
 */
@ScopeAnnotation
@Target(TYPE, METHOD)
@Retention(RUNTIME)
@Inherited
annotation GenerationScoped {
}
