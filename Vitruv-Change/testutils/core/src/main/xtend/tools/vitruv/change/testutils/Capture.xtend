package tools.vitruv.change.testutils

import static com.google.common.base.Preconditions.checkState
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Helper to capture values that are created in Lambdas, so that they can be used outside the Lambda. This is 
 * sometimes necessary because Java never allows writing to variables outside of a Lambda’s scope.
 * <p>
 * Example usage:
 * <pre>
 * <code>
 * def initRepository(String repositoryName) {
 *     val repository = new Capture&lt;Repository&gt;
 *     resourceAt(repositoryModelFor(repositoryName)).propagate [
 *	       contents += pcm.repository.Repository => [
 *		       entityName = repositoryName
 *	       ] >> repository
 *     ]
 *     return +repository
 * }
 * </code>
 * </pre> 
 */
class Capture<T> {
	@Accessors(PUBLIC_GETTER)
	var isSet = false
	var T instance = null

    /**
     * Sets the current value, overriding a previous value.
     */
	def set(T value) {
		instance = value
		isSet = true
	}
	
	/**
	 * Syntactic sugar for {@linkplain #set setting} the {@code value} of this capture.
	 * @return {@code value}
	 */
	def T operator_add(T value) {
		set(value)
		return value
	}
	
	/**
	 * Syntactic sugar for {@linkplain #get getting} this capture’s current value.
	 */
	def T operator_minus() {
		get()
	}
	
	/**
	 * Gets the current value.
	 * 
	 * @throws java.lang.IllegalStateException if no value has been set yet.
	 */
	def get() {
		checkState(isSet, '''No value has been set yet!''')
		return instance
	}
	
	
	/**
	 * Syntactic sugar for {@linkplain #set setting} the {@code value} of this capture.
	 * @return {@code value}
	 */
	def static <T> T operator_doubleGreaterThan(T value, Capture<T> capture) {
		capture += value
		return value
	}
}