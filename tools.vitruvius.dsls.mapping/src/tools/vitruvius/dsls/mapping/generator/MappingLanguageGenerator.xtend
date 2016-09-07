package tools.vitruvius.dsls.mapping.generator

import com.google.inject.Inject
import tools.vitruvius.extensions.dslsruntime.mapping.AbstractMappingRealization
import tools.vitruvius.extensions.dslsruntime.mapping.CandidateGeneratorImpl
import tools.vitruvius.extensions.dslsruntime.mapping.MappedCorrespondenceModel
import tools.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState
import tools.vitruvius.extensions.dslsruntime.mapping.MappingUtil
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.Candidate
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.CandidateGenerator
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.MappingRealization
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.MatchUpdate
import tools.vitruvius.dsls.mapping.helpers.TemplateGenerator
import tools.vitruvius.dsls.mapping.mappingLanguage.Mapping
import tools.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import tools.vitruvius.dsls.mapping.util.PreProcessingFileSystemAccess
import tools.vitruvius.dsls.response.api.generator.ResponseBuilderFactory
import tools.vitruvius.framework.correspondence.Correspondence
import tools.vitruvius.framework.util.bridges.JavaHelper
import java.util.Arrays
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Optional
import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

import static extension tools.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension tools.vitruvius.framework.util.bridges.JavaHelper.*
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard

class MappingLanguageGenerator implements IMappingLanguageGenerator {
	public final static String PACKAGE_NAME_FIELD = "tools.vitruvius.dsls.mapping.generator.MappingLanguageGenerator.PACKAGE_NAME_FIELD"
	
	@Inject
	private IGenerator delegateGenerator

	@Inject
	private extension MappingLanguageGeneratorNameProvider nameProvider

	private extension EMFGeneratorHelper emfGeneratorHelper
	private extension TemplateGenerator templateGenerator
	

	
	override def void initialize() {
		nameProvider.initialize
	}

	override generateAndCreateResponses(Collection<Resource> inputResources, IFileSystemAccess inputFsa) {
		val fsa = PreProcessingFileSystemAccess.createJavaFormattingFSA(inputFsa)
		
		emfGeneratorHelper = new EMFGeneratorHelper(nameProvider)
		templateGenerator = new TemplateGenerator
		val responses = newHashMap
		
		for (resource : inputResources) {
			delegateGenerator.doGenerate(resource, fsa)
			val statefulGenerator = new StatefulMappingLanguageGenerator(resource, emfGeneratorHelper, templateGenerator, nameProvider)
			val generatedResponses = statefulGenerator.generateResponses()
			responses.put(resource, generatedResponses)
		}
		
		templateGenerator.generateAllTemplates(fsa)
		emfGeneratorHelper.generateCode(fsa)
		
		return responses
	}
	
	/**
	 * This method is called during the editing for the JvmModelInferrer
	 */
	override doGenerate(Resource input, IFileSystemAccess inputFsa) {
		// to always achieve the same naming, the name provider has to be reset here
		nameProvider.initialize
		delegateGenerator.doGenerate(input, inputFsa)
	}
	
	/**
	 * Nested static class, to make the state local to the
	 * stateful generator.
	 * Is instantiated for each resource.
	 */
	private static class StatefulMappingLanguageGenerator {
		public new(Resource resource, EMFGeneratorHelper emfGeneratorHelper, TemplateGenerator templateGenerator, MappingLanguageGeneratorNameProvider nameProvider) {
			this.resource = resource
			this.emfGeneratorHelper = emfGeneratorHelper
			this.templateGenerator = templateGenerator
			this.nameProvider = nameProvider
			
			this.responseBuilderFactory = new ResponseBuilderFactory
		}

		private Resource resource
		
		private extension EMFGeneratorHelper emfGeneratorHelper
		private extension MappingLanguageGeneratorNameProvider nameProvider
		private extension TemplateGenerator templateGenerator

		private extension ResponseBuilderFactory responseBuilderFactory
		
		private extension MappingLanguageGeneratorState state
		private extension ConstraintLanguageGenerator clg
	
		private def generateResponses() {
			val mappingFile = resource.contents.get(0).requireType(MappingFile)
	
			this.state = new MappingLanguageGeneratorState(mappingFile)
			this.clg = new ConstraintLanguageGenerator(emfGeneratorHelper, nameProvider, this.state)
			
			val importToAllCalls = newHashMap(imports.map[it -> newArrayList])
			for (mapping : mappings) {
				val mappingCalls = mapping.generateMappingClass
				mappingCalls.forEach[imp, call |
					importToAllCalls.get(imp).add(call)
				]
			}
			
			(new DefaultContainmentGenerator(state, templateGenerator, nameProvider, clg)).generate
	
			val responses = newArrayList
			for (imp : imports) {
				val response = responseBuilderFactory.createResponseBuilder
					.setName(getResponseName(imp, resource))
					.setTrigger(imp.package)
					.setTargetChange(imp.otherImport.package)
					.setExecutionBlock('''
						final tools.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState state =
							new tools.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState(transformationResult, this.userInteracting, blackboard);
						«importToAllCalls.get(imp).join("\n")»
						tools.vitruvius.extensions.dslsruntime.mapping.DefaultContainmentMapping
							.INSTANCE.applyEChange(change, blackboard, state);
					''')
					.generateResponse
				responses.add(response)
			}
			return responses
		}
	

		
		private def generateMappingClass(Mapping mapping) {
			if (mapping.^default) {
				generateDefaultMappingClass(mapping)
			} else {
				generateNonDefaultMappingClass(mapping)
			}
		}
		
		private def generateNonDefaultMappingClass(Mapping mapping) {
			val fqn = getMappingClassName(mapping)
			val className = fqn.toSimpleName
			val allRequires = mapping.allRequires
			
			addTemplateJavaFile(fqn, [ extension ih, templates | 
					'''
					public class «className» extends «typeRef(AbstractMappingRealization)» {
						public static final «className» INSTANCE = new «className»();
						
						private «className»() {}
						
						@Override
						public String getMappingID() {
							return "«className»";
						}
						
						«FOR imp : imports»
						public static boolean check«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()») {
							«FOR checkExpression : getConstraints(mapping, imp).map[checkSignatureConstraint(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)].filterNull»
								if (!(«checkExpression»))
									return false;
							«ENDFOR»
							return true;
						}
						
						public static void enforceCorrectInitializationOn«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()», «typeRef(MappingExecutionState)» state) {
							«FOR constraint : getConstraints(mapping, imp) AFTER "\n"»
								«FOR updateTUIDJava : clg.getEObjectsWithPossiblyChangedTUID(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, constraint)»
									state.record(«updateTUIDJava»);
								«ENDFOR»
							«ENDFOR»
							«FOR constraint : getConstraints(mapping, imp)
								.map[establishSignatureConstraintOnCreate(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)]
								.filterNull.filter[!it.toString.empty]»
								«constraint»;
							«ENDFOR»
							state.updateAllTuidsOfCachedObjects();
							state.persistAll();
						}
						
						public static void propagateAttributesFrom«imp.toFirstUpperName»(
							«typeRef(mapping.correspondenceWrapperClassName)» «mapping.name.toFirstLower»,
							«typeRef(MappingExecutionState)» state) {
							«IF mapping.constraintsBody != null»
								«FOR constraint : mapping.constraintsBody.expressions»
									«FOR updateTUIDJava : clg.getEObjectsWithPossiblyChangedTUID(ih, #{#['this']->mapping.name.toFirstLower}, constraint, imp.package)»
									state.record(«updateTUIDJava»);
									«ENDFOR»
									«restoreBodyConstraintFrom(ih, #{#['this']->mapping.name.toFirstLower}, constraint, imp.package)»
								«ENDFOR»
							«ELSE»
								// no post conditions, ignore
							«ENDIF»
							
							state.updateAllTuidsOfCachedObjects();
							state.persistAll();
						}
						
						public static «typeRef(mapping.getWrapperClassName(imp))» create«imp.toFirstUpperName»Candidate(«typeRef(Candidate)» candidate) {
							«FOR req : allRequires AFTER "\n"»
							«typeRef(req.mapping.getCorrespondenceWrapperClassName)» «req.mapping.correspondenceWrapperClassName.toVarName»
								= new «typeRef(req.mapping.getCorrespondenceWrapperClassName)»(candidate.getRequiredCorrespondences()
									.get(«typeRef(mapping.getCorrespondenceWrapperClassName)».«req.name.toUpperCase»_INDEX));
							«ENDFOR»
							return «typeRef(mapping.getWrapperClassName(imp))».createTransientWrapper(
								«FOR req : allRequires SEPARATOR "," AFTER ","»«req.mapping.correspondenceWrapperClassName.toVarName»«ENDFOR»
								candidate
							);
						}
						«ENDFOR»
						
						private static «typeRef(mapping.correspondenceWrapperClassName)» createMappedCorrespondenceWrapper(
								«typeRef(MappedCorrespondenceModel)» mci,
								«FOR req : allRequires SEPARATOR "," AFTER ","»
								«typeRef(req.mapping.correspondenceWrapperClassName)» «req.name.toFirstLower»
								«ENDFOR»
								«typeRef(List)»<«typeRef(EObject)»> as,
								«typeRef(List)»<«typeRef(EObject)»> bs,
								«typeRef(MappingExecutionState)» state) {
							«typeRef(Correspondence)» corr = mci.createAndAddCorrespondence(as, bs);
							«FOR req : allRequires»
							corr.getDependsOn().add(«req.name.toFirstLower».getCorrespondence());
							«ENDFOR»
							mci.registerMappingForCorrespondence(corr, INSTANCE);
							state.addCreatedCorrespondence(corr);
							return new «typeRef(mapping.correspondenceWrapperClassName)»(corr);
						}
						
						private final «typeRef(CandidateGenerator)» candidateGenerator = new «typeRef(CandidateGeneratorImpl)»();

						«FOR imp : imports SEPARATOR "\n\n"»
						public final static «typeRef(EPackage)» «imp.name.toUpperCase»_PACKAGE = «ePackageInstance(ih, imp.package)»;
						public final static «typeRef(List)»<«typeRef(EClass)»> «imp.name.toUpperCase»_SIGNATURE = «typeRef(Arrays)».asList(
							«FOR modelElement : getModelElements(mapping, imp) SEPARATOR ","»«eRef(ih, modelElement.element)»«ENDFOR»
						);
						«ENDFOR»
					
						public final static «typeRef(List)»<«typeRef(MappingRealization)»> REQUIREMENTS = «typeRef(Arrays)».asList(
							«FOR req : allRequires SEPARATOR ","»«typeRef(req.mapping.mappingClassName)».INSTANCE«ENDFOR»
						);
						
						«expandTemplate(ih, #[mapping, 'mappingClassBody'])»
					
						«FOR imp : imports SEPARATOR "\n"»
						public void applyEChangeFor«imp.toFirstUpperName»(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard, «typeRef(MappingExecutionState)» state) {
							«typeRef(MappedCorrespondenceModel)» mci = state.getMci();

							System.out.println("«mapping.name.toFirstUpper». EChange, «imp.toFirstUpperName»: " + eChange.toString());
							
							«typeRef(Set)»<«typeRef(Candidate)»> candidates = candidateGenerator.createCandidates(eChange, REQUIREMENTS, «imp.name.toUpperCase»_SIGNATURE,
									INSTANCE, mci);
							«typeRef(MatchUpdate)»<«typeRef(mapping.getWrapperClassName(imp))», «typeRef(mapping.correspondenceWrapperClassName)»> matchUpdate =
								new «typeRef(MatchUpdate)»<>(INSTANCE, mci,
									candidates, «className»::create«imp.toFirstUpperName»Candidate, «className»::check«imp.toFirstUpperName», «typeRef(mapping.correspondenceWrapperClassName)»::new);

							for («typeRef(mapping.getWrapperClassName(imp))» newCandidate : matchUpdate.getNewCandidates()) {
								«typeRef(List)»<«typeRef(EObject)»> «getOtherImport(imp).toVarName»Objects
									= «typeRef(MappingUtil)».createSignature(«getOtherImport(imp).name.toUpperCase»_PACKAGE, «getOtherImport(imp).name.toUpperCase»_SIGNATURE, state);
								«typeRef(mapping.correspondenceWrapperClassName)» currentCorrespondence
									= createMappedCorrespondenceWrapper(mci,
										«FOR req : allRequires SEPARATOR "," AFTER ","»
										newCandidate.get«req.name.toFirstUpper»()
										«ENDFOR»
										newCandidate.getElements(), «getOtherImport(imp).toVarName»Objects, state);
								enforceCorrectInitializationOn«getOtherImport(imp).toFirstUpperName»(currentCorrespondence.get«getOtherImport(imp).toFirstUpperName»(), state);
								propagateAttributesFrom«imp.toFirstUpperName»(currentCorrespondence, state);
								
								«expandTemplate(ih, #[mapping, imp, 'defaultContainmentCheck'])»
							}

							for («typeRef(mapping.correspondenceWrapperClassName)» voidedCorrespondence : matchUpdate.getVoidedCorrespondences()) {
								destroy(voidedCorrespondence.get«getOtherImport(imp).toFirstUpperName»(), state);
							}

							for («typeRef(mapping.correspondenceWrapperClassName)» currentCorrespondence : matchUpdate.getCurrentCorrespondences()) {
								propagateAttributesFrom«imp.toFirstUpperName»(currentCorrespondence, state);
								
								«expandTemplate(ih, #[mapping, imp, 'defaultContainmentCheck'])»
							}
							
						}
						«ENDFOR»
					}
					'''
				])
				
				return newHashMap(imports.map [ imp |
					imp -> '''«mapping.mappingClassName».INSTANCE.applyEChangeFor«imp.toFirstUpperName»(change, blackboard, state);'''
				])
		}
		
		private def generateDefaultMappingClass(Mapping mapping) {
			val fqn = getMappingClassName(mapping)
			val className = fqn.toSimpleName
			val allRequires = mapping.allRequires
			
			val imp = claimOneImport(mapping)
			
			addTemplateJavaFile(fqn, [ extension ih, templates | 
					'''
					public class «className» extends «typeRef(AbstractMappingRealization)» {
						public static final «className» INSTANCE = new «className»();
						
						private «className»() {}
						
						@Override
						public String getMappingID() {
							return "«className»";
						}
						
						public static boolean check«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()») {
							«FOR checkExpression : getConstraints(mapping, imp).map[checkSignatureConstraint(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)].filterNull»
								if (!(«checkExpression»))
									return false;
							«ENDFOR»
							return true;
						}
						
						public static void enforceCorrectInitializationOn«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()», «typeRef(MappingExecutionState)» state) {
							«FOR constraint : getConstraints(mapping, imp) AFTER "\n"»
								«FOR updateTUIDJava : clg.getEObjectsWithPossiblyChangedTUID(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, constraint)»
									state.record(«updateTUIDJava»);
								«ENDFOR»
							«ENDFOR»
							«FOR constraint : getConstraints(mapping, imp)
								.map[establishSignatureConstraintOnCreate(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)]
								.filterNull.filter[!it.toString.empty]»
								«constraint»;
							«ENDFOR»
							state.updateAllTuidsOfCachedObjects();
							state.persistAll();
						}
						
						public static void propagateAttributesFrom«imp.toFirstUpperName»(
							«typeRef(mapping.correspondenceWrapperClassName)» «mapping.name.toFirstLower»,
							«typeRef(MappingExecutionState)» state) {
							«IF mapping.constraintsBody != null»
								«FOR constraint : mapping.constraintsBody.expressions»
									«FOR updateTUIDJava : clg.getEObjectsWithPossiblyChangedTUID(ih, #{#['this']->mapping.name.toFirstLower}, constraint, imp.package)»
									state.record(«updateTUIDJava»);
									«ENDFOR»
									«restoreBodyConstraintFrom(ih, #{#['this']->mapping.name.toFirstLower}, constraint, imp.package)»
								«ENDFOR»
							«ELSE»
								// no post conditions, ignore
							«ENDIF»
							
							state.updateAllTuidsOfCachedObjects();
							state.persistAll();
						}
						
						public static «typeRef(mapping.getWrapperClassName(imp))» create«imp.toFirstUpperName»Candidate(«typeRef(Candidate)» candidate) {
							«FOR req : allRequires AFTER "\n"»
							«typeRef(req.mapping.getCorrespondenceWrapperClassName)» «req.mapping.correspondenceWrapperClassName.toVarName»
								= new «typeRef(req.mapping.getCorrespondenceWrapperClassName)»(candidate.getRequiredCorrespondences()
									.get(«typeRef(mapping.getCorrespondenceWrapperClassName)».«req.name.toUpperCase»_INDEX));
							«ENDFOR»
							return «typeRef(mapping.getWrapperClassName(imp))».createTransientWrapper(
								«FOR req : allRequires SEPARATOR "," AFTER ","»«req.mapping.correspondenceWrapperClassName.toVarName»«ENDFOR»
								candidate
							);
						}
						
						private static «typeRef(mapping.correspondenceWrapperClassName)» createMappedCorrespondenceWrapper(
								«typeRef(MappedCorrespondenceModel)» mci,
								«FOR req : allRequires SEPARATOR "," AFTER ","»
								«typeRef(req.mapping.correspondenceWrapperClassName)» «req.name.toFirstLower»
								«ENDFOR»
								«typeRef(List)»<«typeRef(EObject)»> as,
								«typeRef(List)»<«typeRef(EObject)»> bs,
								«typeRef(MappingExecutionState)» state) {
							«typeRef(Correspondence)» corr = mci.createAndAddCorrespondence(as, bs);
							«FOR req : allRequires»
							corr.getDependsOn().add(«req.name.toFirstLower».getCorrespondence());
							«ENDFOR»
							mci.registerMappingForCorrespondence(corr, INSTANCE);
							state.addCreatedCorrespondence(corr);
							return new «typeRef(mapping.correspondenceWrapperClassName)»(corr);
						}
						
						private final «typeRef(CandidateGenerator)» candidateGenerator = new «typeRef(CandidateGeneratorImpl)»();

						public final static «typeRef(EPackage)» PACKAGE = «ePackageInstance(ih, imp.package)»;
						public final static «typeRef(List)»<«typeRef(EClass)»> SIGNATURE = «typeRef(Arrays)».asList(
							«FOR modelElement : getModelElements(mapping, imp) SEPARATOR ","»«eRef(ih, modelElement.element)»«ENDFOR»
						);
					
						public final static «typeRef(List)»<«typeRef(MappingRealization)»> REQUIREMENTS = «typeRef(Arrays)».asList(
							«FOR req : allRequires SEPARATOR ","»«typeRef(req.mapping.mappingClassName)».INSTANCE«ENDFOR»
						);
					
						public static «mapping.correspondenceWrapperClassName» getOrCreate(«typeRef(MappingExecutionState)» state) {
							final «typeRef(MappedCorrespondenceModel)» mci = state.getMci();
							«mapping.correspondenceWrapperClassName» currentCorrespondence = null;
							
							final «typeRef(Optional)»<«typeRef(Correspondence)»> «mapping.correspondenceWrapperClassName.toVarName» =
								«typeRef(JavaHelper)».claimOneOrNone(mci.getCorrespondencesForMapping(INSTANCE));
								
							if (!«mapping.correspondenceWrapperClassName.toVarName».isPresent()) {
								currentCorrespondence = createMappedCorrespondenceWrapper(mci,
									«IF imp.equals(imports.get(0))»
										«typeRef(MappingUtil)».createSignature(PACKAGE, SIGNATURE, state),
										«typeRef(Collections)».emptyList(),
									«ELSE»
										«typeRef(Collections)».emptyList(),
										«typeRef(MappingUtil)».createSignature(PACKAGE, SIGNATURE, state),
									«ENDIF»
										state);
										
								enforceCorrectInitializationOn«imp.toFirstUpperName»(currentCorrespondence.get«imp.toFirstUpperName»(), state);
								propagateAttributesFrom«imp.toFirstUpperName»(currentCorrespondence, state);
								«expandTemplate(ih, #[mapping, imp, 'defaultContainmentCheck'])»
							} else {
								currentCorrespondence = new «mapping.correspondenceWrapperClassName»(«mapping.correspondenceWrapperClassName.toVarName».get());
								propagateAttributesFrom«imp.toFirstUpperName»(currentCorrespondence, state);
								«expandTemplate(ih, #[mapping, imp, 'defaultContainmentCheck'])»
							}
							
							state.updateAllTuidsOfCachedObjects();
							return currentCorrespondence;
						}
						
						«expandTemplate(ih, #[mapping, 'mappingClassBody'])»
						
						public void applyEChangeFor«imp.toFirstUpperName»(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard, «typeRef(MappingExecutionState)» state) {
							«typeRef(MappedCorrespondenceModel)» mci = state.getMci();

							System.out.println("«mapping.name.toFirstUpper». EChange, «imp.toFirstUpperName»: " + eChange.toString());
							
							getOrCreate(state);
						}
					}
					'''
				])
				
				return newHashMap(imports.map [ it |
					it -> '''«mapping.mappingClassName».INSTANCE.applyEChangeFor«imp.toFirstUpperName»(change, blackboard, state);'''
				])
		}
	}
}
