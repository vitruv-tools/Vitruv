package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ChangeSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject;

public class PCM2JaMoPPUtils {
    private PCM2JaMoPPUtils() {
    }

    public static Repository createAndSyncRepository(final ResourceSet resourceSet,
            final ChangeSynchronizer changeSynchronizer) {
        final VURI repoVURI = VURI.getInstance("/tmp/repository.repository");
        final Resource resource = resourceSet.createResource(repoVURI.getEMFUri());
        final Repository repo = RepositoryFactory.eINSTANCE.createRepository();
        resource.getContents().add(repo);
        final CreateRootEObject createRootEObj = ChangeFactory.eINSTANCE.createCreateRootEObject();
        createRootEObj.setChangedEObject(repo);
        changeSynchronizer.synchronizeChange(createRootEObj);
        return repo;
    }

    public static BasicComponent createBasicComponent(final Repository repo) {
        final BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
        basicComponent.setRepository__RepositoryComponent(repo);
        basicComponent.setEntityName("TestBasicComponent");
        return basicComponent;
    }

    public static VURI createDummyVURI(final String name) {
        return VURI.getInstance(EMFBridge.createPlatformResourceURI("test/" + name));
    }

    public static Resource createResource(final String resourceName, final ResourceSet resourceSet) {
        final URI uri = EMFBridge.createPlatformResourceURI(resourceName);
        // final URI uri = URI.createPlatformResourceURI("test/" + resourceName, true);
        final Resource resource = resourceSet.createResource(uri);
        return resource;
    }

    public static ModelInstance createModelInstance(final String name, final ResourceSet resourceSet) {
        final Resource resource = createResource(name, resourceSet);
        final VURI vuri = VURI.getInstance(name);
        final ModelInstance modelInstance = new ModelInstance(vuri, resource);
        return modelInstance;
    }

    public static Change createCreateChange(final EObject changedEObject, final EObject affectedEObject,
            final String featureName) {
        final CreateNonRootEObject<EObject> createChange = ChangeFactory.eINSTANCE.createCreateNonRootEObject();
        createChange.setChangedEObject(changedEObject);
        createChange.setAffectedEObject(affectedEObject);
        createChange.setAffectedFeature(getEReferenceByName(affectedEObject, featureName));
        createChange.setNewValue(changedEObject);
        final EMFModelChange emfModelChange = new EMFModelChange(createChange);
        return emfModelChange;
    }

    public static EReference getEReferenceByName(final EObject eObject, final String featureName) {
        return (EReference) eObject.eClass().getEStructuralFeature(featureName);
    }

    public static void saveEMFChangeResult(final EMFChangeResult emfChangeResult, final ResourceSet resourceSet)
            throws IOException {
        for (final VURI vuri : emfChangeResult.getExistingObjectsToSave()) {
            saveVURI(vuri, resourceSet);
        }
        for (final Pair<EObject, VURI> eObjectVURIPair : emfChangeResult.getNewRootObjectsToSave()) {
            final Resource resource = resourceSet.createResource(eObjectVURIPair.getSecond().getEMFUri());
            resource.getContents().add(eObjectVURIPair.getFirst());
            EcoreResourceBridge.saveResource(resource);
        }
    }

    public static Resource saveVURI(final VURI vuri, final ResourceSet resourceSet) throws IOException {
        final Resource resource = resourceSet.createResource(vuri.getEMFUri());
        EcoreResourceBridge.saveResource(resource);
        return resource;
    }

    public static boolean isEmptyTransformationChangeResult(final TransformationChangeResult transformationChangeResult) {
        if (null == transformationChangeResult) {
            return true;
        }
        return 0 == transformationChangeResult.getExistingObjectsToDelete().size()
                && 0 == transformationChangeResult.getExistingObjectsToSave().size()
                && 0 == transformationChangeResult.getNewRootObjectsToSave().size();
    }

}
