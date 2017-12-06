package tools.vitruv.demo.domains.addresses

import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.metamodels.addresses.AddressesPackage

final class AddressesNamespace {
	private new() {
	}

	// file extensions
	public static final String FILE_EXTENSION = "addresses";

	// MM Namespaces
	public static final EPackage ROOT_PACKAGE = AddressesPackage.eINSTANCE;
	public static final String METAMODEL_NAMESPACE = AddressesPackage.eNS_URI;
	
}