package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature

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
		if (affectedEObject != null) {
			change.affectedEObject = TypeInferringUnresolvingAtomicEChangeFactory.createProxy(affectedEObject)
		} else {
			change.affectedEObject = null
		}
		change.affectedFeature = affectedFeature
	}	
}