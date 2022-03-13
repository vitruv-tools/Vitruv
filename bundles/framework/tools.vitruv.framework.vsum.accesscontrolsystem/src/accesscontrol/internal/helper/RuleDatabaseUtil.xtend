package accesscontrol.internal.helper

import accesscontrol.OperationAccessRightEvaluator
import accesscontrolsystem.AccessRule
import accesscontrolsystem.AccesscontrolsystemFactory
import accesscontrolsystem.RuleDatabase
import accesscontrolsystem.accessright.OperationAccessRight
import accesscontrolsystem.accessright.accessrightFactory
import accesscontrolsystem.role.Role
import accesscontrolsystem.role.roleFactory
import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

/**
 * 
 */
final class RuleDatabaseUtil {
	static final String DEFAULT_ROLE_NAME = "Admin"
	
	private new(){}

	def static RuleDatabase createRuleDatabase(URI uri, ResourceSet set, OperationAccessRightEvaluator evaluator) {
		val ruleDatabase = AccesscontrolsystemFactory.eINSTANCE.createRuleDatabase()
		val ruleDatabaseResource = set.createResource(uri)
		ruleDatabaseResource.getContents().add(ruleDatabase)
		ruleDatabase.setRoleprovider(roleFactory.eINSTANCE.createRoleProvider())
		ruleDatabase.setAcessrightprovider(accessrightFactory.eINSTANCE.createAcessRightProvider())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(evaluator.allowRead())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(evaluator.allowWrite())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(evaluator.denyRead())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(evaluator.denyWrite())
		val admin = roleFactory.eINSTANCE.createRole()
		admin.setName(DEFAULT_ROLE_NAME)
		ruleDatabase.getRoleprovider().getRole().add(admin)
		return ruleDatabase
	}

	def static Collection<AccessRule> addAccessRule(EObject modified, EObject containment, Collection<Role> roles,
		Collection<OperationAccessRight> availableRights) {
		val newRules = new ArrayList
		for (Role role : roles) {
			val newRule = AccesscontrolsystemFactory.eINSTANCE.createAccessRule();
			newRule.setContainment(containment);
			newRule.setElement(modified);
			newRule.setName(role.getName() + "," + modified.eClass().getName() + "," + modified.hashCode());
			newRule.setRole(role);
			newRule.getOperationAccessRights().addAll(availableRights);
			newRules.add(newRule)
		}
		return newRules
	}
}
