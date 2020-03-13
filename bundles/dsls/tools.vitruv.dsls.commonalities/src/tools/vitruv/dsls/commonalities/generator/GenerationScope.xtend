package tools.vitruv.dsls.commonalities.generator

import com.google.common.base.Preconditions
import com.google.inject.Key
import com.google.inject.Provider
import com.google.inject.Scope
import java.util.HashMap

/**
 * Scopes a single generator execution for one input resource.
 * <p>
 * There can only be a single generation scope active per thread any any given
 * time.
 */
final class GenerationScope {

	/**
	 * Guice scope handler.
	 * <p>
	 * Used in combination with the {@link GenerationScoped} annotation.
	 */
	static class GuiceScope implements Scope {

		public static val INSTANCE = new GuiceScope()

		private new() {
		}

		override <T> Provider<T> scope(Key<T> key, Provider<T> unscopedProvider) {
			return [
				val currentScope = currentScope
				Preconditions.checkState(currentScope !== null, '''Not within a GenerationScope!''')
				currentScope.values.computeIfAbsent(key, [unscopedProvider.get]) as T
			]
		}
	}

	static var CURRENT_SCOPE = new ThreadLocal<GenerationScope>()

	def static GenerationScope getCurrentScope() {
		return CURRENT_SCOPE.get()
	}

	def private static setCurrentScope(GenerationScope scope) {
		if (scope === null) {
			CURRENT_SCOPE.remove()
		} else {
			CURRENT_SCOPE.set(scope)
		}
	}

	val values = new HashMap<Key<?>, Object>()

	new() {
	}

	def <T> seed(Key<T> key, T value) {
		Preconditions.checkState(!values.containsKey(key), '''The scope already contains a value for the key «key»!''')
		values.put(key, value)
	}

	def <T> void seed(Class<T> clazz, T value) {
		seed(Key.get(clazz), value);
	}

	def enter() {
		Preconditions.checkState(currentScope === null, '''Already inside a GenerationScope!''')
		currentScope = this
	}

	def leave() {
		Preconditions.checkState(currentScope !== null, '''Already not within a GenerationScope!''')
		currentScope = null
	}
}
