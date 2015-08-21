package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.ChangeSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class PCMJaMoPPChange2CommandTransformerBase implements Change2CommandTransforming {

    private static final Logger logger = Logger.getLogger(PCMJaMoPPChange2CommandTransformerBase.class.getSimpleName());

    protected final ChangeSynchronizer changeSynchronizer;

    private final List<Pair<VURI, VURI>> pairList;

    protected UserInteractor userInteracting;

    public PCMJaMoPPChange2CommandTransformerBase() {
        this.changeSynchronizer = new ChangeSynchronizer();
        this.initializeChangeSynchronizer();
        final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        final Pair<VURI, VURI> pcm2JaMoPP = new Pair<VURI, VURI>(pcmVURI, jaMoPPVURI);
        final Pair<VURI, VURI> jamopp2PCM = new Pair<VURI, VURI>(jaMoPPVURI, jaMoPPVURI);
        this.pairList = new ArrayList<Pair<VURI, VURI>>();
        this.pairList.add(jamopp2PCM);
        this.pairList.add(pcm2JaMoPP);
    }

    protected void initializeChangeSynchronizer() {
        this.userInteracting = new UserInteractor();

        // Mapping for EObjects in order to avoid runtime exceptions
        this.changeSynchronizer.addMapping(new DefaultEObjectMappingTransformation());

        // set userInteractor
        this.changeSynchronizer.setUserInteracting(this.userInteracting);
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

    private Command transformChange2Command(final EMFModelChange emfModelChange, final Blackboard blackboard) {
        this.handlePackageInEChange(emfModelChange);
        this.changeSynchronizer.setBlackboard(blackboard);

        // execute actual Transformation
        final TransformationChangeResult transformationChangeResult = this.changeSynchronizer
                .synchronizeChange(emfModelChange.getEChange());

        // Translate TransformationChangeResult to EMFChangeResult
        final EMFChangeResult emfChangeResult = new EMFChangeResult();
        this.handleEObjectsToDeleteInTransformationChange(transformationChangeResult.getExistingObjectsToDelete(),
                emfChangeResult.getExistingObjectsToDelete());
        this.handleEObjectsToSaveInTransformationChange(transformationChangeResult.getExistingObjectsToSave(),
                emfChangeResult.getExistingObjectsToSave());
        this.handleNewRootEObjects(transformationChangeResult.getNewRootObjectsToSave(),
                emfChangeResult.getNewRootObjectsToSave(), emfModelChange.getURI());

        emfChangeResult.addCorrespondenceChanges(transformationChangeResult);
        return null;
    }

    public List<Command> transformCompositeChange(final CompositeChange compositeChange, final Blackboard blackboard) {
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

    @Override
    public List<Pair<VURI, VURI>> getTransformableMetamodels() {
        return this.pairList;
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

    private void handleNewRootEObjects(final Set<EObject> newRootEObjectsToSave,
            final Set<Pair<EObject, VURI>> newVURIsToSave, final VURI sourceModelVURI) {
        for (final EObject newRootEObject : newRootEObjectsToSave) {
            if (newRootEObject instanceof JavaRoot) {
                final JavaRoot newJavaRoot = (JavaRoot) newRootEObject;
                // TODO: use configured src-folder path instead of hardcoded "src"
                final String srcFolderPath = this.getFolderPathInProjectOfResource(sourceModelVURI, "src");
                String javaRootPath = newJavaRoot.getNamespacesAsString().replace(".", "/").replace("$", "/")
                        + newJavaRoot.getName().replace("$", ".");
                if (newJavaRoot instanceof Package) {
                    javaRootPath = javaRootPath + "/package-info.java";
                }
                final VURI cuVURI = VURI.getInstance(srcFolderPath + javaRootPath);
                final Pair<EObject, VURI> newEObjectVURIPair = new Pair<EObject, VURI>(newJavaRoot, cuVURI);
                newVURIsToSave.add(newEObjectVURIPair);
                this.addRootEObjectVURIPair(newVURIsToSave, newJavaRoot, srcFolderPath, javaRootPath);
            } else if (newRootEObject instanceof Repository) {
                this.handlePCMRootEObject(newVURIsToSave, sourceModelVURI, newRootEObject, "repository");
            } else if (newRootEObject instanceof System) {
                this.handlePCMRootEObject(newVURIsToSave, sourceModelVURI, newRootEObject, "system");
            }
        }
    }

    private void handlePCMRootEObject(final Set<Pair<EObject, VURI>> newVURIsToSave, final VURI sourceModelVURI,
            final EObject newRootEObject, final String fileExt) {
        final NamedElement namedElement = (NamedElement) newRootEObject;
        final String modelFolderPath = this.getFolderPathInProjectOfResource(sourceModelVURI, "model");
        final String repoitoryFileName = namedElement.getEntityName() + "." + fileExt;
        this.addRootEObjectVURIPair(newVURIsToSave, namedElement, modelFolderPath, repoitoryFileName);
    }

    private void addRootEObjectVURIPair(final Set<Pair<EObject, VURI>> newVURIsToSave, final EObject rootEObject,
            String folderName, final String fileName) {
        if (!folderName.endsWith("/")) {
            folderName = folderName + "/";
        }
        final VURI repoVURI = VURI.getInstance(folderName + fileName);
        final Pair<EObject, VURI> newEObjectVURIPair = new Pair<EObject, VURI>(rootEObject, repoVURI);
        newVURIsToSave.add(newEObjectVURIPair);
    }

    private void handleEObjectsToSaveInTransformationChange(final Set<EObject> eObjectsInTransformationChange,
            final Set<VURI> vurisInEMFResultChange) {
        for (final EObject eObject : eObjectsInTransformationChange) {
            final Resource resource = eObject.eResource();
            if (null == resource) {
                logger.warn("Resource of EObject is null. Can not save resource of eObject: " + eObject);
                continue;
            }
            final VURI vuri = VURI.getInstance(resource);
            vurisInEMFResultChange.add(vuri);
        }
    }

    private void handleEObjectsToDeleteInTransformationChange(
            final Set<VURI> existingObjectsToDeleteeInTransformationChangeResult,
            final Set<VURI> existingObjectsToDeleteInEMFChangeResult) {
        existingObjectsToDeleteInEMFChangeResult.addAll(existingObjectsToDeleteeInTransformationChangeResult);
    }

    private String getFolderPathInProjectOfResource(final VURI sourceModelVURI, final String folderName) {
        final IFile fileSourceModel = EMFBridge.getIFileForEMFUri(sourceModelVURI.getEMFUri());
        final IProject projectSourceModel = fileSourceModel.getProject();
        String srcFolderPath = projectSourceModel.getFullPath().toString() + "/" + folderName + "/";
        if (srcFolderPath.startsWith("/")) {
            srcFolderPath = srcFolderPath.substring(1, srcFolderPath.length());
        }
        return srcFolderPath;
    }
}
