package tools.vitruv.testutils.change.processing

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.emf.ecore.EPackage
import java.util.List
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.change.echange.EChange

/**
 * Xtext tests reset the metamodel registry between test runs. Hence, it might
 * be necessary to re-register the domains’ metamodels before propagating changes.
 */
@FinalFieldsConstructor
class MetamodelRegisteringChangePropagationSpecification implements ChangePropagationSpecification {
	@Delegate val ChangePropagationSpecification delegate
	
	override propagateChange(EChange change, CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		sourceDomain.registerAllMetamodels()
		targetDomain.registerAllMetamodels()
		delegate.propagateChange(change, correspondenceModel, resourceAccess)
	}
	
	def private registerAllMetamodels(VitruvDomain domain) {
		for (metamodel : domain.metamodelRootPackage.allPackages) {
			EPackage.Registry.INSTANCE.putIfAbsent(metamodel.nsURI, metamodel)
		}
	}
	
	def private Iterable<EPackage> getAllPackages(EPackage ePackage) {
		List.of(ePackage) + ePackage.ESubpackages.flatMap[allPackages]
	}
	
	def static ChangePropagationSpecification registerMetamodelsBeforePropagating(ChangePropagationSpecification specification) {
		new MetamodelRegisteringChangePropagationSpecification(specification)
	}
}
