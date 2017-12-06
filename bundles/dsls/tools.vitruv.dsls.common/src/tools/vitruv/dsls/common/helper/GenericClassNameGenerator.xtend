package tools.vitruv.dsls.common.helper

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class GenericClassNameGenerator implements ClassNameGenerator {
	val String packageName
	val String className
	
	override getSimpleName() {
		className
	}
	
	override getPackageName() {
		packageName
	}
	
}