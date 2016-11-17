package tools.vitruv.domains.asem

import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.ASEM.ASEMPackage

class AsemNamespace {
	private new() {
	}

	// file extensions
	public static final String FILE_EXTENSION = "asem";

	// MM Namespaces
	public static final EPackage ROOT_PACKAGE = ASEMPackage.eINSTANCE;
	public static final String METAMODEL_NAMESPACE = ASEMPackage.eNS_URI;
	
}