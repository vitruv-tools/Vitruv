package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

public class TransModelObjectCopier {
    private final ModelTranslator modelTranslator;

    public TransModelObjectCopier(ModelTranslator translator) {
        this.modelTranslator = translator;
    }

    public EObject addCopyInTarget(EObject sourceObj) {
        EStructuralFeature containingFeature = sourceObj.eContainingFeature();
        int position = -1;
        if (containingFeature.isMany()) {
            EList<?> containingList = (EList<?>) sourceObj.eContainer().eGet(sourceObj.eContainingFeature());
            position = containingList.indexOf(sourceObj);
        }
        return addCopyInTarget(sourceObj, position);
    }

    public EObject addCopyInTarget(EObject sourceObj, int targetPosition) {
        try {
            EObject container = modelTranslator.lookupInTarget(sourceObj.eContainer());
            return addCopyInContainer(sourceObj, container, targetPosition);
        } catch (NoSuchElementException e) {
            throw new ParentMissingException();
        }
    }

    private EObject addCopyInContainer(EObject sourceObj, EObject targetContainer, int targetPosition) {
        EStructuralFeature targetFeature = Utils.getStructuralFeatureInTargetContainer(targetContainer, sourceObj);

        // Copier copier = new EcoreUtil.Copier(true, true);
        // EObject newObj = copier.copy(sourceObj);

        EClass newObjClass = sourceObj.eClass();
        EObject newObj = newObjClass.getEPackage().getEFactoryInstance().create(newObjClass);

        if (targetPosition >= 0) {
            @SuppressWarnings("unchecked")
            EList<EObject> targetList = (EList<EObject>) targetContainer.eGet(targetFeature);
            targetList.add(targetPosition, newObj);
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

    @SuppressWarnings("serial")
    public static class ParentMissingException extends NoSuchElementException {
    }
}
