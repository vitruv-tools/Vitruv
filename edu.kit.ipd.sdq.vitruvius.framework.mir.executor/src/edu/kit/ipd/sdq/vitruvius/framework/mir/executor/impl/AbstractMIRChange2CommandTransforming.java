package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;

public abstract class AbstractMIRChange2CommandTransforming implements Change2CommandTransforming {
    private final static Logger LOGGER = Logger.getLogger(AbstractMIRChange2CommandTransforming.class);

    private final ResponseRegistry responseRegistry;
    private final InvariantRegistry invariantRegistry;
    private final Collection<MIRMappingRealization> mappings;

    public AbstractMIRChange2CommandTransforming() {
        this.responseRegistry = new ResponseRegistryImpl();
        this.invariantRegistry = new InvariantRegistryImpl();
        this.mappings = new HashSet<MIRMappingRealization>();

        this.setup();
    }

    protected void addResponse(final Response response) {
        this.responseRegistry.addResponse(response);
    }

    protected void addInvariant(final Invariant invariant) {
        this.invariantRegistry.addInvariant(invariant);
    }

    protected void addMIRMapping(final MIRMappingRealization mapping) {
        this.mappings.add(mapping);
    }

    @Override
    public void transformChanges2Commands(final Blackboard blackboard) {
        final List<Change> changes = blackboard.getAndArchiveChangesForTransformation();
        final List<Command> commands = new ArrayList<Command>();

        for (final Change change : changes) {
            commands.addAll(this.handleChange(change, blackboard));
        }

        blackboard.pushCommands(commands);
    }

    private List<Command> handleChange(final Change change, final Blackboard blackboard) {
        final List<Command> result = new ArrayList<Command>();
        if (change instanceof CompositeChange) {
            for (final Change c : ((CompositeChange) change).getChanges()) {
                result.addAll(this.handleChange(c, blackboard));
            }
        } else if (change instanceof EMFModelChange) {
            result.addAll(this.handleEChange(((EMFModelChange) change).getEChange(), blackboard));
        } else {
            throw new IllegalArgumentException("Change subtype " + change.getClass().getName() + " not handled");
        }

        return result;
    }

    protected List<Command> callRelevantMappings(final EChange eChange, final Blackboard blackboard) {
        final List<Command> result = new ArrayList<Command>();
        final Collection<MIRMappingRealization> relevantMappings = this.getCandidateMappings(eChange);
        for (final MIRMappingRealization mapping : relevantMappings) {
            result.add(EMFCommandBridge.createVitruviusTransformationRecordingCommand(() -> mapping.applyEChange(eChange, blackboard)));
        }
        return result;
    }

    protected List<Command> handleEChange(final EChange eChange, final Blackboard blackboard) {
        return this.callRelevantMappings(eChange, blackboard);
    }

    /**
     * Returns mappings that could be affected by the given {@link EChange}. Always returns a
     * conservative estimate. Should be overwritten by the generated subclass of
     * {@link AbstractMIRChange2CommandTransforming}, since the base implementation returns all
     * mappings (i.e. the most conservative estimate).
     */
    protected Collection<MIRMappingRealization> getCandidateMappings(final EChange eChange) {
        return this.mappings;
    }

    protected abstract void setup();
}
