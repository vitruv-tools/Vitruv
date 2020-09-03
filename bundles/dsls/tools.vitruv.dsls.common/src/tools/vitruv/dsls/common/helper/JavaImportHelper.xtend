package tools.vitruv.dsls.common.helper

import java.util.Map
import java.util.Collections
import org.eclipse.emf.ecore.EClassifier

class JavaImportHelper {
	public static final char FQN_SEPARATOR = '.';
	static val NO_IMPORT_NEEDED = Collections.singleton('java.lang')
	val Map<String, String> imports = newHashMap
	val Map<String, String> staticImports = newHashMap

	def generateImportCode() '''
		«FOR i : imports.values»
			import «i»;
		«ENDFOR»
		«FOR i : staticImports.values»
			import static «i»;
		«ENDFOR»
	'''

	def staticRef(Class<?> javaClass, String methodName) {
		if (!staticImports.containsKey(methodName)) {
			staticImports.put(methodName, javaClass.name)
			return methodName;
		}

		return javaClass.name + FQN_SEPARATOR + methodName;
	}
	
	def typeRef(ClassNameGenerator nameGenerator) {
		typeRef(nameGenerator.qualifiedName)
	}

	def typeRef(Class<?> javaClass) {
		typeRef(javaClass.name)
	}

	def typeRef(EClassifier eClassifier) {
		typeRef(eClassifier.instanceTypeName)
	}

	def typeRef(CharSequence fullyQualifiedJVMName) {
		val fullyQualifiedJVMNameString = fullyQualifiedJVMName.toString
		if (fullyQualifiedJVMNameString.isSimpleName)
			return fullyQualifiedJVMNameString

		val className = ClassNameGenerator.fromQualifiedName(fullyQualifiedJVMNameString)
		
		if (NO_IMPORT_NEEDED.contains(className.packageName)) {
			return className.simpleName
		}

		if (!imports.containsKey(className.simpleName)) {
			imports.put(className.simpleName, className.qualifiedName)
		} else if (!imports.get(className.simpleName).equals(fullyQualifiedJVMNameString)) {
			return className.qualifiedName
		}

		return className.simpleName
	}

	def private static isSimpleName(String fqn) {
		val	lastSeparatorPos = fqn.lastIndexOf(FQN_SEPARATOR);
		return (lastSeparatorPos == -1);
	}
}
