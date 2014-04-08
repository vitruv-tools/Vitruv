package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;

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
    private ClaimableMap<String, Set<Correspondence>> tuid2CorrespondencesMap;
    private ClaimableMap<String, Set<EObject>> tuid2CorrespondingEObjectsMap;
    private ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap;

    public CorrespondenceInstance(final Mapping mapping, final VURI vuri, final Resource resource) {
        super(vuri, resource);
        this.mapping = mapping;
        this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
        // TODO implement lazy loading for correspondences because they may get really big
        this.tuid2CorrespondencesMap = new ClaimableHashMap<String, Set<Correspondence>>();
        this.tuid2CorrespondingEObjectsMap = new ClaimableHashMap<String, Set<EObject>>();
        this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance, Set<FeatureInstance>>();
        // TODO implement loading of existing correspondences from resources (fill maps)
        // TODO create TUIDs during loading of existing corresponding from resource
    }

    public Mapping getMapping() {
        return this.mapping;
    }

    public boolean hasCorrespondences(final EObject eObject) {
        return this.tuid2CorrespondencesMap.containsKey(eObject);
    }

    public Set<Correspondence> claimAllCorrespondences(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        return this.tuid2CorrespondencesMap.claimValueForKey(tuid);
    }

    public boolean hasCorrespondingEObjects(final EObject eObject) {
        return this.tuid2CorrespondingEObjectsMap.containsKey(eObject);
    }

    public Set<EObject> claimCorrespondingEObjects(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        return this.tuid2CorrespondingEObjectsMap.claimValueForKey(tuid);
    }

    public boolean isCorrespondingEObjectUnique(final EObject eObject) {
        return this.tuid2CorrespondingEObjectsMap.containsKey(eObject)
                && 1 == this.tuid2CorrespondingEObjectsMap.get(eObject).size();
    }

    public EObject claimCorrespondingEObjectIfUnique(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        Set<EObject> correspondingEObjects = this.tuid2CorrespondingEObjectsMap.claimValueForKey(tuid);
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
        String tuid = getTUIDFromEObject(eObject);
        if (!hasCorrespondenceObjects(eObject)) {
            this.tuid2CorrespondencesMap.put(tuid, new HashSet<Correspondence>());
        }
        return this.tuid2CorrespondencesMap.claimValueForKey(tuid);
    }

    public boolean hasCorrespondenceObjects(final EObject eObject) {
        String tuid = getTUIDFromEObject(eObject);
        return this.tuid2CorrespondencesMap.containsKey(tuid) && this.tuid2CorrespondencesMap.get(tuid).size() > 0;
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

    public void addSameTypeCorrespondence(final SameTypeCorrespondence correspondence) {
        // add TUIDs
        String tuidA = this.mapping.getMetamodelA().getTUID(correspondence.getElementA());
        String tuidB = this.mapping.getMetamodelB().getTUID(correspondence.getElementB());
        correspondence.setElementATUID(tuidA);
        correspondence.setElementBTUID(tuidB);
        // add correspondence to model
        this.correspondences.getCorrespondences().add(correspondence);
        EList<EObject> allInvolvedEObjects = correspondence.getAllInvolvedEObjects();
        List<String> allInvolvedTUIDs = getInvolvedTUIDs(allInvolvedEObjects);
        // add all involved eObjects to the sets for these objects in the map
        for (String involvedTUID : allInvolvedTUIDs) {
            Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(involvedTUID);
            if (correspondences == null) {
                correspondences = new HashSet<Correspondence>();
                this.tuid2CorrespondencesMap.put(involvedTUID, correspondences);
            }
            if (!correspondences.contains(correspondence)) {
                correspondences.add(correspondence);
            }
        }
        if (correspondence instanceof EObjectCorrespondence) {
            for (String involvedTUID : allInvolvedTUIDs) {
                Set<EObject> correspondingEObjects = this.tuid2CorrespondingEObjectsMap.get(involvedTUID);
                if (correspondingEObjects == null) {
                    correspondingEObjects = new HashSet<EObject>();
                    this.tuid2CorrespondingEObjectsMap.put(involvedTUID, correspondingEObjects);
                }
                correspondingEObjects.addAll(allInvolvedEObjects);
                correspondingEObjects.remove(involvedTUID);
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
        Set<Correspondence> correspondencesForEObj = this.tuid2CorrespondencesMap.get(eObject);
        this.tuid2CorrespondencesMap.remove(eObject);
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

    private List<String> getInvolvedTUIDs(final EList<EObject> allInvolvedEObjects) {
        List<String> involvedTUIDs = new ArrayList<String>();
        for (EObject involvedEObject : allInvolvedEObjects) {
            String involvedTUID = getTUIDFromEObject(involvedEObject);
            involvedTUIDs.add(involvedTUID);
        }
        return involvedTUIDs;
    }

    private String getTUIDFromEObject(final EObject eObject) {
        if (this.mapping.getMetamodelA().getURI().toString().contains(eObject.eClass().getEPackage().getNsURI())) {
            return this.mapping.getMetamodelA().getTUID(eObject);
        }
        return this.mapping.getMetamodelB().getTUID(eObject);
    }
}
