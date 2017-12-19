package tools.vitruv.dsls.reactions.postprocessor;

import static tools.vitruv.framework.util.bridges.JavaHelper.requireType;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

@SuppressWarnings("restriction")
public class ReactionsLanguageXtext2EcorePostProcessor implements IXtext2EcorePostProcessor {
	private static EClass getEClass(EPackage ePackage, String name) {
		return requireType(ePackage.getEClassifier(name), EClass.class);
	}
	
	@Override
	public void process(GeneratedMetamodel metamodel) {
		if (!metamodel.getName().equals("reactionsLanguage"))
			return;
		
		final EPackage ePackage = metamodel.getEPackage();
		final EClass reactionsFileEClass = getEClass(ePackage, "ReactionsFile");
		final EClass reactionsSegmentEClass = getEClass(ePackage, "ReactionsSegment");
		final EClass reactionEClass = getEClass(ePackage, "Reaction");
		final EClass effectEClass = getEClass(ePackage, "Routine");
		
		// Add an opposite reference for the metamodel pair to the reaction
		final EReference reactionsSegmentReactionsReference = (EReference)reactionsSegmentEClass.getEStructuralFeature("reactions");
		addOppositeEReference(reactionEClass, "reactionsSegment", reactionsSegmentReactionsReference);
		
		final EReference reactionsSegmentEffectsReference = (EReference)reactionsSegmentEClass.getEStructuralFeature("routines");
		addOppositeEReference(effectEClass, "reactionsSegment", reactionsSegmentEffectsReference);
		
		// Add an opposite reference for the reactions file to the reactions segment
		final EReference reactionsFileReactionsSegmentsReference = (EReference) reactionsFileEClass.getEStructuralFeature("reactionsSegments");
		addOppositeEReference(reactionsSegmentEClass, "reactionsFile", reactionsFileReactionsSegmentsReference);
	}
	
	private EReference addOppositeEReference(EClass classToAddReferenceTo, String referenceName, EReference oppositeReference) {
		final EReference reference = EcoreFactory.eINSTANCE.createEReference();
		reference.setName(referenceName);
		reference.setEType(oppositeReference.getEContainingClass());
		reference.setLowerBound(1);
		reference.setUpperBound(1);
		oppositeReference.setEOpposite(reference);
		reference.setEOpposite(oppositeReference);
		classToAddReferenceTo.getEStructuralFeatures().add(reference);
		return reference;
	}
}
