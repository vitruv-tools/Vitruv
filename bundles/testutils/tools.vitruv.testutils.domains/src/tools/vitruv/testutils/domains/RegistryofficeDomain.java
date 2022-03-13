package tools.vitruv.testutils.domains;

import java.util.List;
import java.util.Set;

import registryoffice.RegistryofficePackage;
import tools.vitruv.framework.domains.AbstractVitruvDomain;

public class RegistryofficeDomain extends VitruvTestDomain {

	static final String METAMODEL_NAME = RegistryofficePackage.eNAME;
	public static Set<String> NAMESPACE_URIS = AbstractVitruvDomain.getNsURIsRecursive(RegistryofficePackage.eINSTANCE);
	public static final String FILE_EXTENSION = "registryoffice";

	public RegistryofficeDomain() {
		super(METAMODEL_NAME, RegistryofficePackage.eINSTANCE, List.of(RegistryofficePackage.Literals.PERSON__NAME), FILE_EXTENSION);
	}

}
