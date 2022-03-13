package tools.vitruv.framework.vsum.filtered.util;

import java.util.Set;

import accesscontrolsystem.AccesscontrolsystemPackage;
import tools.vitruv.framework.domains.AbstractVitruvDomain;

public class AccessControlSystemDomain extends AbstractVitruvDomain {

	static final String METAMODEL_NAME = AccesscontrolsystemPackage.eNAME;
	public static final Set<String> NAMESPACE_URIS = AbstractVitruvDomain.getNsURIsRecursive(AccesscontrolsystemPackage.eINSTANCE);
	public static final String FILE_EXTENSION = "accesscontrolsystem";
	boolean shouldTransitivelyPropagateChanges = false;

	public AccessControlSystemDomain() {
		super(METAMODEL_NAME, AccesscontrolsystemPackage.eINSTANCE, FILE_EXTENSION);
	}

	@Override
	public boolean shouldTransitivelyPropagateChanges() {
		return shouldTransitivelyPropagateChanges;
	}
}
