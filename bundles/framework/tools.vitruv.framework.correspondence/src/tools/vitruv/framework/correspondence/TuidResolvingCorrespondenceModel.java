package tools.vitruv.framework.correspondence;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.tuid.Tuid;

/**
 * Historically the correspondence model is capable of resolving and calculating TUIDs.
 * The logic is separated into this interface to be removed as soon as the logic is separated
 * from the correspondence model as should be done.
 *  
 * @author Heiko Klare
 *
 */
public interface TuidResolvingCorrespondenceModel {
	public EObject resolveEObjectFromTuid(final Tuid tuid);

	public Tuid calculateTuidFromEObject(final EObject eObject);

	/**
	 * Is necessary to make the remove operation possible. TODO: check whether we
	 * can remove this from the public API
	 *
	 * @return
	 */
	public Tuid calculateTuidFromEObject(final EObject eObject, EObject virtualRootObject, String prefix);
}
