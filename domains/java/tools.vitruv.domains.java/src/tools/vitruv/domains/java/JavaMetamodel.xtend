package tools.vitruv.domains.java

import tools.vitruv.framework.metamodel.Metamodel
import com.google.common.collect.Sets
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.domains.java.tuid.JavaTuidCalculatorAndResolver
import static tools.vitruv.domains.java.JavaNamespace.*

class JavaMetamodel extends Metamodel {
	package new() {
		super(Sets.newHashSet(ROOT_PACKAGE.nsURIsRecursive.toList), VURI.getInstance(METAMODEL_NAMESPACE), 
			#[FILE_EXTENSION]
		);
	}
	
	override protected generateTuidCalculator(String nsPrefix) {
		return new JavaTuidCalculatorAndResolver();
	}
	
}