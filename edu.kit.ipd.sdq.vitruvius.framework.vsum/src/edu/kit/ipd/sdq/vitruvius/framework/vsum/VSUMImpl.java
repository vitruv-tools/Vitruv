package edu.kit.ipd.sdq.vitruvius.framework.vsum;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.helper.FileSystemHelper;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;

public class VSUMImpl implements ModelProviding, CorrespondenceProviding {
    private final MappingManaging mappingManaging;
    private final MetamodelManaging metamodelManaging;
    private final ViewTypeManaging viewTypeManaging;
    private final CorrespondenceMMProviding correspondenceMMproviding;

    private final Map<VURI, ModelInstance> modelInstances;
    private final ResourceSet resourceSet;

    public VSUMImpl(final MetamodelManaging metamodelManaging, final ViewTypeManaging viewTypeManaging,
            final MappingManaging mappingManaging, final CorrespondenceMMProviding correspondenceMMproviding) {
        this.metamodelManaging = metamodelManaging;
        this.viewTypeManaging = viewTypeManaging;
        this.mappingManaging = mappingManaging;
        this.correspondenceMMproviding = correspondenceMMproviding;

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.resourceSet = new ResourceSetImpl();
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
        List<EObject> resourceContents = modelResource.getContents();
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        // createAndRegisterAllCorrespondenceInstances
        Collection<Mapping> mappings = this.mappingManaging.getAllMappings(metamodel);
        for (Mapping mapping : mappings) {
            VURI[] mmURIs = mapping.getMetamodelURIs();
            VURI correspondenceURI = FileSystemHelper.getCorrespondenceURI(mmURIs);
            Resource correspondenceResource;
            // TODO AAA KEEP ON WORKING HERE
            // CorrespondenceInstance correspondence = new CorrespondenceInstance(modelInstance,
            // metamodel, mapping,
            // correspondenceURI, correspondenceResource);
        }

        return modelInstance;
    }

    // @Override
    public ModelInstance getModelInstanceOriginalForImport(final VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CorrespondenceInstance getCorrespondenceInstance(final VURI model1uri, final VURI model2uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<CorrespondenceInstance> getAllCorrespondenceInstances(final VURI model1uri) {
        // TODO Auto-generated method stub
        return null;
    }
}
