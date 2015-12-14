package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.DefaultTUIDCalculatorAndResolver;

public class EcoreHelper {
	public static String createSensibleString(EObject eObject) {
		String className = eObject.eClass().getName();

		String name = EcoreHelper.getStringValueOfAttribute(eObject, "name");
		if (name == null)
			name = EcoreHelper.getStringValueOfAttribute(eObject, "entityName");
		if (name == null)
			name = EcoreHelper.getStringValueOfAttribute(eObject, "id");
		if (name == null)
			return className;

		return String.join(" ", className, name);
	}

	// TODO: more efficient with EcoreUtil?
	public static Set<EObject> findOppositeForFeature(EObject target, EReference reference) {
		Set<EObject> result = new HashSet<EObject>();

		Resource resource = target.eResource();
		TreeIterator<Object> iter = EcoreUtil.getAllProperContents(resource, true);
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof EObject) {
				EObject eobj = (EObject) obj;

				if (reference.getEContainingClass().isSuperTypeOf(eobj.eClass())) {
					Object referencedObject = eobj.eGet(reference);
					if ((referencedObject instanceof EList<?>) && (((EList<?>) referencedObject).contains(target))) {
						result.add(eobj);
					} else if (referencedObject.equals(target)) {
						result.add(eobj);
					}
				}
			}
		}

		return result;

	}

	public static Set<EObject> findOppositeForFeatures(EObject target, EReference... references) {
		Set<EObject> currentLevel = new HashSet<EObject>();
		currentLevel.add(target);
		Set<EObject> nextLevel = new HashSet<EObject>();

		for (EReference reference : references) {
			nextLevel.clear();
			for (EObject eObj : currentLevel)
				nextLevel.addAll(findOppositeForFeature(eObj, reference));

			// swap references
			Set<EObject> swap = currentLevel;
			currentLevel = nextLevel;
			nextLevel = swap;
		}

		return currentLevel;
	}

	/**
	 * Uses {@link #findOppositeForFeature(EObject, EReference)} to find
	 * referencing objects of the given type. If more than one such object is
	 * found, an exception is thrown.
	 */
	public static <T extends EObject> Optional<T> findOneReferencee(EObject target, EReference reference,
			Class<T> type) {
		final Set<T> oppositeEObjects = JavaHelper.filterType(findOppositeForFeature(target, reference), type)
				.collect(Collectors.toSet());

		// TODO is the restriction <= 1 needed?

		if (oppositeEObjects.size() > 1) {
			throw new IllegalArgumentException("There is more than one " + type.getName() + " referencing "
					+ target.toString() + " by " + reference.toString());
		}
		return oppositeEObjects.stream().findAny();
	}

	/**
	 * Copied from
	 * {@link DefaultTUIDCalculatorAndResolver#getValueOfAttribute(EObject, String)}
	 * . Tries to obtain the value of the feature named <code>featureName</code>
	 * from the given {@link EObject}. If the feature is of type
	 * <code>EString</code>, it is returned directly, if it is of type
	 * <code>EInt</code>, it is converted to the integers string representation.
	 */
	// TODO: refactor into helper class that both
	// DefaultTUIDCalculatorAndResolver and AttributeTUIDCalculatorAndResolver
	// use.
	public static String getStringValueOfAttribute(final EObject eObject, final String featureName) {
		EStructuralFeature idFeature = eObject.eClass().getEStructuralFeature(featureName);
		if (idFeature != null && idFeature instanceof EAttribute) {
			EAttribute idAttribute = (EAttribute) idFeature;
			EDataType eAttributeType = idAttribute.getEAttributeType();

			if (eAttributeType.getInstanceClassName().equals(String.class.getName())) {
				return (String) eObject.eGet(idFeature);
			}

			if (eAttributeType.getInstanceClassName().equals("int")) {
				return String.valueOf(eObject.eGet(idFeature));
			}
		}
		return null;
	}

	/**
	 * Determines whether an {@link EObject} is the root object in its resource.
	 */
	public static boolean isRootInResource(EObject object) {
		return ((object.eContainer() == null && object.eResource() != null) || object.eContainer().equals(object.eResource()));
	}
}
