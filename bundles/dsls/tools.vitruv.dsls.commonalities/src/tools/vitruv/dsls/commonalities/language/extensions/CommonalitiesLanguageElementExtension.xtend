package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility package class CommonalitiesLanguageElementExtension {
	
	def static dispatch CommonalityFile getOptionalContainingCommonalityFile(CommonalityFile commonalityFile) {
		commonalityFile
	}
	
	def static dispatch CommonalityFile getOptionalContainingCommonalityFile(EObject object) {
		object.eContainer.optionalContainingCommonalityFile
	}
	
	def static dispatch CommonalityFile getOptionalContainingCommonalityFile(Void nill) {
		null
	}
	
	def static dispatch CommonalityFile getContainingCommonalityFile(CommonalityFile commonalityFile) {
		return commonalityFile;
	}

	def static dispatch CommonalityFile getContainingCommonalityFile(EObject object) {
		if (object.eContainer === null) {
			throw new RuntimeException('''«object» is not inside a «CommonalityFile.simpleName» definition''')
		}
		object.eContainer.containingCommonalityFile
	}
	
	def static CommonalityFile getContainedCommonalityFile(Resource resource) {
		val result = resource.optionalContainedCommonalityFile
		if (result !== null) {
			return result
		}
		throw new IllegalStateException('''The resource ‹«resource»› is expected to contain only a commonality file, but it«
		IF (result === null)» is empty.«ELSE» contains «resource.contents».«ENDIF»''')
	}
	
	def static CommonalityFile getOptionalContainedCommonalityFile(Resource resource) {
		val result = resource.contents.head
		if (result instanceof CommonalityFile) {
			return result
		}
		return null
	}
}