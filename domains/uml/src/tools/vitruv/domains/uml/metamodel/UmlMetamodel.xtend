package tools.vitruv.domains.uml.metamodel

import java.util.Set
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.uml2.uml.resource.UMLResource
import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import tools.vitruv.framework.tuid.TUIDCalculatorAndResolver
import tools.vitruv.framework.util.datatypes.VURI

class UmlMetamodel extends Metamodel {
	public static final String NAMESPACE_URI = UMLPackage::eNS_URI;
	public static final String FILE_EXTENSION = UMLResource::FILE_EXTENSION;
	private static UmlMetamodel instance;

	private new() {
		super(NAMESPACE_URI, VURI::getInstance(NAMESPACE_URI), FILE_EXTENSION);
	}

	override protected TUIDCalculatorAndResolver generateTuidCalculator(Set<String> nsURIs) {
		return new AttributeTUIDCalculatorAndResolver(NAMESPACE_URI, #[UMLPackage.Literals.NAMED_ELEMENT__NAME.getName()]);
	}

	def public static synchronized UmlMetamodel getInstance() {
		if (instance === null) {
			instance = new UmlMetamodel()
		}
		return instance;
	}
	
}
