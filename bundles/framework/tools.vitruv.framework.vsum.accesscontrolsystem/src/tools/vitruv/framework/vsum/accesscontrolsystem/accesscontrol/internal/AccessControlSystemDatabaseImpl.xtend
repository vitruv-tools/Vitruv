package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.internal

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.stream.Collectors
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.TreeIterator
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.vsum.accesscontrolsystem.AccessRule
import tools.vitruv.framework.vsum.accesscontrolsystem.AccesscontrolsystemFactory
import tools.vitruv.framework.vsum.accesscontrolsystem.RuleDatabase
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightEvaluator
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightUtil
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.OperationAccessRight
import tools.vitruv.framework.vsum.accesscontrolsystem.role.Role

@FinalFieldsConstructor
class AccessControlSystemDatabaseImpl implements AccesscontrolsystemDatabase{
	@Accessors(PUBLIC_GETTER) @Delegate final RuleDatabase ruleDatabase
	
	override Map<Resource, Collection<EObject>> computeElementsWithMissingAccessRights(Collection<Resource> resourceSet,
			Collection<Role> roles, Collection<OperationAccessRight> operationAccessRightsNeeded,
			OperationAccessRightEvaluator evaluator) {
		val rules = ruleDatabase.getAccessrules();
		val availableRoles = computeAvailableRoles(roles, ruleDatabase);
		val toBeRemoved = new HashMap();
		for (Resource resource : resourceSet) {
			if (!resource.getContents().isEmpty() && !(resource.getContents().get(0) instanceof RuleDatabase)) {
				val toBeRemovedForModel = computeModelElementsWithMissingAccessRights(rules,
						availableRoles, resource.getAllContents(), operationAccessRightsNeeded, true, evaluator);
				toBeRemoved.put(resource, toBeRemovedForModel);
			}
		}
		return toBeRemoved;
	}

	def Collection<Role> computeAvailableRoles(Collection<Role> roles, RuleDatabase ruleDatabase) {
		val availableRoles = new ArrayList()
		availableRoles.addAll(roles)
		availableRoles.addAll(
				ruleDatabase.getRoleprovider().getSubsumption().stream().filter[roles.contains(it.getSubsumer())]
						.map[it.subsumes].collect(Collectors.toList()))
		return availableRoles;
	}

	def Collection<EObject> computeModelElementsWithMissingAccessRights(Collection<AccessRule> rules,
			Collection<Role> availableRoles, TreeIterator<EObject> modelToFilter,
			Collection<OperationAccessRight> operationAccessRightsNeeded, boolean inheritance,
			OperationAccessRightEvaluator evaluator) {
		val EList<EObject> toBeRemoved = new BasicEList()
		while (modelToFilter.hasNext()) {
			val modelElement = modelToFilter.next();
			// only use rules for the given element and for the given role. In case the
			// OperationAccessRights are split across multiple rules, use all rights.
			val availableRights = rules.stream().
			// remove all rules that restrict the access to other elements
					filter[modelElement.equals(it.getElement())].
					// remove all rules that contain roles that are not available
					filter[availableRoles.contains(it.getRole())].
					// map on the OperationAccessRights that those rules contain
					map[it.getOperationAccessRights].
					// flatMap them to contain the list of OperationAccessRights the given roles
					// have for this element
					flatMap[it.stream].collect(Collectors.toList());
			if (inheritance) {
				availableRights.addAll(calculateRightsWithInheritance(modelElement, rules, availableRoles));
			}
			if (!evaluator.hasAccess(availableRights, operationAccessRightsNeeded)) {
				toBeRemoved.add(modelElement);
			}
		}
		return toBeRemoved;
	}

	def List<OperationAccessRight> calculateRightsWithInheritance(EObject object,
			Collection<AccessRule> rules, Collection<Role> availableRoles) {
		val availabelRights = new ArrayList()
		var parent = object
		val visitedParents = new HashSet()
		while (parent !== null && !visitedParents.contains(parent)) {
			val recent = parent;
			visitedParents.add(parent);
			availabelRights.addAll(rules.stream().filter[availableRoles.contains(it.getRole())]
					.filter[recent.equals(it.getElement())].map[it.getOperationAccessRights]
					.flatMap[it.stream].collect(Collectors.toList()));
			parent = parent.eContainer();
		}
		return availabelRights;
	}

	
		override boolean addAccessRule(EObject modified, EObject containment,
			Collection<OperationAccessRight> grantedRights, Collection<Role> availableRoles) {
		if (getRuleDatabase().getAccessrules().stream().anyMatch[it.getElement().equals(modified)]) {
			return false;
		}
		for (Role role : availableRoles) {
			val newRule = AccesscontrolsystemFactory.eINSTANCE.createAccessRule()
			newRule.setElementContainingOrReferencing(containment)
			newRule.setElement(modified)
			newRule.setName(role.getName() + "," + modified.eClass().getName() + "," + modified.hashCode())
			newRule.setRole(role)
			newRule.getOperationAccessRights()
					.addAll(OperationAccessRightUtil.findOrAddRights(grantedRights, ruleDatabase.getAcessrightprovider()))
			ruleDatabase.getAccessrules().add(newRule)
		}
		return true;
	}

	override removeAccessRules(EObject modified) {
		return getRuleDatabase().getAccessrules().removeAll(ruleDatabase.getAccessrules().stream()
						.filter[it.getElement().equals(modified)].collect(Collectors.toSet()));

	}
	
}