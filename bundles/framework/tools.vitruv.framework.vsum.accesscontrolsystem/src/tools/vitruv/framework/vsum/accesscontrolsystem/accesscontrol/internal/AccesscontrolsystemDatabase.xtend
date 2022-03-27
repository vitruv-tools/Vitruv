package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.internal

import java.io.File
import java.util.Collection
import java.util.Map
import java.util.Random
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.vsum.accesscontrolsystem.AccesscontrolsystemFactory
import tools.vitruv.framework.vsum.accesscontrolsystem.RuleDatabase
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightEvaluator
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightUtil
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.AccessrightFactory
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.OperationAccessRight
import tools.vitruv.framework.vsum.accesscontrolsystem.role.Role
import tools.vitruv.framework.vsum.accesscontrolsystem.role.RoleFactory

interface AccesscontrolsystemDatabase extends RuleDatabase {

	static final String DEFAULT_ROLE_NAME = "Admin"

	/**
	 * {@link tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.ResourceSetFilter#addAccessRule interface method}
	 */
	def boolean addAccessRule(EObject modified, EObject containment, Collection<OperationAccessRight> grantedRights,
		Collection<Role> availableRoles)

	/**
	 * {@link tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.ResourceSetFilter#removeAccessRules interface method}
	 */
	def boolean removeAccessRules(EObject removed)

	def RuleDatabase getRuleDatabase()

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
	def Map<Resource, Collection<EObject>> computeElementsWithMissingAccessRights(
		Collection<Resource> availableResources, Collection<Role> roles,
		Collection<OperationAccessRight> operationAccessRightsNeeded,
		OperationAccessRightEvaluator evaluator)

	def static RuleDatabase createRuleDatabase(URI uri) {
		val ruleDatabase = AccesscontrolsystemFactory.eINSTANCE.createRuleDatabase()
		val resourceSet = new ResourceSetImpl()
		val ruleDatabaseResource = resourceSet.createResource(uri)
		ruleDatabaseResource.getContents().add(ruleDatabase)
		ruleDatabase.setRoleprovider(RoleFactory.eINSTANCE.createRoleProvider())
		ruleDatabase.setAcessrightprovider(AccessrightFactory.eINSTANCE.createAcessRightProvider())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(OperationAccessRightUtil.allowRead())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(OperationAccessRightUtil.allowWrite())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(OperationAccessRightUtil.denyRead())
		ruleDatabase.getAcessrightprovider().getOperationAccessRights().add(OperationAccessRightUtil.denyWrite())
		val admin = RoleFactory.eINSTANCE.createRole()
		admin.setName(DEFAULT_ROLE_NAME)
		ruleDatabase.getRoleprovider().getRole().add(admin)
		return ruleDatabase
	}

	def static AccesscontrolsystemDatabase create(RuleDatabase database) {
		return new AccessControlSystemDatabaseImpl(
			database === null ? createNewRuleDatabase(String.valueOf(new Random().nextInt(10000000))) : database)
	}

	static def RuleDatabase createNewRuleDatabase(String name) {
		return AccesscontrolsystemDatabase.createRuleDatabase(
			URI.createFileURI(new File("").getAbsolutePath() + "/vsum/" + name + ".accesscontrolsystem"));
	}
}
