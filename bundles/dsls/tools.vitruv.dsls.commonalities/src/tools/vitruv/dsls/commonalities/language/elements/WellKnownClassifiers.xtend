package tools.vitruv.dsls.commonalities.language.elements

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EcorePackage

@Utility
class WellKnownClassifiers {
	public static val JAVA_OBJECT = 
		LanguageElementsFactory.eINSTANCE.createEDataTypeClassifier.forEDataType(EcorePackage.eINSTANCE.EJavaObject)
	public static val Classifier MOST_SPECIFIC_TYPE = LanguageElementsFactory.eINSTANCE.createMostSpecificType
}
