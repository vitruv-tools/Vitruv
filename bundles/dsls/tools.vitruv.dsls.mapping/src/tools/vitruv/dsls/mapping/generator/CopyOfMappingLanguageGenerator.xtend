//package tools.vitruv.dsls.mapping.generator
//
//import tools.vitruv.extensions.dslsruntime.mapping.AbstractMappingRealization
//import tools.vitruv.extensions.dslsruntime.mapping.AbstractWrapper
//import tools.vitruv.extensions.dslsruntime.mapping.CandidateGeneratorImpl
//import tools.vitruv.extensions.dslsruntime.mapping.EclipseDialogMIRUserInteracting
//import tools.vitruv.extensions.dslsruntime.mapping.MIRMappingHelper
//import tools.vitruv.extensions.dslsruntime.mapping.MappedCorrespondenceModel
//import tools.vitruv.extensions.dslsruntime.mapping.MappingExecutionState
//import tools.vitruv.extensions.dslsruntime.mapping.MappingUtil
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.AbstractCorrespondenceWrapper
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.Candidate
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.CandidateGenerator
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.ElementProvider
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.MIRUserInteracting
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.MappingRealization
//import tools.vitruv.extensions.dslsruntime.mapping.interfaces.MatchUpdate
//import tools.vitruv.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
//import tools.vitruv.dsls.mapping.helpers.TemplateGenerator
//import tools.vitruv.dsls.mapping.mappingLanguage.Mapping
//import tools.vitruv.dsls.mapping.mappingLanguage.MappingFile
//import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMapping
//import tools.vitruv.dsls.mapping.util.PreProcessingFileSystemAccess
//import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
//import tools.vitruv.dsls.reactions.api.generator.ReactionBuilderFactory
//import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
//import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard
//import tools.vitruv.framework.util.command.ChangePropagationResult
//import tools.vitruv.framework.util.datatypes.VURI
//import tools.vitruv.framework.correspondence.Correspondence
//import tools.vitruv.framework.meta.change.EChange
//import tools.vitruv.framework.util.bridges.EcoreBridge
//import tools.vitruv.framework.util.bridges.JavaHelper
//import tools.vitruv.framework.util.datatypes.ClaimableHashMap
//import tools.vitruv.framework.util.datatypes.Pair
//import java.util.ArrayList
//import java.util.Arrays
//import java.util.Collection
//import java.util.Collections
//import java.util.List
//import java.util.Optional
//import java.util.Set
//import java.util.function.Supplier
//import org.apache.log4j.Logger
//import org.eclipse.emf.ecore.EClass
//import org.eclipse.emf.ecore.EObject
//import org.eclipse.emf.ecore.EPackage
//import org.eclipse.emf.ecore.resource.Resource
//import org.eclipse.xtext.generator.IFileSystemAccess
//
//import static extension tools.vitruv.dsls.mapping.helpers.MappingLanguageHelper.*
//import static extension tools.vitruv.framework.util.bridges.JavaHelper.*
//
///**
// * The main Java plugin project generator for the mapping language.
// */
//class MappingLanguageGenerator {
//	def Collection<Reaction> doGenerate(Resource input, IFileSystemAccess fsa) {
//		val mappingFile = input.contents.filter(MappingFile).claimExactlyOne
//		XMIDumper.dump(mappingFile, input, "mxmi")
//		doGenerate(mappingFile, fsa)
//	}
//	
//	def Collection<Reaction> doGenerate(MappingFile mappingFile, IFileSystemAccess fsa) {
//		
////		if (mappingFile.pluginName !== null) {
////			val contributorNames = mappingFile.imports.map[
////				EclipseBridge.getNameOfContributorOfExtension(
////					"org.eclipse.emf.ecore.generated_package",
////					"uri", it.package.nsURI)].toSet
//			
////			val name = mappingFile.pluginName
////			val project = new EclipseProjectHelper(name)
////			project.reinitializeXtextPluginProject
//			
//			val srcGenFSA = fsa
//			
////			val srcGenFSA = project.srcGenFSA
////			val rootFSA = project.rootFSA
//			
//			val generator = new StatefulMappingLanguageGenerator(mappingFile, srcGenFSA)
//			generator.generate;
//			
////			MappingPluginProjectHelper.createPluginXML(rootFSA, generator.change2CommandTransformingFQN)
////			MappingPluginProjectHelper.createManifest(rootFSA, name, contributorNames, generator.getPkgNames)
//			
////			project.synchronizeProject
////		}
//	}
//	
//	private static class StatefulMappingLanguageGenerator {
//		private static val LOGGER = Logger.getLogger(StatefulMappingLanguageGenerator)
//
//		private MappingFile file
//		private IFileSystemAccess fsa
//		
//		private extension EMFGeneratorHelper emfGeneratorHelper
//		private extension MappingLanguageGeneratorNameProvider nameProvider
//		private extension ConstraintLanguageGenerator clg
//		private extension MappingLanguageGeneratorState state
//		
//		private final Set<Reaction> reactions
//		
//		private final String pkgName;
//	
//		private final extension TemplateGenerator templateGenerator
//		
//		private final ReactionBuilderFactory reactionBuilderFactory = new ReactionBuilderFactory
//		
//		
//		private ClaimableHashMap<MetamodelImport, String> correspondenceGetMethod = new ClaimableHashMap
//
//		new(MappingFile file, IFileSystemAccess fsa) {
//			this.reactions = newHashSet
//			this.file = file
//			this.pkgName = "mappings.generated"
//			this.fsa = PreProcessingFileSystemAccess.createJavaFormattingFSA(fsa)
//			this.nameProvider = new MappingLanguageGeneratorNameProvider(pkgName)
//			this.templateGenerator = new TemplateGenerator(this.fsa)
//		}
//		
//		public def getPkgNames() {
//			#[pkgName, pkgName + ".test"]
//		}
//		
//		public def getChange2CommandTransformingFQN() {
//			change2CommandTransformingClassName
//		}
//
//		public def generate() {
//			// FIXME DW there are some constraints on a valid file here that should be
//			// checked before generating.
//			// sort so dependencies are resolved before they are used
//			this.emfGeneratorHelper = new EMFGeneratorHelper(constantsClassName)
//			this.clg = new ConstraintLanguageGenerator(emfGeneratorHelper)
//
//			this.state = new MappingLanguageGeneratorState(file)
//			
//			correspondenceGetMethod.put(imports.get(0), "getAs()")
//			correspondenceGetMethod.put(imports.get(1), "getBs()")
//
//			for (mapping : mappings) {
//				generateMapping(mapping)
//			}
//
////			generateChange2CommandTransforming;
////			generateTestClass;
//			
//			// EXTENSION 1: default containments
//			(new DefaultContainmentGenerator(state, templateGenerator)).generate
//			
//			templateGenerator.generateAllTemplates			
//			emfGeneratorHelper.generateCode(fsa)
//			
//			reactions
//		}
//
//		private def generateMapping(Mapping mapping) {
//			generateWrapperClasses(mapping)
//			generateMappingClass(mapping)
//		}
//		
//		private def generateWrapperClasses(Mapping mapping) {
//			// if mapping is not default, generate empty wrapper too.
//			// else only generate one wrapper (for mapping.imports with size 1)
//			val importsToGenerate = if (mapping.^default) mapping.imports else imports
//			
//			for (imp : importsToGenerate)
//				generateWrapperClass(mapping, imp)
//				
//			generateCorrespondenceWrapper(mapping)
//		}
//		
//		private def generateCorrespondenceWrapper(Mapping mapping) {
//			if (mapping.^default)
//				generateDefaultCorrespondenceWrapper(mapping)
//			else
//				generateNonDefaultCorrespondenceWrapper(mapping)
//		}
//		
//		private def generateDefaultCorrespondenceWrapper(Mapping mapping) {
//			val imp = mapping.imports.claimExactlyOne
//			
//			val fqnMC = mapping.correspondenceWrapperClassName
//			val classNameMC = fqnMC.toSimpleName
//			
//			// generate mapped correspondencemapping, "mc.checkContainment"
//			addTemplateJavaFile(fqnMC, [ extension ih, templates |
//				'''
//				public class «classNameMC» extends «typeRef(AbstractCorrespondenceWrapper)» {
//					private «typeRef(mapping.getWrapperClassName(imp))» «mapping.getWrapperClassName(imp).toVarName»
//						= «typeRef(mapping.getWrapperClassName(imp))».createHalfMappedCorrespondence(this);
//				
//					public «typeRef(mapping.getWrapperClassName(imp))» get«imp.toFirstUpperName»() {
//						return this.«mapping.getWrapperClassName(imp).toVarName»;
//					}
//					
//					public «classNameMC»(«typeRef(Correspondence)» correspondence) {
//						super(correspondence);
//					}
//				}
//
//				'''
//			])
//		}
//		
//		private def generateNonDefaultCorrespondenceWrapper(Mapping mapping) {
//			val fqnMC = mapping.correspondenceWrapperClassName
//			val classNameMC = fqnMC.toSimpleName
//			
//			val allRequires = mapping.allRequires
//			
//			// generate mapped correspondencemapping, "mc.checkContainment"
//			addTemplateJavaFile(fqnMC, [ extension ih, templates |
//				var reqIndex = 0;
//				'''
//				public class «classNameMC» extends «typeRef(AbstractCorrespondenceWrapper)» {
//					«FOR req : allRequires»
//					public final static int «req.name.toUpperCase»_INDEX = «reqIndex++»;
//					«ENDFOR»
//					
//					«FOR imp : imports»
//					private «typeRef(mapping.getWrapperClassName(imp))» «mapping.getWrapperClassName(imp).toVarName»
//						= «typeRef(mapping.getWrapperClassName(imp))».createHalfMappedCorrespondence(this);
//					«ENDFOR»
//				
//					«FOR req : allRequires»
//					private «typeRef(req.mapping.correspondenceWrapperClassName)» «req.name.toFirstLower»;
//					«ENDFOR»
//					
//					«FOR imp : imports»
//					public «typeRef(mapping.getWrapperClassName(imp))» get«imp.toFirstUpperName»() {
//						return this.«mapping.getWrapperClassName(imp).toVarName»;
//					}
//					«ENDFOR»
//					
//					«FOR req : allRequires»
//					public «typeRef(req.mapping.correspondenceWrapperClassName)» get«req.name.toFirstUpper»() {
//						return this.«req.name.toFirstLower»;
//					}
//					«ENDFOR»
//					
//					public «classNameMC»(«typeRef(Correspondence)» correspondence) {
//						super(correspondence);
//						«FOR req : allRequires»
//						this.«req.name.toFirstLower» = new «typeRef(req.mapping.correspondenceWrapperClassName)»(
//							correspondence.getDependsOn().get(«req.name.toUpperCase»_INDEX)
//						);
//						«ENDFOR»
//					}
//				}
//
//				'''
//			])
//		}
//		
//		// TODO: move to extra class
//		private def getSupplierVariableName(RequiredMapping req)
//			'''«req.name.toFirstLower»Supplier'''
//			
//		private def getSupplierType(RequiredMapping req, extension ImportHelper ih)
//			'''«typeRef(Supplier)»<«typeRef(req.mapping.getCorrespondenceWrapperClassName)»>'''
//			
//		private def generateWrapperClass(Mapping mapping, MetamodelImport imp) {
//			val signatureElements = getMappingToImportToModelElements.get(mapping).get(imp)
//			val fqn = getWrapperClassName(mapping, imp)
//			val className = fqn.toSimpleName
//			
//			val allRequires = mapping.allRequires
//			
//			addTemplateJavaFile(fqn, [ extension ih, templates | 
//				var elementIndex = 0;
//				'''
//					public class «className» extends «typeRef(AbstractWrapper)» {
//						public «className»(
//							«FOR req : allRequires SEPARATOR "," AFTER ","»
//								«req.getSupplierType(ih)» «req.getSupplierVariableName»
//							«ENDFOR»
//							«typeRef(ElementProvider)» elementProvider) {
//							super(«typeRef(Arrays)».asList(
//							«FOR req : allRequires SEPARATOR ","»
//								() -> «req.getSupplierVariableName».get().getCorrespondence()
//							«ENDFOR»
//							), elementProvider);
//							«FOR req : allRequires BEFORE "\n"»
//								this.«req.getSupplierVariableName» = «req.getSupplierVariableName»;
//							«ENDFOR»
//						}
//						
//						«FOR req : allRequires»
//							private «req.getSupplierType(ih)» «req.getSupplierVariableName»;
//						«ENDFOR»
//						
//						«FOR req : allRequires»
//							public «typeRef(req.mapping.getCorrespondenceWrapperClassName)» get«req.name.toFirstUpper»() {
//								return this.«req.getSupplierVariableName».get();
//							}
//						«ENDFOR»
//						
//						«FOR modelElement : signatureElements»
//							private static final int «modelElement.name.toUpperCase»_INDEX = «elementIndex++»;
//						«ENDFOR»
//						
//						«FOR modelElement : signatureElements»
//							public «typeRef(modelElement.element)» get«modelElement.name.toFirstUpper»() {
//								return «typeRef(JavaHelper)».requireType(getElements().get(«modelElement.name.toUpperCase»_INDEX), «typeRef(modelElement.element)».class);
//							}
//						«ENDFOR»
//						
//						public static «className» createHalfMappedCorrespondence(«typeRef(mapping.getCorrespondenceWrapperClassName)» «mapping.getCorrespondenceWrapperClassName.toVarName») {
//							return new «className»(
//								«FOR req : allRequires SEPARATOR "," AFTER ","»
//									() -> «mapping.getCorrespondenceWrapperClassName.toVarName».get«req.name.toFirstUpper»()
//								«ENDFOR»
//									() -> «mapping.getCorrespondenceWrapperClassName.toVarName».getCorrespondence().«correspondenceGetMethod.claimValueForKey(imp)»
//							);
//						}
//						
//						public static «className» createTransientWrapper(
//							«FOR req : allRequires SEPARATOR "," AFTER ","»
//								«typeRef(req.mapping.correspondenceWrapperClassName)» «req.name.toFirstLower»
//							«ENDFOR»
//							«typeRef(List)»<«typeRef(EObject)»> elements) {
//							«FOR req : allRequires»
//								«typeRef(req.mapping.correspondenceWrapperClassName)» transient«req.name.toFirstUpper»Wrapper =
//									new «typeRef(req.mapping.correspondenceWrapperClassName)»(«req.name.toFirstLower».getCorrespondence());
//							«ENDFOR»
//							final «typeRef(List)»<«typeRef(EObject)»> transientElementsCopy = new «typeRef(ArrayList)»<>(elements);
//							return new «className»(
//							«FOR req : allRequires SEPARATOR "," AFTER ","»
//								() -> transient«req.name.toFirstUpper»Wrapper
//							«ENDFOR»
//								() -> transientElementsCopy);
//						}
//						
//						public static «className» createTransientWrapper(
//							«FOR req : allRequires SEPARATOR "," AFTER ","»
//								«typeRef(req.mapping.correspondenceWrapperClassName)» «req.name.toFirstLower»
//							«ENDFOR»
//							«typeRef(ElementProvider)» elementProvider) {
//							return «className».createTransientWrapper(
//							«FOR req : allRequires SEPARATOR "," AFTER ","»«req.name.toFirstLower»«ENDFOR»
//							elementProvider.getElements());
//						}
//					}
//				'''
//			])
//		}
//		
//		private def generateMappingClass(Mapping mapping) {
//			if (mapping.^default)
//				generateDefaultMappingClass(mapping)
//			else
//				generateNonDefaultMappingClass(mapping)
//		}
//		
//		private def generateNonDefaultMappingClass(Mapping mapping) {
//			val fqn = getMappingClassName(mapping)
//			val className = fqn.toSimpleName
//			val allRequires = mapping.allRequires
//			
//			val otherImport = newHashMap(
//				imports.get(0) -> imports.get(1),
//				imports.get(1) -> imports.get(0)
//			)
//			
//			addTemplateJavaFile(fqn, [ extension ih, templates | 
//				'''
//				
//				public class «className» extends «typeRef(AbstractMappingRealization)» {
//					public static final «className» INSTANCE = new «className»();
//					
//					private «className»() {}
//					
//					@Override
//					public String getMappingID() {
//						return "«className»";
//					}
//					
//					«FOR imp : imports»
//					public static boolean check«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()») {
//						«IF !getConstraints(mapping, imp).empty»
//							«FOR checkExpression : getConstraints(mapping, imp).map[checkSignatureConstraint(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)].filterNull»
//								if (!(«checkExpression»))
//									return false;
//							«ENDFOR»
//						«ENDIF»
//						return true;
//					}
//					
//					public static void enforceCorrectInitializationOn«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()», «typeRef(MappingExecutionState)» state) {
//						«FOR constraint : getConstraints(mapping, imp) AFTER "\n"»
//							«FOR updateTuidJava : clg.getEObjectsWithPossiblyChangedTuid(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, constraint)»
//								state.record(«updateTuidJava»);
//							«ENDFOR»
//						«ENDFOR»
//						«FOR constraint : getConstraints(mapping, imp)
//							.map[establishSignatureConstraintOnCreate(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)]
//							.filterNull.filter[!it.toString.empty]»
//							«constraint»;
//						«ENDFOR»
//						state.updateAllTuidsOfCachedObjects();
//						state.persistAll();
//					}
//					
//					public static void propagateAttributesFrom«imp.toFirstUpperName»(
//						«typeRef(mapping.correspondenceWrapperClassName)» «mapping.name.toFirstLower»,
//						«typeRef(MappingExecutionState)» state) {
//						«IF mapping.constraintsBody !== null»
//							«FOR constraint : mapping.constraintsBody.expressions»
//								«FOR updateTuidJava : clg.getEObjectsWithPossiblyChangedTuid(ih, #{#['this']->mapping.name.toFirstLower}, constraint, imp.package)»
//								state.record(«updateTuidJava»);
//								«ENDFOR»
//								«restoreBodyConstraintFrom(ih, #{#['this']->mapping.name.toFirstLower}, constraint, imp.package)»
//							«ENDFOR»
//						«ELSE»
//							// no post conditions, ignore
//						«ENDIF»
//						
//						state.updateAllTuidsOfCachedObjects();
//						state.persistAll();
//					}
//					
//					public static «typeRef(mapping.getWrapperClassName(imp))» create«imp.toFirstUpperName»Candidate(«typeRef(Candidate)» candidate) {
//						«FOR req : allRequires AFTER "\n"»
//						«typeRef(req.mapping.getCorrespondenceWrapperClassName)» «req.mapping.correspondenceWrapperClassName.toVarName»
//							= new «typeRef(req.mapping.getCorrespondenceWrapperClassName)»(candidate.getRequiredCorrespondences()
//								.get(«typeRef(mapping.getCorrespondenceWrapperClassName)».«req.name.toUpperCase»_INDEX));
//						«ENDFOR»
//						return «typeRef(mapping.getWrapperClassName(imp))».createTransientWrapper(
//							«FOR req : allRequires SEPARATOR "," AFTER ","»«req.mapping.correspondenceWrapperClassName.toVarName»«ENDFOR»
//							candidate
//						);
//					}
//					«ENDFOR»
//					
//					private «typeRef(mapping.correspondenceWrapperClassName)» createMappedCorrespondenceWrapper(
//							«typeRef(MappedCorrespondenceModel)» mci,
//							«FOR req : allRequires SEPARATOR "," AFTER ","»
//							«typeRef(req.mapping.correspondenceWrapperClassName)» «req.name.toFirstLower»
//							«ENDFOR»
//							«typeRef(List)»<«typeRef(EObject)»> as,
//							«typeRef(List)»<«typeRef(EObject)»> bs,
//							«typeRef(MappingExecutionState)» state) {
//						«typeRef(Correspondence)» corr = mci.createAndAddCorrespondence(as, bs);
//						«FOR req : allRequires»
//						corr.getDependsOn().add(«req.name.toFirstLower».getCorrespondence());
//						«ENDFOR»
//						mci.registerMappingForCorrespondence(corr, INSTANCE);
//						state.addCreatedCorrespondence(corr);
//						return new «typeRef(mapping.correspondenceWrapperClassName)»(corr);
//					}
//					
//					private final «typeRef(CandidateGenerator)» candidateGenerator = new «typeRef(CandidateGeneratorImpl)»();
//
//					«FOR imp : imports SEPARATOR "\n\n"»
//					public final static «typeRef(EPackage)» «imp.name.toUpperCase»_PACKAGE = «ePackageInstance(ih, imp.package)»;
//					public final static «typeRef(List)»<«typeRef(EClass)»> «imp.name.toUpperCase»_SIGNATURE = «typeRef(Arrays)».asList(
//						«FOR modelElement : getModelElements(mapping, imp) SEPARATOR ","»«eRef(ih, modelElement.element)»«ENDFOR»
//					);
//					«ENDFOR»
//				
//					public final static «typeRef(List)»<«typeRef(MappingRealization)»> REQUIREMENTS = «typeRef(Arrays)».asList(
//						«FOR req : allRequires SEPARATOR ","»«typeRef(req.mapping.mappingClassName)».INSTANCE«ENDFOR»
//					);
//				
//					private «typeRef(MIRUserInteracting)» userInteracting = new «typeRef(EclipseDialogMIRUserInteracting)»();
//					
//					«FOR imp : imports SEPARATOR "\n"»
//					public void applyEChangeFor«imp.toFirstUpperName»(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard, «typeRef(MappingExecutionState)» state) {
//						«typeRef(MappedCorrespondenceModel)» mci = state.getMci();
//
//						System.out.println("«mapping.name.toFirstUpper». EChange, «imp.toFirstUpperName»: " + eChange.toString());
//						
//						«typeRef(Set)»<«typeRef(Candidate)»> candidates = candidateGenerator.createCandidates(eChange, REQUIREMENTS, «imp.name.toUpperCase»_SIGNATURE,
//								INSTANCE, mci);
//						«typeRef(MatchUpdate)»<«typeRef(mapping.getWrapperClassName(imp))», «typeRef(mapping.correspondenceWrapperClassName)»> matchUpdate =
//							new «typeRef(MatchUpdate)»<>(INSTANCE, mci,
//								candidates, «className»::create«imp.toFirstUpperName»Candidate, «className»::check«imp.toFirstUpperName», «typeRef(mapping.correspondenceWrapperClassName)»::new);
//
//						for («typeRef(mapping.getWrapperClassName(imp))» newCandidate : matchUpdate.getNewCandidates()) {
//							«typeRef(List)»<«typeRef(EObject)»> «otherImport.get(imp).toVarName»Objects
//								= «typeRef(MappingUtil)».createSignature(«otherImport.get(imp).name.toUpperCase»_PACKAGE, «otherImport.get(imp).name.toUpperCase»_SIGNATURE, state);
//							«typeRef(mapping.correspondenceWrapperClassName)» corr
//								= createMappedCorrespondenceWrapper(mci,
//									«FOR req : allRequires SEPARATOR "," AFTER ","»
//									newCandidate.get«req.name.toFirstUpper»()
//									«ENDFOR»
//									newCandidate.getElements(), «otherImport.get(imp).toVarName»Objects, state);
//							enforceCorrectInitializationOn«otherImport.get(imp).toFirstUpperName»(corr.get«otherImport.get(imp).toFirstUpperName»(), state);
//							propagateAttributesFrom«imp.toFirstUpperName»(corr, state);
//						}
//
//						for («typeRef(mapping.correspondenceWrapperClassName)» voidedCorrespondence : matchUpdate.getVoidedCorrespondences()) {
//							destroy(voidedCorrespondence.get«otherImport.get(imp).toFirstUpperName»(), state);
//						}
//
//						for («typeRef(mapping.correspondenceWrapperClassName)» currentCorrespondence : matchUpdate.getCurrentCorrespondences()) {
//							propagateAttributesFrom«imp.toFirstUpperName»(currentCorrespondence, state);
//						}
//					}
//					«ENDFOR»
//					
//					public void setUserInteracting(«typeRef(MIRUserInteracting)» userInteracting) {
//						this.userInteracting = userInteracting;
//					}
//				}
//				'''
//			])
//			
//			reactions += #[
//				new Pair(imports.get(0), imports.get(1)),
//				new Pair(imports.get(1), imports.get(0))
//			].map[
//				reactionsBuilderFactory
//					.createReactionBuilder
//					.setName("Reaction_" + className)
//					.setTrigger(first.package)
//					.setTargetChange(second.package)
//					.setExecutionBlock(
//					'''
//						{ «fqn».INSTANCE.applyEChangeFor«first.toFirstUpperName»(change, blackboard, null); }
//					''')
//					.generateReaction
//			]			
//		}
//		
//		private def generateDefaultMappingClass(Mapping mapping) {
//			val fqn = getMappingClassName(mapping)
//			val className = fqn.toSimpleName
//			
//			val imp = claimOneImport(mapping)
//			val allRequires = mapping.allRequires
//			
//			addTemplateJavaFile(fqn, [ extension ih, templates | 
//				'''
//				
//				public class «className» extends «typeRef(AbstractMappingRealization)» {
//					public static final «className» INSTANCE = new «className»();
//					
//					private «className»() {}
//					
//					@Override
//					public String getMappingID() {
//						return "«className»";
//					}
//					
//					public static void enforceCorrectInitializationOn«imp.toFirstUpperName»(«typeRef(mapping.getWrapperClassName(imp))» «imp.toVarName()», «typeRef(MappingExecutionState)» state) {
//						«FOR constraint : getConstraints(mapping, imp) AFTER "\n"»
//							«FOR updateTuidJava : clg.getEObjectsWithPossiblyChangedTuid(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, constraint)»
//								state.record(«updateTuidJava»);
//							«ENDFOR»
//						«ENDFOR»
//						«FOR constraint : getConstraints(mapping, imp)
//							.map[establishSignatureConstraintOnCreate(ih, #{#['this', imp]->imp.toVarName(), #['this']->imp.toVarName()}, it)]
//							.filterNull.filter[!it.toString.empty]»
//							«constraint»;
//						«ENDFOR»
//						state.updateAllTuidsOfCachedObjects();
//						state.persistAll();
//					}
//					
//					private static «typeRef(mapping.correspondenceWrapperClassName)» createMappedCorrespondenceWrapper(
//							«typeRef(MappedCorrespondenceModel)» mci,
//							«typeRef(List)»<«typeRef(EObject)»> as,
//							«typeRef(List)»<«typeRef(EObject)»> bs,
//							«typeRef(MappingExecutionState)» state) {
//						«typeRef(Correspondence)» corr = mci.createAndAddCorrespondence(as, bs);
//						«FOR req : allRequires»
//						corr.getDependsOn().add(«req.name.toFirstLower».getCorrespondence());
//						«ENDFOR»
//						mci.registerMappingForCorrespondence(corr, INSTANCE);
//						state.addCreatedCorrespondence(corr);
//						return new «typeRef(mapping.correspondenceWrapperClassName)»(corr);
//					}
//
//					
//					public final static «typeRef(EPackage)» PACKAGE = «ePackageInstance(ih, imp.package)»;
//					public final static «typeRef(List)»<«typeRef(EClass)»> SIGNATURE = «typeRef(Arrays)».asList(
//						«FOR modelElement : getModelElements(mapping, imp) SEPARATOR ","»«eRef(ih, modelElement.element)»«ENDFOR»
//					);
//				
//					private static «typeRef(MIRUserInteracting)» userInteracting = new «typeRef(EclipseDialogMIRUserInteracting)»();
//					
//
//					public static «mapping.correspondenceWrapperClassName» getOrCreate(«typeRef(MappingExecutionState)» state) {
//						final «typeRef(MappedCorrespondenceModel)» mci = state.getMci();
//						«mapping.correspondenceWrapperClassName» result = null;
//						
//						final «typeRef(Optional)»<«typeRef(Correspondence)»> «mapping.correspondenceWrapperClassName.toVarName» =
//							«typeRef(JavaHelper)».claimOneOrNone(mci.getCorrespondencesForMapping(INSTANCE));
//							
//						if (!«mapping.correspondenceWrapperClassName.toVarName».isPresent()) {
//							result = createMappedCorrespondenceWrapper(mci,
//								«IF imp.equals(imports.get(0))»
//									«typeRef(MappingUtil)».createSignature(PACKAGE, SIGNATURE, state),
//									«typeRef(Collections)».emptyList(),
//								«ELSE»
//									«typeRef(Collections)».emptyList(),
//									«typeRef(MappingUtil)».createSignature(PACKAGE, SIGNATURE, state),
//								«ENDIF»
//									state);
//									
//							«typeRef(MIRMappingHelper)».ensureContainments(state, state::getAllAffectedEObjects, (objectToCreateContainmentFor) -> {
//								state.addObjectForTuidUpdate(objectToCreateContainmentFor);
//								state.addRootEObjectToSave(objectToCreateContainmentFor, «typeRef(VURI)».getInstance(
//										userInteracting.askForNewResource(«typeRef(EcoreBridge)».createSensibleString(objectToCreateContainmentFor))));
//							});
//						} else {
//							result = new «mapping.correspondenceWrapperClassName»(«mapping.correspondenceWrapperClassName.toVarName».get());
//						}
//						
//						state.updateAllTuidsOfCachedObjects();
//						return result;
//					}
//					
//					@Override
//					public «typeRef(TransformationResult)» applyEChange(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard, «MappingExecutionState» state) {
//						final «typeRef(MappedCorrespondenceModel)» mci =
//							«typeRef(JavaHelper)».requireType(blackboard.getCorrespondenceModel(),
//								«typeRef(MappedCorrespondenceModel)».class);
//						final «typeRef(MappingExecutionState)» state = new «typeRef(MappingExecutionState)»(mci, blackboard);
//						
//						getOrCreate(state);
//					}
//					
//					public void setUserInteracting(«typeRef(MIRUserInteracting)» userInteracting) {
//						this.userInteracting = userInteracting;
//					}
//				}
//				'''
//			])
//		}
//			
//	
////		private def generateTestClass() {
////			val fqn = getTestClassName
////			val className = fqn.toSimpleName
////			
////			addTemplateJavaFile(fqn, [ extension ih, templates |
////				// TODO DW resolve cycle and reference by type
////				'''
////					public class «className» extends «typeRef(AbstractMappingTestBase)» {
////						@Override
////						protected String getPluginName() {
////«««							return "«file.pluginName»";
////						}
////						
////						@Override
////						protected «typeRef(Collection)»<«typeRef(Pair)»<String, String>> getMetamodelURIsAndExtensions() {
////							«typeRef(Set)»<«typeRef(Pair)»<String, String>> result = new «typeRef(HashSet)»<>();
////							«FOR id : imports»
////								result.add(new «typeRef(Pair)»<>("«id.package.nsURI»", "«id.name»"));
////							«ENDFOR»
////							
////							return result;
////						}
////						
////						@Override
////						protected Class<? extends «typeRef(AbstractMappingChange2CommandTransforming)»> getChange2CommandTransformingClass() {
////							return «typeRef(change2CommandTransformingClassName)».class;
////						}
////					}
////				'''
////			])
////		}
//		
////		private def generateChange2CommandTransforming() {
////			val fqn = getChange2CommandTransformingClassName
////			val className = fqn.toSimpleName
////			
////			addTemplateJavaFile(fqn, [ extension ih, templates |
////				'''
////					public class «className» extends «typeRef(AbstractMappingChange2CommandTransforming)» {
////							«FOR id : imports»
////							public final static String MM_«id.name.toUpperCase» = "«id.package.nsURI»";
////							«ENDFOR»
////							
////							«FOR id : imports.map[name.toUpperCase]»
////							public final static «typeRef(VURI)» VURI_«id» = «typeRef(VURI)».getInstance(MM_«id»);
////							«ENDFOR»
////							
////						    /* Transformable metamodels. */
////							private «typeRef(List)»<«typeRef(Pair)»<«typeRef(VURI)», «typeRef(VURI)»>> transformableMetamodels;
////							
////							@Override
////							public «typeRef(List)»<«typeRef(Pair)»<«typeRef(VURI)», «typeRef(VURI)»>> getTransformableMetamodels() {
////								return transformableMetamodels;
////							}
////							
////							@Override
////							protected void setup() {
////								transformableMetamodels = new «typeRef(ArrayList)»<>();
////								
////								«FOR id : #[new Pair(0, 1), new Pair(1, 0)]»
////								transformableMetamodels.add(new «typeRef(Pair)»<>(
////									VURI_«imports.get(id.first).name.toUpperCase», VURI_«imports.get(id.second).name.toUpperCase»
////								));
////								«ENDFOR»
////								
////								«FOR mc : mappings.map[typeRef(mappingClassName)] + #[typeRef(DefaultContainmentMapping)]»
////								addMapping(«mc».INSTANCE);
////								«ENDFOR»
////							}
////							
////							@Override
////							public void setUserInteracting(«typeRef(MIRUserInteracting)» userInteracting) {
////								«FOR mc : mappings.map[typeRef(mappingClassName)] + #[typeRef(DefaultContainmentMapping)]»
////								«mc».INSTANCE.setUserInteracting(userInteracting);
////								«ENDFOR»
////							}
////					}
////				'''
////			])
////		}
//	}
//}