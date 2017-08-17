package tools.vitruv.framework.change.echange

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

import static extension tools.vitruv.framework.change.echange.resolve.EChangeUnresolver.*
import tools.vitruv.framework.change.uuid.UuidProviderAndResolver

final class TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	new(UuidProviderAndResolver uuidProviderAndResolver) {
		super(new TypeInferringUnresolvingAtomicEChangeFactory(uuidProviderAndResolver));
	}
	
	override protected <A extends EObject, F extends EStructuralFeature> setUnsetChangeFeatures(
		ExplicitUnsetEFeature<A, F> change, A affectedEObject, F affectedFeature) {
		super.setUnsetChangeFeatures(change, affectedEObject, affectedFeature)
		change.unresolveExplicitUnsetEFeature
	}

	override protected <A extends EObject, T extends Object> setUnsetAttributeChangeSubtractiveChanges(
		ExplicitUnsetEAttribute<A, T> change, List<SubtractiveAttributeEChange<A, T>> changes) {
		for (c : changes) {
			c.unresolve
			change.subtractiveChanges.add(c);
		}
	}

	override protected <A extends EObject> setUnsetReferenceChangeEChanges(ExplicitUnsetEReference<A> change,
		List<EChange> changes) {
		for (c : changes) {
			c.unresolve
			change.changes.add(c);
		}
	}
}
