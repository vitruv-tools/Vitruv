package tools.vitruv.domains.java

import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.domains.java.tuid.JavaTuidCalculatorAndResolver
import static tools.vitruv.domains.java.JavaNamespace.*

class JavaMetamodel extends Metamodel {
	package new() {
		super(VURI.getInstance(METAMODEL_NAMESPACE), ROOT_PACKAGE.nsURIsRecursive, generateTuidCalculator(), #[FILE_EXTENSION]);
	}
	
	def protected static generateTuidCalculator() {
		return new JavaTuidCalculatorAndResolver();
	}
	
}