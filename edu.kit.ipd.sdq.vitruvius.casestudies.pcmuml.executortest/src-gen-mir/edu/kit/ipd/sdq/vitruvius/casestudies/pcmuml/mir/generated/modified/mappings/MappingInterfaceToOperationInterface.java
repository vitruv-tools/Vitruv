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
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization;

public class MappingInterfaceToOperationInterface extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(MappingInterfaceToOperationInterface.class);

	// Singleton
	public final static MappingInterfaceToOperationInterface INSTANCE = new MappingInterfaceToOperationInterface();

	private MappingInterfaceToOperationInterface() {
	}

	@Override
	protected boolean checkConditions(EObject context, Blackboard blackboard) {

		if (!(context instanceof org.eclipse.uml2.uml.Class)) {
			return false;
		}

		org.eclipse.uml2.uml.Class upc = (org.eclipse.uml2.uml.Class) context;
		
		final Optional<Package> up = getParent(upc);
		if (!up.isPresent()) {
			return false;
		}
		
		final Optional<BasicComponent> bc = MappingPackageToBasicComponent.getCorresponding(up.get(), blackboard);
		if (!bc.isPresent()) {
			return false;
		}
		
		/*
		 * Paste code (references to upc and up possible) here.
		 * Or call method with arguments upc, up.
		 */

		return true;
	}

	@Override
	protected EClass getMappedEClass() {
		return UMLPackage.eINSTANCE.getClass_();
	}

	@Override
	protected void restorePostConditions(EObject eObject, EObject target, EChange change, Blackboard blackboard) {
		LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString()
				+ ")");

		org.eclipse.uml2.uml.Class upc = JavaHelper.requireType(eObject, org.eclipse.uml2.uml.Class.class);
		final OperationProvidedRole bcopr = JavaHelper.requireType(target, OperationProvidedRole.class);
		
		final Package up = claimParent(upc);
		final BasicComponent bc = claimParentCorresponding(upc, blackboard);

		bcopr.setEntityName(upc.getName());
		/*
		 * Call method here that references upc, bcopr, up & bc ???
		 */
	}

	@Override
	protected void deleteCorresponding(EObject eObject, EObject target, Blackboard blackboard) {
		LOGGER.trace("deleteCorresponding(" + eObject.toString() + ", " + target.toString() + ", "
				+ blackboard.toString() + ")");

		super.deleteCorresponding(eObject, target, blackboard);
	}

	@Override
	public String getMappingID() {
		return MappingInterfaceToOperationInterface.class.getName();
	}

	@Override
	protected Collection<EObject> createCorresponding(EObject eObject, Blackboard blackboard) {
		LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + blackboard.toString() + ")");
		final MappedCorrespondenceInstance ci = getMappedCorrespondenceInstanceFromBlackboard(blackboard);

		org.eclipse.uml2.uml.Class upc = JavaHelper.requireType(eObject, org.eclipse.uml2.uml.Class.class);

		final OperationProvidedRole bcopr = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		final BasicComponent bc = claimParentCorresponding(upc, blackboard);

		bc.getProvidedRoles_InterfaceProvidingEntity().add(bcopr);

		ci.createMappedCorrespondence(upc, bcopr, this);
		// FIXME DW create correspondence hierarchy?

		return Collections.singleton(bcopr);
	}

	private Optional<Package> getParent(org.eclipse.uml2.uml.Class upc) {
		final Optional<Package> upcParent = EcoreHelper.findOneReferencee(upc,
				UMLPackage.eINSTANCE.getPackage_PackagedElement(), Package.class);

		return upcParent;
	}

	private Package claimParent(org.eclipse.uml2.uml.Class upc) {
		final Optional<Package> upcParent = getParent(upc);
		final Package pkg = upcParent.orElseThrow(
				() -> new IllegalStateException("Could not find a referencing Package for " + upc.toString()));

		return pkg;
	}
	
	private Optional<BasicComponent> getParentCorresponding(org.eclipse.uml2.uml.Class upc, Blackboard blackboard) {
		return getParent(upc).map(it -> MappingPackageToBasicComponent.getCorresponding(it, blackboard).orElse(null));
	}
	
	private BasicComponent claimParentCorresponding(org.eclipse.uml2.uml.Class upc, Blackboard blackboard) {
		final Package parent = claimParent(upc);
		final BasicComponent bc = MappingPackageToBasicComponent.claimCorresponding(parent, blackboard);
		
		return bc;
	}

	/*
	 * public static Optional<BasicComponent> getCorresponding(Package pkg,
	 * Blackboard blackboard) { final MappedCorrespondenceInstance ci =
	 * getMappedCorrespondenceInstanceFromBlackboard(blackboard); final
	 * Optional<BasicComponent> bc = JavaHelper.tryCast(ci.getMappingTarget(pkg,
	 * MappingPackageToBasicComponent.INSTANCE), BasicComponent.class);
	 * 
	 * return bc; }
	 * 
	 * public static BasicComponent claimCorresponding(Package pkg, Blackboard
	 * blackboard) { final BasicComponent bc = getCorresponding(pkg, blackboard)
	 * .orElseThrow(() -> new IllegalStateException(
	 * "Could not find mapped BasicComponent for Package " + pkg.toString()));
	 * 
	 * return bc; }
	 */
}
