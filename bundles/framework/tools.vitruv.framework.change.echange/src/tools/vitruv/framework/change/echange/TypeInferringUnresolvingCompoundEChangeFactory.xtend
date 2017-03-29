package tools.vitruv.framework.change.echange

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

import static extension tools.vitruv.framework.change.echange.resolve.EChangeUnresolver.*

class TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	protected TypeInferringAtomicEChangeFactory atomicFactory
	private static TypeInferringUnresolvingCompoundEChangeFactory instance

	private new(TypeInferringAtomicEChangeFactory atomicFactory) {
		super(atomicFactory)
	}
	
	/**
	 * Get the singleton instance of the factory.
	 * @return The singleton instance.
	 */
	def public static TypeInferringUnresolvingCompoundEChangeFactory getInstance() {
		if (instance == null) {
			instance = new TypeInferringUnresolvingCompoundEChangeFactory(TypeInferringUnresolvingAtomicEChangeFactory.instance)
		}
		return instance
	}
	
	override protected <A extends EObject, F extends EStructuralFeature> setUnsetChangeFeatures(ExplicitUnsetEFeature<A, F> change, 
		A affectedEObject, F affectedFeature) {
		super.setUnsetChangeFeatures(change, affectedEObject, affectedFeature)
		change.unresolveExplicitUnsetEFeature
	}
	
	override protected <A extends EObject, T extends Object> setUnsetAttributeChangeSubtractiveChanges(ExplicitUnsetEAttribute<A, T> change,
		List<SubtractiveAttributeEChange<A, T>> changes) {
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