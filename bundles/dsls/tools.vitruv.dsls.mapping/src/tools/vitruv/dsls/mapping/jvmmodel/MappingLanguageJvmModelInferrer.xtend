package tools.vitruv.dsls.mapping.jvmmodel

import com.google.inject.Inject
import tools.vitruv.extensions.dslsruntime.mapping.AbstractWrapper
import tools.vitruv.extensions.dslsruntime.mapping.interfaces.AbstractCorrespondenceWrapper
import tools.vitruv.extensions.dslsruntime.mapping.interfaces.ElementProvider
import tools.vitruv.dsls.mapping.generator.MappingLanguageGeneratorNameProvider
import tools.vitruv.dsls.mapping.generator.MappingLanguageGeneratorState
import tools.vitruv.dsls.mapping.mappingLanguage.Mapping
import tools.vitruv.dsls.mapping.mappingLanguage.MappingFile
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMapping
import tools.vitruv.dsls.mapping.mappingLanguage.XbaseBodyConstraintExpression
import tools.vitruv.dsls.mapping.mappingLanguage.XbaseSignatureConstraintExpression
import tools.vitruv.dsls.mapping.postprocessor.MappingLanguageDerivedStateComputer
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.util.bridges.JavaHelper
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

import static extension tools.vitruv.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension tools.vitruv.framework.util.bridges.JavaHelper.*

/**
 * <p>Infers a JVM model from the source model.</p> 
 * 
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>
 * 
 * @author Dominik Werle     
 */
class MappingLanguageJvmModelInferrer extends AbstractModelInferrer {
	@Inject extension JvmTypesBuilder
	@Inject MappingLanguageDerivedStateComputer derivedStateComputer

	private extension MappingLanguageGeneratorState state
	@Inject private extension MappingLanguageGeneratorNameProvider nameProvider
	
	/**
	 * TODO move to helper 
	 */
	def String createCorrespondenceGetterCall(MetamodelImport imp) {
		'''getElementsForMetamodel("«imp.package.nsURI»")'''
	}
	
	def dispatch void infer(MappingFile mappingFile, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		if (isPreIndexingPhase) {
			return
		}
		
		if (mappingFile.imports.size != 2) {
			return
		}

		derivedStateComputer.inferDefaultState(mappingFile)

		this.state = new MappingLanguageGeneratorState(mappingFile)

		mappingFile.eAllContents.filter(Mapping).forEach [ mapping |
			val signatureConstraints = mapping.allConstraintExpressions.filter(XbaseSignatureConstraintExpression).
				toList
			val bodyConstraints = mapping.allConstraintExpressions.filter(XbaseBodyConstraintExpression).toList

			val className = nameProvider.getMappingConstraintsClassName(mapping)

			if (!signatureConstraints.empty || !bodyConstraints.empty) {
				acceptor.accept(mapping.toClass(className)) [
					members += signatureConstraints.map [ signatureConstraint |
						val imp = signatureConstraint.getPackage?.getImportsForPackage

						if (imp === null)
							return #[]

						#[
							signatureConstraint.toMethod(nameProvider.getCheckMethodName(signatureConstraint),
								typeRef(Boolean.TYPE), [
									static = true
									parameters +=
										signatureConstraint.toParameter(imp.toVarName,
											typeRef(mapping.getWrapperClassName(imp)))
									body = signatureConstraint.checkBlock
								]),
							signatureConstraint.toMethod(nameProvider.getEnforceMethodName(signatureConstraint),
								typeRef(Void.TYPE), [
									static = true
									parameters +=
										signatureConstraint.toParameter(imp.toVarName,
											typeRef(mapping.getWrapperClassName(imp)))
									body = signatureConstraint.enforceBlock
								])
						]
					].flatten

					members += bodyConstraints.map [ bodyConstraint |
						bodyConstraint.toMethod(nameProvider.getPropagateMethodName(bodyConstraint),
							typeRef(Void.TYPE), [
								static = true
								parameters +=
									bodyConstraint.toParameter(mapping.name.toFirstLower,
										typeRef(mapping.correspondenceWrapperClassName))
								body = bodyConstraint.block
							])
					]
				]
			}

			inferMapping(mapping, acceptor)
		]
	}

	// TODO: move to extra class
	private def String getSupplierVariableName(RequiredMapping req) '''«req.name.toFirstLower»Supplier'''

	private def getSupplierType(RequiredMapping req) {
		typeRef(Supplier, typeRef(req.mapping.getCorrespondenceWrapperClassName))
	}

	def void inferMapping(Mapping mapping, IJvmDeclaredTypeAcceptor acceptor) {
		inferCorrespondenceWrapper(mapping, acceptor)
		getImports(mapping).forEach [ imp |
			inferWrapper(mapping, imp, acceptor)
		]
	}

	def inferCorrespondenceWrapper(Mapping mapping, IJvmDeclaredTypeAcceptor acceptor) {
		val fqn = mapping.correspondenceWrapperClassName
		val allRequires = mapping.allRequires

		acceptor.accept(mapping.toClass(fqn)) [
			superTypes += typeRef(AbstractCorrespondenceWrapper)

			members += allRequires.withIndex.map [ pair |
				val index = pair.first
				val req = pair.second

				#[
					mapping.toField('''«req.name.toUpperCase»_INDEX''', typeRef(Integer.TYPE)) [
						visibility = JvmVisibility.PUBLIC
						static = true
						initializer = '''«index»'''
					],
					mapping.toField(req.name.toFirstLower, typeRef(req.mapping.correspondenceWrapperClassName)) [
						visibility = JvmVisibility.PRIVATE
					],
					mapping.toMethod('''get«req.name.toFirstUpper»''',
						typeRef(req.mapping.correspondenceWrapperClassName)) [
						body = '''return this.«req.name.toFirstLower»;'''
					]
				]
			].flatten

			members += getImports(mapping).map [ imp |
				#[
					mapping.toField(mapping.getWrapperClassName(imp).toVarName,
						typeRef(mapping.getWrapperClassName(imp))) [
						visibility = JvmVisibility.
							PRIVATE
						initializer = '''«typeRef(mapping.getWrapperClassName(imp))».createHalfMappedCorrespondence(this)'''
					],
					mapping.toMethod('''get«imp.toFirstUpperName»''', typeRef(mapping.getWrapperClassName(imp))) [
						visibility = JvmVisibility.PUBLIC
						body = '''return this.«mapping.getWrapperClassName(imp).toVarName»;'''
					]
				]
			].flatten

			members += mapping.toConstructor [
				parameters += mapping.toParameter("correspondence", typeRef(Correspondence))
				body = '''
					super(correspondence);
					«FOR req : allRequires»
						this.«req.name.toFirstLower» = new «typeRef(req.mapping.correspondenceWrapperClassName)»(
							correspondence.getDependsOn().get(«req.name.toUpperCase»_INDEX)
						);
					«ENDFOR»
				'''
			]
		]
	}

	def inferWrapper(Mapping mapping, MetamodelImport imp, IJvmDeclaredTypeAcceptor acceptor) {
		val signatureElements = getMappingToImportToModelElements.get(mapping).get(imp)
		val fqn = getWrapperClassName(mapping, imp)

		val allRequires = mapping.allRequires

		acceptor.accept(mapping.toClass(fqn)) [
			val newClass = it

			superTypes += typeRef(AbstractWrapper)
			members += mapping.toConstructor [
				parameters += allRequires.map[toParameter(supplierVariableName, supplierType)]
				parameters += mapping.toParameter("elementProvider", typeRef(ElementProvider))

				body = '''
					super(«Arrays».asList(
					«FOR req : allRequires SEPARATOR ","»
						() -> «req.getSupplierVariableName».get().getCorrespondence()
					«ENDFOR»
					), elementProvider);
					«FOR req : allRequires BEFORE "\n"»
						this.«req.getSupplierVariableName» = «req.getSupplierVariableName»;
					«ENDFOR»
				'''
			]

			members += allRequires.map [ req |
				#[req.toField(req.supplierVariableName, req.supplierType) [
					visibility = JvmVisibility.PRIVATE
				], req.toMethod('''get«req.name.toFirstUpper»''', typeRef(req.mapping.correspondenceWrapperClassName)) [
					body = '''
						return this.«req.supplierVariableName».get();
					'''
				]]
			].flatten

			members += signatureElements.withIndex.map [ pair |
				val index = pair.first
				val modelElement = pair.second

				#[
					modelElement.toField('''«modelElement.name.toUpperCase»_INDEX''', typeRef(Integer.TYPE)) [
						initializer = '''«index»'''
					],
					modelElement.toMethod('''get«modelElement.name.toFirstUpper»''',
						typeRef(modelElement.metaclass.instanceTypeName)) [
						body = '''
							return «JavaHelper».requireType(getElements().get(«modelElement.name.toUpperCase»_INDEX),
								«typeRef(modelElement.metaclass.instanceTypeName)».class);
						'''
					]
				]
			].flatten

			members += mapping.toMethod("createHalfMappedCorrespondence", typeRef(it)) [
				static = true
				parameters += mapping.toParameter(mapping.correspondenceWrapperClassName.toVarName, typeRef(mapping.
					correspondenceWrapperClassName))
				body = '''
					return new «typeRef(newClass)»(
						«FOR req : allRequires SEPARATOR "," AFTER ","»
							() -> «mapping.getCorrespondenceWrapperClassName.toVarName».get«req.name.toFirstUpper»()
						«ENDFOR»
						() -> «mapping.getCorrespondenceWrapperClassName.toVarName».getCorrespondence().«imp.createCorrespondenceGetterCall»
					);				
				'''
			]

			members += mapping.toMethod("createTransientWrapper", typeRef(it)) [
				static = true
				parameters += allRequires.map [
					toParameter(it.name.toFirstLower, typeRef(it.mapping.correspondenceWrapperClassName))
				]
				parameters += mapping.toParameter("elements", typeRef(List, typeRef(EObject)))
				body = '''
					«FOR req : allRequires»
						«typeRef(req.mapping.correspondenceWrapperClassName)» transient«req.name.toFirstUpper»Wrapper =
							new «typeRef(req.mapping.correspondenceWrapperClassName)»(«req.name.toFirstLower».getCorrespondence());
					«ENDFOR»
					final «List»<«EObject»> transientElementsCopy = new «ArrayList»<>(elements);
					return new «typeRef(newClass)»(
					«FOR req : allRequires SEPARATOR "," AFTER ","»
						() -> transient«req.name.toFirstUpper»Wrapper
					«ENDFOR»
						() -> transientElementsCopy);
				'''
			]

			members += mapping.toMethod("createTransientWrapper", typeRef(it)) [
				static = true
				parameters += allRequires.map [
					toParameter(it.name.toFirstLower, typeRef(it.mapping.correspondenceWrapperClassName))
				]
				parameters += mapping.toParameter("elementProvider", typeRef(ElementProvider))
				body = '''
					return «typeRef(newClass)».createTransientWrapper(
					«FOR req : allRequires SEPARATOR "," AFTER ","»«req.name.toFirstLower»«ENDFOR»
					elementProvider.getElements());
				'''
			]
		]
	}

}
