package tools.vitruv.dsls.mapping.helpers

import java.util.Map
import java.util.function.Function
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension tools.vitruv.dsls.mapping.helpers.StringHelper.*
import static extension tools.vitruv.framework.util.bridges.JavaHelper.*

class JavaGeneratorHelper {
	public static def void generateJavaFile(IFileSystemAccess fsa, CharSequence fqn,
		Function<ImportHelper, CharSequence> generatorFunction) {
		fsa.generateFile(fqn.toString.classNameToJavaPath,
			JavaGeneratorHelper.generate(fqn.toString.classNameToPackageName, generatorFunction))
	}

	public static def CharSequence generate(String packageName,
		Function<ImportHelper, CharSequence> generatorFunction) {
		(new JavaGeneratorHelper(packageName, generatorFunction)).generate
	}

	private val String packageName
	private val Function<ImportHelper, CharSequence> generatorFunction

	private new(String packageName, Function<ImportHelper, CharSequence> generatorFunction) {
		this.packageName = packageName
		this.generatorFunction = generatorFunction
	}

	private def generate() {
		val importHelper = new ImportHelper
		val content = generatorFunction.apply(importHelper)

		'''
			«IF packageName !== null»
				package «packageName»;
			«ENDIF»
			
			«importHelper.generateImportCode»
			
			«content»
		'''
	}

	public static class ImportHelper {
		private val Map<String, String> imports = newHashMap

		public def generateImportCode() '''
			«FOR i : imports.values»
				import «i»;
			«ENDFOR»
		'''

		public def typeRef(Class<?> javaClass) {
			typeRef(javaClass.name)
		}

		public def typeRef(EClassifier eClassifier) {
			typeRef(eClassifier.instanceTypeName)
		}

		public def typeRef(CharSequence fullyQualifiedJVMName) {
			val fullyQualifiedJVMNameString = fullyQualifiedJVMName.toString
			if (fullyQualifiedJVMNameString.isSimpleName)
				return fullyQualifiedJVMNameString

			val simpleName = fullyQualifiedJVMNameString.lastFragmentOrComplete(".")

			if (!imports.containsKey(simpleName)) {
				imports.put(simpleName, fullyQualifiedJVMNameString)
			} else if (!imports.get(simpleName).equals(fullyQualifiedJVMNameString)) {
				return fullyQualifiedJVMNameString
			}

			return simpleName
		}
	}
}