package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import pcm_mockup.Repository;
import pcm_mockup.Interface;
import pcm_mockup.Pcm_mockupFactory;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupPackage;

public class MappingUClassToInterface extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(MappingUClassToInterface.class);

	// Singleton
	public final static MappingUClassToInterface INSTANCE = new MappingUClassToInterface();

	private MappingUClassToInterface() {
	}

	@Override
	protected boolean checkConditions(EObject context, Blackboard blackboard) {

		if (!(context instanceof UClass)) {
			return false;
		}

		UClass upc = (UClass) context;
		
		final Optional<UPackage> up = getParent(upc);
		if (!up.isPresent()) {
			return false;
		}
		
		final Optional<Repository> bc = MappingUPackageToRepository.getCorresponding(up.get(), blackboard);
		if (!bc.isPresent()) {
			return false;
		}
		
		/*
		 * call method with arguments upc, up, bc.
		 */
		
		if (upc.getName().endsWith("nomap")) {
			return false;
		}

		return true;
	}

	@Override
	protected EClass getMappedEClass() {
		return Uml_mockupPackage.eINSTANCE.getUClass();
	}

	@Override
	protected void restorePostConditions(EObject eObject, EObject target, EChange change, Blackboard blackboard) {
		LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString()
				+ ")");

		UClass upc = JavaHelper.requireType(eObject, UClass.class);
		final Interface bcopr = JavaHelper.requireType(target, Interface.class);
		
		final UPackage up = claimParent(upc);
		final Repository bc = claimParentCorresponding(upc, blackboard);

		bcopr.setName(upc.getName());
		/*
		 * Instead:
		 * call method here that references upc, bcopr, up & bc ???
		 */
	}

	@Override
	protected void deleteCorresponding(EObject eObject, EObject target, Blackboard blackboard, TransformationResult transformationResult) {
		LOGGER.trace("deleteCorresponding(" + eObject.toString()
			+ ", " + target.toString()
			+ ", " + blackboard.toString() + ")");
		
		super.deleteCorresponding(eObject, target, blackboard, transformationResult);
	}

	@Override
	public String getMappingID() {
		return MappingUClassToInterface.class.getName();
	}

	@Override
	protected Collection<Pair<EObject, VURI>> createCorresponding(EObject eObject, Blackboard blackboard) {
		LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + blackboard.toString() + ")");
		final MappedCorrespondenceInstance ci = getMappedCorrespondenceInstanceFromBlackboard(blackboard);

		UClass upc = JavaHelper.requireType(eObject, UClass.class);

		final Interface bcopr = Pcm_mockupFactory.eINSTANCE.createInterface();
		final Repository bc = claimParentCorresponding(upc, blackboard);

		bc.getInterfaces().add(bcopr);

		ci.createMappedCorrespondence(upc, bcopr, this);
		// FIXME DW create correspondence hierarchy?

		return Collections.emptySet();
	}

	private Optional<UPackage> getParent(UClass upc) {
		final Optional<UPackage> upcParent = EcoreHelper.findOneReferencee(upc,
				Uml_mockupPackage.eINSTANCE.getUPackage_Classes(), UPackage.class);

		return upcParent;
	}

	private UPackage claimParent(UClass upc) {
		final Optional<UPackage> upcParent = getParent(upc);
		final UPackage pkg = upcParent.orElseThrow(
				() -> new IllegalStateException("Could not find a referencing UPackage for " + upc.toString()));

		return pkg;
	}
	
	private Optional<Repository> getParentCorresponding(UClass upc, Blackboard blackboard) {
		return getParent(upc).map(it -> MappingUPackageToRepository.getCorresponding(it, blackboard).orElse(null));
	}
	
	private Repository claimParentCorresponding(UClass upc, Blackboard blackboard) {
		final UPackage parent = claimParent(upc);
		final Repository bc = MappingUPackageToRepository.claimCorresponding(parent, blackboard);
		
		return bc;
	}
}
