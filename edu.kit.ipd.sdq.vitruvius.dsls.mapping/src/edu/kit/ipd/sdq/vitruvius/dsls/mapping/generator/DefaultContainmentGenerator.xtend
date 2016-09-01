package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.TemplateGenerator
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.MIRMappingHelper
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator.ConstraintLanguageGenerator.*
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelImport
import java.util.Set

class DefaultContainmentGenerator {
	private extension MappingLanguageGeneratorState state
	private extension TemplateGenerator generator
	private extension MappingLanguageGeneratorNameProvider nameProvider
	private extension ConstraintLanguageGenerator constraintLanguageGenerator

	new(MappingLanguageGeneratorState state, TemplateGenerator generator,
		MappingLanguageGeneratorNameProvider nameProvider,
		ConstraintLanguageGenerator constraintLanguageGenerator
		) {
		this.state = state
		this.generator = generator
		this.nameProvider = nameProvider
		this.constraintLanguageGenerator = constraintLanguageGenerator
	}

	def generate() {
		for (mapping : state.mappings) {
			val defaultContainExpressions = mapping.allConstraintExpressions.filter(DefaultContainExpression)
			val Set<MetamodelImport> constrainedImports = newHashSet

			for (expression : defaultContainExpressions) {
				val imp = expression.getImport
				constrainedImports.add(imp)
				
				
				appendToTemplateExpression(#[mapping, 'mappingClassBody']) [ extension ih, template |
					'''
						public static void tryApplyDefaultContainmentFor«expression.target.contextVariableName»(
								«mapping.correspondenceWrapperClassName» «mapping.correspondenceWrapperClassName.toVarName»,
								«typeRef(MappingExecutionState)» state
							) {
							«checkAndCreateDefaultContainment(ih, #{#['this']->mapping.correspondenceWrapperClassName.toVarName}, expression)»
						}
					'''
				]
			}
			
			for (imp : constrainedImports) {
				appendToTemplateExpression(#[mapping, imp, 'defaultContainmentCheck']) [ extension ih, template |
					'''
					tryApplyDefaultContainments(currentCorrespondence, state);
					'''
				]
			}

			if (!defaultContainExpressions.isEmpty) {
				appendToTemplateExpression(#[mapping, 'mappingClassBody']) [ extension ih, template |
					'''
						private static void tryApplyDefaultContainments(
								«mapping.correspondenceWrapperClassName» «mapping.correspondenceWrapperClassName.toVarName»,
								«typeRef(MappingExecutionState)» state
							) {
							«FOR expression : defaultContainExpressions»
								tryApplyDefaultContainmentFor«expression.target.contextVariableName»(
									«mapping.correspondenceWrapperClassName.toVarName», state);
							«ENDFOR»
						}
					'''
					]
			}


		}
	}

}
