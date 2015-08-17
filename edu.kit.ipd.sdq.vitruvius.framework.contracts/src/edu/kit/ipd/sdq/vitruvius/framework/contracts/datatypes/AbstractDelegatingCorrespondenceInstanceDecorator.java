package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

public abstract class AbstractDelegatingCorrespondenceInstanceDecorator<D> implements CorrespondenceInstanceDecorator {
    protected CorrespondenceInstanceDecorator correspondenceInstance;

    public AbstractDelegatingCorrespondenceInstanceDecorator(
            final CorrespondenceInstanceDecorator correspondenceInstance) {
        this.correspondenceInstance = correspondenceInstance;
    }

    protected abstract String getDecoratorFileExtPrefix();

    protected abstract D getDecoratorObject();

    protected abstract void initializeFromDecoratorObject(D object);

    @Override
    public Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
        Map<String, Object> fileExtPrefix2ObjectMap = this.correspondenceInstance.getFileExtPrefix2ObjectMapForSave();
        fileExtPrefix2ObjectMap.put(getDecoratorFileExtPrefix(), getDecoratorObject());
        return fileExtPrefix2ObjectMap;
    }

    @Override
    public Set<String> getFileExtPrefixesForObjectsToLoad() {
        Set<String> fileExtPrefixes = this.correspondenceInstance.getFileExtPrefixesForObjectsToLoad();
        fileExtPrefixes.add(getDecoratorFileExtPrefix());
        return fileExtPrefixes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(final Map<String, Object> fileExtPrefix2ObjectMap) {
        Object object = fileExtPrefix2ObjectMap.get(getDecoratorFileExtPrefix());
        initializeFromDecoratorObject((D) object);
    }

    @Override
    public Resource getResource() {
        return this.correspondenceInstance.getResource();
    }

    @Override
    public boolean changedAfterLastSave() {
        return this.correspondenceInstance.changedAfterLastSave();
    }

    @Override
    public void resetChangedAfterLastSave() {
        this.correspondenceInstance.resetChangedAfterLastSave();
    }

    @Override
    public Mapping getMapping() {
        return this.correspondenceInstance.getMapping();
    }

    @Override
    public boolean hasCorrespondences(final EObject eObject) {
        return this.correspondenceInstance.hasCorrespondences(eObject);
    }

    @Override
    public boolean hasCorrespondences() {
        return this.correspondenceInstance.hasCorrespondences();
    }

    @Override
    public Set<Correspondence> claimCorrespondences(final EObject eObject) {
        return this.correspondenceInstance.claimCorrespondences(eObject);
    }

    @Override
    public Set<Correspondence> getAllCorrespondences(final EObject eObject) {
        return this.correspondenceInstance.getAllCorrespondences(eObject);
    }

    @Override
    public Set<Correspondence> getAllCorrespondences(final TUID involvedTUID) {
        return this.correspondenceInstance.getAllCorrespondences(involvedTUID);
    }

    @Override
    public Set<EObject> claimCorrespondingEObjects(final EObject eObject) {
        return this.correspondenceInstance.claimCorrespondingEObjects(eObject);
    }

    @Override
    public Set<EObject> getAllCorrespondingEObjects(final EObject eObject) {
        return this.correspondenceInstance.getAllCorrespondingEObjects(eObject);
    }

    @Override
    public EObject claimUniqueCorrespondingEObject(final EObject eObject) {
        return this.correspondenceInstance.claimUniqueCorrespondingEObject(eObject);
    }

    @Override
    public <T> Set<T> claimCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
        return this.correspondenceInstance.claimCorrespondingEObjectsByType(eObject, type);
    }

    @Override
    public <T> T claimUniqueCorrespondingEObjectByType(final EObject eObject, final Class<T> type) {
        return this.correspondenceInstance.claimUniqueCorrespondingEObjectByType(eObject, type);
    }

    @Override
    public Correspondence claimUniqueOrNullCorrespondenceForEObject(final EObject eObject) {
        return this.correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(eObject);
    }

    @Override
    public Correspondence claimUniqueCorrespondence(final EObject eObject) {
        return this.correspondenceInstance.claimUniqueCorrespondence(eObject);
    }

    @Override
    public SameTypeCorrespondence claimUniqueSameTypeCorrespondence(final EObject a, final EObject b) {
        return this.correspondenceInstance.claimUniqueSameTypeCorrespondence(a, b);
    }

    @Override
    public <T> Set<T> getAllEObjectsInCorrespondencesWithType(final Class<T> type) {
        return this.correspondenceInstance.getAllEObjectsInCorrespondencesWithType(type);
    }

    @Override
    public void addSameTypeCorrespondence(final SameTypeCorrespondence correspondence) {
        this.correspondenceInstance.addSameTypeCorrespondence(correspondence);
    }

    @Override
    public Set<Correspondence> removeDirectAndChildrenCorrespondencesOnBothSides(final EObject eObject) {
        return this.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(eObject);
    }

    @Override
    public Set<Correspondence> removeDirectAndChildrenCorrespondencesOnBothSides(final TUID tuid) {
        return this.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(tuid);
    }

    @Override
    public Set<Correspondence> removeNeighborAndChildrenCorrespondencesOnBothSides(
            final Correspondence correspondence) {
        return this.correspondenceInstance.removeNeighborAndChildrenCorrespondencesOnBothSides(correspondence);
    }

    @Override
    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final EObject parentEObject,
            final EStructuralFeature feature) {
        return this.correspondenceInstance.getAllCorrespondingFeatureInstances(parentEObject, feature);
    }

    @Override
    public Set<FeatureInstance> getAllCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.correspondenceInstance.getAllCorrespondingFeatureInstances(featureInstance);
    }

    @Override
    public Set<FeatureInstance> claimCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.correspondenceInstance.claimCorrespondingFeatureInstances(featureInstance);
    }

    @Override
    public FeatureInstance claimUniqueCorrespondingFeatureInstance(final FeatureInstance featureInstance) {
        return this.correspondenceInstance.claimUniqueCorrespondingFeatureInstance(featureInstance);
    }

    @Override
    public void update(final EObject oldEObject, final EObject newEObject) {
        this.correspondenceInstance.update(oldEObject, newEObject);
    }

    @Override
    public void update(final TUID oldTUID, final EObject newEObject) {
        this.correspondenceInstance.update(oldTUID, newEObject);
    }

    @Override
    public void update(final TUID oldTUID, final TUID newTUID) {
        this.correspondenceInstance.update(oldTUID, newTUID);
    }

    @Override
    public EObject resolveEObjectFromTUID(final TUID tuid) {
        return this.correspondenceInstance.resolveEObjectFromTUID(tuid);
    }

    @Override
    public Set<EObject> resolveEObjectsFromTUIDs(final Set<TUID> tuid) {
        return this.correspondenceInstance.resolveEObjectsFromTUIDs(tuid);
    }

    @Override
    public TUID calculateTUIDFromEObject(final EObject eObject) {
        return this.correspondenceInstance.calculateTUIDFromEObject(eObject);
    }

    @Override
    public EContainmentReferenceCorrespondence createAndAddEContainmentReferenceCorrespondence(final EObject a,
            final EObject b, final EReference referenceFeatureA, final EReference referenceFeatureB) {
        return this.correspondenceInstance.createAndAddEContainmentReferenceCorrespondence(a, b, referenceFeatureA,
                referenceFeatureB);
    }

    @Override
    public EObjectCorrespondence createAndAddEObjectCorrespondence(final EObject a, final EObject b) {
        return this.correspondenceInstance.createAndAddEObjectCorrespondence(a, b);
    }

    @Override
    public <T> Set<T> getCorrespondingEObjectsByType(final EObject eObject, final Class<T> type) {
        return this.correspondenceInstance.getCorrespondingEObjectsByType(eObject, type);
    }

    @Override
    public VURI getURI() {
        return this.correspondenceInstance.getURI();
    }
}
