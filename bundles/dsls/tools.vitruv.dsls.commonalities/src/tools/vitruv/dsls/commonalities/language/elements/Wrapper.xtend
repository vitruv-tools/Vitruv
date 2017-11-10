package tools.vitruv.dsls.commonalities.language.elements

interface Wrapper<WrappedType> {
	def WrappedType getWrapped();
}