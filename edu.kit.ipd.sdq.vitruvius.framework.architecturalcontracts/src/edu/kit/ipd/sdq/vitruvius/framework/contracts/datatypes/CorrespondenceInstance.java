package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;

// TODO move all methods that don't need direct instance variable access to some kind of util class
/**
 * Contains all correspondences for all model instances that conform to the metamodels of a give
 * mapping. The correspondences do not store any information on metamodels. Only correspondence
 * instances link to the metamodels. Therefore every elementA of a correspondence has to be an
 * instance of a metaclass of the first metamodel of the containing correspondence instance. And
 * every elementB of a correspondence has to be an instance of a metaclass of the second metamodel
 * of the containing correspondence instance.
 * 
 * @author kramerm
 * 
 */
public class CorrespondenceInstance extends ModelInstance {
    private Mapping mapping;
    private Correspondences correspondences;
    private ClaimableMap<EObject, Set<Correspondence>> eObject2CorrespondencesMap;
    private ClaimableMap<EObject, Set<EObject>> eObject2CorrespondingEObjectsMap;
    private ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap;

    public CorrespondenceInstance(final Mapping mapping, final VURI vuri, final Resource resource) {
        super(vuri, resource);
        this.mapping = mapping;
        this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
        // TODO implement lazy loading for correspondences because they may get really big
        this.eObject2CorrespondencesMap = new ClaimableHashMap<EObject, Set<Correspondence>>();
        this.eObject2CorrespondingEObjectsMap = new ClaimableHashMap<EObject, Set<EObject>>();
        this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance, Set<FeatureInstance>>();
        // TODO implement loading of existing correspondences from resources (fill maps)
    }

    public Mapping getMapping() {
        return this.mapping;
    }

    public boolean hasCorrespondences(final EObject eObject) {
        return this.eObject2CorrespondencesMap.containsKey(eObject);
    }

    public Set<Correspondence> claimAllCorrespondences(final EObject eObject) {
        return this.eObject2CorrespondencesMap.claimValueForKey(eObject);
    }

    public boolean hasCorrespondingEObjects(final EObject eObject) {
        return this.eObject2CorrespondingEObjectsMap.containsKey(eObject);
    }

    public Set<EObject> claimCorrespondingEObjects(final EObject eObject) {
        return this.eObject2CorrespondingEObjectsMap.claimValueForKey(eObject);
    }

    public boolean isCorrespondingEObjectUnique(final EObject eObject) {
        return this.eObject2CorrespondingEObjectsMap.containsKey(eObject)
                && 1 == this.eObject2CorrespondingEObjectsMap.get(eObject).size();
    }

    public EObject claimCorrespondingEObjectIfUnique(final EObject eObject) {
        Set<EObject> correspondingEObjects = this.eObject2CorrespondingEObjectsMap.claimValueForKey(eObject);
        if (1 != correspondingEObjects.size()) {
            throw new RuntimeException("claimCorrespondingEObjectIfUnique failed: " + correspondingEObjects.size()
                    + " corresponding objects found (expected 1)" + correspondingEObjects);
        }
        return correspondingEObjects.iterator().next();
    }

    public <T> Set<T> claimCorredpondingEObjectsByType(final EObject eObject, final Class<T> type) {
        Set<EObject> correspondingEObjects = claimCorrespondingEObjects(eObject);
        Set<T> correspondingEObjectsByType = new HashSet<T>();
        for (EObject correspondingEObject : correspondingEObjects) {
            if (type.isInstance(correspondingEObject)) {
                correspondingEObjectsByType.add(type.cast(correspondingEObject));
            }
        }
        return correspondingEObjectsByType;
    }

    public <T> T claimCorrespondingEObjectByTypeIfUnique(final EObject eObject, final Class<T> type) {
        Set<T> correspondingEObjectsByType = this.claimCorredpondingEObjectsByType(eObject, type);
        if (1 != correspondingEObjectsByType.size()) {
            throw new RuntimeException("claimCorrespondingEObjectForTypeIfUnique failed: "
                    + correspondingEObjectsByType.size() + " corresponding objects found (expected 1)"
                    + correspondingEObjectsByType);
        }
        return correspondingEObjectsByType.iterator().next();
    }

    public Set<Correspondence> claimCorrespondencesForEObject(final EObject eObject) {
        if (!hasCorrespondenceObjects(eObject)) {
            new HashSet<Correspondence>();
        }
        return this.eObject2CorrespondencesMap.claimValueForKey(eObject);
    }

    public boolean hasCorrespondenceObjects(final EObject eObject) {
        return this.eObject2CorrespondencesMap.containsKey(eObject)
                && this.eObject2CorrespondencesMap.get(eObject).size() > 0;
    }

    public Correspondence getCorrespondeceForEObjectIfUnique(final EObject eObject) {
        if (!hasCorrespondenceObjects(eObject)) {
            return null;
        }
        Set<Correspondence> objectCorrespondences = claimCorrespondencesForEObject(eObject);
        if (1 != objectCorrespondences.size()) {
            throw new RuntimeException("claimCorrespondingEObjectForTypeIfUnique failed: "
                    + objectCorrespondences.size() + " corresponding objects found (expected 1)"
                    + objectCorrespondences);
        }
        return objectCorrespondences.iterator().next();
    }

    public void addCorrespondence(final Correspondence correspondence) {
        // add correspondence to model
        this.correspondences.getCorrespondences().add(correspondence);
        EList<EObject> allInvolvedEObjects = correspondence.getAllInvolvedEObjects();
        // add all involved eObjects to the sets for these objects in the map
        for (EObject involvedEObject : allInvolvedEObjects) {
            Set<Correspondence> correspondences = this.eObject2CorrespondencesMap.get(involvedEObject);
            if (correspondences == null) {
                correspondences = new HashSet<Correspondence>();
                this.eObject2CorrespondencesMap.put(involvedEObject, correspondences);
            }
            if (!correspondences.contains(correspondence)) {
                correspondences.add(correspondence);
            }
        }
        if (correspondence instanceof EObjectCorrespondence) {
            for (EObject involvedEObject : allInvolvedEObjects) {
                Set<EObject> correspondingEObjects = this.eObject2CorrespondingEObjectsMap.get(involvedEObject);
                if (correspondingEObjects == null) {
                    correspondingEObjects = new HashSet<EObject>();
                    this.eObject2CorrespondingEObjectsMap.put(involvedEObject, correspondingEObjects);
                }
                correspondingEObjects.addAll(allInvolvedEObjects);
                correspondingEObjects.remove(involvedEObject);
            }
        } else if (correspondence instanceof EFeatureCorrespondence) {
            EFeatureCorrespondence<?> featureCorrespondence = (EFeatureCorrespondence<?>) correspondence;
            FeatureInstance featureInstanceA = FeatureInstance.getInstance(featureCorrespondence.getElementA(),
                    featureCorrespondence.getFeatureA());
            FeatureInstance featureInstanceB = FeatureInstance.getInstance(featureCorrespondence.getElementB(),
                    featureCorrespondence.getFeatureB());
            Set<FeatureInstance> featureInstancesForA = this.featureInstance2CorrespondingFIMap.get(featureInstanceA);
            if (featureInstancesForA == null) {
                featureInstancesForA = new HashSet<FeatureInstance>();
            }
            featureInstancesForA.add(featureInstanceB);
            Set<FeatureInstance> featureInstancesForB = this.featureInstance2CorrespondingFIMap.get(featureInstanceB);
            if (featureInstancesForB == null) {
                featureInstancesForB = new HashSet<FeatureInstance>();
            }
            featureInstancesForB.add(featureInstanceA);
        }

    }

    public void removeAllCorrespondingInstances(final EObject eObject) {
        // TODO: Check if it is working
        Set<Correspondence> correspondencesForEObj = this.eObject2CorrespondencesMap.get(eObject);
        this.eObject2CorrespondencesMap.remove(eObject);
        for (Correspondence correspondence : correspondencesForEObj) {
            this.correspondences.getCorrespondences().remove(correspondence);
        }
        // FIXME: remove feature correspondences
    }

    public Set<FeatureInstance> getCorrespondingFeatureInstances(final EObject parentEObject,
            final EStructuralFeature feature) {
        FeatureInstance featureInstance = FeatureInstance.getInstance(parentEObject, feature);
        return getCorrespondingFeatureInstances(featureInstance);
    }

    public Set<FeatureInstance> getCorrespondingFeatureInstances(final FeatureInstance featureInstance) {
        return this.featureInstance2CorrespondingFIMap.get(featureInstance);
    }
}
