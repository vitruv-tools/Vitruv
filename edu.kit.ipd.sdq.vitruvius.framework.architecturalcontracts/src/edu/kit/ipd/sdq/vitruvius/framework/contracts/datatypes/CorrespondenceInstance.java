package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;

public class CorrespondenceInstance extends ModelInstance {
    private Mapping mapping;
    private Correspondences correspondences;
    private Map<EObject, Set<Correspondence>> eObject2CorrespondencesMap;

    public CorrespondenceInstance(final Mapping mapping, final VURI vuri, final Resource resource) {
        super(vuri, resource);
        this.mapping = mapping;
        this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
        // TODO implement lazy loading for correspondences because they may get really big
        this.eObject2CorrespondencesMap = new HashMap<EObject, Set<Correspondence>>();
    }

    public Mapping getMapping() {
        return this.mapping;
    }

    public Set<Correspondence> getAllCorrespondences(final EObject eObject) {
        return this.eObject2CorrespondencesMap.get(eObject);
    }

    public void addCorrespondence(final Correspondence correspondence) {
        // add correspondence to model
        this.correspondences.getCorrespondences().add(correspondence);
        EList<EObject> allInvolvedEObjects = correspondence.getAllInvolvedEObjects();
        // add all involved eObjects to the sets for these objects in the map
        for (EObject involedEObject : allInvolvedEObjects) {
            Set<Correspondence> correspondences = this.eObject2CorrespondencesMap.get(involedEObject);
            if (correspondences == null) {
                correspondences = new HashSet<Correspondence>();
                this.eObject2CorrespondencesMap.put(involedEObject, correspondences);
            }
            if (!correspondences.contains(correspondence)) {
                correspondences.add(correspondence);
            }
        }

    }
}
