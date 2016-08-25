package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util.Java2PcmPackagePreprocessor;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.AbstractChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.ChangeProcessor;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.ChangeProcessorResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.ConcreteChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.DefaultEObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationExecuter;

public abstract class PCMJaMoPPChange2CommandTransformerBase extends AbstractChange2CommandTransforming {

    protected final TransformationExecuter transformationExecuter;

    public PCMJaMoPPChange2CommandTransformerBase(TransformationMetamodelPair metamodelPair) {
    	super(metamodelPair);
        this.transformationExecuter = new TransformationExecuter();
    }

    /**
     * Executes the Java2PCM and PCM2Java transformations.
     */
    @Override
    public void transformChanges2Commands(final Blackboard blackboard) {
        final List<VitruviusChange> changesForTransformation = blackboard.getAndArchiveChangesForTransformation();
        final List<Command> commands = new ArrayList<Command>();
        for (final VitruviusChange change : changesForTransformation) {
        	if (change instanceof ConcreteChange) {
        		ConcreteChange emfModelChange = (ConcreteChange) change;
               	List<Command> generatedCommands = new Java2PcmPackageMappingMethodBodyChangePreprocessor(getUserInteracting()).transformChange(emfModelChange, blackboard.getCorrespondenceModel()).getGeneratedCommands();
               	commands.addAll(generatedCommands);
                // Special behavior for changes to integrated elements
               	ChangeProcessorResult integrationResult = new CodeIntegrationChangeProcessor(getUserInteracting()).transformChange(emfModelChange, blackboard.getCorrespondenceModel());
//              IntegrationChange2CommandResult integrationTransformResult = integrationTransformer.compute(emfModelChange.getEChanges().get(0), blackboard.getCorrespondenceModel());
//              if (integrationTransformResult.isIntegrationChange()) {
//              	commands.addAll(integrationTransformResult.getCommands());
//              } else {
//              	commands.add(this.transformChange2Command(emfModelChange, blackboard));
//              }
               	commands.addAll(integrationResult.getGeneratedCommands());
               	commands.add(this.transformChange2Command(integrationResult.getResultingChange(), blackboard));
            } else if (change instanceof CompositeChange) {
            	commands.addAll(this.transformCompositeChange((CompositeChange) change, blackboard));
            }
        }
        blackboard.pushCommands(commands);
    }

    private VitruviusTransformationRecordingCommand transformChange2Command(final ConcreteChange emfModelChange,
            final Blackboard blackboard) {
    	ChangeProcessor packagePreprocessor = new Java2PcmPackagePreprocessor(getUserInteracting());
   		packagePreprocessor.transformChange(emfModelChange, blackboard.getCorrespondenceModel());
        //this.handlePackageInEChange(emfModelChange);
        this.transformationExecuter.setCorrespondenceModel(blackboard.getCorrespondenceModel());

        final VitruviusTransformationRecordingCommand command = EMFCommandBridge
                .createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
                    @Override
                    public TransformationResult call() {
                        final TransformationResult transformationResult = PCMJaMoPPChange2CommandTransformerBase.this.transformationExecuter
                                .executeTransformationForChange(emfModelChange.getEChanges().get(0));
                        return transformationResult;
                    }
                });

        return command;
    }

    private List<Command> transformCompositeChange(final CompositeChange compositeChange, final Blackboard blackboard) {
        final List<Command> commands = new ArrayList<Command>();
        for (final VitruviusChange change : compositeChange.getChanges()) {
            // handle CompositeChanges in CompositeChanges
            if (change instanceof CompositeChange) {
                commands.addAll(this.transformCompositeChange((CompositeChange) change, blackboard));
                continue;
            } else if (change instanceof ConcreteChange) {
                commands.add(this.transformChange2Command((ConcreteChange) change, blackboard));
            }
        }
        return commands;
    }

    @Override
    protected void setup() {
    	// Mapping for EObjects in order to avoid runtime exceptions
        this.transformationExecuter.addMapping(new DefaultEObjectMappingTransformation());
        // set userInteractor
        this.transformationExecuter.setUserInteracting(getUserInteracting());
    }
    
}
