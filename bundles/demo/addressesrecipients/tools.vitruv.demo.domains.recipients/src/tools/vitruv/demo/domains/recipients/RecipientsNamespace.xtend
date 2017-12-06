package tools.vitruv.demo.domains.recipients

import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.metamodels.recipients.RecipientsPackage

final class RecipientsNamespace {
	private new() {
	}

	// file extensions
	public static final String FILE_EXTENSION = "recipients";

	// MM Namespaces
	public static final EPackage ROOT_PACKAGE = RecipientsPackage.eINSTANCE;
	public static final String METAMODEL_NAMESPACE = RecipientsPackage.eNS_URI;
	
}