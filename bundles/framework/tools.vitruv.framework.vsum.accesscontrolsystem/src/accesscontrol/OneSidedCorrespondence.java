package accesscontrol;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Correspondence between a view and its source where for elements from the view
 * the corresponding elements in the source can be found with the methods given.
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface OneSidedCorrespondence {

	/**
	 * 
	 * @param resource the copy that was created during the filter process
	 * @return the original resource if one is present (if a new Resource is added
	 *         no corresponding Resource can be present and {@code null} is
	 *         returned, it will be generated once the newly added resource gets
	 *         filtered for the first time)
	 */
	Resource getCorrespondingResource(Resource resource);

	/**
	 * 
	 * @param object the copy that was created during the filter process
	 * @return the original object if one is present (if new EObjects are added no
	 *         corresponding EObject is present and {@code null} is returned)
	 */
	EObject getCorrespondentObject(EObject object);

}
