package edu.kit.ipd.sdq.vitruvius.framework.vsum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.helper.FileSystemHelper;

public class VSUMImpl implements ModelProviding, CorrespondenceProviding, Validating {

    private static final Logger logger = Logger.getLogger(VSUMImpl.class.getSimpleName());

    private final MappingManaging mappingManaging;
    private final MetamodelManaging metamodelManaging;
    private final ViewTypeManaging viewTypeManaging;
    private final CorrespondenceMMProviding correspondenceMMproviding;

    private final Map<VURI, ModelInstance> modelInstances;
    private final ResourceSet resourceSet;
    private final Map<Metamodel, Collection<CorrespondenceInstance>> metamodel2CorrespondenceInstancesMap;
    private final Map<Mapping, CorrespondenceInstance> mapping2CorrespondenceInstanceMap;

    public VSUMImpl(final MetamodelManaging metamodelManaging, final ViewTypeManaging viewTypeManaging,
            final MappingManaging mappingManaging, final CorrespondenceMMProviding correspondenceMMproviding) {
        this.metamodelManaging = metamodelManaging;
        this.viewTypeManaging = viewTypeManaging;
        this.mappingManaging = mappingManaging;
        this.correspondenceMMproviding = correspondenceMMproviding;

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.resourceSet = new ResourceSetImpl();
        this.metamodel2CorrespondenceInstancesMap = new HashMap<Metamodel, Collection<CorrespondenceInstance>>();
        this.mapping2CorrespondenceInstanceMap = new HashMap<Mapping, CorrespondenceInstance>();
    }

    @Override
    public ModelInstance getModelInstanceCopy(final VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /** Supports three cases:
     *   1) get registered
     *   2) create non-existing
     *   3) get unregistered but existing that contains at most a root element without children.
     *  But throws an exception if an instance that contains more than one element exists at the uri.
     */
    public ModelInstance getModelInstanceOriginal(final VURI modelURI) {
        ModelInstance modelInstance = this.modelInstances.get(modelURI);
        if (modelInstance == null) {
            // case 2 or 3
            modelInstance = getOrCreateUnregisteredModelInstance(modelURI);
            this.modelInstances.put(modelURI, modelInstance);
        }
        return modelInstance;
    }

    private ModelInstance getOrCreateUnregisteredModelInstance(final VURI modelURI) {
        String fileExtension = modelURI.getFileExtension();
        Metamodel metamodel = this.metamodelManaging.getMetamodel(fileExtension);
        if (metamodel == null) {
            throw new RuntimeException("Cannot create a new model instance at the uri '" + modelURI
                    + "' because no metamodel is registered for the file extension '" + fileExtension + "'!");
        }
        return getOrCreateUnregisteredModelInstance(modelURI, metamodel);
    }

    private ModelInstance getOrCreateUnregisteredModelInstance(final VURI modelURI, final Metamodel metamodel) {
        URI emfURI = modelURI.getEMFUri();
        boolean loadOnDemand = true;
        Resource modelResource = this.resourceSet.getResource(emfURI, loadOnDemand);
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        getOrCreateAllCorrespondenceInstances(metamodel);
        return modelInstance;
    }

    private void getOrCreateAllCorrespondenceInstances(final Metamodel metamodel) {
        Collection<CorrespondenceInstance> correspondenceInstances = this.metamodel2CorrespondenceInstancesMap
                .get(metamodel);
        if (correspondenceInstances == null) {
            correspondenceInstances = createAndRegisterAllCorrespondenceInstances(metamodel);
            this.metamodel2CorrespondenceInstancesMap.put(metamodel, correspondenceInstances);
        }
    }

    private Collection<CorrespondenceInstance> createAndRegisterAllCorrespondenceInstances(final Metamodel metamodel) {
        Collection<Mapping> mappings = this.mappingManaging.getAllMappings(metamodel);
        Collection<CorrespondenceInstance> correspondenceInstances = new ArrayList<CorrespondenceInstance>(
                null == mappings ? 0 : mappings.size());
        if (null == mappings) {
            logger.warn("mappings == null. No correspondence instace for MM: " + metamodel + " created."
                    + "Empty correspondence list will be returned");
            return correspondenceInstances;
        }
        for (Mapping mapping : mappings) {
            CorrespondenceInstance correspondenceInstance = this.mapping2CorrespondenceInstanceMap.get(mapping);
            if (correspondenceInstance == null) {
                VURI[] mmURIs = mapping.getMetamodelURIs();
                VURI correspondenceURI = FileSystemHelper.createCorrespondenceInstanceURI(mmURIs);
                Resource correspondenceResource = this.resourceSet.createResource(correspondenceURI.getEMFUri());
                correspondenceInstance = new CorrespondenceInstance(mapping, correspondenceURI, correspondenceResource);
                this.mapping2CorrespondenceInstanceMap.put(mapping, correspondenceInstance);
            }
            correspondenceInstances.add(correspondenceInstance);
        }
        return correspondenceInstances;
    }

    // @Override
    public ModelInstance getModelInstanceOriginalForImport(final VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CorrespondenceInstance getCorrespondenceInstanceOriginal(final VURI model1uri, final VURI model2uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<CorrespondenceInstance> getAllCorrespondenceInstances(final VURI model1uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CorrespondenceInstance getCorrespondenceInstanceCopy(final VURI model1uri, final VURI model2uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validate(final Invariants invariants) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean validate(final ModelInstance modelInstance, final Invariants invariants) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean validate(final ModelInstance modelInstanceA, final ModelInstance modelInstanceB,
            final Invariants invariants) {
        // TODO Auto-generated method stub
        return false;
    }
}
