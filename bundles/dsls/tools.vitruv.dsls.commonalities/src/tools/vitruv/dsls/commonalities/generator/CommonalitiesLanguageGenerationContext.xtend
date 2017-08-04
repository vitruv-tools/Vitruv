package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.commonalities.language.CommonalityDeclaration
import java.util.HashMap
import static com.google.common.base.Preconditions.*

class CommonalitiesLanguageGenerationContext {
	val modelClassMap = new HashMap<CommonalityDeclaration, EClass>

	def protected getChangeClass(CommonalityDeclaration commonality) {
		checkState(modelClassMap.containsKey(commonality), '''No eClass was created for «commonality» yet!''');
		return modelClassMap.get(commonality)
	}

	def protected associateWith(CommonalityDeclaration commanlity, EClass eClass) {
		modelClassMap.put(commanlity, eClass)
	}
}
