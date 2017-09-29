package tools.vitruv.framework.change.echange.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.domain.EditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.common.command.BasicCommandStack
import org.eclipse.emf.ecore.EReference
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil

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
	
	/**
	 * Return the value of the ID attribute of the given {@link EObject}, according to
	 * {@link EcoreUtil#getID(EObject) EcoreUtil}.
	 * If the object has no ID attribute or if is marked as <code>derived</code>, 
	 * <code>null</code> will be returned.
	 * 
	 * @see 	EcoreUtil#getID(EObject)
	 * @param 	eObject
	 * 			The object to get the ID attribute value from
	 * @return 	The ID attribute value of the given {@link EObject} or <code>null</code> 
	 * 			if it has no ID attribute or if it is marked as <code>derived</code>.
	 */
	public static def String getID(EObject eObject) {
		val idAttribute = eObject.eClass.EIDAttribute
		if (idAttribute !== null && !idAttribute.derived) {
			return EcoreUtil.getID(eObject);
		}
		return null;
	}
}