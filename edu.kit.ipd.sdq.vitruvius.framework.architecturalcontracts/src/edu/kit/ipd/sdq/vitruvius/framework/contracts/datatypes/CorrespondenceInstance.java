package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;

public class CorrespondenceInstance extends ModelInstance {
    private Mapping mapping;
    private Correspondences correspondences;
    private ClaimableMap<EObject, Set<Correspondence>> eObject2CorrespondencesMap;
    private ClaimableMap<EObject, Set<EObject>> eObject2CorrespondingEObjectsMap;

    public CorrespondenceInstance(final Mapping mapping, final VURI vuri, final Resource resource) {
        super(vuri, resource);
        this.mapping = mapping;
        this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
        // TODO implement lazy loading for correspondences because they may get really big
        this.eObject2CorrespondencesMap = new ClaimableHashMap<EObject, Set<Correspondence>>();
        this.eObject2CorrespondingEObjectsMap = new ClaimableHashMap<EObject, Set<EObject>>();
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
            if (correspondingEObject.getClass().cast(type) != null) {
                correspondingEObjectsByType.add((T) correspondingEObject);
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
        for (EObject involvedEObject : allInvolvedEObjects) {
            Set<EObject> correspondingEObjects = this.eObject2CorrespondingEObjectsMap.get(involvedEObject);
            if (correspondingEObjects == null) {
                correspondingEObjects = new HashSet<EObject>();
                this.eObject2CorrespondingEObjectsMap.put(involvedEObject, correspondingEObjects);
            }
            correspondingEObjects.addAll(allInvolvedEObjects);
            correspondingEObjects.remove(involvedEObject);
        }
    }

    public void removeAllCorrespondingInstances(final EObject eObject) {
        // TODO: Check if it is working
        Set<Correspondence> correspondencesForEObj = this.eObject2CorrespondencesMap.get(eObject);
        this.eObject2CorrespondencesMap.remove(eObject);
        for (Correspondence correspondence : correspondencesForEObj) {
            this.correspondences.getCorrespondences().remove(correspondence);
        }
    }
}
