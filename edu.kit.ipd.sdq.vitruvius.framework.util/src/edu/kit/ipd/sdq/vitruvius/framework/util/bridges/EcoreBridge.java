/*******************************************************************************
 * Copyright (c) 2012 University of Luxembourg and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Max E. Kramer - initial API and implementation
 ******************************************************************************/
package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A utility class hiding details of the Ecore metamodel part of the Eclipse Modeling Framework API
 * for recurring tasks that are not project-specific.<br/>
 * <br/>
 * (Note that it is disputable whether this class conforms to the bridge pattern as we are currently
 * only providing one implementation and the "abstractions" can be regarded as low-level.)
 *
 * @author Max E. Kramer
 */
public final class EcoreBridge {
    /** Utility classes should not have a public or default constructor. */
    private EcoreBridge() {
    }

    /**
     * Returns a list containing the values for the given feature and eObject if the feature is
     * many-typed and {@code null} otherwise.
     *
     * @param eObject
     *            an EObject having the given feature
     * @param feature
     *            a feature of the given eObject
     * @return a list containing the values for the given feature and eObject
     */
    @SuppressWarnings("unchecked")
    public static List<EObject> getFeatureValuesIfManyTyped(final EObject eObject, final EStructuralFeature feature) {
        if (feature.isMany()) {
            final Object featureValue = eObject.eGet(feature);
            if (featureValue instanceof List) {
                return (List<EObject>) featureValue;
            } else {
                throw new RuntimeException("The eFeature '" + feature + "' is many-valued but has a value '"
                        + featureValue + "' that is not a List!");
            }
        } else {
            return null;
        }
    }

    /**
     * Returns the value for the given feature and eObject if the feature is not many-typed and
     * {@code null} otherwise.
     *
     * @param eObject
     *            an EObject having the given feature
     * @param feature
     *            a feature of the given eObject
     * @return the values for the given feature and eObject
     */
    public static Object getFeatureValueIfNotManyTyped(final EObject eObject, final EStructuralFeature feature) {
        if (!feature.isMany()) {
            final Object featureValue = eObject.eGet(feature);
            if (!(featureValue instanceof List)) {
                return featureValue;
            } else {
                throw new RuntimeException("The eFeature '" + feature + "' is not many-valued but has a value '"
                        + featureValue + "' that is a List!");
            }
        } else {
            return null;
        }
    }

    /**
     * Returns whether the given objects are "equal" using
     * {@link org.eclipse.emf.ecore.util.EcoreUtil#equals(EObject, EObject)
     * EcoreUtil.equals(EObject, EObject)} for EObjects and {@link java.lang.Object#equals(Object)
     * Object.equals(Object)} otherwise.
     *
     * @param object1
     *            an object
     * @param object2
     *            an object
     * @return {@code true} if the objects are equal and {@code false} otherwise
     */
    public static boolean equals(final Object object1, final Object object2) {
        if (object1 instanceof EObject && object2 instanceof EObject) {
            return EcoreUtil.equals((EObject) object1, (EObject) object2);
        } else {
            if (object1 != null) {
                return object1.equals(object2);
            } else { // object1 == null
                return object2 == null;
            }
        }
    }

    /**
     * Returns whether the given feature is an attribute of type String.
     *
     * @param feature
     *            a feature
     * @return {@code true} if feature is a String attribute and {@code false} otherwise
     */
    public static boolean isStringAttribute(final EStructuralFeature feature) {
        return isAttributeOfJavaType(feature, "String");
    }

    /**
     * Returns whether the given attribute is of type String.
     *
     * @param attribute
     *            an attribute
     * @return {@code true} if attribute is of type String and {@code false} otherwise
     */
    public static boolean isStringAttribute(final EAttribute attribute) {
        return isAttributeOfJavaType(attribute, "String");
    }

    /**
     * Returns whether the given feature is an attribute that has a java type of the given name
     * (e.g. "String" for "java.lang.String").
     *
     * @param feature
     *            a feature
     * @param typeName
     *            a java.lang type name
     * @return {@code true} if attribute is of type boolean and {@code false} otherwise
     */
    public static boolean isAttributeOfJavaType(final EStructuralFeature feature, final String typeName) {
        if (feature instanceof EAttribute) {
            return isAttributeOfJavaType((EAttribute) feature, typeName);
        }
        return false;
    }

    /**
     * Returns whether the given attribute has a java type of the given name (e.g. "String" for
     * "java.lang.String").
     *
     * @param attribute
     *            an attribute
     * @param typeName
     *            a java.lang type name
     * @return {@code true} if attribute is of type boolean and {@code false} otherwise
     */
    public static boolean isAttributeOfJavaType(final EAttribute attribute, final String typeName) {
        if (attribute != null) {
            final EClassifier attributeType = attribute.getEType();
            if (attributeType != null) {
                return ("java.lang." + typeName).equals(attributeType.getInstanceClassName());
            }
        }
        return false;
    }

    /**
     * Returns whether the given feature is an attribute of type Boolean.
     *
     * @param feature
     *            a feature
     * @return {@code true} if feature is a Boolean attribute and {@code false} otherwise
     */
    public static boolean isBooleanAttribute(final EStructuralFeature feature) {
        return isAttributeOfJavaType(feature, "Boolean");
    }

    /**
     * Returns whether the given attribute is of type Boolean.
     *
     * @param attribute
     *            an attribute
     * @return {@code true} if attribute is of type Boolean and {@code false} otherwise
     */
    public static boolean isBooleanAttribute(final EAttribute attribute) {
        return isAttributeOfJavaType(attribute, "Boolean");
    }

    /**
     * Returns a collection containing all reference settings for references to the given EObject.
     *
     * @param referencedEObject
     *            the referenced EObject
     * @return a collection containing all settings for references to the given EObject
     */
    public static Collection<Setting> getReferencesTo(final EObject referencedEObject) {
        ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
                .getCrossReferenceAdapter(referencedEObject);
        if (crossReferenceAdapter == null) {
            crossReferenceAdapter = new ECrossReferenceAdapter();
            referencedEObject.eResource().getResourceSet().eAdapters().add(crossReferenceAdapter);
        }
        return crossReferenceAdapter.getInverseReferences(referencedEObject);
    }

    /**
     * @return the classifier for the EObject metametaclass
     */
    public static EClassifier getEObjectClassifier() {
        return EcorePackage.eINSTANCE.getEClassifier("EObject");
    }

    /**
     * Returns the value for the feature with the given name and the given eObject.
     *
     * @param eObject
     *            an EObject having a feature with the given name
     * @param featureName
     *            the name of a feature of the given eObject
     * @return the value for the feature and eObject
     */
    public static Object getValueForFeatureName(final EObject eObject, final String featureName) {
        final EStructuralFeature feature = getFeatureForName(eObject, featureName);
        return eObject.eGet(feature);
    }

    /**
     * Returns the feature with the given name for the given eObject.
     *
     * @param eObject
     *            an EObject having a feature with the given name
     * @param featureName
     *            the name of a feature of the given eObject
     * @return the feature with the given name for the given eObject.
     */
    public static EStructuralFeature getFeatureForName(final EObject eObject, final String featureName) {
        if (eObject != null) {
            final EClass eClass = eObject.eClass();
            if (eClass != null) {
                return eClass.getEStructuralFeature(featureName);
            }
        }
        return null;
    }

    /**
     * Returns a set containing all direct and indirect contents of the given EObjects.
     *
     * @param eObjects
     *            container EObjects
     * @return a set containing all direct and indirect contents of the given EObjects
     *
     * @see org.eclipse.emf.ecore.EObject#eAllContents() EObject.eAllContents()
     */
    public static Set<EObject> getAllContentsSet(final Set<EObject> eObjects) {
        final Set<EObject> allContentsSet = new HashSet<EObject>(eObjects);
        for (final EObject eObject : eObjects) {
            final Set<EObject> currentAllContentsSet = getAllContentsSet(eObject);
            allContentsSet.addAll(currentAllContentsSet);
        }
        return allContentsSet;
    }

    /**
     * Returns a set containing all direct and indirect contents of the given EObject.
     *
     * @param eObject
     *            container EObject
     * @return a set containing all direct and indirect contents of the given EObject
     *
     * @see org.eclipse.emf.ecore.EObject#eAllContents() EObject.eAllContents()
     */
    public static Set<EObject> getAllContentsSet(final EObject eObject) {
        final Iterator<EObject> allContentsIterator = eObject.eAllContents();
        return getAllContentsSet(allContentsIterator);
    }

    /**
     * Returns a set containing all direct and indirect contents that can be resolved using the
     * given iterator.
     *
     * @param allContentsIterator
     *            an iterator iterating over contained EObjects
     * @return a set containing all direct and indirect contents resolved through the iterator
     *
     * @see org.eclipse.emf.ecore.EObject#eAllContents() EObject.eAllContents()
     */
    protected static Set<EObject> getAllContentsSet(final Iterator<EObject> allContentsIterator) {
        final Set<EObject> allContentsSet = new HashSet<EObject>();
        while (allContentsIterator.hasNext()) {
            final EObject nextContent = allContentsIterator.next();
            allContentsSet.add(nextContent);
        }
        return allContentsSet;
    }

    /**
     * Returns an iterable for iterating over all direct and indirect contents of the given EObject.
     *
     * @param eObject
     *            container EObject
     * @return a direct and indirect content iterating iterable
     *
     * @see org.eclipse.emf.ecore.EObject#eAllContents() EObject.eAllContents()
     */
    public static Iterable<EObject> getAllContents(final EObject eObject) {
        return JavaBridge.toIterable(eObject.eAllContents());
    }

    /**
     * Returns whether the given eObject is contained indirectly in the given collection. Returns
     * {@code false} if the eObject is contained in the collection but no member of the collection
     * is a real ancestor of the eObject (i.e. different from the eObject).
     *
     * @param eObject
     *            an eObject
     * @param possibleContainers
     *            a collection of possible containers
     * @return {@code true} when the given EObjects is contained in a member of the collection but
     *         not itself member of the collection
     */
    public static boolean isReallyIndirectlyContained(final EObject eObject,
            final Collection<EObject> possibleContainers) {
        Collection<EObject> cleanedPossibleContainers;
        if (possibleContainers.contains(eObject)) {
            cleanedPossibleContainers = new ArrayList<EObject>(possibleContainers);
            cleanedPossibleContainers.remove(eObject);
        } else {
            cleanedPossibleContainers = possibleContainers;
        }
        return EcoreUtil.isAncestor(cleanedPossibleContainers, eObject);
    }

    /**
     * Returns whether the given EObject is directly or indirectly contained in one of the given
     * possible containers.
     *
     * @param eObject
     *            an EObject
     * @param possibleContainers
     *            a collection of possible container EObjects
     * @return {@code true} when the given EObject is directly or indirectly contained in at least
     *         one of the given possible containers and {@code false} otherwise
     */
    public static boolean containedIn(final EObject eObject, final Collection<EObject> possibleContainers) {
        for (final EObject possibleContainer : possibleContainers) {
            if (containedIn(eObject, possibleContainer)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the given EObject is directly or indirectly contained in the given possible
     * container.
     *
     * @param eObject
     *            an EObject
     * @param possibleContainer
     *            a possible container EObjects
     * @return {@code true} when the given EObject is directly or indirectly contained in the given
     *         possible container and {@code false} otherwise
     */
    public static boolean containedIn(final EObject eObject, final EObject possibleContainer) {
        if (eObject == null) {
            return false;
        } else if (eObject == possibleContainer) {
            return true;
        } else {
            return containedIn(eObject.eContainer(), possibleContainer);
        }
    }

    /**
     * Ensures that the upper bound of the given feature will not be exceed once the given elements
     * are added to the the given current values. {@link java.util.RuntimeException
     * RuntimeException} if the upper bound would be exceeded and returns {@code true} otherwise.
     * The given action message is only used for output.
     *
     * @param currentValues
     *            a list containing the current values of the given feature
     * @param feature
     *            a structural feature
     * @param elementsToAdd
     *            a list of elements to be newly added to the feature
     * @param actionMessage
     *            a message describing the action to be performed if the bound is not exceeded (only
     *            for error output)
     * @return {@code true} if the upper bound of the given feature will not be exceeded once the
     *         given elements are added
     */
    public static boolean ensureUpperBoundNotExceeded(final List<EObject> currentValues,
            final EStructuralFeature feature, final List<EObject> elementsToAdd, final String actionMessage) {
        final int currentSize = currentValues.size();
        return ensureUpperBoundNotExceeded(currentSize, feature, elementsToAdd, actionMessage);
    }

    /**
     * Ensures that the upper bound of the given feature will not be exceed once the given elements
     * are added to it using the given current size (that may be different from the real current
     * size (for convenience). Throws a {@link java.util.RuntimeException RuntimeException} if the
     * upper bound would be exceeded and returns {@code true} otherwise. The given action message is
     * only used for output.
     *
     * @param currentSize
     *            the current total number of values of the given feature
     * @param feature
     *            a structural feature
     * @param elementsToAdd
     *            a list of elements to be newly added to the feature
     * @param actionMessage
     *            a message describing the action to be performed if the bound is not exceeded (only
     *            for error output)
     * @return {@code true} if the upper bound of the given feature will not be exceeded once the
     *         given elements are added
     */
    public static boolean ensureUpperBoundNotExceeded(final int currentSize, final EStructuralFeature feature,
            final List<EObject> elementsToAdd, final String actionMessage) {
        final int upperBound = feature.getUpperBound();
        final int increase = elementsToAdd.size();
        if (currentSize + increase <= upperBound || upperBound == -1) {
            return true;
        } else {
            throw new RuntimeException("Cannot " + actionMessage + "because the '" + feature.getName()
                    + "' reference would exceed the upper bound of '" + upperBound + "'!");
        }
    }

    /**
     * Return the root eObject of the tree that contains the given eObject if this tree exists and
     * otherwise the given eObject itself (beeing the root of a containment tree of depth 0).
     * 
     * @param eObject
     * @return the root
     * @author Dominik Werle
     */
    public static EObject getRootEObject(final EObject eObject) {
        return EcoreUtil.getRootContainer(eObject);
    }
    
    
    /**
     * Returns a string representation that contains the attributes
     * <code>name</name>, <code>entityName</code>, <code>id</code>
     * if any of them exists (in that order).
     * @param eObject the object to create a string representation for
     * @author Dominik Werle
     */
	public static String createSensibleString(EObject eObject) {
		String className = eObject.eClass().getName();

		String name = EcoreBridge.getStringValueOfAttribute(eObject, "name");
		if (name == null)
			name = EcoreBridge.getStringValueOfAttribute(eObject, "entityName");
		if (name == null)
			name = EcoreBridge.getStringValueOfAttribute(eObject, "id");
		if (name == null)
			return className;

		return String.join(" ", className, name);
	}

	/**
	 * Determines all objects that refer to <code>target</code> by the
	 * reference <code>reference</code>
	 * @param target the target of the references
	 * @param reference the reference to search for
	 * @return all (distinct) objects that refer to target by reference
     * @author Dominik Werle
	 */
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

	/**
	 * Determines all objects that refer to <code>target</code> by the
	 * references <code>references</code>
	 * @param target the target of the references
	 * @param reference the reference to search for
	 * @return all (distinct) objects that refer to target by references
     * @author Dominik Werle
	 */
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
	 * @author Dominik Werle
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
	 * @author Dominik Werle
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
	 * @author Dominik Werle
	 */
	public static boolean isRootInResource(EObject object) {
		return ((object.eContainer() == null && object.eResource() != null) || object.eContainer().equals(object.eResource()));
	}
}
