package tools.vitruvius.extensions.dslsruntime.mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState;
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.AbstractCorrespondenceWrapper;
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.ElementProvider;
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.MappingRealization;
import tools.vitruvius.framework.correspondence.Correspondence;
import tools.vitruvius.framework.util.bridges.EcoreBridge;
import tools.vitruvius.framework.util.datatypes.VURI;

public abstract class AbstractMappingRealization implements MappingRealization {
	protected static void destroy(ElementProvider elementProvider, MappingExecutionState state) {
		Set<EObject> elementsToDelete = new HashSet<>();

		List<EObject> elements = elementProvider.getElements();
		state.getDeletedEObjects().addAll(elements);

		for (EObject element : elements) {
			if (EcoreBridge.isRootInResource(element)) {
				state.getTransformationResult().addVURIToDeleteIfNotNull(VURI.getInstance(element.eResource()));
			}
		}

		elements.forEach(element -> element.eAllContents().forEachRemaining(elementsToDelete::add));
		elements.forEach(element -> state.addResourceToSave(element));
		
		final Set<Correspondence> correspondences = state.getMci().getCorrespondences(elements);
		correspondences.forEach(state.getMci()::removeCorrespondencesAndDependendCorrespondences);
		elements.forEach(it -> EcoreUtil.delete(it, true));
		
		state.persistAll();
		state.updateAllTuidsOfCachedObjects();
	}

	protected static <CW extends AbstractCorrespondenceWrapper> CW createAndWrapCorrespondence(
			MappingRealization mapping, Function<Correspondence, CW> correspondenceWrapper,
			List<Correspondence> requiredCorrespondences, ElementProvider elementProviderA,
			ElementProvider elementProviderB, MappingExecutionState state) {

		final List<EObject> as = elementProviderA.getElements();
		final List<EObject> bs = elementProviderB.getElements();

		Correspondence corr = state.getMci().createAndAddCorrespondence(as, bs);
		state.getMci().registerMappingForCorrespondence(corr, mapping);
		state.addCreatedCorrespondence(corr);
		for (Correspondence requiredCorrespondence : requiredCorrespondences) {
			corr.getDependsOn().add(requiredCorrespondence);
		}

		return correspondenceWrapper.apply(corr);
	}

	protected static Correspondence createCorrespondence(MappingRealization mapping,
			List<Correspondence> requiredCorrespondences, ElementProvider elementProviderA,
			ElementProvider elementProviderB, MappingExecutionState state) {

		final List<EObject> as = elementProviderA.getElements();
		final List<EObject> bs = elementProviderB.getElements();

		Correspondence corr = state.getMci().createAndAddCorrespondence(as, bs);
		state.getMci().registerMappingForCorrespondence(corr, mapping);
		state.addCreatedCorrespondence(corr);
		for (Correspondence requiredCorrespondence : requiredCorrespondences) {
			corr.getDependsOn().add(requiredCorrespondence);
		}

		return corr;
	}
}
