package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.containers.Package;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class PCMJaMoPPChange2CommandTransformerBase implements Change2CommandTransforming {

    // private static final Logger logger =
    // Logger.getLogger(PCMJaMoPPChange2CommandTransformerBase.class.getSimpleName());

    protected final TransformationExecuter transformationExecuter;

    private final List<Pair<VURI, VURI>> pairList;

    protected UserInteractor userInteracting;

    public PCMJaMoPPChange2CommandTransformerBase() {
        this.transformationExecuter = new TransformationExecuter();
        this.initializeTransformationExecuter();
        final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        final Pair<VURI, VURI> pcm2JaMoPP = new Pair<VURI, VURI>(pcmVURI, jaMoPPVURI);
        final Pair<VURI, VURI> jamopp2PCM = new Pair<VURI, VURI>(jaMoPPVURI, jaMoPPVURI);
        this.pairList = new ArrayList<Pair<VURI, VURI>>();
        this.pairList.add(jamopp2PCM);
        this.pairList.add(pcm2JaMoPP);
    }

    protected void initializeTransformationExecuter() {
        this.userInteracting = new UserInteractor();

        // Mapping for EObjects in order to avoid runtime exceptions
        this.transformationExecuter.addMapping(new DefaultEObjectMappingTransformation());

        // set userInteractor
        this.transformationExecuter.setUserInteracting(this.userInteracting);
    }

    /**
     * Executes the Java2PCM and PCM2Java transformations.
     */
    @Override
    public void transformChanges2Commands(final Blackboard blackboard) {
        final List<Change> changesForTransformation = blackboard.getAndArchiveChangesForTransformation();
        final List<Command> commands = new ArrayList<Command>();
        for (final Change change : changesForTransformation) {
            if (change instanceof EMFModelChange) {
                commands.add(this.transformChange2Command((EMFModelChange) change, blackboard));
            } else if (change instanceof CompositeChange) {
                commands.addAll(this.transformCompositeChange((CompositeChange) change, blackboard));
            }
        }
        blackboard.pushCommands(commands);
    }

    @Override
    public List<Pair<VURI, VURI>> getTransformableMetamodels() {
        return this.pairList;
    }

    private Command transformChange2Command(final EMFModelChange emfModelChange, final Blackboard blackboard) {
        this.handlePackageInEChange(emfModelChange);
        this.transformationExecuter.setBlackboard(blackboard);

        final Command command = EMFCommandBridge.createCommand(new Runnable() {

            @Override
            public void run() {
                // execute command converting
                PCMJaMoPPChange2CommandTransformerBase.this.transformationExecuter
                        .executeTransformationForChange(emfModelChange.getEChange());
            }
        });

        return command;
    }

    private List<Command> transformCompositeChange(final CompositeChange compositeChange, final Blackboard blackboard) {
        final List<Command> commands = new ArrayList<Command>();
        for (final Change change : compositeChange.getChanges()) {
            // handle CompositeChanges in CompositeChanges
            if (change instanceof CompositeChange) {
                commands.addAll(this.transformCompositeChange((CompositeChange) change, blackboard));
                continue;
            } else if (change instanceof EMFModelChange) {
                this.transformChange2Command((EMFModelChange) change, blackboard);
            }
        }
        return commands;
    }

    /**
     * Special treatment for packages: we have to use the package-info file as input for the
     * transformation and make sure that the packages have resources attached
     *
     * @param change
     *            the change that may contain the newly created package
     */
    private void handlePackageInEChange(final EMFModelChange change) {
        if (change.getEChange() instanceof CreateRootEObject<?>) {
            final CreateRootEObject<?> createRoot = (CreateRootEObject<?>) change.getEChange();
            this.attachPackageToResource(createRoot.getNewValue(), change.getURI());
        } else if (change.getEChange() instanceof UpdateSingleValuedEAttribute<?>) {
            final UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute = (UpdateSingleValuedEAttribute<?>) change
                    .getEChange();
            this.prepareRenamePackageInfos(updateSingleValuedEAttribute, change.getURI());
        } // TODO: package deletion
    }

    private void prepareRenamePackageInfos(final UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute,
            final VURI vuri) {
        if (updateSingleValuedEAttribute.getOldAffectedEObject() instanceof Package
                && updateSingleValuedEAttribute.getNewAffectedEObject() instanceof Package) {
            final Package oldPackage = (Package) updateSingleValuedEAttribute.getOldAffectedEObject();
            final Package newPackage = (Package) updateSingleValuedEAttribute.getNewAffectedEObject();
            this.attachPackageToResource(oldPackage, vuri);
            String newVURIKey = vuri.toString();
            final String oldPackagePath = oldPackage.getName().replace(".", "/");
            final String newPackagePath = newPackage.getName().replace(".", "/");
            newVURIKey = newVURIKey.replace(oldPackagePath, newPackagePath);
            final VURI newVURI = VURI.getInstance(newVURIKey);
            this.attachPackageToResource(newPackage, newVURI);
        }
    }

    private void attachPackageToResource(final EObject eObject, final VURI vuri) {
        if (eObject instanceof Package) {
            final Package newPackage = (Package) eObject;
            // attach the package to a resource in order to enable the calculation of
            // a TUID in the transformations
            final ResourceSet resourceSet = new ResourceSetImpl();
            final Resource resource = resourceSet.createResource(vuri.getEMFUri());
            resource.getContents().add(newPackage);
        }
    }
}
