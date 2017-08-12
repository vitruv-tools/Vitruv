package tools.vitruv.dsls.common.helper

interface ClassNameGenerator {
	public static val QNAME_SEPARATOR = '.'

	def String getQualifiedName() {
		packageName + QNAME_SEPARATOR + simpleName
	}

	def String getSimpleName()

	def String getPackageName()

	def static fromQualifiedName(String qualifiedName) {
		val lastIndex = qualifiedName.lastIndexOf(QNAME_SEPARATOR)
		if (lastIndex == -1) {
			return new GenericClassNameGenerator('', qualifiedName);
		} else {
			new GenericClassNameGenerator(qualifiedName.substring(0, lastIndex),
				qualifiedName.substring(lastIndex + QNAME_SEPARATOR.length))
		}
	}
}
