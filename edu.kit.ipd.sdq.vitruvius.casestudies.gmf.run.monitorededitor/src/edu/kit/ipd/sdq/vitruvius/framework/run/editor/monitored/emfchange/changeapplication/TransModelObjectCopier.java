/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * {@link TransModelObjectCopier} copies objects between models whose containment structures are
 * equal "above" the respective copied object, i.e. with respect to the respective positions in the
 * containments on the path from the root object to the copied object.
 */
class TransModelObjectCopier {
    private final ModelTranslator modelTranslator;

    public TransModelObjectCopier(ModelTranslator translator) {
        this.modelTranslator = translator;
    }

    /**
     * Adds a copy of <code>sourceObj</code> at the corresponding structural location in the target
     * model.
     * 
     * @param sourceObj
     *            The object to be copied.
     * @return The copy of <code>sourceObj</code>.
     * 
     * @throws ParentMissingException
     *             The corresponding containment of the given object could not be found.
     */
    public EObject addCopyInTarget(EObject sourceObj) {
        EStructuralFeature containingFeature = sourceObj.eContainingFeature();
        int position = -1;
        if (containingFeature.isMany()) {
            EList<?> containingList = (EList<?>) sourceObj.eContainer().eGet(sourceObj.eContainingFeature());
            position = containingList.indexOf(sourceObj);
        }
        return addCopyInTarget(sourceObj, position);
    }

    /**
     * Adds a copy of <code>sourceObj</code> in the corresponding containment feature at the given
     * index.
     * 
     * @param sourceObj
     *            The object to be copied.
     * @param targetPosition
     *            The index where the copy should be inserted in its corresponding containment; If
     *            the feature is multiplicity-one, this parameter is ignored.
     * @return The copy of <code>sourceObj</code>.
     * 
     * @throws ParentMissingException
     *             The corresponding containment of the given object could not be found.
     */
    public EObject addCopyInTarget(EObject sourceObj, int targetPosition) {
        try {
            EObject container = modelTranslator.lookupInTarget(sourceObj.eContainer());
            return addCopyInContainer(sourceObj, container, targetPosition);
        } catch (NoSuchElementException e) {
            throw new ParentMissingException();
        }
    }

    private EObject addCopyInContainer(EObject sourceObj, EObject targetContainer, int targetPosition) {
        EStructuralFeature targetFeature = Util.getStructuralFeatureInTargetContainer(targetContainer, sourceObj);

        EClass newObjClass = sourceObj.eClass();
        EObject newObj = newObjClass.getEPackage().getEFactoryInstance().create(newObjClass);

        if (targetFeature.isMany()) {
            @SuppressWarnings("unchecked")
            EList<EObject> targetList = (EList<EObject>) targetContainer.eGet(targetFeature);
            // targetList.add(targetPosition, newObj);
            targetList.add(newObj);
        } else {
            targetContainer.eSet(targetFeature, newObj);
        }

        return newObj;
    }

    protected Resource getSourceResource() {
        return modelTranslator.getSourceResource();
    }

    protected ModelTranslator getModelTranslator() {
        return modelTranslator;
    }

    /**
     * {@link ParentMissingException} indicates that no target location for the object to be copied
     * could be found.
     */
    @SuppressWarnings("serial")
    public static class ParentMissingException extends NoSuchElementException {
    }
}
