package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappedCorrespondenceInstance;
import org.eclipse.emf.ecore.EObject;
import org.apache.log4j.Logger;

public class PCM2UMLExecutorCorrespondence extends AbstractMappedCorrespondenceInstance {
	private static final Logger LOGGER = Logger.getLogger(PCM2UMLExecutorCorrespondence.class);
	
	private CorrespondenceInstance correspondenceInstance;
	
	@Override
	public CorrespondenceInstance getCorrespondenceInstance() {
		return correspondenceInstance;
	}
	
	public void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance;
	}
	
	public PCM2UMLExecutorCorrespondence() {
	}
	
	public PCM2UMLExecutorCorrespondence(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance;
	}
	
	public boolean isMappedByMapping0(org.eclipse.uml2.uml.Package up) {
		LOGGER.trace("isMappedByMapping0(" + up.toString() +")");
		return false;
	}
	
	public org.palladiosimulator.pcm.repository.BasicComponent getMappingTargetForMapping0(org.eclipse.uml2.uml.Package up) {
		LOGGER.trace("getMappingTargetForMapping0(" + up.toString() +")");
		return null;
	}
	public boolean isMappedByMapping1(org.palladiosimulator.pcm.repository.BasicComponent bc) {
		LOGGER.trace("isMappedByMapping1(" + bc.toString() +")");
		return false;
	}
	
	public org.eclipse.uml2.uml.Package getMappingTargetForMapping1(org.palladiosimulator.pcm.repository.BasicComponent bc) {
		LOGGER.trace("getMappingTargetForMapping1(" + bc.toString() +")");
		return null;
	}
}

