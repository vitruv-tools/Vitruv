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
		final EClass reactionsSegmentEClass = getEClass(ePackage, "ReactionsSegment");
		final EClass reactionEClass = getEClass(ePackage, "Reaction");
		final EClass effectEClass = getEClass(ePackage, "Routine");
		
		// Add an opposite reference for the metamodel pair to the reaction
		final EReference reactionsSegmentReactionsReference = (EReference)reactionsSegmentEClass.getEStructuralFeature("reactions");
		addReactionsSegmentEReference(reactionEClass, reactionsSegmentReactionsReference);
		
		final EReference reactionsSegmentEffectsReference = (EReference)reactionsSegmentEClass.getEStructuralFeature("routines");
		addReactionsSegmentEReference(effectEClass, reactionsSegmentEffectsReference);
	}
	
	private EReference addReactionsSegmentEReference(EClass classToAddReferenceTo, EReference oppositeReference) {
		final EReference reactionsSegmentReference = EcoreFactory.eINSTANCE.createEReference();
		reactionsSegmentReference.setName("reactionsSegment");
		reactionsSegmentReference.setEType(classToAddReferenceTo.getEPackage().getEClassifier("ReactionsSegment"));
		reactionsSegmentReference.setLowerBound(1);
		reactionsSegmentReference.setUpperBound(1);
		oppositeReference.setEOpposite(reactionsSegmentReference);
		reactionsSegmentReference.setEOpposite(oppositeReference);
		classToAddReferenceTo.getEStructuralFeatures().add(reactionsSegmentReference);
		return reactionsSegmentReference;
	}
}
