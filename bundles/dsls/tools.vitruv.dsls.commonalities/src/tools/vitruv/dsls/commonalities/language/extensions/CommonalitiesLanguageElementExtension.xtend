package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility package class CommonalitiesLanguageElementExtension {
	
	def static dispatch CommonalityFile getContainingCommonalityFile(CommonalityFile commonalityFile) {
		return commonalityFile;
	}

	def static dispatch CommonalityFile getContainingCommonalityFile(EObject object) {
		if (object.eContainer === null) {
			throw new RuntimeException('''«object» is not inside a «CommonalityFile.simpleName» definition''')
		}
		object.eContainer.containingCommonalityFile
	}
}