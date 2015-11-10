package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.formatting.PreProcessingFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Objects
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import java.util.stream.Collectors
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper
import java.util.Collection
import java.util.Optional
import org.eclipse.emf.ecore.EPackage

class MappingLanguageGenerator implements IGenerator {
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		val mappingFile = input.contents.filter(MappingFile).claimExactlyOne;

		(new StatefulMappingLanguageGenerator(mappingFile, fsa)).generate()
	}
	
	private static class StatefulMappingLanguageGenerator {
		private static val LOGGER = Logger.getLogger(StatefulMappingLanguageGenerator)

		private MappingFile file
		private IFileSystemAccess fsa
		private List<Import> imports
		private extension EMFGeneratorHelper emfGeneratorHelper
		private extension NameProvider nameProvider
		
		private extension ConstraintLanguageGenerator clg

		private static val WRAPPER_PACKAGE = "wrappers"
		private static val CANDIDATE_PACKAGE = "wrappers"
		private static val MAPPED_CORRESPONDENCE_PACKAGE = "wrappers"
		private static val C2CTRANSFORMING_PACKAGE = "wrappers"
		
		private static val VURI_ONE_FIELD_NAME = "VURI_ONE"
		private static val VURI_TWO_FIELD_NAME = "VURI_TWO"
		private static val MM_ONE_FIELD_NAME = "MM_ONE"
		private static val MM_TWO_FIELD_NAME = "MM_TWO"

		new(MappingFile file, IFileSystemAccess fsa) {
			this.file = file
			this.fsa = PreProcessingFileSystemAccess.createJavaFormattingFSA(fsa)
			this.nameProvider = new NameProvider()
		}

		public def generate() {
			// FIXME DW there are some dependencies on a valid file here that should be
			// checked before generating.
			// sort so dependencies are resolved before they are used			
			val mappings = file.mappings.sortWith [ m1, m2 |
				if(m1.requires.contains(m2)) ( 1 ) else ( -1)
			]

			this.imports = file.imports.claim[size == 2]
			
			this.emfGeneratorHelper = new EMFGeneratorHelper("constants.EMFWrapper")
			this.clg = new ConstraintLanguageGenerator(emfGeneratorHelper)

			for (mapping : mappings) {
				generateMapping(mapping)
			}

			generateChange2CommandTransforming
			
			emfGeneratorHelper.generateCode(fsa)
		}

		private static class NameProvider {
			private var anonMappingIndex = 0

			public def getMappingName(Mapping mapping) {
				if ((mapping.name == null) || (mapping.name.empty)) {
					mapping.name = mapping.nextAnonymousMappingName.toString
				}

				return mapping.name
			}

			public def nextAnonymousMappingName(Mapping mapping) {
				'''Mapping«anonMappingIndex++»'''
			}

			public def getMappedCorrespondenceName(Mapping mapping) {
				'''«mapping.name»_MappedCorrespondence'''
			}
			
			public def getHelperClassName(Mapping mapping) {
				'''«mapping.name»_Helper'''
			}

			public def getWrapperName(Mapping mapping, Import imp) {
				'''«mapping.mappingName»_Wrapper_«imp.name»'''
			}
			
			public def getCandidateClassName(Mapping mapping, Import imp) {
				'''«mapping.mappingName»_Candidate_«imp.name»'''
			}
			
			public def getChange2CommandTransformingClassName() {
				'''MappingsChange2CommandTransforming'''
			}
			
			public def getMappingClassName(Mapping mapping) {
				'''«mapping.name»_Mapping'''
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
			val className = mapping.getMappingClassName
			val fqn = WRAPPER_PACKAGE + "." + className
			
			val requires = mapping.requires.map[new Pair(mappedCorrespondenceName, name.toFirstUpper)]
			val mcn = mapping.mappedCorrespondenceName
			 
			fsa.generateJavaFile(fqn, [ extension ih |
				
				val flatElements = (mapping.signatures.map[it.elements].flatten).map[new Pair(it.type.instanceTypeName.typeRef, it.name)]
				
				'''
					import «C2CTRANSFORMING_PACKAGE».«change2CommandTransformingClassName».MappingPackage;

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
					}
				'''
			])
		}
	
		private def generateWrapperClass(Mapping parent, Import imp) {
			val otherImport = imports.filter[it != imp].claimExactlyOne
			val otherWrapperClass = getWrapperName(parent, otherImport)
			
			val signature = parent.signatures.claimExactlyOneInPackage(imp.package)
			val className = getWrapperName(parent, imp)
			val fqn = WRAPPER_PACKAGE + "." + className

			fsa.generateJavaFile(fqn, [
				var elementIndex = 0;
				'''
					public class «className» {
						// Use builder for creating «className»
						private «className»(«typeRef(List)»<«typeRef(EObject)»> elements) {
							// TODO make immutable
							this.elements = elements;
						}
						
						«FOR namedEClass : signature.elements»
							private static final int «namedEClass.name.toUpperCase»_INDEX = «elementIndex++»;
						«ENDFOR»
						
						«FOR namedEClass : signature.elements»
							public «typeRef(namedEClass.type)» get«namedEClass.name.toFirstUpper»() {
								/* return get(«namedEClass.name.toUpperCase»_INDEX); */
								return null;
							}
						«ENDFOR»
						
						private «typeRef(List)»<«typeRef(EObject)»> elements;
						
						public «typeRef(List)»<«typeRef(EObject)»> getElements() { return this.elements; }
						
						/*public «parent.mappedCorrespondenceName» getMappedCorrespondence() {
							return «parent.mappedCorrespondenceName».claimOneFor«imp.name.toFirstUpper»(this);
						}
						
						public «parent.mappedCorrespondenceName» claimMappedCorrespondence() {
							return «typeRef(Objects)».requireNonNull(getMappedCorrespondence());
						}
						
						// move to API ?
						public «otherWrapperClass» getCorresponding() {
							return getMappedCorrespondence().get«otherImport.name.toFirstUpper»();
						}
						
						// move to API ?
						public «otherWrapperClass» claimCorresponding() {
							return claimMappedCorrespondence().get«otherImport.name.toFirstUpper»();
						}
						
						// move to API
						public boolean isMapped() {
							return (getMappedCorrespondence() != null);
						}*/
						
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
			
			val className = mapping.mappedCorrespondenceName
			val fqn = MAPPED_CORRESPONDENCE_PACKAGE + "." + className
			
			val requires = mapping.requires
					.map[req | new Pair(req.mappedCorrespondenceName, req.name)]
					
			val packages = imports.map[name.toFirstUpper]
			
			val indices = #[0,1]
			val pairs = #[new Pair(0, 1), new Pair(1, 0)]
			val simplePairs = #[new Pair(0, 1)]
			
			
			fsa.generateJavaFile(fqn, [ extension ih |
				val classSignatureWithoutRequires = 
					zip(wrapperClasses, wrapperFields)
					+ #[new Pair(typeRef(SameTypeCorrespondence), "correspondence"), new Pair("State", "state")]
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
						
						private «typeRef(SameTypeCorrespondence)» correspondence;
						
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
							
							if ((state == State.MAPPED) != (correspondence == null)) {
								throw new «typeRef(IllegalStateException)»("...");
							}
							
							«FOR el : indices»
								if (stateHas«packages.get(el)»() != («wrapperFields.get(el)» == null)) {
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
						
						private void destroy«packages.get(el)»() {
							for («typeRef(EObject)» eObject : get«packages.get(el)»().getElements()) {
								«typeRef(EcoreUtil)».remove(eObject);
							}
						}
						«ENDFOR»
						
						«FOR el : pairs»
						public void unset«packages.get(el.first)»() {
							validateCorrespondence();
							if (!isMapped())
								throw new «typeRef(IllegalStateException)»("Cannot unset «packages.get(el.first)» in state " + state.toString());
								
							Helper.removeCorrespondence(correspondence);
							destroy«packages.get(el.first)»();
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
										if (eObject.eContainer() == null) {
											// request new resource or infer it
											// add to result
											nonContainedFound = true;
											throw new «typeRef(UnsupportedOperationException)»();
										}
									}
								} while (nonContainedFound);
							}
							«ENDFOR»
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
								«typeRef(SameTypeCorrespondence)» stc = mci.getMappedCorrespondence(/*unwrap(«wrapperFields.get(pair.first)»)*/null, MAPPING);
								if (stc == null) {
									return null;
								} else {
									«wrapperClasses.get(pair.second)» «wrapperFields.get(pair.second)» = null; /* wrap other side */
									
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
							«ENDFOR»
							
							public static «className» get(SameTypeCorrespondence stc) {
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
								«typeRef(SameTypeCorrespondence)» stc = mci.createAndAddEObjectCorrespondence(null, null); // wrap ...
								mci.registerMappingForCorrespondence(stc, MAPPING);
								return new «className»(
										«FOR el : requires.map[second] + wrapperFields»«el», «ENDFOR»
										stc, State.MAPPED);
							}
							
							public static void removeCorrespondence(SameTypeCorrespondence correspondence) {
								EcoreUtil.remove(correspondence); // ???
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
									return Helper.getExistingFor«packages.get(id)»(this.«wrapperFields.get(id)»);
								«ENDFOR»
								
								throw new «typeRef(IllegalStateException)»();
							}
						}
						
					}
				'''
			])
		}
		
		def generateChange2CommandTransforming() {
			val className = getChange2CommandTransformingClassName
			val fqn = C2CTRANSFORMING_PACKAGE + "." + className
			
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
							private final «typeRef(List)»<«typeRef(Pair)»<«typeRef(VURI)», «typeRef(VURI)»>> transformableMetamodels = new «typeRef(ArrayList)»<>();
							
							@Override
							public «typeRef(List)»<«typeRef(Pair)»<«typeRef(VURI)», «typeRef(VURI)»>> getTransformableMetamodels() {
								return transformableMetamodels;
							}
							
							@Override
							protected void setup() {
								«FOR id : #[new Pair(0, 1), new Pair(1, 0)]»
								transformableMetamodels.add(new «typeRef(Pair)»<>(
									VURI_«imports.get(id.first).name.toUpperCase», VURI_«imports.get(id.second).name.toUpperCase»
								));
								«ENDFOR»
								
								«FOR mapping : file.mappings»
								addMapping(«mapping.getMappingClassName».INSTANCE);
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