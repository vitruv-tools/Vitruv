package tools.vitruv.dsls.mapping.postprocessor

import tools.vitruv.dsls.mapping.helpers.MappingLanguageHelper
import tools.vitruv.dsls.mapping.mappingLanguage.Mapping
import tools.vitruv.dsls.mapping.mappingLanguage.MappingLanguageFactory
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMapping
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator
import tools.vitruv.dsls.mapping.mappingLanguage.MappingFile
import static extension tools.vitruv.dsls.mapping.helpers.MappingLanguageHelper.*
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

class MappingLanguageDerivedStateComputer extends JvmModelAssociator {
	override discardDerivedState(DerivedStateAwareResource resource) {
		super.discardDerivedState(resource)
	}

	override installDerivedState(DerivedStateAwareResource resource, boolean preIndexingPhase) {
		super.installDerivedState(resource, preIndexingPhase)
		resource.contents.filter(MappingFile).forEach[inferDefaultState]
	}
	
	public def inferDefaultState(MappingFile mappingFile) {
		val defaultMappings = mappingFile.eAllContents.filter(Mapping).filter[^default].toList

		mappingFile.defaultRequirements.clear
		mappingFile.defaultRequirements +=
			defaultMappings
				.map [
					val newReq = MappingLanguageFactory.eINSTANCE.createRequiredMapping
					newReq.mapping = it
					newReq.name = it.name
					newReq
				]

		mappingFile.eAllContents.filter(Mapping).filter[parentMapping !== null].forEach [
			val parentName = if ((parentMapping.name ?: "").empty)
					"parent"
				else
					parentMapping.toSensibleDefaultName
			
//			if ((it.requires ?: #[]).exists[req|req.mapping === null]) {
//				throw new IllegalStateException
//			}
			
			val hasParentRequiredMapping = (it.requires ?: #[]).exists[req|parentName.equals(req.name)]
			if (!hasParentRequiredMapping) {
				val newReq = MappingLanguageFactory.eINSTANCE.createRequiredMapping
//				newReq.mapping = parentMapping
				newReq.name = parentName
				it.requires += newReq
			}
			(it.requires ?: #[]).filter[req|parentName.equals(req.name)].forEach [req|
				req.mapping = parentMapping
			]
			
		]

		mappingFile.eAllContents.filter(NamedMetaclassReference).filter[name === null || name.isEmpty].forEach [
			it.name = MappingLanguageHelper.toSensibleDefaultName(it)
		]

		mappingFile.eAllContents.filter(RequiredMapping).filter[name === null || name.isEmpty].forEach [
			it.name = MappingLanguageHelper.toSensibleDefaultName(it)
		]
		
		/*mappingFile.eAllContents.filter(Mapping).forEach [ mapping |
			(mapping?.constraintsBody?.expressions ?: #[]).filter(AttributeEquivalenceExpression).forEach [ exp |
				#[exp.left, exp.right].forEach [
					val notNullExpression = MappingLanguageFactory.eINSTANCE.createNotNullExpression
					notNullExpression.feature = it
					
					mapping.constraintExpressions += notNullExpression
				]
			]
		]*/
	}
}