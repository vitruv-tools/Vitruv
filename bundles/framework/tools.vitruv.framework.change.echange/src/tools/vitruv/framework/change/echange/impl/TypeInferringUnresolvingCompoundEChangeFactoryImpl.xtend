package tools.vitruv.framework.change.echange.impl

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

import static extension tools.vitruv.framework.change.echange.resolve.EChangeUnresolver.*
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.EChange

final class TypeInferringUnresolvingCompoundEChangeFactoryImpl extends TypeInferringCompoundEChangeFactoryImpl implements TypeInferringUnresolvingCompoundEChangeFactory {

	static def TypeInferringUnresolvingCompoundEChangeFactory init() {
		new TypeInferringUnresolvingCompoundEChangeFactoryImpl(TypeInferringUnresolvingAtomicEChangeFactory::instance)
	}

	private new(TypeInferringAtomicEChangeFactory atomicFactory) {
		super(atomicFactory)
	}

	override protected <A extends EObject, F extends EStructuralFeature> setUnsetChangeFeatures(
		ExplicitUnsetEFeature<A, F> change, A affectedEObject, F affectedFeature) {
		super.setUnsetChangeFeatures(change, affectedEObject, affectedFeature)
		change.unresolveExplicitUnsetEFeature
	}

	override protected <A extends EObject, T extends Object> setUnsetAttributeChangeSubtractiveChanges(
		ExplicitUnsetEAttribute<A, T> change, List<SubtractiveAttributeEChange<A, T>> changes) {
		changes.forEach [
			unresolve
			change.subtractiveChanges.add(it)
		]
	}

	override protected <A extends EObject> setUnsetReferenceChangeEChanges(ExplicitUnsetEReference<A> change,
		List<EChange> changes) {
		changes.forEach [
			unresolve
			change.changes.add(it)
		]
	}
}
