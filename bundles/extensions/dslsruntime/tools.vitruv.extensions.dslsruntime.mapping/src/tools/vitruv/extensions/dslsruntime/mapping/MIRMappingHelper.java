package tools.vitruv.extensions.dslsruntime.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.util.command.ResourceAccess;
import tools.vitruv.framework.util.datatypes.VURI;

/**
 * Helper class for MIR mapping realizations.
 * 
 * @author Dominik Werle
 *
 */
public final class MIRMappingHelper {

	public static Collection<EObject> getReverseFeature(EObject target, EStructuralFeature feature) {
		Collection<Setting> settings = EcoreUtil.UsageCrossReferencer.find(target, target.eResource());

		Collection<EObject> result = new ArrayList<EObject>();
		for (Setting setting : settings) {
			if (feature.equals(setting.getEStructuralFeature())) {
				result.add(setting.getEObject());
			}
		}

		return result;
	}

	public static Collection<EObject> getAllAffectedObjects(EChange eChange) {
		Collection<EObject> result = new HashSet<EObject>();

		if (eChange instanceof FeatureEChange<?, ?>) {
			EObject newAffectedEObject = ((FeatureEChange<?, ?>) eChange).getAffectedEObject();
			result.add(newAffectedEObject);

			if (eChange instanceof UpdateReferenceEChange<?>) {
				UpdateReferenceEChange<?> updateEReference = (UpdateReferenceEChange<?>) eChange;
				EReference affectedFeature = updateEReference.getAffectedFeature();
				Object featureValue = newAffectedEObject.eGet(affectedFeature);

				if (featureValue instanceof Collection<?>) {
					for (Object affectedObject : (Collection<?>) featureValue) {
						if (affectedObject instanceof EObject) {
							result.add((EObject) affectedObject);
						}
					}
				} else if (featureValue instanceof EObject) {
					result.add((EObject) featureValue);
				}
			}
		} else if (eChange instanceof InsertRootEObject<?>) {
			EObject newValue = ((InsertRootEObject<?>) eChange).getNewValue();
			result.add(newValue);
		}

		return result;
	}

	public static List<Resource> getResources(Collection<EObject> eObjects) {
		return eObjects.stream().map(it -> it.eResource()).distinct().collect(Collectors.toList());
	}

	public static void addEmptyResourcesToTransformationResult(Iterable<Resource> resources) {
		for (Resource res : resources) {
			if (res.getContents().isEmpty()) {
				// Not necessary any more, because VSUM removes empty resources
				// result.addVuriToDeleteIfNotNull(VURI.getInstance(res));
			}
		}
	}

	public static void removeAll(Collection<EObject> eObjects) {
		for (EObject obj : eObjects) {
			EcoreUtil.remove(obj);
		}
	}

	public static boolean hasContainment(EObject eObject) {
		return (eObject.eContainer() != null || eObject.eResource() != null);
	}

	/**
	 * Checks the {@link EObject EObjects} supplied by <code>elementSupplier</code>
	 * for containment. If no containment is found for an object, a containment is
	 * requested from the <code>vuriProvider</code> and appended to the given
	 * {@link ChangePropagationResult} as well as to the return value of the
	 * function.
	 */
	public static Set<EObject> ensureContainments(Supplier<Iterable<EObject>> elementSupplier,
			Consumer<EObject> containmentEnsurer) {

		Set<EObject> result = new HashSet<EObject>();
		boolean nonContainedFound;
		do {
			nonContainedFound = false;
			for (EObject eObject : elementSupplier.get()) {
				if (!result.contains(eObject) && !MIRMappingHelper.hasContainment(eObject)) {
					nonContainedFound = true;
					containmentEnsurer.accept(eObject);
					result.add(eObject);
				}
			}
		} while (nonContainedFound);

		return result;
	}

	/**
	 * Resolves the given <code>relativePath</code> against the resource of
	 * <code>relativeSource</code>. If it is not contained and the URI is not
	 * absolute, returns null.
	 * 
	 * @param relativeSource
	 * @param relativePath
	 * @return
	 */
	public static VURI resolveIfRelative(EObject relativeSource, String relativePath) {
		URI relativeURI = URI.createURI(relativePath);

		if (!relativeURI.isRelative())
			return VURI.getInstance(relativeURI);
		if (relativeSource.eResource() == null)
			return null;

		return VURI.getInstance(relativeURI.resolve(relativeSource.eResource().getURI()));
	}
}
