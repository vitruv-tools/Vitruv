package tools.vitruv.dsls.reactions.helper

import java.util.Map
import org.eclipse.emf.ecore.EClassifier
import static extension tools.vitruv.dsls.reactions.helper.StringHelper.*

class XtendImportHelper {
	public static final char FQN_SEPARATOR = '.';
	private val Map<String, String> imports = newHashMap
	private val Map<String, String> staticExtensionImports = newHashMap

	public def generateImportCode() '''
		«FOR i : imports.values»
			import «i»;
		«ENDFOR»
		«FOR i : staticExtensionImports.values»
			import static extension «i».*;
		«ENDFOR»
	'''

	public def callExtensionMethod(Class<?> javaClass, String methodCall) {
		val fullyQualifiedJVMName = javaClass.name;
		val fullyQualifiedJVMNameString = fullyQualifiedJVMName.toString

		val simpleName = fullyQualifiedJVMNameString.lastFragmentOrComplete(".")

		if (!staticExtensionImports.containsKey(simpleName)) {
			staticExtensionImports.put(simpleName, fullyQualifiedJVMNameString)
		}

		return methodCall;
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

		val simpleName = fullyQualifiedJVMNameString.lastFragmentOrComplete(".")

		if (!imports.containsKey(simpleName)) {
			imports.put(simpleName, fullyQualifiedJVMNameString)
		} else if (!imports.get(simpleName).equals(fullyQualifiedJVMNameString)) {
			return fullyQualifiedJVMNameString
		}

		return simpleName
	}

	def private static isSimpleName(String fqn) {
		val	lastSeparatorPos = fqn.lastIndexOf(FQN_SEPARATOR);
		return (lastSeparatorPos == -1);
	}
}
