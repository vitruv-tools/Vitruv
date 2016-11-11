package tools.vitruv.domains.sysml

import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.domains.uml.UmlMetamodel
import org.eclipse.papyrus.sysml14.sysmlPackage
import org.eclipse.uml2.uml.UMLPackage
import java.util.List
import org.eclipse.emf.ecore.EObject

class SysMlMetamodel extends Metamodel {
	public static val NAMESPACE_URIS = sysmlPackage.eINSTANCE.nsURIsRecursive;
	public static final String FILE_EXTENSION = UmlMetamodel.FILE_EXTENSION;
	private static SysMlMetamodel instance;
	private val extension SysMlToUmlResolver sysMlToUmlResolver;
	
	private new() {
		super(VURI.getInstance(UMLPackage.eNS_URI), newHashSet(UmlMetamodel.NAMESPACE_URIS + NAMESPACE_URIS),
			generateTuidCalculator(), FILE_EXTENSION
		);
		sysMlToUmlResolver = SysMlToUmlResolver.instance;
	}

	def protected static TUIDCalculatorAndResolver generateTuidCalculator() {
		return new SysMlTuidCalculatorAndResolver(UMLPackage.eNS_URI);
	}
	
	override hasMetaclassInstances(List<EObject> eObjects) {
		if (eObjects.exists[it === null]) {
			return false;
		}
		super.hasMetaclassInstances(eObjects.map[stereotypedObject])
	}

	def public static synchronized SysMlMetamodel getInstance() {
		if (instance === null) {
			instance = new SysMlMetamodel()
		}
		return instance;
	}
	
}
