package tools.vitruv.dsls.mappings.tests.pcmuml

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsLanguageFactory
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.framework.domains.VitruvDomain

import static tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging.*
import static tools.vitruv.dsls.mappings.tests.pcmuml.PcmUmlClassHelper.*
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory

class CorrespondenceTaggingHelper {

	private ReactionGeneratorContext contextMock

	new(String mappingName, VitruvDomain leftDomain, VitruvDomain rightDomain) {
		contextMock = new ContextMock(mappingName, leftDomain, rightDomain)
	}

	public def getTag(MappingParameter left, MappingParameter right) {
		ParameterCorrespondenceTagging.context = contextMock
		ParameterCorrespondenceTagging.getCorrespondenceTag(left, right)
	}

	public def getTag(EClass leftClass, String leftName, EClass rightClass, String rightName) {
		getTag(createMappingParameter(leftClass, leftName), createMappingParameter(rightClass, rightName))
	}

	private static class ContextMock extends ReactionGeneratorContext {

		new(String mappingName, VitruvDomain leftDomain, VitruvDomain rightDomain) {
			super(null, null, MappingsLanguageFactory.eINSTANCE.createMappingsSegment => [
				name = mappingName
				it.leftDomain = MirBaseFactory.eINSTANCE.createDomainReference => [
					domain = leftDomain.name
				]
				it.rightDomain = MirBaseFactory.eINSTANCE.createDomainReference => [
					domain = rightDomain.name
				]
			], null, null, true)
		}

	}
}
