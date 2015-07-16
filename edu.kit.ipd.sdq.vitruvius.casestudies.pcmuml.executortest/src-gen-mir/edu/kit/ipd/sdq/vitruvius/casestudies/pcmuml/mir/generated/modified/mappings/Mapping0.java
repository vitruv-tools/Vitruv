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
public class Mapping0 extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(Mapping0.class);
	
	// Singleton
	public final static Mapping0 INSTANCE = new Mapping0();
	
	private Mapping0() {}
	
	protected boolean checkConditions(EObject context,
		MappedCorrespondenceInstance correspondenceInstance) {

		if (!(context instanceof org.eclipse.uml2.uml.Package)) {
			return false;
		}
		
		org.eclipse.uml2.uml.Package up =
			(org.eclipse.uml2.uml.Package) context;
		
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
