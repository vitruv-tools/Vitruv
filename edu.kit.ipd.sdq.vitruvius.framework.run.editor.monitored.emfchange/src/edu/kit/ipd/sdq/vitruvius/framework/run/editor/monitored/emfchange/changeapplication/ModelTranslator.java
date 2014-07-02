package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

class ModelTranslator {
    private final Resource sourceResource;
    private final Resource targetResource;
    private static final String ROOT_ELEMENT_URI = "/";

    public ModelTranslator(Resource sourceResource, Resource targetResource) {
        this.sourceResource = sourceResource;
        this.targetResource = targetResource;
    }

    public ModelTranslator getInverseTranslator() {
        return new ModelTranslator(targetResource, sourceResource);
    }

    protected List<PathElement> getPathFromRootElement(EObject obj) {
        if (obj.eContainer() == null) {
            List<PathElement> result = new LinkedList<>();
            result.add(new PathElement(obj, -1));
            return result;
        } else {
            EObject container = obj.eContainer();
            List<PathElement> pathToContainer = getPathFromRootElement(container);

            int index = 0;
            EStructuralFeature contFeat = obj.eContainingFeature();

            if (contFeat.isMany()) {
                EList<?> containingList = (EList<?>) container.eGet(obj.eContainingFeature());
                index = containingList.indexOf(obj);
            }

            pathToContainer.add(pathToContainer.size(), new PathElement(obj, index));
            return pathToContainer;
        }
    }

    private EObject walkDownPath(EObject rootObject, List<PathElement> path) {
        if (path.isEmpty()) {
            return rootObject;
        } else {
            PathElement pe = path.remove(0);
            EStructuralFeature targetFeature = Utils.getStructuralFeatureInTargetContainer(rootObject, pe.getObj());

            EObject stepElement;
            if (targetFeature.isMany()) {
                EList<?> containingList = (EList<?>) rootObject.eGet(targetFeature);
                if (pe.getPosition() >= containingList.size()) {
                    throw new NoSuchElementException();
                }
                stepElement = (EObject) containingList.get(pe.getPosition());
            } else {
                stepElement = (EObject) rootObject.eGet(targetFeature);
            }
            return walkDownPath(stepElement, path);
        }
    }

    public EObject lookupInTarget(EObject sourceObj) {
        List<PathElement> pathToSource = getPathFromRootElement(sourceObj);
        pathToSource.remove(0); // remove root element from path
        try {
            return walkDownPath(targetResource.getEObject(ROOT_ELEMENT_URI), pathToSource);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(sourceObj.toString());
        }
    }

    protected Resource getSourceResource() {
        return sourceResource;
    }

    protected static class PathElement {
        private final int position;
        private final EObject obj;

        public PathElement(EObject obj, int position) {
            this.position = position;
            this.obj = obj;
        }

        public int getPosition() {
            return position;
        }

        public EObject getObj() {
            return obj;
        }
    }

}
