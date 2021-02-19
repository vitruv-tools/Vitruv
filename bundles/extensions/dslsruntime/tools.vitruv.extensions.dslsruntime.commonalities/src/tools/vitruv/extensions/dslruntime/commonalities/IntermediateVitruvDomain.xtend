package tools.vitruv.extensions.dslruntime.commonalities

import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.framework.domains.AbstractVitruvDomain

abstract class IntermediateVitruvDomain extends AbstractVitruvDomain {

	new(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, String... fileExtensions) {
		super(name, metamodelRootPackage, furtherRootPackages, fileExtensions)
	}

	new(String name, EPackage metamodelRootPackage, String... fileExtensions) {
		super(name, metamodelRootPackage, fileExtensions)
	}

	override shouldTransitivelyPropagateChanges() {
		true
	}

	override isUserVisible() {
		false
	}

	override boolean isInstanceOfDomainMetamodel(EObject eObject) {
		// IntermediateResourceBridge is shared by the various intermediate
		// domains. During the creation of a new IntermediateResourceBridge
		// instance we assign it the namespace URI of the intermediate domain
		// it belongs to and check for that here.
		// Alternatives could be to handle resources differently, generate an
		// IntermediateResourceBridge class per concept metamodel (which would
		// need to use or delegate to the implementation found in
		// IntermediateResourceBridgeI), or add a generally applicable
		// mechanism to the Vitruvius framework in order to support metamodels
		// that are shared by various domains but still be able to determine
		// the domain a certain shared metaclass instance belongs to.
		if (eObject instanceof IntermediateResourceBridge) {
			return nsUris.contains(eObject.intermediateNS)
		} else {
			return super.isInstanceOfDomainMetamodel(eObject)
		}
	}
}
