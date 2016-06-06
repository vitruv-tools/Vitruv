package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.palladiosimulator.pcm.core.entity.NamedElement;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationExecuter;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class PCMJaMoPPChange2CommandTransformerBase implements Change2CommandTransforming {

    // private static final Logger logger =
    // Logger.getLogger(PCMJaMoPPChange2CommandTransformerBase.class.getSimpleName());

    protected final TransformationExecuter transformationExecuter;

    private final List<Pair<VURI, VURI>> pairList;

    protected UserInteracting userInteracting;

    public PCMJaMoPPChange2CommandTransformerBase() {
        this.transformationExecuter = new TransformationExecuter();
        this.initializeTransformationExecuter();
        final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        final Pair<VURI, VURI> pcm2JaMoPP = new Pair<VURI, VURI>(pcmVURI, jaMoPPVURI);
        final Pair<VURI, VURI> jamopp2PCM = new Pair<VURI, VURI>(jaMoPPVURI, pcmVURI);
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
        if (this.hasChangeRefinerForChanges(changesForTransformation)) {
            commands.add(this.executeChangeRefiner(changesForTransformation, blackboard));
        } else {
            for (final Change change : changesForTransformation) {
                if (change instanceof EMFModelChange) {
                    commands.add(this.transformChange2Command((EMFModelChange) change, blackboard));
                } else if (change instanceof CompositeChange) {
                    commands.addAll(this.transformCompositeChange((CompositeChange) change, blackboard));
                }
            }
        }
        blackboard.pushCommands(commands);
    }

    @Override
    public List<Pair<VURI, VURI>> getTransformableMetamodels() {
        return this.pairList;
    }

    private VitruviusTransformationRecordingCommand transformChange2Command(final EMFModelChange emfModelChange,
            final Blackboard blackboard) {
        this.handlePackageInEChange(emfModelChange);
        this.transformationExecuter.setBlackboard(blackboard);

        final VitruviusTransformationRecordingCommand command = EMFCommandBridge
                .createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
                    @Override
                    public TransformationResult call() {
                    	if (handledByIntegration(emfModelChange, blackboard)) {
                    		return new TransformationResult();
                    	}
                        
                        // Standard Vitruvius Process:
                        final TransformationResult transformationResult = PCMJaMoPPChange2CommandTransformerBase.this.transformationExecuter
                                .executeTransformationForChange(emfModelChange.getEChange());
                        return transformationResult;
                    }
                });

        return command;
    }
    
    private boolean handledByIntegration(final EMFModelChange emfModelChange, final Blackboard blackboard) {
    	final boolean isNewClassOrInterfaceInIntegratedArea = isNewClassOrInterfaceInIntegratedArea(emfModelChange,
    			blackboard);
    	if (isNewClassOrInterfaceInIntegratedArea) {
    		showNewTypeInIntegratedAreaDialog();
            return true;
    	}
        final Set<EObject> correspondingIntegratedEObjects = getCorrespondingEObjectsIfIntegrated(emfModelChange, blackboard);
        if (correspondingIntegratedEObjects != null) {
        	StringBuffer buffer = new StringBuffer();
        	buffer.append("Elements in change were integrated into Vitruvius.\n");
        	buffer.append("Please fix manually. Corresponding object(s):\n");
        	for (EObject obj : correspondingIntegratedEObjects) {
        		String name = getReadableName(obj);
        		buffer.append("\n");
        		buffer.append(name);
        	}
            PCMJaMoPPChange2CommandTransformerBase.this.userInteracting.showMessage(
                    UserInteractionType.MODAL,
                    buffer.toString());
            return true;
        } 
        return false;
	}
    
    private void showNewTypeInIntegratedAreaDialog() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Created class or interface in area with integrated objects.\n");
		buffer.append("Please handle consistency manually.");
		PCMJaMoPPChange2CommandTransformerBase.this.userInteracting.showMessage(
		        UserInteractionType.MODAL,
		        buffer.toString());
	}

	private boolean isNewClassOrInterfaceInIntegratedArea(EMFModelChange emfModelChange,
            final Blackboard blackboard) {
    	final EChange eChange = emfModelChange.getEChange();
        if (eChange instanceof CreateNonRootEObjectInList) { 
        	//Check if this is a creation of a class or interface on file level.
        	//In this case we need to check if any siblings in the package have been integrated
        	final CreateNonRootEObjectInList<?> change = (CreateNonRootEObjectInList<?>) eChange;
        	Class<?> classOfAffected = change.getNewAffectedEObject().eClass().getInstanceClass();
        	Class<?> classOfCreated = change.getNewValue().eClass().getInstanceClass();
        	if (classOfAffected.equals(CompilationUnit.class) && 
        			(classOfCreated.equals(org.emftext.language.java.classifiers.Class.class) || 
        			 classOfCreated.equals(Interface.class))) {
        		CompilationUnit cu = (CompilationUnit) change.getNewAffectedEObject();
                final InternalCorrespondenceInstance ci = blackboard.getCorrespondenceInstance();
                TUID newCompilationUnitTuid = ci.calculateTUIDFromEObject(cu);
                String packagePartOfNewTuid = getPackagePart(newCompilationUnitTuid);
    			for (Correspondence corr : ci.getAllCorrespondences()) {
    				if (corr instanceof IntegrationCorrespondence) {
    					IntegrationCorrespondence integrationCorr = (IntegrationCorrespondence) corr;
	    				if (integrationCorr.isCreatedByIntegration()) {
	    					ArrayList<TUID> allTUIDs = new ArrayList<TUID>();
	    					allTUIDs.addAll(corr.getATUIDs());
	    					allTUIDs.addAll(corr.getBTUIDs());
	    					for (TUID tuid : allTUIDs) {
	    						String packagePart = getPackagePart(tuid);
	    						if (packagePartOfNewTuid.startsWith(packagePart)) {
	    							// Is in same package or child package of a package with integrated objects
	    							return true;
	    						}
	    					}
	    				}
    				}
    			}
        	}
    	} 
        return false;
	}

    private String getPackagePart(TUID newCompilationUnitTuid) {
		String originalTuidAsString = newCompilationUnitTuid.toString();
		int lastSlashIndex = originalTuidAsString.lastIndexOf("/");
		return originalTuidAsString.substring(0, lastSlashIndex);
	}

	private String getReadableName(EObject obj) {
    	String name = getDirectNameIfNamed(obj);
		String className = obj.eClass().getName();
    	EObject container = obj.eContainer();
    	while (name == null) {
    		if (container == null) {
    			name = className;
    		} else {
    			String containerName = getDirectNameIfNamed(container);
    			if (containerName != null) {
    				String containerClassName = container.eClass().getName();
					name = className + " in " + containerClassName + ": " + containerName;
    			} else {
    				container = container.eContainer();
    			}
    		}
    	}
		return name;
	}

	private String getDirectNameIfNamed(EObject obj) {
		String name = null;
		String className = obj.eClass().getName();
		if (obj instanceof NamedElement) {
			NamedElement named = (NamedElement) obj;
			name =  className + ": " + named.getEntityName();
		} else if (obj instanceof org.emftext.language.java.commons.NamedElement) {
			org.emftext.language.java.commons.NamedElement named = (org.emftext.language.java.commons.NamedElement) obj;
			name =  className + ": " + named.getName();
		}
		return name;
	}

	/**
     * 
     * @return set of corresponding EObjects if integrated, else null
     */
    private Set<EObject> getCorrespondingEObjectsIfIntegrated(final EMFModelChange emfModelChange,
            final Blackboard blackboard) {
        final CorrespondenceInstance<Correspondence> ci = blackboard.getCorrespondenceInstance();

        final EChange eChange = emfModelChange.getEChange();
        EObject eObj = null;
        if (eChange instanceof EFeatureChange<?>) {
            final EFeatureChange<?> featureChange = (EFeatureChange<?>) eChange;
            eObj = featureChange.getNewAffectedEObject();
        } else if (eChange instanceof ReplaceRootEObject<?>) {
            final ReplaceRootEObject<?> replChange = (ReplaceRootEObject<?>) eChange;
            eObj = replChange.getNewValue();
        } else if (eChange instanceof DeleteRootEObject<?>) {
            final DeleteRootEObject<?> delChange = (DeleteRootEObject<?>) eChange;
            eObj = delChange.getOldValue();
        }

        final CorrespondenceInstance<IntegrationCorrespondence> integrationView = ci
                .getView(IntegrationCorrespondence.class);
        if (eObj != null) {
            final Set<EObject> set = CollectionBridge.toSet(eObj);
            final Set<IntegrationCorrespondence> correspondences = integrationView
                    .getCorrespondencesThatInvolveAtLeast(set);
            Set<EObject> correspondingObjects = new HashSet<EObject>();
            for (final IntegrationCorrespondence integratedCorrespondence : correspondences) {
                if (integratedCorrespondence.isCreatedByIntegration()) {
                	EList<EObject> as = integratedCorrespondence.getAs();
                	EList<EObject> bs = integratedCorrespondence.getAs();
                	if (as.contains(eObj)) {
                		correspondingObjects.addAll(bs);
                	} else {
                		correspondingObjects.addAll(as);
                	}
                	return correspondingObjects;
                }
            }
        }
        return null;
    }

    private List<Command> transformCompositeChange(final CompositeChange compositeChange, final Blackboard blackboard) {
        final List<Command> commands = new ArrayList<Command>();
        for (final Change change : compositeChange.getChanges()) {
            // handle CompositeChanges in CompositeChanges
            if (change instanceof CompositeChange) {
                commands.addAll(this.transformCompositeChange((CompositeChange) change, blackboard));
                continue;
            } else if (change instanceof EMFModelChange) {
                commands.add(this.transformChange2Command((EMFModelChange) change, blackboard));
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

    protected boolean hasChangeRefinerForChanges(final List<Change> changesForTransformation) {
        return false;
    }

    protected abstract VitruviusTransformationRecordingCommand executeChangeRefiner(
            final List<Change> changesForTransformation, final Blackboard blackboard);

    @Override
    public void setUserInteracting(final UserInteracting userInteracting) {
        this.userInteracting = userInteracting;
        this.transformationExecuter.setUserInteracting(userInteracting);
    }
}
