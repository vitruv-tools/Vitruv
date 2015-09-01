package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import pcm_mockup.Repository;
import pcm_mockup.Pcm_mockupFactory;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupPackage;


/**
 * Class Mapping
 */
public class MappingUPackageToRepository extends AbstractMIRMappingRealization {
	private static final Logger LOGGER = Logger.getLogger(MappingUPackageToRepository.class);
	
	// Singleton
	public final static MappingUPackageToRepository INSTANCE = new MappingUPackageToRepository();
	
	private MappingUPackageToRepository() {}
	
	@Override
	protected boolean checkConditions(EObject context,
		Blackboard blackboard) {

		if (!(context instanceof UPackage)) {
			return false;
		}
		
		UPackage up = (UPackage) context;
		
		/*
		 * call method with arguments up.
		 */
		
		return true;
	}

	@Override
	protected EClass getMappedEClass() {
		return Uml_mockupPackage.eINSTANCE.getUPackage();
	}

	@Override
	protected void restorePostConditions(EObject eObject, EObject target, EChange change, Blackboard blackboard) {
		LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString() + ")");
		
		UPackage pkg = JavaHelper.requireType(eObject, UPackage.class);
		final Repository bc = JavaHelper.requireType(target, Repository.class);

		bc.setName(pkg.getName());
		/*
		 * Instead:
		 * call method here that references pkg, bc
		 */
	}
	
//	@Override
//	protected void deleteCorresponding(EObject eObject, EObject target, Blackboard blackboard) {
//		LOGGER.trace("deleteCorresponding(" + eObject.toString()
//			+ ", " + target.toString()
//			+ ", " + blackboard.toString() + ")");
//		
//		super.deleteCorresponding(eObject, target, blackboard);
//	}

	@Override
	public String getMappingID() {
		return MappingUPackageToRepository.class.getName();
	}

	@Override
	protected Collection<Pair<EObject, VURI>> createCorresponding(EObject eObject, Blackboard blackboard) {
		LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + blackboard.toString() + ")");

		UPackage pkg = (UPackage) eObject;
		Repository bc = Pcm_mockupFactory.eINSTANCE.createRepository();
		VURI resourceVURI = VURI.getInstance(EclipseHelper.askForNewResource(bc));
		
		final MappedCorrespondenceInstance correspondenceInstance = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
		correspondenceInstance.createMappedCorrespondence(pkg, bc, this);
		
		final Pair<EObject, VURI> resultPair = new Pair<EObject, VURI>((EObject) bc, resourceVURI);
		return Collections.singleton(resultPair);
	}
	
	public static Optional<Repository> getCorresponding(UPackage pkg, Blackboard blackboard) {
		final MappedCorrespondenceInstance ci = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
		final Optional<Repository> bc = JavaHelper.tryCast(ci.getMappingTarget(pkg, MappingUPackageToRepository.INSTANCE), Repository.class);
				
		return bc;
	}
	
	public static Repository claimCorresponding(UPackage pkg, Blackboard blackboard) {
		final Repository bc = getCorresponding(pkg, blackboard)
				.orElseThrow(() -> new IllegalStateException("Could not find mapped Repository for UPackage " + pkg.toString()));
		
		return bc;
	}
}
