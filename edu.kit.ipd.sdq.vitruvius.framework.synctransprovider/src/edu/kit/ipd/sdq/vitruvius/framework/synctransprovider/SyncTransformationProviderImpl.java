package edu.kit.ipd.sdq.vitruvius.framework.synctransprovider;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecutingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

/**
 * The class @SyncTransformationProviderImpl provides the interface
 * @link(TransformationExecutingProviding) hence it enable users of the class to find the correct
 * synchronisation Transformation for two meta models. In the first iteration
 * SyncTransformationProviderImpl directly knows the syncTransformations instead of looking it up
 * via an extension point mechanism.
 * 
 * @author Langhamm
 * 
 */

public class SyncTransformationProviderImpl implements TransformationExecutingProviding {

    private ClaimableMap<Pair<VURI, VURI>, TransformationExecuting> transformationExecuterMap;

    public SyncTransformationProviderImpl() {
        this.transformationExecuterMap = new ClaimableHashMap<Pair<VURI, VURI>, TransformationExecuting>();
        VURI pcmMMVURI = VURI.getInstance("");
        VURI jaMoPPVURI = VURI.getInstance("");
        PCMJaMoPPTransformationExecuter pcmJaMoppTransformation = new PCMJaMoPPTransformationExecuter();
        // JaMoPPPCMTransformationExecuter jamoppPCMTransformation = new
        // JaMoPPPCMTransformationExecuter();
        this.transformationExecuterMap.put(new Pair<VURI, VURI>(pcmMMVURI, jaMoPPVURI), pcmJaMoppTransformation);
        // this.transformationExecuterMap.put(new Pair<VURI, VURI>(jaMoPPVURI, pcmMMVURI),
        // jamoppPCMTransformation);
    }

    @Override
    public SyncTransformation getSyncTransformation(final VURI mmURI1, final Change change, final VURI mmURI2) {
        throw new RuntimeException("getSyncTransformation is not implemented yet");
    }

    @Override
    public TransformationExecuting getTransformationExecuting(final VURI mmURI1, final VURI mmURI2) {
        Pair<VURI, VURI> vuriPair = new Pair<VURI, VURI>(mmURI1, mmURI2);
        return this.transformationExecuterMap.claimValueForKey(vuriPair);
    }
}
