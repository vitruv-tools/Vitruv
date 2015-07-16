package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.MIRMappingChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;


/**
 * Class Mapping
 */
public class Mapping1 extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(Mapping1.class);
	
	// Singleton
	public final static Mapping1 INSTANCE = new Mapping1();
	
	private Mapping1() {}
	
	protected boolean checkConditions(EObject context,
		MappedCorrespondenceInstance correspondenceInstance) {

		if (!(context instanceof org.palladiosimulator.pcm.repository.BasicComponent)) {
			return false;
		}
		
		org.palladiosimulator.pcm.repository.BasicComponent bc =
			(org.palladiosimulator.pcm.repository.BasicComponent) context;

		return true;
	}

	@Override
	protected EClass getMappedEClass() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected MIRMappingChangeResult restorePostConditions(EObject eObject, EObject target, EChange change) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected MIRMappingChangeResult createCorresponding(EObject eObject,
			MappedCorrespondenceInstance correspondenceInstance) {
		throw new UnsupportedOperationException();
	}
}
