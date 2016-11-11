package tools.vitruv.domains.uml

import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.uml2.uml.resource.UMLResource
import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI

class UmlMetamodel extends Metamodel {
	public static val NAMESPACE_URIS = UMLPackage.eINSTANCE.nsURIsRecursive;
	public static final String FILE_EXTENSION = UMLResource::FILE_EXTENSION;
	private static UmlMetamodel instance;

	private new() {
		super(VURI.getInstance(UMLPackage.eNS_URI), NAMESPACE_URIS, generateTuidCalculator(), FILE_EXTENSION);
	}

	def protected static TUIDCalculatorAndResolver generateTuidCalculator() {
		return new AttributeTUIDCalculatorAndResolver(UMLPackage.eNS_URI, #[UMLPackage.Literals.NAMED_ELEMENT__NAME.getName()]);
	}

	def public static synchronized UmlMetamodel getInstance() {
		if (instance === null) {
			instance = new UmlMetamodel()
		}
		return instance;
	}
	
}
