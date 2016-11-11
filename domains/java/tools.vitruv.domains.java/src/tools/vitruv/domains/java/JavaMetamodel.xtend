package tools.vitruv.domains.java

import tools.vitruv.framework.metamodel.Metamodel
import com.google.common.collect.Sets
import tools.vitruv.framework.util.datatypes.VURI
import org.emftext.language.java.JavaPackage
import tools.vitruv.domains.java.util.jamopp.JaMoPPTUIDCalculatorAndResolver

class JavaMetamodel extends Metamodel {
	package new() {
		super(Sets.newHashSet(JavaPackage.eINSTANCE.nsURIsRecursive.toList), VURI.getInstance(JavaPackage.eNS_URI), 
			#["java"]
		);
	}
	
	override protected generateTuidCalculator(String nsPrefix) {
		return new JaMoPPTUIDCalculatorAndResolver();
	}
	
}