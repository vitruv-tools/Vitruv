package tools.vitruvius.dsls.response.postprocessor;

import static tools.vitruvius.framework.util.bridges.JavaHelper.requireType;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

@SuppressWarnings("restriction")
public class ResponseLanguageXtext2EcorePostProcessor implements IXtext2EcorePostProcessor {
	private static EClass getEClass(EPackage ePackage, String name) {
		return requireType(ePackage.getEClassifier(name), EClass.class);
	}
	
	@Override
	public void process(GeneratedMetamodel metamodel) {
		if (!metamodel.getName().equals("responseLanguage"))
			return;
		
		final EPackage ePackage = metamodel.getEPackage();
		final EClass responsesSegmentEClass = getEClass(ePackage, "ResponsesSegment");
		final EClass responseEClass = getEClass(ePackage, "Response");
		final EClass effectEClass = getEClass(ePackage, "ExplicitRoutine");
		
		// Add an opposite reference for the metamodel pair to the response
		final EReference responsesSegmentResponsesReference = (EReference)responsesSegmentEClass.getEStructuralFeature("responses");
		addResponsesSegmentEReference(responseEClass, responsesSegmentResponsesReference);
		
		final EReference responsesSegmentEffectsReference = (EReference)responsesSegmentEClass.getEStructuralFeature("routines");
		addResponsesSegmentEReference(effectEClass, responsesSegmentEffectsReference);
	}
	
	private EReference addResponsesSegmentEReference(EClass classToAddReferenceTo, EReference oppositeReference) {
		final EReference responsesSegmentReference = EcoreFactory.eINSTANCE.createEReference();
		responsesSegmentReference.setName("responsesSegment");
		responsesSegmentReference.setEType(classToAddReferenceTo.getEPackage().getEClassifier("ResponsesSegment"));
		responsesSegmentReference.setLowerBound(1);
		responsesSegmentReference.setUpperBound(1);
		oppositeReference.setEOpposite(responsesSegmentReference);
		responsesSegmentReference.setEOpposite(oppositeReference);
		classToAddReferenceTo.getEStructuralFeatures().add(responsesSegmentReference);
		return responsesSegmentReference;
	}
}
