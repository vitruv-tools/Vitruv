package edu.kit.ipd.sdq.vitruvius.dsls.mapping.postprocessor

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.AttributeEquivalenceExpression
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.*
import edu.kit.ipd.sdq.vitruvius.dsls.mirBase.ModelElement

class MappingLanguageDerivedStateComputer extends JvmModelAssociator {
	override discardDerivedState(DerivedStateAwareResource resource) {
		super.discardDerivedState(resource)
	}

	override installDerivedState(DerivedStateAwareResource resource, boolean preIndexingPhase) {
		super.installDerivedState(resource, preIndexingPhase)
		val defaultMappings = resource.allContents.filter(Mapping).filter[^default].toList

		resource.allContents.filter(MappingFile).forEach [
			defaultRequirements.clear
			defaultRequirements +=
				defaultMappings
					.map [
						val newReq = MappingLanguageFactory.eINSTANCE.createRequiredMapping
						newReq.mapping = it
						newReq.name = it.name
						newReq
					]
		]

		resource.allContents.filter(Mapping).filter[parentMapping != null].forEach [
			val parentName = if ((parentMapping.name ?: "").empty)
					"parent"
				else
					parentMapping.toSensibleDefaultName
			
//			if ((it.requires ?: #[]).exists[req|req.mapping == null]) {
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

		resource.allContents.filter(ModelElement).filter[name == null || name.isEmpty].forEach [
			it.name = MappingLanguageHelper.toSensibleDefaultName(it)
		]

		resource.allContents.filter(RequiredMapping).filter[name == null || name.isEmpty].forEach [
			it.name = MappingLanguageHelper.toSensibleDefaultName(it)
		]
		
		resource.allContents.filter(Mapping).forEach [ mapping |
			(mapping?.constraintsBody?.expressions ?: #[]).filter(AttributeEquivalenceExpression).forEach [ exp |
				#[exp.left, exp.right].forEach [
					val notNullExpression = MappingLanguageFactory.eINSTANCE.createNotNullExpression
					notNullExpression.feature = it
					
					mapping.constraintExpressions += notNullExpression
				]
			]
		]
	}
}