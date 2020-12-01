package tools.vitruv.testutils

import static com.google.common.base.Preconditions.checkState

class Capture<T> {
	var isSet = false
	var T instance = null

	def set(T t) {
		instance = t
		isSet = true
	}
	
	def T operator_add(T t) {
		set(t)
		return t
	}
	
	def T operator_plus() {
		get()
	}
	
	def get() {
		checkState(isSet, '''No value has been set yet!''')
		return instance
	}
	
	def static <T> operator_doubleGreaterThan(T element, Capture<T> capture) {
		capture += element
	}
}