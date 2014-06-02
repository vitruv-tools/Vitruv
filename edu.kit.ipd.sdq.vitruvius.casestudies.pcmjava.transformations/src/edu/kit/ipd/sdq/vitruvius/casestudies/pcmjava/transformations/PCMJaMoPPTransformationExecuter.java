package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

public class PCMJaMoPPTransformationExecuter implements TransformationExecuting {

    private static final Logger logger = Logger.getLogger(PCMJaMoPPTransformationExecuter.class.getSimpleName());

    private final ChangeSynchronizer changeSynchronizer;

    private final List<Pair<VURI, VURI>> pairList;
    private final ResourceSet resourceSet;

    public PCMJaMoPPTransformationExecuter() {
        this.changeSynchronizer = new ChangeSynchronizer();
        final VURI pcmVURI = VURI.getInstance(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final Pair<VURI, VURI> pcm2JaMoPP = new Pair<VURI, VURI>(pcmVURI, jaMoPPVURI);
        final Pair<VURI, VURI> jamopp2PCM = new Pair<VURI, VURI>(jaMoPPVURI, jaMoPPVURI);
        this.pairList = new ArrayList<Pair<VURI, VURI>>();
        this.pairList.add(jamopp2PCM);
        this.pairList.add(pcm2JaMoPP);
        this.resourceSet = new ResourceSetImpl();
    }

    @Override
    public void executeTransformation(final Change change, final ModelInstance sourceModel,
            final CorrespondenceInstance correspondenceInstance) {
        final EMFModelChange emfModelChange = (EMFModelChange) change;
        this.changeSynchronizer.setCorrespondenceInstance(correspondenceInstance);
        final EObject[] changedEObjects = this.changeSynchronizer.synchronizeChange(emfModelChange.getEChange());
        // TODO check wheather we should move the save operation of changed EObjects to somewhere
        // else
        // FIXME: saving of objects not working yet :-(.
        // for (final EObject eObject : changedEObjects) {
        // Resource resourceToSave = eObject.eResource();
        // if (null == resourceToSave) {
        // if (eObject instanceof CompilationUnit) {
        // final CompilationUnit cu = (CompilationUnit) eObject;
        // String resourceName = cu.getNamespacesAsString();
        // if (resourceName.endsWith("$")) {
        // resourceName = resourceName.substring(0, resourceName.length() - 1);
        // }
        // resourceName = resourceName.replaceAll("\\.", "/");
        // if (!resourceName.endsWith("java")) {
        // resourceName = resourceName + ".java";
        // }
        // final VURI cuUri = VURI.getInstance(resourceName);
        // resourceToSave = this.resourceSet.createResource(cuUri.getEMFUri());
        // } else {
        // continue;
        // }
        // }
        // try {
        // resourceToSave.save(new HashMap<Object, Object>());
        // } catch (final IOException e) {
        // logger.warn("Could not save resource " + resourceToSave + " Exception: " + e);
        // e.printStackTrace();
        // }
        // }
    }

    @Override
    public List<Pair<VURI, VURI>> getTransformableMetamodels() {
        return this.pairList;
    }
}
