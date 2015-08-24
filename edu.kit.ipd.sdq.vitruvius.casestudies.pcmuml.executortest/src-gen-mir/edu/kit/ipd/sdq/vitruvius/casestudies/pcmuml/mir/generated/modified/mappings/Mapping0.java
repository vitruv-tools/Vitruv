package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings;

import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization;


/**
 * Class Mapping
 */
public class Mapping0 extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(Mapping0.class);
	
	// Singleton
	public final static Mapping0 INSTANCE = new Mapping0();
	
	private Mapping0() {}
	
	@Override
	protected boolean checkConditions(EObject context,
		Blackboard blackboard) {

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
	protected void restorePostConditions(EObject eObject, EObject target, EChange change) {
		LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString() + ")");
		
		Package pkg = JavaHelper.requireType(eObject, Package.class);
		final BasicComponent bc = JavaHelper.requireType(target, BasicComponent.class);

		bc.setEntityName(pkg.getName());	
	}
	
	@Override
	protected void deleteCorresponding(EObject eObject, EObject target, Blackboard blackboard) {
		LOGGER.trace("deleteCorresponding(" + eObject.toString()
			+ ", " + target.toString()
			+ ", " + blackboard.toString() + ")");
		
		super.deleteCorresponding(eObject, target, blackboard);
	}

	@Override
	public String getMappingID() {
		return Mapping0.class.getName();
	}

	@Override
	protected Collection<EObject> createCorresponding(EObject eObject, Blackboard blackboard) {
		LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + blackboard.toString() + ")");

		org.eclipse.uml2.uml.Package pkg = (org.eclipse.uml2.uml.Package) eObject;
		BasicComponent bc = RepositoryFactory.eINSTANCE.createBasicComponent();
		getMappedCorrespondenceInstanceFromBlackboard(blackboard).createMappedCorrespondence(pkg, bc, this);
		
		return Collections.singleton(bc);
	}
}
