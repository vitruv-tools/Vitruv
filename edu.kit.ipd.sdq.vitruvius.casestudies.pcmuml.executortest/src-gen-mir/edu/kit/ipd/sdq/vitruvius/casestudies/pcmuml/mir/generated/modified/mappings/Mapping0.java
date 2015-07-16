package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper;
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
		return UMLPackage.eINSTANCE.getPackage();
	}

	@Override
	protected MIRMappingChangeResult restorePostConditions(EObject eObject, EObject target, EChange change) {
		MIRMappingChangeResult result = new MIRMappingChangeResult();
		LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString() + ")");
		
		Package pkg = JavaHelper.requireType(eObject, Package.class);
		BasicComponent bc = JavaHelper.requireType(target, BasicComponent.class);

		bc.setEntityName(pkg.getName());

		result.addObjectToSave(bc);
		
		return result;
	}

	@Override
	protected MIRMappingChangeResult createCorresponding(EObject eObject,
			MappedCorrespondenceInstance correspondenceInstance) {
		LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + correspondenceInstance.toString() + ")");

		org.eclipse.uml2.uml.Package iface = (org.eclipse.uml2.uml.Package) eObject;
		BasicComponent bc = RepositoryFactory.eINSTANCE.createBasicComponent();

		MIRMappingChangeResult changeResult = new MIRMappingChangeResult();
		changeResult.addObjectToSave(bc);
		LOGGER.trace("eObject to save: " + bc.toString());
		changeResult.addCorrespondence(iface, bc);
		LOGGER.trace("addCorrespondence: " + iface.toString() + ", " + bc.toString());
		return changeResult;
	}
	
	@Override
	protected MIRMappingChangeResult deleteCorresponding(EObject eObject, EObject target,
			MappedCorrespondenceInstance correspondenceInstance) {
		LOGGER.trace("deleteCorresponding(" + eObject.toString()
			+ ", " + target.toString()
			+ ", " + correspondenceInstance.toString() + ")");
		
		return super.deleteCorresponding(eObject, target, correspondenceInstance);
	}
}
