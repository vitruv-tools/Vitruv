package tools.vitruv.domains.sysml

import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.domains.uml.UmlMetamodel
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.domains.sysml.tuid.SysMlToUmlResolver
import tools.vitruv.domains.sysml.tuid.SysMlTuidCalculatorAndResolver
import static tools.vitruv.domains.sysml.SysMlNamspace.*;

class SysMlMetamodel extends Metamodel {
	public static val NAMESPACE_URIS = ROOT_PACKAGE.nsURIsRecursive;
	private val extension SysMlToUmlResolver sysMlToUmlResolver;
	
	package new() {
		super(VURI.getInstance(METAMODEL_NAMESPACE), newHashSet(UmlMetamodel.NAMESPACE_URIS + NAMESPACE_URIS),
			generateTuidCalculator(), FILE_EXTENSION
		);
		sysMlToUmlResolver = SysMlToUmlResolver.instance;
	}

	def protected static TUIDCalculatorAndResolver generateTuidCalculator() {
		return new SysMlTuidCalculatorAndResolver(METAMODEL_NAMESPACE);
	}
	
	override hasMetaclassInstances(List<EObject> eObjects) {
		if (eObjects.exists[it === null]) {
			return false;
		}
		super.hasMetaclassInstances(eObjects.map[stereotypedObject])
	}

}
