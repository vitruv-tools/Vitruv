package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.containers.CompilationUnit;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.InterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.PackageMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.BasicComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.OperationInterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.RepositoryMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.transformationexecuter.ChangeSynchronizer;

public class PCMJaMoPPTransformationExecuter implements EMFModelTransformationExecuting {

    private static final Logger logger = Logger.getLogger(PCMJaMoPPTransformationExecuter.class.getSimpleName());

    private final ChangeSynchronizer changeSynchronizer;

    private final List<Pair<VURI, VURI>> pairList;

    public PCMJaMoPPTransformationExecuter() {
        this.changeSynchronizer = new ChangeSynchronizer();
        this.initializeChangeSynchronizer();
        final VURI pcmVURI = VURI.getInstance(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final Pair<VURI, VURI> pcm2JaMoPP = new Pair<VURI, VURI>(pcmVURI, jaMoPPVURI);
        final Pair<VURI, VURI> jamopp2PCM = new Pair<VURI, VURI>(jaMoPPVURI, jaMoPPVURI);
        this.pairList = new ArrayList<Pair<VURI, VURI>>();
        this.pairList.add(jamopp2PCM);
        this.pairList.add(pcm2JaMoPP);
    }

    private void initializeChangeSynchronizer() {
        this.changeSynchronizer.addMapping(new ClassMappingTransformation());
        this.changeSynchronizer.addMapping(new InterfaceMappingTransformation());
        this.changeSynchronizer.addMapping(new PackageMappingTransformation());
        this.changeSynchronizer.addMapping(new BasicComponentMappingTransformation());
        this.changeSynchronizer.addMapping(new OperationInterfaceMappingTransformation());
        this.changeSynchronizer.addMapping(new RepositoryMappingTransformation());
    }

    /**
     * Executes the Java2PCM and PCM2Java transformations and returns the changed VURIs
     *
     * @param change
     *            the occurred change
     * @param correspondenceInstance
     *            the correspondence model
     * @return set of changed VURIs
     */
    @Override
    public EMFChangeResult executeTransformation(final EMFModelChange change,
            final CorrespondenceInstance correspondenceInstance) {
        final EMFModelChange emfModelChange = change;
        this.changeSynchronizer.setCorrespondenceInstance(correspondenceInstance);

        // execute actual Transformation
        final TransformationChangeResult transformationChangeResult = this.changeSynchronizer
                .synchronizeChange(emfModelChange.getEChange());

        // Translate TransformationChangeResult to EMFChangeResult
        final EMFChangeResult emfChangeResult = new EMFChangeResult();
        this.handleEObjectsInTransformationChange(transformationChangeResult.getExistingObjectsToDelete(),
                emfChangeResult.getExistingObjectsToDelete());
        this.handleEObjectsInTransformationChange(transformationChangeResult.getExistingObjectsToSave(),
                emfChangeResult.getExistingObjectsToSave());
        this.handleNewRootEObjects(transformationChangeResult.getNewRootObjectsToSave(),
                emfChangeResult.getNewRootObjectsToSave(), emfModelChange.getURI());

        return emfChangeResult;
    }

    @Override
    public List<Pair<VURI, VURI>> getTransformableMetamodels() {
        return this.pairList;
    }

    private void handleNewRootEObjects(final Set<EObject> newRootEObjectsToSave,
            final Set<Pair<EObject, VURI>> newVURIsToSave, final VURI sourceModelVURI) {
        for (final EObject newRootEObject : newRootEObjectsToSave) {
            if (newRootEObject instanceof CompilationUnit) {
                final CompilationUnit newCompUnit = (CompilationUnit) newRootEObject;
                final IFile fileSourceModel = EMFBridge.getIFileForEMFUri(sourceModelVURI.getEMFUri());
                final IProject projectSourceModel = fileSourceModel.getProject();
                // TODO: use configured src-folder path instead of hardcoded "src"
                String srcFolderPath = projectSourceModel.getFullPath().toString() + "/src/";
                if (srcFolderPath.startsWith("/")) {
                    srcFolderPath = srcFolderPath.substring(1, srcFolderPath.length());
                }
                final String compUnitPath = newCompUnit.getNamespacesAsString().replace(".", "/").replace("$", "/")
                        + newCompUnit.getName().replace("$", ".");
                final VURI cuVURI = VURI.getInstance(srcFolderPath + compUnitPath);
                final Pair<EObject, VURI> newEObjectVURIPair = new Pair<EObject, VURI>(newCompUnit, cuVURI);
                newVURIsToSave.add(newEObjectVURIPair);
            }// TODO: Else if instanceof Repository
        }
    }

    private void handleEObjectsInTransformationChange(final Set<EObject> eObjectsInTransformationChange,
            final Set<VURI> vurisInEMFResultChange) {
        for (final EObject eObject : eObjectsInTransformationChange) {
            final Resource resource = eObject.eResource();
            if (null == resource) {
                logger.warn("Resource of EObject is null. Can not handle resource of eObject: " + eObject);
                continue;
            }
            final VURI vuri = VURI.getInstance(resource);
            vurisInEMFResultChange.add(vuri);
        }
    }
}
