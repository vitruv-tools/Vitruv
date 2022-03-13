package accesscontrol.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrol.ResourceSetFilter;
import accesscontrol.internal.helper.RuleDatabaseUtil;
import accesscontrol.internal.helper.AccessControlImpl;
import accesscontrolsystem.AccessRule;
import accesscontrolsystem.AccesscontrolsystemFactory;
import accesscontrolsystem.RuleDatabase;
import accesscontrolsystem.accessright.OperationAccessRight;
import accesscontrolsystem.role.Role;
import accesscontrolsystem.role.RoleProvider;

public class FilteredResourceSet implements ResourceSetFilter {

	private ResourceSet unfiltered;
	private RuleDatabase ruleDatabase;
	private ResourceSet filtered;
	private final OperationAccessRightEvaluator evaluator;
	private Collection<Integer> directlyAvailableRoleIndices;
	// copy, original
	private Map<EObject, EObject> correspondentObjects;
	// copy, original
	private Map<Resource, Resource> correspondentResources;

	/**
	 * 
	 * @param ruleDatabase the {@link RuleDatabase} which contains access control information
	 * @param directlyAvailableRoleIndices information to identify the available roles (Right now indices in the {@link RuleDatabase#getRoleprovider()} {@link RoleProvider#getRole()} List)
	 * @param evaluator an evaluator to evaluate {@link OperationAccessRight}s
	 */
	public FilteredResourceSet(RuleDatabase ruleDatabase, Collection<Integer> directlyAvailableRoleIndices,
			OperationAccessRightEvaluator evaluator) {
		Objects.requireNonNull(evaluator);
		this.evaluator = evaluator;
		initializeEmptyInternalDataStructures();
		this.unfiltered = new ResourceSetImpl();
		this.ruleDatabase = ruleDatabase == null ? createNewAccessControlSystem(String.valueOf(this.hashCode()))
				: ruleDatabase;
		this.directlyAvailableRoleIndices = Optional.ofNullable(directlyAvailableRoleIndices)
				.orElse(Collections.emptySet());
	}
	
	@Override
	public boolean addAccessRule(EObject modified) {
		return addAccessRule(modified, null, computeRoles(), List.of(evaluator.allowRead(), evaluator.allowWrite()));

	}

	@Override
	public boolean addAccessRule(EObject modified, EObject containment, Collection<Role> roles) {
		return addAccessRule(modified, containment, roles, List.of(evaluator.allowRead()));

	}

	@Override
	public boolean addAccessRule(EObject modified, EObject containment) {
		return addAccessRule(modified, containment, computeRoles());

	}

	@Override
	public boolean addAccessRule(EObject modified, EObject containment, Collection<Role> roles,
			Collection<OperationAccessRight> grantedRights) {
		if (getRuleDatabase().getAccessrules().stream().anyMatch(it -> it.getElement().equals(modified))) {
			return false;
		}
		for (Role role : roles) {
			AccessRule newRule = AccesscontrolsystemFactory.eINSTANCE.createAccessRule();
			newRule.setContainment(containment);
			newRule.setElement(modified);
			newRule.setName(role.getName() + "," + modified.eClass().getName() + "," + modified.hashCode());
			newRule.setRole(role);
			newRule.getOperationAccessRights()
					.addAll(evaluator.findExistingRights(grantedRights, ruleDatabase.getAcessrightprovider()));
			ruleDatabase.getAccessrules().add(newRule);
		}
		return true;
	}

	@Override
	public boolean removeAccessRules(EObject modified) {
//		System.out.println("perhaps removed rule for " + modified);
		return getRuleDatabase().getAccessrules().removeAll(ruleDatabase.getAccessrules().stream()
						.filter(it -> it.getElement().equals(modified)).collect(Collectors.toSet()));

	}

	@Override
	public Resource getCorrespondingResource(Resource resource) {
		return correspondentResources.get(resource);
	}

	@Override
	public EObject getCorrespondentObject(EObject object) {
		return correspondentObjects.get(object);
	}

	@Override
	public boolean canModify(final Collection<EObject> toModify) {
		if (toModify == null || toModify.isEmpty())
			return true;

		var eObjectsWithMissingAccessRights = AccessControlImpl.computeElementsWithMissingAccessRights(unfiltered,
				computeRoles(), evaluator.neededRightsModifying(), ruleDatabase, evaluator);
		var unfilteredToModify = mapToCorrespondingEObjects(toModify);
		Map<EObject, Boolean> exists = checkIfObjectsCanBeModified(unfilteredToModify);
		return evaluateExistsMap(exists, eObjectsWithMissingAccessRights, unfilteredToModify);
	}

	private boolean evaluateExistsMap(Map<EObject, Boolean> exists,
			Map<Resource, Collection<EObject>> eObjectsWithMissingAccessRights, List<EObject> unfilteredToModify) {
		if (exists.values().stream().reduce((t, u) -> t && u).isEmpty())
			return false;
		if (Boolean.FALSE.equals(exists.values().stream().reduce((t, u) -> t && u).get()))
			return false;
		for (Collection<EObject> resource : eObjectsWithMissingAccessRights.values()) {
			for (EObject object : resource) {
				if (unfilteredToModify.contains(object)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ResourceSet filter(ResourceSet resourceSet) {
		return filter(resourceSet, evaluator.neededRightsViewing());
	}

	@Override
	public ResourceSet filter(ResourceSet resourceSet, Collection<OperationAccessRight> needed) {
		extractData(resourceSet);
		constructCorrespondingResourcesAndEObjects();
		removeObjectsWithMissingAccessRights(AccessControlImpl.computeElementsWithMissingAccessRights(unfiltered,
				computeRoles(), needed, ruleDatabase, evaluator));
		return filtered;
	}

	private void constructCorrespondingResourcesAndEObjects() {
		for (int i = 0; i < unfiltered.getResources().size(); i++) {
			Resource resource = unfiltered.getResources().get(i);
			if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof RuleDatabase)
				continue;
			if (isWritableUmlResource(resource)) {
				createCorrespondentUMLResource(resource);
			} else {
				Resource correspondantResource = createAndAddCorrespondentResource(resource);
				for (EObject object : resource.getContents()) {
					createAndAddCorrespondentEObjects(correspondantResource, object);
				}
			}
		}
	}
	
	private Map<EObject, Boolean> checkIfObjectsCanBeModified(List<EObject> unfilteredToModify) {
		Map<EObject, Boolean> exists = new HashMap<>();
		unfilteredToModify.stream().forEach(t -> exists.put(t, false));
		for (Resource resource : unfiltered.getResources()) {
			if (resource.getContents().isEmpty() || resource.getContents().get(0) instanceof RuleDatabase)
				continue;
			Iterator<EObject> it = resource.getAllContents();
			while (it.hasNext()) {
				EObject next = it.next();
				if (unfilteredToModify.contains(next)) {
					exists.put(next, true);
				}
			}
		}
		return exists;
	}

	private List<EObject> mapToCorrespondingEObjects(final Collection<EObject> toModify) {
		return toModify.stream().map((EObject it) -> {
			if (getCorrespondentObject(it) == null) {
				return it;
			}
			return getCorrespondentObject(it);
		}).collect(Collectors.toList());
	}

	/*------------------------------------ Copy from the uml special case in vitruv ------------------------------------------- */
	private void createCorrespondentUMLResource(Resource resource) {
		try {
			Resource copy = copyUmlModel(resource, filtered);
			correspondentResources.put(copy, resource);
			// copy and resource are 1:1 copies so we use the TreeIterator to add
			// correspondences between them
			Iterator<EObject> copyIterator = copy.getAllContents();
			Iterator<EObject> originalIterator = resource.getAllContents();
			while (copyIterator.hasNext()) {
				correspondentObjects.put(copyIterator.next(), originalIterator.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isWritableUmlResource(Resource resource) {
		return resource.getURI().fileExtension().equals("uml");
	}

	private static Resource copyUmlModel(Resource originalResource, ResourceSet newResourceSet) throws IOException {
		var originalURI = originalResource.getURI();
		var tempFilePath = Files.createTempFile(null, "." + originalURI.fileExtension());
		var tempURI = URI.createFileURI(tempFilePath.toString());
		originalResource.setURI(tempURI);
		originalResource.save(null);
		originalResource.setURI(originalURI);
		var viewResource = newResourceSet.getResource(tempURI, true);
		viewResource.setURI(originalURI);
		Files.delete(tempFilePath);
		EcoreUtil.resolveAll(viewResource);
		return viewResource;
	}
	
	/*------------------------------------ Copy from the uml special case in vitruv end ------------------------------------------- */

	private void removeObjectsWithMissingAccessRights(
			Map<Resource, Collection<EObject>> eObjectsWithMissingAccessRights) {
		List<EObject> remove = new ArrayList<>();
		for (Resource correspondentResource : filtered.getResources()) {
			var toDeleteResource = eObjectsWithMissingAccessRights
					.get(correspondentResources.get(correspondentResource));
			var iterator = correspondentResource.getAllContents();
			while (iterator.hasNext()) {
				EObject copy = iterator.next();
				if (toDeleteResource != null && toDeleteResource.contains(correspondentObjects.get(copy))) {
					remove.add(copy);
					this.correspondentObjects.remove(copy);
				}
			}
		}
		EcoreUtil.deleteAll(remove, true);
		EcoreUtil.resolveAll(filtered);
	}

	private void initializeEmptyInternalDataStructures() {
		this.filtered = new ResourceSetImpl();
		this.correspondentObjects = new HashMap<>();
		this.correspondentResources = new HashMap<>();
	}

	private void extractRuleDatabaseFromUnfiltered() {
		unfiltered.getResources().stream()
				.filter(t -> !t.getContents().isEmpty() && (t.getContents().get(0) instanceof RuleDatabase)).findFirst()
				.ifPresent(t -> ruleDatabase = (RuleDatabase) t.getContents().get(0));
	}

	private boolean containsRuleDatabase(ResourceSet set) {
		return ruleDatabaseCount(set) == 1;
	}

	private int ruleDatabaseCount(ResourceSet set) {
		return (int) set.getResources().stream()
				.filter(t -> !t.getContents().isEmpty() && t.getContents().get(0) instanceof RuleDatabase).count();
	}

	private RuleDatabase createNewAccessControlSystem(String name) {
		return RuleDatabaseUtil.createRuleDatabase(
				URI.createFileURI(new File("").getAbsolutePath() + "/vsum/" + name + ".accesscontrolsystem"),
				this.unfiltered, this.evaluator);
	}

	private void extractData(ResourceSet unfiltered) {
		// different accesscontrolsystems are not permitted
		if (ruleDatabaseCount(unfiltered) > 1) {
			throw new IllegalArgumentException();
		}
		if (!containsRuleDatabase(unfiltered)) {
			// no ruleDatabase part of the model, so add a default one and add it here
			unfiltered.getResources().add(ruleDatabase.eResource());
			this.unfiltered = unfiltered;
		} else {
			// the given set contains a ruleDatabase so we should use that
			this.unfiltered = unfiltered;
			extractRuleDatabaseFromUnfiltered();
		}
		initializeEmptyInternalDataStructures();
		EcoreUtil.resolveAll(unfiltered);
	}

	private Collection<Role> computeRoles() {
		var roles = ruleDatabase.getRoleprovider().getRole();
		var availableRoles = new ArrayList<Role>();
		for (int index = 0; index < roles.size(); index++) {
			if (directlyAvailableRoleIndices.contains(index)) {
				availableRoles.add(roles.get(index));
			}
		}
		return availableRoles;
	}

	/**
	 * Gives direct access to the underlying {@link RuleDatabase}. For testing
	 * purpose only! Removes rules without elements!
	 * 
	 * @deprecated
	 * 
	 * @return
	 */
	@Deprecated(since = "0.1", forRemoval = true)
	public RuleDatabase getRuleDatabase() {
		ruleDatabase.getAccessrules().removeAll(ruleDatabase.getAccessrules().stream()
				.filter(it -> it.getElement() == null).collect(Collectors.toSet()));
		return ruleDatabase;
	}

	private void createAndAddCorrespondentEObjects(Resource correspondantResource, EObject object) {
		Copier copier = new Copier();
		EObject copy = copier.copy(object);
		correspondantResource.getContents().add(copy);
		copier.copyReferences();
		this.correspondentObjects.put(copy, object);
		// now add correspondences for all child objects
		addInnerCorrespondences(object, copy);
	}

	private void addInnerCorrespondences(EObject object, EObject copy) {
		var copyIterator = copy.eAllContents();
		var originalIterator = object.eAllContents();
		while (copyIterator.hasNext()) {
			var innerCopy = copyIterator.next();
			var innerOriginal = originalIterator.next();
			this.correspondentObjects.put(innerCopy, innerOriginal);
		}
	}

	private Resource createAndAddCorrespondentResource(Resource resource) {
		Resource correspondantResource = filtered.createResource(resource.getURI());
		correspondentResources.put(correspondantResource, resource);
		return correspondantResource;
	}

}
