package tools.vitruv.dsls.commonalities.language.elements

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EcorePackage

@Utility
class WellKnownClassifiers {

	public static val JAVA_OBJECT = ClassifierProvider.INSTANCE.toDataTypeAdapter(EcorePackage.eINSTANCE.EJavaObject)
	public static val Classifier MOST_SPECIFIC_TYPE = LanguageElementsFactory.eINSTANCE.createMostSpecificType
	public static val Classifier LEAST_SPECIFIC_TYPE = LanguageElementsFactory.eINSTANCE.createLeastSpecificType
}
