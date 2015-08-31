package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization;


/**
 * Class Mapping
 */
public class MappingPackageToBasicComponent extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(MappingPackageToBasicComponent.class);
	
	// Singleton
	public final static MappingPackageToBasicComponent INSTANCE = new MappingPackageToBasicComponent();
	
	private MappingPackageToBasicComponent() {}
	
	@Override
	protected boolean checkConditions(EObject context,
		Blackboard blackboard) {

		if (!(context instanceof org.eclipse.uml2.uml.Package)) {
			return false;
		}
		
		org.eclipse.uml2.uml.Package up =
			(org.eclipse.uml2.uml.Package) context;
		
		/*
		 * call method with arguments up.
		 */
		
		return true;
	}

	@Override
	protected EClass getMappedEClass() {
		return UMLPackage.eINSTANCE.getPackage();
	}

	@Override
	protected void restorePostConditions(EObject eObject, EObject target, EChange change, Blackboard blackboard) {
		LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString() + ")");
		
		Package pkg = JavaHelper.requireType(eObject, Package.class);
		final BasicComponent bc = JavaHelper.requireType(target, BasicComponent.class);

		bc.setEntityName(pkg.getName());
		/*
		 * Instead:
		 * call method here that references pkg, bc
		 */
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
		return MappingPackageToBasicComponent.class.getName();
	}

	@Override
	protected Collection<EObject> createCorresponding(EObject eObject, Blackboard blackboard) {
		LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + blackboard.toString() + ")");

		org.eclipse.uml2.uml.Package pkg = (org.eclipse.uml2.uml.Package) eObject;
		BasicComponent bc = RepositoryFactory.eINSTANCE.createBasicComponent();
		VURI resourceVURI = VURI.getInstance(EclipseHelper.askForNewResource(bc));
		
		// FIXME DW add to Mapping...
		final ModelProviding modelProviding = blackboard.getModelProviding();
		final ModelInstance modelInstance = modelProviding.getAndLoadModelInstanceOriginal(resourceVURI);
		modelInstance.getRootElements().add(bc);
		
		final MappedCorrespondenceInstance correspondenceInstance = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
		correspondenceInstance.createMappedCorrespondence(pkg, bc, this);
		
		return Collections.singleton(bc);
	}
	
	public static Optional<BasicComponent> getCorresponding(Package pkg, Blackboard blackboard) {
		final MappedCorrespondenceInstance ci = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
		final Optional<BasicComponent> bc = JavaHelper.tryCast(ci.getMappingTarget(pkg, MappingPackageToBasicComponent.INSTANCE), BasicComponent.class);
				
		return bc;
	}
	
	public static BasicComponent claimCorresponding(Package pkg, Blackboard blackboard) {
		final BasicComponent bc = getCorresponding(pkg, blackboard)
				.orElseThrow(() -> new IllegalStateException("Could not find mapped BasicComponent for Package " + pkg.toString()));
		
		return bc;
	}
}
