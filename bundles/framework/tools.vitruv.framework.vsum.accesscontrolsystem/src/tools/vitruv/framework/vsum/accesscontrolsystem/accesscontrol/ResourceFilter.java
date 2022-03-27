package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.vsum.accesscontrolsystem.RuleDatabase;
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.internal.ResourceFilterImpl;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.OperationAccessRight;

/**
 * Classes that implement {@link ResourceFilter} can be used to get a
 * filtered model view from a given source. The filtered resources view does not
 * support changes. The methods
 * {@link #addAccessRule(EObject, EObject, Collection)} and
 * {@link #removeAccessRules(EObject)} can be used to modify the used
 * {@link RuleDatabase} with the available roles, but the RuleDatabase has to be
 * managed outside of this class.
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface ResourceFilter extends ViewSourceCorrespondence {

	/**
	 * Returns the filtered resources, list may be empty if the given resources were
	 * empty
	 * 
	 * @return filtered resources
	 */
	List<Resource> getFilteredResources();

	/**
	 * Adds a new access rule for the modified object if no rules for the modified
	 * EObject already exist.
	 * 
	 * @param modified
	 * @param containmentOrReference
	 * @param roles
	 * @param grantedRights
	 * @return whether or not rules have been added
	 */
	boolean addAccessRule(EObject modified, EObject containmentOrReference,
			Collection<OperationAccessRight> grantedRights);

	/**
	 * Removes any AccessRules which have the given eobject as element. AccessRules
	 * which use the given eobject as containment are not removed and instead the
	 * rule has no containment.
	 * 
	 * @param removed
	 * @return whether or not rules have been removed
	 */
	boolean removeAccessRules(EObject removed);

	/**
	 * Checks if for all given EObjects the needed OperationAccessRights are
	 * present.
	 * 
	 * @param toCheck
	 * @return whether or not sufficient OperationAccessRights are present
	 */
	boolean hasAccessRights(final Collection<EObject> toCheck, Collection<OperationAccessRight> needed);

	/**
	 * Will never be null but may not contain any AccessRules.
	 * 
	 * @return the underlying RuleDatabase
	 */
	RuleDatabase getRuleDatabase();

	/**
	 * Creates a default ResourceSetFilter with the given parameters.
	 * 
	 * @param ruleDatabase                 database containing rules to filter the
	 *                                     given resources, if it is {@code null} a
	 *                                     new one is generated in the {@code vsum}
	 *                                     folder and accessible through
	 *                                     {@link #getRuleDatabase()}
	 * @param directlyAvailableRoleIndices information to identify roles that are
	 *                                     available to the user of this
	 *                                     ResourceSetFilter (27.03.2022 indices in
	 *                                     the Role List of the given RuleDatabase),
	 *                                     may be null (no role is available so all
	 *                                     access is blocked)
	 * @param evaluator                    used to evaluate rights in the
	 *                                     ruleDatabase against the given needed
	 *                                     rights, may not be {@code null}
	 * @param resources                    collection of resources to be filtered,
	 *                                     may not be {@code null}
	 * @param needed                       the OperationAccessRights that have to be
	 *                                     available for an element to be part of
	 *                                     the {@link #getFilteredResources()}, may
	 *                                     not be {@code null}
	 * @return a {@link ResourceFilter} with access to the filtered Resources
	 *         through {@link #getFilteredResources()} as well as access to the used
	 *         RuleDatabase through {@link #getRuleDatabase()} and the
	 *         correspondendences between elements in the filtered Resources and in
	 *         the given unfiltered Resources through
	 *         {@link #getSourceEObject(EObject)} and
	 *         {@link #getSourceResource(Resource)}
	 */
	static ResourceFilter create(RuleDatabase ruleDatabase, Collection<Integer> directlyAvailableRoleIndices,
			OperationAccessRightEvaluator evaluator, Collection<Resource> resources,
			Collection<OperationAccessRight> needed) {
		final var frs = new ResourceFilterImpl(ruleDatabase, directlyAvailableRoleIndices, evaluator);
		frs.filter(resources, needed);
		return frs;
	}

}
