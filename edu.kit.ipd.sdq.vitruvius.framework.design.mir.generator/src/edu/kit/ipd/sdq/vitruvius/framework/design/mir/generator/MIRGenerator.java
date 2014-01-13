package edu.kit.ipd.sdq.vitruvius.framework.design.mir.generator;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MIRManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SyncTransformationGenerating;

public class MIRGenerator implements SyncTransformationGenerating {
	private final MIRManaging mirManaging;

	public MIRGenerator(MIRManaging mirManaging) {
		this.mirManaging = mirManaging;
	}

	@Override
	public Collection<SyncTransformation> generateSyncTransformations(
			VURI mmURI1, VURI mmURI2) {
		// TODO Auto-generated method stub
		return null;
	}

}
