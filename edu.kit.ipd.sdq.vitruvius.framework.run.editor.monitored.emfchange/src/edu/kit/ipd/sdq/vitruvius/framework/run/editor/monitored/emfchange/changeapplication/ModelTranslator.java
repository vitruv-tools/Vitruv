package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * {@link ModelTranslator} finds corresponding objects in models which are structurally equal with
 * respect to the location of the containment objects "above" the queried objects, i.e. their
 * respective position in the containments on the path from the root object to the queried object.
 */
class ModelTranslator {
    private static final Logger LOGGER = Logger.getLogger(ModelTranslator.class);

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

    /**
     * Constructs a representation of the path from the root element to the given EObject.
     * 
     * @param obj
     *            The source model object to which a path should be computed.
     * @return The path from the source root object to <code>obj</code>.
     */
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

    /**
     * Retrieves the target model object adressed by the given path.
     * 
     * @param rootObject
     *            The target model's root object.
     * @param path
     *            The path which should be walked down in the target model.
     * @return The target model object at the end of the path.
     */
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

    /**
     * Looks up the given source-model object in the target model.
     * 
     * @param sourceObj
     *            An object in the source model.
     * @return The corresponding object in the target model.
     * 
     * @throws NoSuchElementException
     *             No corresponding object could be found in the target model.
     */
    public EObject lookupInTarget(EObject sourceObj) {
        List<PathElement> pathToSource = getPathFromRootElement(sourceObj);
        pathToSource.remove(0); // remove root element from path

        LOGGER.trace("Looking up " + sourceObj);
        assert sourceObj.eResource() != null;
        URI matchingURI = sourceObj.eResource().getURI();
        Resource matchingRes = targetResource.getResourceSet().getResource(matchingURI, true);

        try {
            EObject result = walkDownPath(matchingRes.getEObject(ROOT_ELEMENT_URI), pathToSource);
            assert result.getClass() == sourceObj.getClass() : "Result has class " + result.getClass() + ", expected "
                    + sourceObj.getClass();
            return result;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(sourceObj.toString());
        }
    }

    protected Resource getSourceResource() {
        return sourceResource;
    }

    /**
     * @param sourceFeature
     *            A structural feature of a source model object.
     * @param targetObject
     *            A target model object of the same type as the object to which
     *            <code>sourceFeature</code> belongs.
     * @return The value of <code>sourceFeature</code> in <code>targetObject</code>.
     */
    public Object getFeatureValue(EStructuralFeature sourceFeature, EObject targetObject) {
        EStructuralFeature targetFeature = getFeature(sourceFeature, targetObject);
        return targetObject.eGet(targetFeature);
    }

    /**
     * @param sourceFeature
     *            A structural feature of a source model object.
     * @param targetObject
     *            A target model object of the same type as the object to which
     *            <code>sourceFeature</code> belongs.
     * @return The structural feature corresponding to <code>sourceFeature</code> in
     *         <code>targetObject</code>.
     */
    public EStructuralFeature getFeature(EStructuralFeature sourceFeature, EObject targetObject) {
        String containerFeatureName = sourceFeature.getName();
        return targetObject.eClass().getEStructuralFeature(containerFeatureName);
    }

    /**
     * {@link PathElement} represents an element in the path between the root of a model and a given
     * model object. This class is protected only for testing purposes.
     */
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
