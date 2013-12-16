package edu.kit.ipd.sdq.vitruvius.framework.design.mir.generator;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MIRManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SyncTransformationGenerating;

public class MIRGenerator implements SyncTransformationGenerating {
	private final MIRManaging mirManaging;

	public MIRGenerator(MIRManaging mirManaging) {
		this.mirManaging = mirManaging;
	}

	@Override
	public Collection<SyncTransformation> generateSyncTransformations(
			URI mmURI1, URI mmURI2) {
		// TODO Auto-generated method stub
		return null;
	}

}
