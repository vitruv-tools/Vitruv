package tools.vitruv.dsls.common.helper

import java.util.Map
import java.util.Collections
import org.eclipse.emf.ecore.EClassifier

class JavaImportHelper {
	public static final char FQN_SEPARATOR = '.';
	private static val NO_IMPORT_NEEDED = Collections.singleton('java.lang')
	private val Map<String, String> imports = newHashMap
	private val Map<String, String> staticImports = newHashMap

	public def generateImportCode() '''
		«FOR i : imports.values»
			import «i»;
		«ENDFOR»
		«FOR i : staticImports.values»
			import static «i»;
		«ENDFOR»
	'''

	public def staticRef(Class<?> javaClass, String methodName) {
		if (!staticImports.containsKey(methodName)) {
			staticImports.put(methodName, javaClass.name)
			return methodName;
		}

		return javaClass.name + FQN_SEPARATOR + methodName;
	}
	
	public def typeRef(ClassNameGenerator nameGenerator) {
		typeRef(nameGenerator.qualifiedName)
	}

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
