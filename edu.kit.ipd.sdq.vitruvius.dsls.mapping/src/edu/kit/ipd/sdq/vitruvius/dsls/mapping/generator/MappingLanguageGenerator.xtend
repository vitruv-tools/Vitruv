package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.AbstractMappingRealization
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.CandidateGeneratorImpl
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.EclipseDialogMIRUserInteracting
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappingExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappingUtil
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.Candidate
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.CandidateGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MIRUserInteracting
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MappingRealization
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MatchUpdate
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.TemplateGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.PreProcessingFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseBuilderFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper
import java.util.Arrays
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

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.*

class MappingLanguageGenerator implements IMappingLanguageGenerator {
	public final static String PACKAGE_NAME_FIELD = "edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator.MappingLanguageGenerator.PACKAGE_NAME_FIELD"
	
	@Inject
	private IGenerator delegateGenerator

	@Inject
	private MappingLanguageGeneratorStateProvider stateProvider
	
	@Inject
	private extension MappingLanguageGeneratorNameProvider nameProvider
	
	@Inject
	private extension EMFGeneratorHelper emfGeneratorHelper
	
	@Inject
	private extension ConstraintLanguageGenerator clg
	
	@Inject
	private extension TemplateGenerator templateGenerator
	
	@Inject
	private extension ResponseBuilderFactory responseBuilderFactory
	
	private extension MappingLanguageGeneratorState state

	override generateAndCreateResponses(Resource input, IFileSystemAccess inputFsa) {
		val fsa = PreProcessingFileSystemAccess.createJavaFormattingFSA(inputFsa)
		
		doGenerate(input, fsa)

		val mappingFile = input.contents.get(0).requireType(MappingFile)

		this.state = stateProvider.get(mappingFile)
		
		clg.mappingFile = mappingFile

		val importToAllCalls = newHashMap(imports.map[it -> newArrayList])
		for (mapping : mappings) {
			val mappingCalls = mapping.generateMappingClass
			mappingCalls.forEach[imp, call |
				importToAllCalls.get(imp).add(call)
			]
		}

		val responses = newArrayList		
		for (imp : imports) {
			val response = responseBuilderFactory.createResponseBuilder
				.setName(getResponseName(imp, input))
				.setTrigger(imp.package)
				.setTargetChange(imp.otherImport.package)
				.setExecutionBlock('''
					{
						final edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappingExecutionState state =
							new edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappingExecutionState(blackboard);
						«importToAllCalls.get(imp).join("\n")»
					}
				''')
				.generateResponse
			responses.add(response)
		}
		
		templateGenerator.generateAllTemplates(fsa)
		emfGeneratorHelper.generateCode(fsa)
		
		return responses
	}

	override doGenerate(Resource input, IFileSystemAccess inputFsa) {
		delegateGenerator.doGenerate(input, inputFsa)
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
					
					private «typeRef(mapping.correspondenceWrapperClassName)» createMappedCorrespondenceWrapper(
							«typeRef(MappedCorrespondenceInstance)» mci,
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
				
					private «typeRef(MIRUserInteracting)» userInteracting = new «typeRef(EclipseDialogMIRUserInteracting)»();
					
					«FOR imp : imports SEPARATOR "\n"»
					public void applyEChangeFor«imp.toFirstUpperName»(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard, «typeRef(MappingExecutionState)» state) {
						«typeRef(MappedCorrespondenceInstance)» mci = state.getMci();

						System.out.println("«mapping.name.toFirstUpper». EChange, «imp.toFirstUpperName»: " + eChange.toString());
						
						«typeRef(Set)»<«typeRef(Candidate)»> candidates = candidateGenerator.createCandidates(eChange, REQUIREMENTS, «imp.name.toUpperCase»_SIGNATURE,
								INSTANCE, mci);
						«typeRef(MatchUpdate)»<«typeRef(mapping.getWrapperClassName(imp))», «typeRef(mapping.correspondenceWrapperClassName)»> matchUpdate =
							new «typeRef(MatchUpdate)»<>(INSTANCE, mci,
								candidates, «className»::create«imp.toFirstUpperName»Candidate, «className»::check«imp.toFirstUpperName», «typeRef(mapping.correspondenceWrapperClassName)»::new);

						for («typeRef(mapping.getWrapperClassName(imp))» newCandidate : matchUpdate.getNewCandidates()) {
							«typeRef(List)»<«typeRef(EObject)»> «getOtherImport(imp).toVarName»Objects
								= «typeRef(MappingUtil)».createSignature(«getOtherImport(imp).name.toUpperCase»_PACKAGE, «getOtherImport(imp).name.toUpperCase»_SIGNATURE, state);
							«typeRef(mapping.correspondenceWrapperClassName)» corr
								= createMappedCorrespondenceWrapper(mci,
									«FOR req : allRequires SEPARATOR "," AFTER ","»
									newCandidate.get«req.name.toFirstUpper»()
									«ENDFOR»
									newCandidate.getElements(), «getOtherImport(imp).toVarName»Objects, state);
							enforceCorrectInitializationOn«getOtherImport(imp).toFirstUpperName»(corr.get«getOtherImport(imp).toFirstUpperName»(), state);
							propagateAttributesFrom«imp.toFirstUpperName»(corr, state);
						}

						for («typeRef(mapping.correspondenceWrapperClassName)» voidedCorrespondence : matchUpdate.getVoidedCorrespondences()) {
							destroy(voidedCorrespondence.get«getOtherImport(imp).toFirstUpperName»(), state);
						}

						for («typeRef(mapping.correspondenceWrapperClassName)» currentCorrespondence : matchUpdate.getCurrentCorrespondences()) {
							propagateAttributesFrom«imp.toFirstUpperName»(currentCorrespondence, state);
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
					
					private «typeRef(mapping.correspondenceWrapperClassName)» createMappedCorrespondenceWrapper(
							«typeRef(MappedCorrespondenceInstance)» mci,
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
				
					private «typeRef(MIRUserInteracting)» userInteracting = new «typeRef(EclipseDialogMIRUserInteracting)»();
					
					public static «mapping.correspondenceWrapperClassName» getOrCreate(«typeRef(MappingExecutionState)» state) {
						final «typeRef(MappedCorrespondenceInstance)» mci = state.getMci();
						«mapping.correspondenceWrapperClassName» result = null;
						
						final «typeRef(Optional)»<«typeRef(Correspondence)»> «mapping.correspondenceWrapperClassName.toVarName» =
							«typeRef(JavaHelper)».claimOneOrNone(mci.getCorrespondencesForMapping(INSTANCE));
							
						if (!«mapping.correspondenceWrapperClassName.toVarName».isPresent()) {
							result = createMappedCorrespondenceWrapper(mci,
								«IF imp.equals(imports.get(0))»
									«typeRef(MappingUtil)».createSignature(PACKAGE, SIGNATURE, state),
									«typeRef(Collections)».emptyList(),
								«ELSE»
									«typeRef(Collections)».emptyList(),
									«typeRef(MappingUtil)».createSignature(PACKAGE, SIGNATURE, state),
								«ENDIF»
									state);
						} else {
							result = new «mapping.correspondenceWrapperClassName»(«mapping.correspondenceWrapperClassName.toVarName».get());
						}
						
						state.updateAllTuidsOfCachedObjects();
						return result;
					}
					
					public void applyEChangeFor«imp.toFirstUpperName»(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard, «typeRef(MappingExecutionState)» state) {
						«typeRef(MappedCorrespondenceInstance)» mci = state.getMci();

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
