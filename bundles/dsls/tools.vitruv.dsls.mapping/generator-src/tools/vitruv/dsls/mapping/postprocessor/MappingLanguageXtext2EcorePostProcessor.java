package tools.vitruv.dsls.mapping.postprocessor;

import static tools.vitruv.framework.util.bridges.JavaHelper.requireType;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

import tools.vitruv.dsls.mirbase.mirBase.MirBaseFile;

@SuppressWarnings("restriction")
public class MappingLanguageXtext2EcorePostProcessor implements IXtext2EcorePostProcessor {
	private static EClass getEClass(EPackage ePackage, String name) {
		return requireType(ePackage.getEClassifier(name), EClass.class);
	}
	
	private static EClass getEClass(EPackage ePackage, Class<?> clazz) {
		return getEClass(ePackage, clazz.getSimpleName());
	}
	
	@Override
	public void process(GeneratedMetamodel metamodel) {
		if (!metamodel.getName().equals("mappingLanguage"))
			return;
		
		final EPackage ePackage = metamodel.getEPackage();
		final EClass mappingFileEClass = getEClass(ePackage, "MappingFile");
		final EClass mappingEClass = getEClass(ePackage, "Mapping");
		
		// we need to load the model from the platform, since we can't reference the meta model element that
		// is available as MirBasePackage.LITERALS.MIR_BASE_FILE to the *workflow*.
		final ResourceSet rs = new ResourceSetImpl();
		final Resource baseResource = rs.getResource(URI.createPlatformResourceURI("tools.vitruv.dsls.mirbase/model/generated/MirBase.ecore", true), true);
		final EPackage mirBasePackage = (EPackage) baseResource.getContents().get(0);
		final EClass mirBaseFile = getEClass(mirBasePackage, MirBaseFile.class);
		mappingFileEClass.getESuperTypes().add(mirBaseFile);
		
		final EReference newRef = EcoreFactory.eINSTANCE.createEReference();
		newRef.setName("defaultRequirements");
		newRef.setEType(ePackage.getEClassifier("RequiredMapping"));
		newRef.setLowerBound(0);
		newRef.setUpperBound(-1);
		newRef.setContainment(true);
		mappingFileEClass.getEStructuralFeatures().add(newRef);
		
		
		final EReference childMappingsRef = requireType(mappingEClass.getEStructuralFeature("childMappings"), EReference.class);
		final EReference parentMappingRef = EcoreFactory.eINSTANCE.createEReference();
		parentMappingRef.setName("parentMapping");
		parentMappingRef.setEOpposite(childMappingsRef);
		childMappingsRef.setEOpposite(parentMappingRef);
		parentMappingRef.setLowerBound(0);
		parentMappingRef.setUpperBound(1);
		parentMappingRef.setEType(mappingEClass);
		mappingEClass.getEStructuralFeatures().add(parentMappingRef);
		
		// add not-null-constraint (that is not manually specifiable)
/*		final EClass notNullConstraint = EcoreFactory.eINSTANCE.createEClass();
		notNullConstraint.setName("NotNullExpression");
		notNullConstraint.getESuperTypes().add(constraintExpressionEClass);
		ePackage.getEClassifiers().add(notNullConstraint);
		
		final EReference featureRef = EcoreFactory.eINSTANCE.createEReference();
		featureRef.setName("feature");
		featureRef.setEType(featureOfContextVariableEClass);
		notNullConstraint.getEStructuralFeatures().add(featureRef);
		
		final EReference constraintsRef = EcoreFactory.eINSTANCE.createEReference();
		constraintsRef.setName("constraintExpressions");
		constraintsRef.setContainment(true);
		constraintsRef.setEType(constraintExpressionEClass);
		constraintsRef.setLowerBound(0);
		constraintsRef.setUpperBound(-1);
		mappingEClass.getEStructuralFeatures().add(constraintsRef);*/
	}
}
