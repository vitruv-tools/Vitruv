package tools.vitruv.framework.change.echange.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.domain.EditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.common.command.BasicCommandStack
import org.eclipse.emf.ecore.EReference
import java.util.List

/**
 * Static utility class for the EChange package and subpackages.
 */
class EChangeUtil {
	
	/**
	 * Get the editing domain of the an object. If the object has no 
	 * editing domain, it returns a new {@code AdapterFactoryEditingDomain}.
	 * @param object The EObject.
	 * @return The editing domain of the object.
	 */
	public static def EditingDomain getEditingDomain(EObject object) {
		val ed = AdapterFactoryEditingDomain.getEditingDomainFor(object)
		if (ed === null) {
			return new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), new BasicCommandStack());
		}
		return ed
	}
	
	public static def boolean alreadyContainsObject(EObject affectedEObject, EReference feature, EObject value) {
		if (!affectedEObject.eClass.EAllStructuralFeatures.contains(feature)) {
			throw new IllegalStateException("Given object " + affectedEObject + " does not contain reference " + feature);
		}
		val List<EObject> featureContents = 
			if (feature.many) {
				affectedEObject.eGet(feature) as List<EObject>
			} else {
				#[affectedEObject.eGet(feature) as EObject]
			}
		if (featureContents.contains(value)) {
			return true;
		}
		return false;
	}
}