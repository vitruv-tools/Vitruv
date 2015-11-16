package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class AbstractMappingChange2CommandTransforming implements Change2CommandTransforming {
	private final static Logger LOGGER = Logger.getLogger(AbstractMappingChange2CommandTransforming.class);

	private Set<Pair<ChangeType, MIRMappingRealization>> changeTypeAndmappings = new HashSet<Pair<ChangeType, MIRMappingRealization>>();

	public AbstractMappingChange2CommandTransforming() {
		this.changeTypeAndmappings = new HashSet<>();
		this.setup();
	}

	protected void addMapping(final Pair<ChangeType, MIRMappingRealization> mapping) {
		this.changeTypeAndmappings.add(mapping);
	}
	
	protected void addMapping(MIRMappingRealization mapping) {
		this.changeTypeAndmappings.add(new Pair<ChangeType, MIRMappingRealization>(ChangeType.ANY_CHANGE, mapping));
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
		final Set<MIRMappingRealization> relevantMappings = this.getCandidateMappings(eChange);
		for (final MIRMappingRealization mapping : relevantMappings) {
			result.add(EMFCommandBridge
					.createVitruviusTransformationRecordingCommand(() -> mapping.applyEChange(eChange, blackboard)));
		}
		return result;
	}

	protected List<Command> handleEChange(final EChange eChange, final Blackboard blackboard) {
		return this.callRelevantMappings(eChange, blackboard);
	}

	protected Set<MIRMappingRealization> getCandidateMappings(final EChange eChange) {
		return this.changeTypeAndmappings.stream().filter((it) -> it.getFirst().covers(eChange)).map((it) -> it.getSecond())
				.collect(Collectors.toSet());
	}

	protected abstract void setup();
	
	public abstract void setUserInteracting(MIRUserInteracting userInteracting);
}
