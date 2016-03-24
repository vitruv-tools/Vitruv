package edu.kit.ipd.sdq.vitruvius.dsls.response.postprocessor;

import static edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.requireType;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile;

@SuppressWarnings("restriction")
public class ResponseLanguageXtext2EcorePostProcessor implements IXtext2EcorePostProcessor {
	private static EClass getEClass(EPackage ePackage, String name) {
		return requireType(ePackage.getEClassifier(name), EClass.class);
	}
	
	private static EClass getEClass(EPackage ePackage, Class<?> clazz) {
		return getEClass(ePackage, clazz.getSimpleName());
	}
	
	@Override
	public void process(GeneratedMetamodel metamodel) {
		if (!metamodel.getName().equals("responseLanguage"))
			return;
		
		final EPackage ePackage = metamodel.getEPackage();
		final EClass responseFileEClass = getEClass(ePackage, ResponseFile.class);
		final EClass metamodelPairFileEClass = getEClass(ePackage, MetamodelPairResponses.class);
		final EClass responseEClass = getEClass(ePackage, Response.class);
		
		// Add an opposite reference for the metamodel pair to the response
		final EReference oppositeRef = EcoreFactory.eINSTANCE.createEReference();
		oppositeRef.setName("metamodelPair");
		oppositeRef.setEType(ePackage.getEClassifier(MetamodelPairResponses.class.getSimpleName()));
		oppositeRef.setLowerBound(1);
		oppositeRef.setUpperBound(1);
		final EReference metamodelPairResponsesReference = (EReference)metamodelPairFileEClass.getEStructuralFeature("responses");
		oppositeRef.setEOpposite(metamodelPairResponsesReference);
		metamodelPairResponsesReference.setEOpposite(oppositeRef);
		responseEClass.getEStructuralFeatures().add(oppositeRef);
		//sponseFileEClass.getEStructuralFeature("responses")
	}
}
