package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Correspondence between the elements of a view and the corresponding elements
 * in the source from which the view was created. The correspondence is one-sided
 * between the view and the source. Every element that was taken over to the view
 * has a correspondent element in the source. Elements in the view are copies of
 * elements in the source.
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface ViewSourceCorrespondence {

	/**
	 * 
	 * @param resource the copy that was created during the filter process
	 * @return the original resource if one is present (if a new Resource is added
	 *         no corresponding Resource can be present and {@code null} is
	 *         returned, it will be generated once the newly added resource gets
	 *         filtered for the first time)
	 */
	Resource getSourceResource(Resource resource);

	/**
	 * 
	 * @param eObject the copy that was created during the filter process
	 * @return the original object if one is present (if new EObjects are added no
	 *         corresponding EObject is present and {@code null} is returned)
	 */
	EObject getSourceEObject(EObject eObject);

}
