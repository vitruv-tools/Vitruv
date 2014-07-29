package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.containers.CompilationUnit;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

public class PCMJaMoPPTransformationExecuter implements TransformationExecuting {

    private static final Logger logger = Logger.getLogger(PCMJaMoPPTransformationExecuter.class.getSimpleName());

    private final ChangeSynchronizer changeSynchronizer;

    private final List<Pair<VURI, VURI>> pairList;

    public PCMJaMoPPTransformationExecuter() {
        this.changeSynchronizer = new ChangeSynchronizer();
        final VURI pcmVURI = VURI.getInstance(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final Pair<VURI, VURI> pcm2JaMoPP = new Pair<VURI, VURI>(pcmVURI, jaMoPPVURI);
        final Pair<VURI, VURI> jamopp2PCM = new Pair<VURI, VURI>(jaMoPPVURI, jaMoPPVURI);
        this.pairList = new ArrayList<Pair<VURI, VURI>>();
        this.pairList.add(jamopp2PCM);
        this.pairList.add(pcm2JaMoPP);
    }

    /**
     * Executes the Java2PCM and PCM2Java transformations and returns the changed VURIs
     *
     * @param change
     *            the occurred change
     * @param sourceModel
     *            the source model where the change occurred
     * @param correspondenceInstance
     *            the correspondence model
     * @return set of changed VURIs
     */
    @Override
    public EMFChangeResult executeTransformation(final Change change, final ModelInstance sourceModel,
            final CorrespondenceInstance correspondenceInstance) {
        final EMFModelChange emfModelChange = (EMFModelChange) change;
        this.changeSynchronizer.setCorrespondenceInstance(correspondenceInstance);
        final EObject[] changedEObjects = this.changeSynchronizer.synchronizeChange(emfModelChange.getEChange());
        final Set<VURI> changedVURIs = new HashSet<VURI>(changedEObjects.length);
        final Set<Pair<EObject, VURI>> newRootEObjectsVURIPairs = new HashSet<Pair<EObject, VURI>>();
        final Set<VURI> existingVURIsToDelete = new HashSet<VURI>();
        for (final EObject changedEObject : changedEObjects) {
            final Resource resource = changedEObject.eResource();
            if (null != resource) {
                if (changedEObject instanceof CompilationUnit) {
                    existingVURIsToDelete.add(VURI.getInstance(resource));
                } else {
                    changedVURIs.add(VURI.getInstance(resource));
                }
            } else {
                if (changedEObject instanceof CompilationUnit) {
                    final CompilationUnit newCompUnit = (CompilationUnit) changedEObject;
                    final IFile fileSourceModel = EMFBridge.getIFileForEMFUri(sourceModel.getURI().getEMFUri());
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
                    newRootEObjectsVURIPairs.add(newEObjectVURIPair);
                }
            }
        }

        return new EMFChangeResult(changedVURIs, newRootEObjectsVURIPairs, existingVURIsToDelete);
    }

    @Override
    public List<Pair<VURI, VURI>> getTransformableMetamodels() {
        return this.pairList;
    }
}
