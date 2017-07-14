package tools.vitruv.dsls.commonalities.util

import java.util.function.Consumer

final class With {
	private new () {}
	
	def static <T> with(T object, Consumer<T> consumer) {
		consumer.accept(object)
		object
	}
}