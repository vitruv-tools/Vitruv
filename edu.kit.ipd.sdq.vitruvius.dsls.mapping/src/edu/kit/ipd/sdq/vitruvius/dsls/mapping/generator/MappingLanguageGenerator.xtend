package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingPluginProjectHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseProjectHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.PreProcessingFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.Collection
import java.util.HashSet
import java.util.List
import java.util.Objects
import java.util.Optional
import java.util.Set
import java.util.stream.Collectors
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*

class MappingLanguageGenerator {
	def doGenerate(Resource input) {
		val mappingFile = input.contents.filter(MappingFile).claimExactlyOne
		doGenerate(mappingFile)
	}
	
	def doGenerate(MappingFile mappingFile) {
		if (mappingFile.pluginName != null) {
			val contributorNames = mappingFile.imports.map[
				EclipseBridge.getNameOfContributorOfExtension(
					"org.eclipse.emf.ecore.generated_package",
					"uri", it.package.nsURI)].toSet
			
			val name = mappingFile.pluginName
			val project = new EclipseProjectHelper(name)
			project.reinitializeXtextPluginProject
			
			val srcGenFSA = project.srcGenFSA
			val rootFSA = project.rootFSA
			
			val generator = new StatefulMappingLanguageGenerator(mappingFile, srcGenFSA)
			generator.generate
			
			MappingPluginProjectHelper.createPluginXML(rootFSA, generator.change2CommandTransformingFQN)
			MappingPluginProjectHelper.createManifest(rootFSA, name, contributorNames, generator.getPkgNames)
			
			project.synchronizeProject
		}
	}
	
	private static class StatefulMappingLanguageGenerator {
		private static val LOGGER = Logger.getLogger(StatefulMappingLanguageGenerator)

		private MappingFile file
		private IFileSystemAccess fsa
		private List<Import> imports
		private extension EMFGeneratorHelper emfGeneratorHelper
		private extension NameProvider nameProvider
		
		private extension ConstraintLanguageGenerator clg
		
		private final String pkgName;

		new(MappingFile file, IFileSystemAccess fsa) {
			this.file = file
			this.pkgName = file.pluginName + ".generated"
			this.fsa = PreProcessingFileSystemAccess.createJavaFormattingFSA(fsa)
			this.nameProvider = new NameProvider(pkgName)
		}
		
		public def getPkgNames() {
			#[pkgName, pkgName + ".test"]
		}
		
		public def getChange2CommandTransformingFQN() {
			change2CommandTransformingClassName
		}

		public def generate() {
			// FIXME DW there are some dependencies on a valid file here that should be
			// checked before generating.
			// sort so dependencies are resolved before they are used			
			val mappings = file.mappings.sortWith [ m1, m2 |
				if(m1.requires.contains(m2)) ( 1 ) else ( -1)
			]

			this.imports = file.imports.claim[size == 2]
			
			this.emfGeneratorHelper = new EMFGeneratorHelper(constantsClassName)
			this.clg = new ConstraintLanguageGenerator(emfGeneratorHelper)

			for (mapping : mappings) {
				generateMapping(mapping)
			}

			generateChange2CommandTransforming
			generateTestClass
			
			emfGeneratorHelper.generateCode(fsa)
		}

		private static class NameProvider {
			new(String pkgName) {
				this.pkgName = pkgName
			}
			
			private var anonMappingIndex = 0
			private final String pkgName
			
			public def getMappingName(Mapping mapping) {
				if ((mapping.name == null) || (mapping.name.empty)) {
					mapping.name = mapping.nextAnonymousMappingName.toString
				}

				return mapping.name
			}

			public def nextAnonymousMappingName(Mapping mapping) {
				'''Mapping«anonMappingIndex++»'''.toString
			}

			public def getMappedCorrespondenceName(Mapping mapping) {
				'''«pkgName».«mapping.name»_MappedCorrespondence'''.toString
			}
			
			public def getHelperClassName(Mapping mapping) {
				'''«pkgName».«mapping.name»_Helper'''.toString
			}

			public def getWrapperName(Mapping mapping, Import imp) {
				'''«pkgName».«mapping.mappingName»_Wrapper_«imp.name»'''.toString
			}
			
			public def getCandidateClassName(Mapping mapping, Import imp) {
				'''«pkgName».«mapping.mappingName»_Candidate_«imp.name»'''.toString
			}
			
			public def getChange2CommandTransformingClassName() {
				'''«pkgName».MappingsChange2CommandTransforming'''.toString
			}
			
			public def getMappingClassName(Mapping mapping) {
				'''«pkgName».«mapping.name»_Mapping'''.toString
			}
			
			public def getConstantsClassName() {
				'''«pkgName».EMFWrapper'''.toString
			}
			
			public def getTestClassName() {
				'''«pkgName».test.TestBase'''.toString
			}
		}

		private def generateMapping(Mapping mapping) {
			for (imp : imports) {
				generateWrapperClass(mapping, imp)
			}
			generateMappedCorrespondenceClass(mapping)
			generateMappingClass(mapping)
		}
	
		private def generateMappingClass(Mapping mapping) {
			val fqn = mapping.getMappingClassName
			val className = fqn.toSimpleName
			
			val requires = mapping.requires.map[new Pair(it.mapping.mappedCorrespondenceName, name.toFirstUpper)]
			val mcn = mapping.mappedCorrespondenceName
			 
			fsa.generateJavaFile(fqn, [ extension ih |
				
				val flatElements = (mapping.signatures.map[it.elements].flatten).map[new Pair(it.type.instanceTypeName.typeRef, it.name)]
				
				'''
					import «change2CommandTransformingClassName».MappingPackage;

					public class «className» implements «typeRef(MIRMappingRealization)» {
						public final static «className» INSTANCE = new «className»();
						
						// Singleton
						private «className»() {}
						
						@Override
						public «typeRef(TransformationResult)» applyEChange(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard) {
							«typeRef(Collection)»<«typeRef(EObject)»> affectedObjects = «typeRef(MIRMappingHelper)».getAllAffectedObjects(eChange);
							MappingPackage change_pkg = «change2CommandTransformingClassName».inferPackage(affectedObjects);
							if (change_pkg == MappingPackage.UNDEFINED)
								throw new «typeRef(IllegalStateException)»("Could not infer package for change " + eChange.toString() + "(" + affectedObjects.toString() + ")");
							
							«mcn».Helper.setCI(«typeRef(JavaHelper)».requireType(blackboard.getCorrespondenceInstance(), «typeRef(MappedCorrespondenceInstance)».class));
							
							«FOR req : requires AFTER "\n"»
							«typeRef(Iterable)»<«req.first»> affected_«req.second» = «req.first».Helper.getAll();
							«ENDFOR»							
							«FOR el : flatElements»
							«typeRef(Iterable)»<«el.first»> affected_«el.second» = «typeRef(IterableExtensions)».filter(affectedObjects, «el.first».class);
							«ENDFOR»
							
							«typeRef(Iterable)»<«mcn»> candidates = «mcn».Factory.createAllCandidates(
								«FOR el : requires + flatElements SEPARATOR ", "»affected_«el.second»«ENDFOR»);
							
							«typeRef(TransformationResult)» result = new «typeRef(TransformationResult)»();
							
							for («mcn» candidate : candidates) {
								«FOR imp : imports»
								if (change_pkg == MappingPackage.«imp.name.toUpperCase»)
									candidate.repairFrom«imp.name.toFirstUpper»(result);
								«ENDFOR»
							}
							
							return result;
						}

						@Override
						public String getMappingID() {
							return "«className»";
						}
						
						private «typeRef(MIRUserInteracting)» userInteracting;
						public void setUserInteracting(«typeRef(MIRUserInteracting)» userInteracting) {
							this.userInteracting = userInteracting;
						}
						
						public «typeRef(MIRUserInteracting)» getUserInteracting() {
							return this.userInteracting;
						}
					}
				'''
			])
		}
	
		private def generateWrapperClass(Mapping parent, Import imp) {
			val signature = parent.signatures.claimExactlyOneInPackage(imp.package)
			val fqn = getWrapperName(parent, imp)
			val className = fqn.toSimpleName 

			fsa.generateJavaFile(fqn, [
				var elementIndex = 0;
				'''
					public class «className» {
						// Use builder for creating «className»
						// TODO remove check when using builder
						public «className»(«typeRef(List)»<«typeRef(EObject)»> elements) {
							if (elements.size() != «signature.elements.size») {
								throw new «typeRef(IllegalArgumentException)»("argument does not have «signature.elements.size» elements.");
							}
							
							«FOR id : signature.elements.withIndex»
							if ((elements.get(«id.first») == null) || !(elements.get(«id.first») instanceof «typeRef(id.second.type)»)) {
								throw new «typeRef(IllegalArgumentException)»("element at position «id.first» must be of type «id.second.type.instanceTypeName»");
							}
							«ENDFOR»
							
							// TODO make immutable
							this.elements = elements;
						}
						
						«FOR namedEClass : signature.elements»
							private static final int «namedEClass.name.toUpperCase»_INDEX = «elementIndex++»;
						«ENDFOR»
						
						«FOR namedEClass : signature.elements»
							public «typeRef(namedEClass.type)» get«namedEClass.name.toFirstUpper»() {
								return «typeRef(JavaHelper)».requireType(elements.get(«namedEClass.name.toUpperCase»_INDEX), «typeRef(namedEClass.type)».class);
							}
						«ENDFOR»
						
						private final «typeRef(List)»<«typeRef(EObject)»> elements;
						
						public «typeRef(List)»<«typeRef(EObject)»> getElements() { return this.elements; }
						
						public static class Builder {
							«FOR namedEClass : signature.elements»
							private «typeRef(namedEClass.type)» «namedEClass.name»;
							«ENDFOR»
							
							«FOR namedEClass : signature.elements»
								public Builder set«namedEClass.name.toFirstUpper»(«typeRef(namedEClass.type)» «namedEClass.name») {
									if (this.«namedEClass.name» != null)
										throw new «typeRef(IllegalStateException)»("«namedEClass.name» has already been set.");
									this.«namedEClass.name» = «namedEClass.name»;
									return this;
								}
							«ENDFOR»
								
							public «className» build() {
								«typeRef(List)»<«typeRef(EObject)»> elements = new «typeRef(ArrayList)»<«typeRef(EObject)»>();
								
								«FOR namedEClass : signature.elements»
									elements.add(«typeRef(Objects)».requireNonNull(this.«namedEClass.name»));
								«ENDFOR»
								
								return new «className»(elements);
							}
						}
					}
				'''
			])
		}
		
			
		private static def getTypesAndNames(ImportHelper ih, Signature signature) {
			signature.elements.map[new Pair(ih.typeRef(type.instanceTypeName), name.toFirstLower)]
		}

		private def generateMappedCorrespondenceClass(Mapping mapping) {
			// FIXME DW das stimmt noch nicht für alle möglichen "Weglassungskombinationen" von signatures/constraints
			val signatures = imports.map[mapping.signatures.claimExactlyOneInPackage(it.package)]
			val wrapperClasses = imports.map[getWrapperName(mapping, it).toString]
			val wrapperFields = imports.map[name.toFirstLower]
			val constraints = imports.map[mapping.constraintBlocks.filterWithPackage(it.package).claimOneOrNone]
			val stateNames = imports.map[name.toUpperCase]
			val mappingName = mapping.mappingClassName
			
			val fqn = mapping.mappedCorrespondenceName
			val className = fqn.toSimpleName
			
			val requires = mapping.requires
					.map[req | new Pair(req.mapping.mappedCorrespondenceName, req.name)]
					
			val packages = imports.map[name.toFirstUpper]
			
			val indices = #[0,1]
			val pairs = #[new Pair(0, 1), new Pair(1, 0)]
			val simplePairs = #[new Pair(0, 1)]
			
			
			fsa.generateJavaFile(fqn, [ extension ih |
				val classSignatureWithoutRequires = 
					zip(wrapperClasses, wrapperFields)
					+ #[new Pair(typeRef(Correspondence), "correspondence"), new Pair("State", "state")]
				val classSignature = requires
								+ classSignatureWithoutRequires
								
				'''
					public class «className» {
						private «className»(
							«FOR el : classSignature SEPARATOR ", "»
								«el.first» «el.second»
							«ENDFOR»
						) {
							«FOR el : classSignature»
							this.«el.second» = «el.second»;
							«ENDFOR»
							
							validateCorrespondence();
						}
						
						public void clone(«className» other) {
							«FOR req : requires AFTER "\n"»
							if (this.«req.second» != other.«req.second») {
								// TODO DW warning?
								throw new «typeRef(IllegalStateException)»("can not change parent mappings");
							}
							«ENDFOR»
							«FOR el : classSignatureWithoutRequires»
							this.«el.second» = other.«el.second»;
							«ENDFOR»
							
							validateCorrespondence();
						}
						
						
						
						public static enum State {
							UNDEFINED, «stateNames.join(", ")», MAPPED
						}
						
						private State state;
						
						// required mapped correspondences
						«FOR el : requires»
							private final «el.first» «el.second»;
						«ENDFOR»
						
						«FOR el : requires»
							public «el.first» get«el.second.toFirstUpper»() {
								return this.«el.second»;
							}
						«ENDFOR»
						// end required mapped correspondences
						
						private «typeRef(Correspondence)» correspondence;
						
						«FOR el : indices»
							private «wrapperClasses.get(el)» «wrapperFields.get(el)»;
						«ENDFOR»
						
						«FOR el : indices»
							private boolean stateHas«packages.get(el)»() {
								return ((state == State.«stateNames.get(el)») || (state == State.MAPPED));
							}
						«ENDFOR»
						
						private void validateCorrespondence() {
							if (!isValid()) {
								throw new «typeRef(IllegalStateException)»("inconsistent state: not valid");
							}
							
							if ((state == State.MAPPED) != (correspondence != null)) {
								throw new «typeRef(IllegalStateException)»("...");
							}
							
							«FOR el : indices»
								if (stateHas«packages.get(el)»() != («wrapperFields.get(el)» != null)) {
									throw new «typeRef(IllegalStateException)»("«packages.get(el)» not consistent with state " + state.toString());
								}
								
							«ENDFOR»
							
							if ((state == State.MAPPED)){
								// M0B_Wrapper_pcm resolvedPcmWrapper = ...;
								// M0B_Wrapper_uml resolvedUmlWrapper = ...;
								// falls unterschied: Fehler/Warning und setzen
							}
							
							// check parent validity. all required mappings must be set.
						}
						
						«FOR el : indices»
						public «wrapperClasses.get(el)» get«packages.get(el)»() {
							if (!stateHas«packages.get(el)»())
								throw new «typeRef(IllegalStateException)»("Cannot get «packages.get(el)» in state " + state.toString());
								
							validateCorrespondence();
							
							return this.«wrapperFields.get(el)»;
						}
						
						public void set«packages.get(el)»(«wrapperClasses.get(el)» new_«wrapperFields.get(el)») {
							validateCorrespondence();
							
							if (stateHas«packages.get(el)»())
								throw new «typeRef(IllegalStateException)»("Cannot set «packages.get(el)» in state " + state.toString());
								
							clone(Helper.create(
								«(requires.map[second] + indices.map[if (it == el) ('''new_«wrapperFields.get(it)»''') else ('''get«packages.get(it)»()''')]).join (", ")»
							));
						}
						«ENDFOR»
						
						«FOR el : pairs»
						public void unset«packages.get(el.first)»() {
							validateCorrespondence();
							if (!isMapped())
								throw new «typeRef(IllegalStateException)»("Cannot unset «packages.get(el.first)» in state " + state.toString());
								
							Helper.removeCorrespondence(correspondence);
							«typeRef(MIRMappingHelper)».removeAll(get«packages.get(el.first)»().getElements());
							this.correspondence = null;
							this.«wrapperFields.get(el.first)» = null;
							this.state = State.«stateNames.get(el.second)»;
						}
						«ENDFOR»
						
						public boolean isMapped() {
							validateCorrespondence();
							return (this.state == State.MAPPED);
						}
						
						public boolean isValid() {
							return («FOR req : requires»(this.«req.second» != null) &&«ENDFOR»
								(«FOR wf : wrapperFields SEPARATOR " || "»(this.«wf» != null)«ENDFOR»));
						}
						
						private void checkAllContainments(«typeRef(TransformationResult)» result) {
							«FOR id : indices»
							if (stateHas«packages.get(id)»()) {
								boolean nonContainedFound;
								do {
									nonContainedFound = false;
									for («typeRef(EObject)» eObject : get«packages.get(id)»().getElements()) {
										if (!hasContainment(eObject, result)) {
											// request new resource or infer it
											// add to result
											nonContainedFound = true;
											final «typeRef(VURI)» resourceVURI = «typeRef(VURI)».getInstance(«typeRef(mappingName)».INSTANCE.getUserInteracting().askForNewResource(«typeRef(EcoreHelper)».createSensibleString(eObject)));
											result.addRootEObjectToSave(eObject, resourceVURI);
										}
									}
								} while (nonContainedFound);
							}
							«ENDFOR»
						}
						
						// TODO api?
						private boolean hasContainment(«typeRef(EObject)» eObject, «typeRef(TransformationResult)» result) {
							return (eObject.eContainer() != null || eObject.eResource() != null || (result.getRootEObjectsToSave().stream().anyMatch(it -> it.getFirst().equals(eObject))));
						}
						
						«FOR el : pairs»
						public void repairFrom«packages.get(el.first)»(«typeRef(TransformationResult)» result) {
							if (!stateHas«packages.get(el.first)»())
								throw new «typeRef(IllegalStateException)»("Cannot repair from «packages.get(el.first)» in state " + state.toString());
								
							boolean nowMapped = checkConstraintsFor«packages.get(el.first)»();
							
							if (!isMapped() && nowMapped) {
								createCorresponding«packages.get(el.second)»From«packages.get(el.first)»();
								checkAllContainments(result);
							} else if (isMapped() && !nowMapped) {
								«typeRef(List)»<«typeRef(Resource)»> resources = «typeRef(MIRMappingHelper)».getResources(get«packages.get(el.first)»().getElements());
								unset«packages.get(el.second)»();
								«typeRef(MIRMappingHelper)».addEmptyResourcesToTransformationResult(resources, result);
							}
							
							if (nowMapped) {
								restorePostConditionsFrom«packages.get(el.first)»();
							}
						}
						
						public void restorePostConditionsFrom«packages.get(el.first)»() {
							if (!stateHas«packages.get(el.first)»())
								throw new «typeRef(IllegalStateException)»("Cannot restore post conditions from «packages.get(el.first)» in state " + state.toString() + " (must be MAPPED)");
							
							«IF mapping.constraintsBody != null»
								«FOR constraint : mapping.constraintsBody.expressions»
									«restoreBodyConstraintFrom(ih, #{'''MCI_«mapping.name»''' -> "this"}, constraint, imports.get(el.first).package)»
								«ENDFOR»
							«ELSE»
								// no post conditions, ignore
							«ENDIF»
						}
						
						public boolean checkConstraintsFor«packages.get(el.first)»() {
							«IF constraints.get(el.first).present»
								«FOR expression : constraints.get(el.first).get.expressions»
									if (!«checkSignatureConstraint(ih, #{'''MCI_«mapping.name»''' -> "this"}, expression)»)
										return false;
								«ENDFOR»
							«ENDIF»
							
							return true;
						}
						
						public void createCorresponding«packages.get(el.second)»From«packages.get(el.first)»() {
							if (state != State.«stateNames.get(el.first)»)
								throw new «typeRef(IllegalStateException)»("State must be «stateNames.get(el.first)»");
								
							«FOR namedEClass : signatures.get(el.second).elements»
								«typeRef(namedEClass.type)» new_«namedEClass.name» = «eCreate(ih, namedEClass.type)»;
							«ENDFOR»
							
							set«packages.get(el.second)»(
								(new «wrapperClasses.get(el.second)».Builder())
									«FOR namedEClass : signatures.get(el.second).elements»
										.set«namedEClass.name.toFirstUpper»(new_«namedEClass.name»)
									«ENDFOR»
									.build());
							
							«IF constraints.get(el.second).present»
								«FOR constraint : constraints.get(el.second).get.expressions»
									«enforceSignatureConstraint(ih, #{'''MCI_«mapping.name»''' -> "this"}, constraint)»;
								«ENDFOR»
							«ELSE»
								// no constraints for «imports.get(el.second).name»
							«ENDIF»
						}
						
						«ENDFOR»
						
						
						public static class Helper {
							private static final «typeRef(MIRMappingRealization)» MAPPING = «mapping.mappingClassName».INSTANCE;
							private static «typeRef(MappedCorrespondenceInstance)» mci;
							
							private Helper() {}
							
							public static void setCI(MappedCorrespondenceInstance mci) {
								if (Helper.mci != null) {
									// warning
								}
								
								Helper.mci = mci;
							}
							
							public static «typeRef(Set)»<«className»> getAll() {
								return mci.getCorrespondencesForMapping(MAPPING).stream().map(it -> get(it)).collect(«typeRef(Collectors)».toSet());
							}
							
							«FOR pair : pairs»
							/**
							 * @Nullable...
							 */
							public static «className» getExistingFor«packages.get(pair.first)»(«wrapperClasses.get(pair.first)» «wrapperFields.get(pair.first)») {
								«typeRef(Correspondence)» stc = mci.getMappedCorrespondence(«wrapperFields.get(pair.first)».getElements(), MAPPING);
								if (stc == null) {
									return null;
								} else {
									«typeRef(List)»<«typeRef(EObject)»> opposite = «typeRef(MappedCorrespondenceInstance)».getOpposite(stc, «wrapperFields.get(pair.first)».getElements());
									«wrapperClasses.get(pair.second)» «wrapperFields.get(pair.second)» = new «wrapperClasses.get(pair.second)»(opposite);
									
									return new «className»(
										«FOR req : requires»/*resolve «req.second»*/null, «ENDFOR»
										«FOR wf : wrapperFields»«wf», «ENDFOR»
										stc, State.MAPPED);
								}
							}
							
							public static «className» createHalfMappingFor«packages.get(pair.first)»(
								«FOR req : requires»«req.first» «req.second», «ENDFOR»
								«wrapperClasses.get(pair.first)» «wrapperFields.get(pair.first)») {
								// assert getExistingFor«packages.get(pair.first)» == null
								
								return new «className»(
									«FOR req : requires»«req.second», «ENDFOR»
									«FOR wf : wrapperFields.withIndex.map[if (it.first == pair.first) it.second else "null"]»«wf», «ENDFOR»
									null, State.«stateNames.get(pair.first)»);
							}
							
							public static «className» getExistingOrHalfFor«packages.get(pair.first)»(
									«FOR req : requires»«req.first» «req.second», «ENDFOR»
									«wrapperClasses.get(pair.first)» «wrapperFields.get(pair.first)») {
								«className» existing = getExistingFor«packages.get(pair.first)»(«wrapperFields.get(pair.first)»);
								if (existing != null) {
									return existing;
								} else {
									return createHalfMappingFor«packages.get(pair.first)»(«FOR req : requires»«req.second», «ENDFOR»«wrapperFields.get(pair.first)»);
								}
							}
							«ENDFOR»
							
							public static «className» get(Correspondence stc) {
								«FOR req : requires»
								«req.first» «req.second» = null; // wrap parent
								«ENDFOR»
								«FOR id : indices»
								«wrapperClasses.get(id)» «wrapperFields.get(id)» = null; // wrap
								«ENDFOR»
								
								return new «className»(
									«FOR el : requires.map[second] + wrapperFields»«el», «ENDFOR»
									stc, State.MAPPED);
							}
							
							««« TODO hardcoded indices
							/**
							 * @Nullable ...
							 */
							public static «className» getExisting(«FOR arg : zip(wrapperClasses, wrapperFields) SEPARATOR ", "»«arg.first» «arg.second»«ENDFOR») {
								«className» claimedMC = getExistingFor«packages.get(0)»(«wrapperFields.get(0)»);
								if (claimedMC.get«packages.get(1)»() == «wrapperFields.get(1)»)
									return claimedMC;
								else
									return null;
							}
							
							// claim
							// create (w/ explicit claim that doesn't exist)
							//   val new = create(pcm)
							//   new.setUml(uml);
							public static «className» create(«FOR arg : requires + zip(wrapperClasses, wrapperFields) SEPARATOR ", "»«arg.first» «arg.second»«ENDFOR») {
								// claim getExisting(«FOR wf : wrapperFields SEPARATOR ", "»«wf»«ENDFOR») == null
								«typeRef(Correspondence)» stc = mci.createAndAddCorrespondence(«FOR arg : wrapperFields SEPARATOR ", "»«arg».getElements()«ENDFOR»); // wrap ...
								mci.registerMappingForCorrespondence(stc, MAPPING);
								// create "parents"
								return new «className»(
										«FOR el : requires.map[second] + wrapperFields»«el», «ENDFOR»
										stc, State.MAPPED);
							}
							
							public static void removeCorrespondence(Correspondence correspondence) {
								«typeRef(EcoreUtil)».remove(correspondence); // ???
							}
							
						}
						
						public static class Factory {
							public static «typeRef(Iterable)»<«className»> createAllCandidates(
								«FOR el :
									((requires)
									+ (signatures.map[elements].flatten).map[new Pair(it.type.instanceTypeName.typeRef, it.name)])
								SEPARATOR ", "»
									«typeRef(Iterable)»<«el.first»> «el.second»
								«ENDFOR»
							) {
								// create cross product
								«typeRef(Set)»<«className»> result = new «typeRef(HashSet)»<«className»>();
								
								«FOR req : requires»
								for («req.first» «req.second»_it : «req.second») {
								«ENDFOR»
								
								«FOR id : indices»
									«FOR el : getTypesAndNames(ih, signatures.get(id)) SEPARATOR "\n"»
									for («el.first» «el.second»_it : «el.second») {
									«ENDFOR»

									«wrapperClasses.get(id)» «wrapperFields.get(id)» = new «wrapperClasses.get(id)».Builder()
										«FOR el : getTypesAndNames(ih, signatures.get(id))»
											.set«el.second.toFirstUpper»(«el.second»_it)
										«ENDFOR»
										.build();
										
									result.add(new Builder()
										«FOR el : requires.map[new Pair(second.toFirstUpper, second + "_it")] + #[new Pair(packages.get(id), wrapperFields.get(id))]»
											.set«el.first»(«el.second»)
										«ENDFOR»
										.build());

									«FOR el : signatures.get(id).elements»
									}
									«ENDFOR»
								«ENDFOR»
								«FOR req : requires»
								}
								«ENDFOR»
								
								return result;
								}
						}
						
						public static class Builder {
							«FOR req : requires + zip(wrapperClasses, wrapperFields) AFTER "\n"»
							private «req.first» «req.second»;
							«ENDFOR»
							«FOR el : requires»
								public Builder set«el.second.toFirstUpper»(«el.first» «el.second») {
									if (this.«el.second» != null)
										throw new «typeRef(IllegalStateException)»("«el.second» has already been set.");
									this.«el.second» = «el.second»;
									return this;
								}
							«ENDFOR»
							
							«FOR id : indices»
								public Builder set«packages.get(id)»(«wrapperClasses.get(id)» «wrapperFields.get(id)») {
									if (this.«wrapperFields.get(id)» != null)
										throw new «typeRef(IllegalStateException)»("«wrapperFields.get(id)» has already been set.");
										
									this.«wrapperFields.get(id)» = «wrapperFields.get(id)»;
									return this; 
								}
							«ENDFOR»
								
							public «className» build() {
								«FOR el : requires AFTER "\n"»
									if (this.«el.second» == null)
										throw new «typeRef(IllegalStateException)»("«el.second» has not been set.");
								«ENDFOR»
								if («FOR wf : wrapperFields SEPARATOR " && "»(this.«wf» != null)«ENDFOR»)
									return Helper.create(«FOR el : requires.map[second] + wrapperFields SEPARATOR  ", "»«el»«ENDFOR»);
								
								«FOR id : indices SEPARATOR "\n else "»
								if (this.«wrapperFields.get(id)» != null)
									return Helper.getExistingOrHalfFor«packages.get(id)»(
										«FOR req : requires SEPARATOR ", " AFTER ", "»«req.second»«ENDFOR»
										this.«wrapperFields.get(id)»
									);
								«ENDFOR»
								
								throw new «typeRef(IllegalStateException)»();
							}
						}
						
					}
				'''
			])
		}
		
		def generateTestClass() {
			val fqn = getTestClassName
			val className = fqn.toSimpleName
			
			fsa.generateJavaFile(fqn, [
				// TODO DW resolve cycle and reference by type
				'''
					public class «className» extends «typeRef("edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests.AbstractMappingTestBase")» {
						@Override
						protected String getPluginName() {
							return "«file.pluginName»";
						}
						
						@Override
						protected «typeRef(Collection)»<«typeRef(Pair)»<String, String>> getMetamodelURIsAndExtensions() {
							«typeRef(Set)»<«typeRef(Pair)»<String, String>> result = new «typeRef(HashSet)»<>();
							«FOR id : imports»
								result.add(new «typeRef(Pair)»<>("«id.package.nsURI»", "«id.name»"));
							«ENDFOR»
							
							return result;
						}
						
						@Override
						protected Class<? extends «typeRef(AbstractMappingChange2CommandTransforming)»> getChange2CommandTransformingClass() {
							return «typeRef(change2CommandTransformingClassName)».class;
						}
					}
				'''
			])
		}
		
		def generateChange2CommandTransforming() {
			val fqn = getChange2CommandTransformingClassName
			val className = fqn.toSimpleName
			
			val packages = imports.map[name.toFirstUpper]
			
			fsa.generateJavaFile(fqn, [
				'''
					public class «className» extends «typeRef(AbstractMappingChange2CommandTransforming)» {
							«FOR id : imports»
							public final static String MM_«id.name.toUpperCase» = "«id.package.nsURI»";
							«ENDFOR»
							
							«FOR id : imports.map[name.toUpperCase]»
							public final static «typeRef(VURI)» VURI_«id» = «typeRef(VURI)».getInstance(MM_«id»);
							«ENDFOR»
							
						    /* Transformable metamodels. */
							private «typeRef(List)»<«typeRef(Pair)»<«typeRef(VURI)», «typeRef(VURI)»>> transformableMetamodels;
							
							@Override
							public «typeRef(List)»<«typeRef(Pair)»<«typeRef(VURI)», «typeRef(VURI)»>> getTransformableMetamodels() {
								return transformableMetamodels;
							}
							
							@Override
							protected void setup() {
								transformableMetamodels = new «typeRef(ArrayList)»<>();
								
								«FOR id : #[new Pair(0, 1), new Pair(1, 0)]»
								transformableMetamodels.add(new «typeRef(Pair)»<>(
									VURI_«imports.get(id.first).name.toUpperCase», VURI_«imports.get(id.second).name.toUpperCase»
								));
								«ENDFOR»
								
								«FOR mapping : file.mappings»
								addMapping(«mapping.getMappingClassName».INSTANCE);
								«ENDFOR»
							}
							
							@Override
							public void setUserInteracting(«typeRef(MIRUserInteracting)» userInteracting) {
								«FOR mapping : file.mappings»
								«mapping.getMappingClassName».INSTANCE.setUserInteracting(userInteracting);
								«ENDFOR»
							}
							
							public enum MappingPackage {
								UNDEFINED, «imports.map[name.toUpperCase].join(", ")»
							}
							
							public static MappingPackage inferPackage(«typeRef(Collection)»<«typeRef(EObject)»> objects) {
								«typeRef(Optional)»<«typeRef(EPackage)»> eObjectPackage = objects.stream().map(it -> it.eClass().getEPackage()).collect(«typeRef(JavaHelper)».identicalElementCollector());
								if (!eObjectPackage.isPresent())
									return MappingPackage.UNDEFINED;
								«FOR id : imports.withIndex»
								else if (eObjectPackage.get().getNsURI().equals(MM_«id.second.name.toUpperCase»))
									return MappingPackage.«id.second.name.toUpperCase»;
								«ENDFOR»
								
								return MappingPackage.UNDEFINED;
							}
					}
				'''
			])
		}
	}
}