package accesscontrol;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import accesscontrolsystem.accessright.OperationAccessRight;
import accesscontrolsystem.role.Role;

/**
 * Classes that implement {@link ResourceSetFilter} can be used to get a
 * filtered model view.
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface ResourceSetFilter extends ViewSourceCorrespondence{
	
	/**
	 * @param resourceSet containing resources to be filtered
	 * @return a {@link ResourceSet} which contains filtered copies of the Resources
	 */
	ResourceSet filter(ResourceSet resourceSet, Collection<OperationAccessRight> needed);
	
	/**
	 * Adds a new access rule for the modified object if no rules for the modified EObject already exist.
	 * @param modified
	 * @param containment
	 * @param roles
	 * @param grantedRights
	 * @return
	 */
	boolean addAccessRule(EObject modified, EObject containment, Collection<OperationAccessRight> grantedRights);
	
	/**
	 * Removes any AccessRules which have the given eobject as element. AccessRules which use the given eobject as containment are not removed and instead the rule has no containment.
	 * @param removed
	 * @return
	 */
	boolean removeAccessRules(EObject removed);

	/**
	 * Checks if for all given EObjects the needed OperationAccessRights are present.
	 * @param toModify
	 * @return
	 */
	boolean canModify(final Collection<EObject> toModify, Collection<OperationAccessRight> needed);

}
