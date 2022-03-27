package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.internal;

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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import tools.vitruv.framework.vsum.accesscontrolsystem.RuleDatabase;
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightEvaluator;
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.ResourceFilter;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.OperationAccessRight;
import tools.vitruv.framework.vsum.accesscontrolsystem.role.Role;
import tools.vitruv.framework.vsum.accesscontrolsystem.role.RoleProvider;

public class ResourceFilterImpl implements ResourceFilter {

	private Collection<Resource> unfiltered;
	private AccesscontrolsystemDatabase database;
	private ResourceSet filtered;
	private final OperationAccessRightEvaluator evaluator;
	private Collection<Integer> directlyAvailableRoleIndices;
	// copy, original
	private Map<EObject, EObject> correspondentObjects;
	// copy, original
	private Map<Resource, Resource> correspondentResources;

	/**
	 * 
	 * @param ruleDatabase                 the {@link RuleDatabase} which contains
	 *                                     access control information
	 * @param directlyAvailableRoleIndices information to identify the available
	 *                                     roles (Right now indices in the
	 *                                     {@link RuleDatabase#getRoleprovider()}
	 *                                     {@link RoleProvider#getRole()} List)
	 * @param evaluator                    an evaluator to evaluate
	 *                                     {@link OperationAccessRight}s
	 */
	public ResourceFilterImpl(RuleDatabase ruleDatabase, Collection<Integer> directlyAvailableRoleIndices,
			OperationAccessRightEvaluator evaluator) {
		Objects.requireNonNull(evaluator);
		this.evaluator = evaluator;
		initializeEmptyInternalDataStructures();
		this.unfiltered = new ArrayList<>();
		this.database = AccesscontrolsystemDatabase.create(ruleDatabase);
		this.directlyAvailableRoleIndices = Optional.ofNullable(directlyAvailableRoleIndices)
				.orElse(Collections.emptySet());
	}

	@Override
	public Resource getSourceResource(Resource resource) {
		return correspondentResources.get(resource);
	}

	@Override
	public EObject getSourceEObject(EObject object) {
		return correspondentObjects.get(object);
	}

	@Override
	public boolean hasAccessRights(final Collection<EObject> toModify, Collection<OperationAccessRight> needed) {
		if (toModify == null || toModify.isEmpty())
			return true;

		var eObjectsWithMissingAccessRights = database.computeElementsWithMissingAccessRights(unfiltered,
				computeRoles(), needed, evaluator);
		var unfilteredToModify = mapToCorrespondingEObjects(toModify);
		Map<EObject, Boolean> exists = checkIfObjectsCanBeModified(unfilteredToModify);
		return evaluateExistsMap(exists, eObjectsWithMissingAccessRights, unfilteredToModify);
	}

	@Override
	public List<Resource> getFilteredResources() {
		return filtered.getResources();
	}

	@Override
	public boolean addAccessRule(EObject modified, EObject containment,
			Collection<OperationAccessRight> grantedRights) {
		return database.addAccessRule(modified, containment, grantedRights, computeRoles());
	}

	@Override
	public boolean removeAccessRules(EObject removed) {
		return database.removeAccessRules(removed);
	}

	public ResourceSet filter(Collection<Resource> resourceSet, Collection<OperationAccessRight> needed) {
		extractData(resourceSet);
		constructCorrespondingResourcesAndEObjects();
		removeObjectsWithMissingAccessRights(
				database.computeElementsWithMissingAccessRights(unfiltered, computeRoles(), needed, evaluator));
		return filtered;
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

	private void constructCorrespondingResourcesAndEObjects() {
		for (int index = 0; index < unfiltered.size(); index++) {
			final Resource resource = getElement(unfiltered, index);
			if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof RuleDatabase)
				continue;
			if (UMLHelper.isWritableUmlResource(resource)) {
				UMLHelper.createCorrespondentUMLResource(resource, filtered, correspondentObjects,
						correspondentResources);
			} else {
				Resource correspondantResource = createAndAddCorrespondentResource(resource);
				for (EObject object : resource.getContents()) {
					createAndAddCorrespondentEObjects(correspondantResource, object);
				}
			}
		}
	}

	private <T> T getElement(Collection<T> collection, int index) {
		var iter = collection.iterator();
		for (int i = 0; i < index; i++)
			iter.next();
		return iter.next();
	}

	private Map<EObject, Boolean> checkIfObjectsCanBeModified(List<EObject> unfilteredToModify) {
		Map<EObject, Boolean> exists = new HashMap<>();
		unfilteredToModify.stream().forEach(t -> exists.put(t, false));
		for (Resource resource : unfiltered) {
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
			if (getSourceEObject(it) == null) {
				return it;
			}
			return getSourceEObject(it);
		}).collect(Collectors.toList());
	}

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
	}

	private void initializeEmptyInternalDataStructures() {
		this.filtered = new ResourceSetImpl();
		this.correspondentObjects = new HashMap<>();
		this.correspondentResources = new HashMap<>();
	}

	private void extractRuleDatabaseFromUnfiltered() {
		unfiltered.stream().filter(t -> !t.getContents().isEmpty() && (t.getContents().get(0) instanceof RuleDatabase))
				.findFirst()
				.ifPresent(t -> database = AccesscontrolsystemDatabase.create((RuleDatabase) t.getContents().get(0)));
	}

	private boolean containsRuleDatabase(Collection<Resource> set) {
		return ruleDatabaseCount(set) == 1;
	}

	private int ruleDatabaseCount(Collection<Resource> unfiltered) {
		return (int) unfiltered.stream()
				.filter(t -> !t.getContents().isEmpty() && t.getContents().get(0) instanceof RuleDatabase).count();
	}

	private void extractData(Collection<Resource> unfiltered) {
		// different accesscontrolsystems are not permitted
		if (ruleDatabaseCount(unfiltered) > 1) {
			throw new IllegalArgumentException();
		}
		if (!containsRuleDatabase(unfiltered)) {
			// no ruleDatabase part of the model, so add a default one and add it here
			unfiltered.add(database.eResource());
			this.unfiltered = unfiltered;
		} else {
			// the given set contains a ruleDatabase so we should use that
			this.unfiltered = unfiltered;
			extractRuleDatabaseFromUnfiltered();
		}
		initializeEmptyInternalDataStructures();
	}

	private Collection<Role> computeRoles() {
		var roles = database.getRoleprovider().getRole();
		var availableRoles = new ArrayList<Role>();
		for (int index = 0; index < roles.size(); index++) {
			if (directlyAvailableRoleIndices.contains(index)) {
				availableRoles.add(roles.get(index));
			}
		}
		return availableRoles;
	}

	public RuleDatabase getRuleDatabase() {
		database.getAccessrules().removeAll(database.getAccessrules().stream()
				.filter(it -> it.getElement() == null).collect(Collectors.toSet()));
		return database.getRuleDatabase();
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
