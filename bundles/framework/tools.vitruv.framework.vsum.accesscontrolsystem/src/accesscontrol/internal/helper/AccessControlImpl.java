package accesscontrol.internal.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrolsystem.AccessRule;
import accesscontrolsystem.RuleDatabase;
import accesscontrolsystem.accessright.OperationAccessRight;
import accesscontrolsystem.role.Role;
import accesscontrolsystem.role.Subsumption;

public class AccessControlImpl {

	private AccessControlImpl() {
		throw new AssertionError();
	}

	/**
	 * 
	 * @param resourceSet                 containing resources to be filtered and
	 *                                    the ruledatabase
	 * @param roles                       the directly available roles, in case direct
	 *                                    subsumptions are available they are added
	 *                                    here - indirect subsumptions are not considered
	 * @param operationAccessRightsNeeded the needed rights to not get into the
	 *                                    returned toberemoved collection
	 * @param ruleDatabase                the ruledatabase containing access
	 *                                    information
	 * @param evaluator                   used to evaluate whether or not a given
	 *                                    right is sufficient for a needed one
	 * @return a map from every resource to the elements in this resource the given
	 *         access rights are not sufficient
	 */
	public static Map<Resource, Collection<EObject>> computeElementsWithMissingAccessRights(ResourceSet resourceSet,
			Collection<Role> roles, Collection<OperationAccessRight> operationAccessRightsNeeded,
			RuleDatabase ruleDatabase, OperationAccessRightEvaluator evaluator) {
		Collection<AccessRule> rules = ruleDatabase.getAccessrules();
		Collection<Role> availableRoles = computeAvailableRoles(roles, ruleDatabase);
		Map<Resource, Collection<EObject>> toBeRemoved = new HashMap<>();
		for (final Resource resource : resourceSet.getResources()) {
			if (!resource.getContents().isEmpty() && !(resource.getContents().get(0) instanceof RuleDatabase)) {
				Collection<EObject> toBeRemovedForModel = computeModelElementsWithMissingAccessRights(rules,
						availableRoles, resource.getAllContents(), operationAccessRightsNeeded, true, evaluator);
				toBeRemoved.put(resource, toBeRemovedForModel);
			}
		}
		return toBeRemoved;
	}

	private static Collection<Role> computeAvailableRoles(Collection<Role> roles, RuleDatabase ruleDatabase) {
		List<Role> availableRoles = new ArrayList<>();
		availableRoles.addAll(roles);
		availableRoles.addAll(
				ruleDatabase.getRoleprovider().getSubsumption().stream().filter(it -> roles.contains(it.getSubsumer()))
						.map(Subsumption::getSubsumes).collect(Collectors.toList()));
		return availableRoles;
	}

	private static Collection<EObject> computeModelElementsWithMissingAccessRights(final Collection<AccessRule> rules,
			final Collection<Role> availableRoles, final TreeIterator<EObject> modelToFilter,
			final Collection<OperationAccessRight> operationAccessRightsNeeded, boolean inheritance,
			OperationAccessRightEvaluator evaluator) {
		final EList<EObject> toBeRemoved = new BasicEList<>();
		while (modelToFilter.hasNext()) {
			final var modelElement = modelToFilter.next();
			// only use rules for the given element and for the given role. In case the
			// OperationAccessRights are split across multiple rules, use all rights.
			final List<OperationAccessRight> availableRights = rules.stream().
			// remove all rules that restrict the access to other elements
					filter(rule -> modelElement.equals(rule.getElement())).
					// remove all rules that contain roles that are not available
					filter(rule -> availableRoles.contains(rule.getRole())).
					// map on the OperationAccessRights that those rules contain
					map(AccessRule::getOperationAccessRights).
					// flatMap them to contain the list of OperationAccessRights the given roles
					// have for this element
					flatMap(List::stream).collect(Collectors.toList());
			if (inheritance) {
				availableRights.addAll(calculateRightsWithInheritance(modelElement, rules, availableRoles));
			}
			if (!evaluator.hasAccess(availableRights, operationAccessRightsNeeded)) {
				toBeRemoved.add(modelElement);
			}
		}
		return toBeRemoved;
	}

	private static List<OperationAccessRight> calculateRightsWithInheritance(final EObject object,
			final Collection<AccessRule> rules, final Collection<Role> availableRoles) {
		final List<OperationAccessRight> availabelRights = new ArrayList<>();
		EObject parent = object;
		final Set<EObject> visitedParents = new HashSet<>();
		while (parent != null && !visitedParents.contains(parent)) {
			EObject recent = parent;
			visitedParents.add(parent);
			availabelRights.addAll(rules.stream().filter(rule -> availableRoles.contains(rule.getRole()))
					.filter(rule -> recent.equals(rule.getElement())).map(AccessRule::getOperationAccessRights)
					.flatMap(List::stream).collect(Collectors.toList()));
			parent = parent.eContainer();
		}
		return availabelRights;
	}
}
