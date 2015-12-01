package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingPluginProjectHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintBlock
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.NamedEClass
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.SignatureConstraintBlock
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseProjectHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.PreProcessingFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Objects
import java.util.Optional
import java.util.Set
import java.util.stream.Collectors
import javax.naming.OperationNotSupportedException
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression

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
		private extension MappingLanguageGeneratorNameProvider nameProvider
		
		private extension ConstraintLanguageGenerator clg
		
		private final String pkgName;

		new(MappingFile file, IFileSystemAccess fsa) {
			this.file = file
			this.pkgName = file.pluginName + ".generated"
			this.fsa = PreProcessingFileSystemAccess.createJavaFormattingFSA(fsa)
			this.nameProvider = new MappingLanguageGeneratorNameProvider(pkgName)
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



		private def generateMapping(Mapping mapping) {
			if (mapping.^default) {
				generateDefaultMapping(mapping)
			} else {
				for (signature : mapping.signatures) {
					val imp = mapping.mapSignatureToImport.get(signature)
					generateWrapperClass(mapping, imp, signature.elements)
				}
				generateMappedCorrespondenceClass(mapping)
				generateMappingClass(mapping)
			}
		}
		
		private def generateDefaultMapping(Mapping mapping) {
			if (!mapping.^default) {
				throw new IllegalArgumentException("Mapping is not default mapping.")
			}
			
			// get to a structure of the form { pkg1 => (all triples of pkg1/signature/constraintBlock for pkg1), pkg2 => ..., ... }
			val pkgs = mapping.inferPackagesForSignaturesAndConstraints
				.zipAny(mapping.signatures, mapping.constraints)
				.groupBy[first]
			
			
			// get to a structure of the form { pkg1 => (ONE triples of import/signatureElements/constraints for pkg1), pkg2 => ..., ... }	
			val reducedPkgs = newHashMap
			
			val wrapperImports = new ArrayList<Import>()
			for (ePackage : pkgs.keySet) {
				val value = pkgs.get(ePackage)
				val elements = reduceSignatures(value)
				val constraints = reduceContraintBlocks(value)
				val imp = imports.findFirst[package.equals(ePackage)]
				if (imp != null) {
					generateWrapperClass(mapping, imp, elements)
					wrapperImports.add(imp)
					
					reducedPkgs.put(ePackage, new Triple(imp, elements, constraints))
				}
			}
			
			val defaultContainments = newHashMap
			
			for (imp : imports) {
				if (pkgs.containsKey(imp.package)) {
					defaultContainments.put(imp, pkgs.get(imp.package).map[third?.expressions ?: #[]].flatten.filter(DefaultContainExpression))
				}
			}
			
			val fqnMapping = mapping.getMappingClassName
			val classNameMapping = fqnMapping.toSimpleName
			
			val fqnMC = mapping.mappedCorrespondenceName
			val classNameMC = fqnMC.toSimpleName
			
			// generate mapping class
			fsa.generateJavaFile(fqnMapping, [ extension ih |
				'''
					public class «classNameMapping» implements «typeRef(MIRMappingRealization)» {
						public final static «classNameMapping» INSTANCE = new «classNameMapping»();
						
						@Override
						public «typeRef(TransformationResult)» applyEChange(«typeRef(EChange)» eChange, «typeRef(Blackboard)» blackboard) {
							// create if not already existant
							«typeRef(TransformationResult)» result = new «typeRef(TransformationResult)»();
							
							if (!«fqnMC».Helper.exists()) {
								«fqnMC».Helper.createDefaultMapping(result);
							}
							
							return result;
						}
						
						@Override
						public String getMappingID() {
							return "«classNameMapping»";
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

			// generate mapped correspondence
			fsa.generateJavaFile(fqnMC, [ extension ih |
				'''
					public class «classNameMC» {
						«FOR wi : wrapperImports»
							private final «getWrapperName(mapping, wi)» «wi.wrapperFieldName»;
						«ENDFOR»
						
						private «classNameMC»(«FOR wi : wrapperImports SEPARATOR ", "»«getWrapperName(mapping, wi)» «wi.wrapperFieldName»«ENDFOR») {
							«FOR wi : wrapperImports»
								this.«wi.wrapperFieldName» = «wi.wrapperFieldName»;
							«ENDFOR»
						}
						
						«FOR wi : wrapperImports»
							public «getWrapperName(mapping, wi)» get«wi.toFirstUpperName»() {
								return this.«wi.wrapperFieldName»;
							}
						«ENDFOR»
						
						public static class Helper {
							private static final «typeRef(MIRMappingRealization)» MAPPING = «classNameMapping».INSTANCE;
							private static «typeRef(MappedCorrespondenceInstance)» mci;
							
							/*
							todo is this mechanism needed?
							private static Map<EObject, List<TUID>> oldTUIDMap = new HashMap<EObject, List<TUID>>();

							public static void addObjectToUpdate(EObject eObject) {
								if (!oldTUIDMap.containsKey(eObject))
									oldTUIDMap.put(eObject, new ArrayList<TUID>());
								oldTUIDMap.get(eObject).add(mci.calculateTUIDFromEObject(eObject));
							}
							
							public static void updateObject(EObject eObject) {
								if (!oldTUIDMap.containsKey(eObject)) {
									LOGGER.info("EObject " + eObject.toString() + " not in old tuid map");
								} else {
									for (TUID tuid : oldTUIDMap.get(eObject)) {
										mci.updateTUID(tuid, eObject);
									}
									oldTUIDMap.remove(eObject);
								}
							}
							*/
							
							private Helper() {}
							
							public static void setCI(MappedCorrespondenceInstance mci) {
								if (Helper.mci != null) {
									// warning
								}
								
								Helper.mci = mci;
							}
							
							public static <T extends «typeRef(EObject)»> T reload(T eObject) {
								return (T) mci.resolveEObjectFromTUID(mci.calculateTUIDFromEObject(eObject));
							}
							
							public static «typeRef(Set)»<«classNameMC»> getAll() {
								return mci.getCorrespondencesForMapping(MAPPING).stream().map(it -> get(it)).collect(«typeRef(Collectors)».toSet());
							}
							
							private static «classNameMC» get(«typeRef(Correspondence)» stc) {
								«IF wrapperImports.size > 2»throw new «typeRef(IllegalArgumentException)»("more than two wrapper classes!");«ENDIF»
								
								«FOR wi : wrapperImports.map[new Pair(it, #["A", "B"].get(imports.indexOf(it)))]»
								«getWrapperName(mapping, wi.first)» «wi.first.wrapperFieldName» = new «getWrapperName(mapping, wi.first)»(stc.get«wi.second»s());
								«ENDFOR»
								
								return new «classNameMC»(
									«FOR wi : wrapperImports SEPARATOR ", "»«wi.wrapperFieldName»«ENDFOR»);
							}
							
							public static boolean exists() {
								«typeRef(Set)»<«classNameMC»> mcs = getAll();
								return (mcs.size() > 0);
							}
							
							public static «classNameMC» get() {
								«typeRef(Set)»<«classNameMC»> mcs = getAll();
								if (mcs.size() == 0)
									throw new «typeRef(IllegalStateException)»("Default mapping for «classNameMC» could not be created.");
								
								if (mcs.size() > 1)
									throw new «typeRef(IllegalStateException)»("There is more than one default mapping present for «classNameMC»");
									
								return mcs.iterator().next();
							}
							
							private static void checkAllContainments(«typeRef(TransformationResult)» result) {
								«FOR imp : wrapperImports»
									«FOR defaultContainExpression : defaultContainments.get(imp) ?: #[] SEPARATOR ";"»
									«clg.checkAndCreateDefaultContainment(ih, #{'this' -> 'get()'}, defaultContainExpression)»
									«ENDFOR»
								«ENDFOR»
									
								boolean nonContainedFound;
								do {
									nonContainedFound = false;
									«FOR imp : wrapperImports»
									for («typeRef(EObject)» eObject : get().get«imp.toFirstUpperName»().getElements()) {
										if (!«typeRef(MIRMappingHelper)».hasContainment(eObject, result)) {
											// request new resource or infer it
											// add to result
											nonContainedFound = true;
											final «typeRef(VURI)» resourceVURI = «typeRef(VURI)».getInstance(«typeRef(fqnMapping)».INSTANCE.getUserInteracting().askForNewResource(«typeRef(EcoreHelper)».createSensibleString(eObject)));
											result.addRootEObjectToSave(eObject, resourceVURI);
										}
									}
									«ENDFOR»
								} while (nonContainedFound);
							}
							
							public static void createDefaultMapping(«typeRef(TransformationResult)» result) {
								«FOR triple : reducedPkgs.values»
									«FOR namedEClass : triple.second ?: #[]»
										«typeRef(namedEClass.type)» new_«namedEClass.name» = «eCreate(ih, namedEClass.type)»;
									«ENDFOR»
									
									«getWrapperName(mapping, triple.first)» wrap«triple.first.name.toFirstUpper» = new «getWrapperName(mapping, triple.first)».Builder()
										«FOR namedEClass : triple.second ?: #[]»
											.set«namedEClass.name.toFirstUpper»(new_«namedEClass.name»)
										«ENDFOR»
										.build();
								
									«IF !(triple.third ?: #[]).empty»
										«FOR constraint : (triple.third ?: #[])»
											«establishSignatureConstraintOnCreate(ih, #{triple.first -> '''wrap«triple.first.name.toFirstUpper»'''}, constraint)»;
										«ENDFOR»
									«ELSE»
										// no constraints for «triple.first.name»
									«ENDIF»
								«ENDFOR»
								
								«typeRef(Correspondence)» stc = mci.createAndAddCorrespondence(«FOR imp : imports SEPARATOR ", "»
									«IF reducedPkgs.containsKey(imp.package)»wrap«imp.name.toFirstUpper».getElements()
									«ELSE»«typeRef(Collections)».emptyList()
									«ENDIF»
								«ENDFOR»);
								mci.registerMappingForCorrespondence(stc, MAPPING);
								
								checkAllContainments(result);
							}
						}
					}
				'''
			])
		}
	
		def reduceContraintBlocks(List<Triple<EPackage, Signature, SignatureConstraintBlock>> input) {
			val result = new ArrayList<ConstraintExpression>
			input.forEach[result.addAll(it.third.expressions)]
			return result
		}
		
		def reduceSignatures(List<Triple<EPackage, Signature, SignatureConstraintBlock>> input) {
			val result = new ArrayList<NamedEClass>
			input.forEach[result.addAll(it.second.elements)]
			return result
		}
	
		private def generateMappingClass(Mapping mapping) {
			if (mapping.^default) {
				throw new IllegalArgumentException("Mapping is default mapping.")
			}
			
			val fqn = mapping.getMappingClassName
			val className = fqn.toSimpleName
			
			val requires = mapping.requires.filter[!it.mapping.^default].map[new Triple(it.mapping.mappedCorrespondenceName, name.toFirstUpper, it)]
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
							
							«FOR m : file.mappings»
							«m.mappedCorrespondenceName».Helper.setCI(«typeRef(JavaHelper)».requireType(blackboard.getCorrespondenceInstance(), «typeRef(MappedCorrespondenceInstance)».class));
							«ENDFOR»
							
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
	
		// create map from signature/constraint index to correct import
		private def Map<Object, Import> mapSignatureToImport(Mapping parent) {
			val result = new HashMap<Object, Import>()
			
			val mappingData = zipAny(parent.signatures, parent.constraints, parent.inferPackagesForSignaturesAndConstraints)
			if (mappingData.forall[third!=null]) {
				mappingData.forEach[ data |
					val imp = imports.findFirst[imp | data.third.equals(imp.package)]
					result.put(data.first, imp)
					result.put(data.second, imp)
				]
			} else if (mappingData.forall[third==null]) {
				mappingData.zip(imports).forEach[ data |
					// first = (signature, constraintBlock, package), second = import 
					result.put(data.first.first, data.second)
					result.put(data.first.second, data.second)
				]
			} else {
				throw new IllegalArgumentException(
					'''Mapping «parent.name» does not have enough information '''
					+ '''in its signature to infer correct imports.''')
			}
			
			result
		}
		
		private def generateWrapperClass(Mapping parent, Import imp, List<NamedEClass> signatureElements) {
			val fqn = getWrapperName(parent, imp)
			val className = fqn.toSimpleName
			
			fsa.generateJavaFile(fqn, [
				var elementIndex = 0;
				'''
					public class «className» {
						// Use builder for creating «className»
						// TODO remove check when using builder
						public «className»(«typeRef(List)»<«typeRef(EObject)»> elements) {
							if (elements.size() != «signatureElements.size») {
								throw new «typeRef(IllegalArgumentException)»("argument does not have «signatureElements.size» elements.");
							}
							
							«FOR id : signatureElements.withIndex»
							if (elements.get(«id.first») == null) {
								throw new «typeRef(IllegalArgumentException)»("element at position «id.first» must be of type «id.second.type.instanceTypeName», but was null");
							} else if (!(elements.get(«id.first») instanceof «typeRef(id.second.type)»)) {
								throw new «typeRef(IllegalArgumentException)»("element at position «id.first» must be of type «id.second.type.instanceTypeName», but was: " + elements.get(«id.first»).toString());
							}
							«ENDFOR»
							
							// TODO make immutable
							this.elements = elements;
						}
						
						«FOR namedEClass : signatureElements»
							private static final int «namedEClass.name.toUpperCase»_INDEX = «elementIndex++»;
						«ENDFOR»
						
						«FOR namedEClass : signatureElements»
							public «typeRef(namedEClass.type)» get«namedEClass.name.toFirstUpper»() {
								return «typeRef(JavaHelper)».requireType(elements.get(«namedEClass.name.toUpperCase»_INDEX), «typeRef(namedEClass.type)».class);
							}
						«ENDFOR»
						
						private final «typeRef(List)»<«typeRef(EObject)»> elements;
						
						public «typeRef(List)»<«typeRef(EObject)»> getElements() { return this.elements; }
						
						public void reload() {
							«typeRef(List)»<«typeRef(EObject)»> tmp = new «typeRef(ArrayList)»<«typeRef(EObject)»>();
							tmp.addAll(elements);
							elements.clear();
							tmp.stream().map(it -> «parent.mappedCorrespondenceName».Helper.reload(it)).forEach(elements::add);
						}
						
						public static class Builder {
							«FOR namedEClass : signatureElements»
							private «typeRef(namedEClass.type)» «namedEClass.name»;
							«ENDFOR»
							
							«FOR namedEClass : signatureElements»
								public Builder set«namedEClass.name.toFirstUpper»(«typeRef(namedEClass.type)» «namedEClass.name») {
									if (this.«namedEClass.name» != null)
										throw new «typeRef(IllegalStateException)»("«namedEClass.name» has already been set.");
									this.«namedEClass.name» = «namedEClass.name»;
									return this;
								}
							«ENDFOR»
								
							public «className» build() {
								«typeRef(List)»<«typeRef(EObject)»> elements = new «typeRef(ArrayList)»<«typeRef(EObject)»>();
								
								«FOR namedEClass : signatureElements»
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
			if (signature == null)
				return #[]
			else
				signature.elements?.map[new Pair(ih.typeRef(type.instanceTypeName), name.toFirstLower)] ?: #[]
		}

		private def generateMappedCorrespondenceClass(Mapping mapping) {
			// TODO: Refactor and clean up. Align with default mappings
			
			// FIXME DW das stimmt noch nicht für alle möglichen "Weglassungskombinationen" von signatures/constraints
			val pkgs = mapping.inferPackagesForSignaturesAndConstraints
				.zipAny(mapping.signatures, mapping.constraints)
				.groupBy[first]
			
			val sig2Import = mapping.mapSignatureToImport
			val import2constraint = new HashMap<Import, Collection<ConstraintBlock>>
			val import2signature = new HashMap<Import, Collection<Signature>>
			sig2Import.entrySet.forEach [
				if (key instanceof ConstraintBlock) {
					if (!import2constraint.containsKey(value))
						import2constraint.put(value, new ArrayList<ConstraintBlock>())
					import2constraint.get(value).add(key as ConstraintBlock)
				} else if (key instanceof Signature) {
					if (!import2signature.containsKey(value))
						import2signature.put(value, new ArrayList<Signature>())
					import2signature.get(value).add(key as Signature)
				}
			]
			
			// get correctly ordered signatures and constraints
			val wrapperClasses = imports.map[getWrapperName(mapping, it)]
			val wrapperFields = imports.map[wrapperFieldName]
			val stateNames = imports.map[toStateName]
			val mappingName = mapping.mappingClassName
			
			val fqn = mapping.mappedCorrespondenceName
			val className = fqn.toSimpleName
			
			
			// todo consolidate
			val requires = mapping.requires.filter[!it.mapping.^default]
					.map[req | new Pair(req.mapping.mappedCorrespondenceName, req.name)]
					
			val defaultRequirements = mapping.requires.filter[it.mapping.^default]
					.map[req | new Pair(req.mapping.mappedCorrespondenceName, req.name)]
					
			val packages = imports.map[name.toFirstUpper]
			
//			val indices = #[0,1]
			val pairs = #[new Pair(0, 1), new Pair(1, 0)]
			val importPairs = pairs.map[new Pair(imports.get(first), imports.get(second))]
			
			
			val wrapperImports = new ArrayList<Import>()
			for (ePackage : pkgs.keySet) {
				val value = pkgs.get(ePackage)
				val elements = reduceSignatures(value)
				val imp = imports.findFirst[package.equals(ePackage)]
				if (imp != null) {
					wrapperImports.add(imp)
				}
			}
			
			val defaultContainments = imports.toInvertedMap[(pkgs.get(it.package).map[third?.expressions ?: #[]].flatten.filter(DefaultContainExpression))]
						
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
						
						public void reload() {
							«FOR req : requires AFTER "\n"»
								«req.second».reload();
							«ENDFOR»
							if (state == State.MAPPED) {
								clone(Helper.get(this.correspondence));
							}
						}
						
						public void clone(«className» other) {
							«FOR el : classSignature»
							this.«el.second» = other.«el.second»;
							«ENDFOR»
							
							validateCorrespondence();
						}
						
						
						
						public static enum State {
							UNDEFINED, «stateNames.join(", ")», MAPPED
						}
						
						private State state;
						
						// default requirements
						«FOR def : defaultRequirements»
							public «def.first» get«def.second.toFirstUpper»() {
								return «def.first».Helper.get();
							}
						«ENDFOR»
						
						// required mapped correspondences
						«FOR el : requires»
							private «el.first» «el.second»;a
						«ENDFOR»
						
						«FOR el : requires»
							public «el.first» get«el.second.toFirstUpper»() {
								this.«el.second».reload();
								return this.«el.second»;
							}
						«ENDFOR»
						// end required mapped correspondences
						
						private «typeRef(Correspondence)» correspondence;
						
						public «typeRef(Correspondence)» getCorrespondence() {
							if (state != State.MAPPED) {
								throw new «typeRef(IllegalStateException)»("Cannot get correspondence if not in state MAPPED");
							}
							
							return this.correspondence;
						}
						
						«FOR imp : imports»
							private «mapping.getWrapperName(imp)» «imp.wrapperFieldName»;
						«ENDFOR»
						
						«FOR imp : imports»
							private boolean stateHas«imp.toFirstUpperName»() {
								return ((state == State.«imp.toStateName») || (state == State.MAPPED));
							}
						«ENDFOR»
						
						private void validateCorrespondence() {
							if (!isValid()) {
								throw new «typeRef(IllegalStateException)»("inconsistent state: not valid");
							}
							
							if ((state == State.MAPPED) != (correspondence != null)) {
								throw new «typeRef(IllegalStateException)»("...");
							}
							
							«FOR imp : imports»
								if (stateHas«imp.toFirstUpperName»() != («imp.wrapperFieldName» != null)) {
									throw new «typeRef(IllegalStateException)»("«imp.package.name» not consistent with state " + state.toString());
								}
								
							«ENDFOR»
							
							if ((state == State.MAPPED)) {
								// M0B_Wrapper_pcm resolvedPcmWrapper = ...;
								// M0B_Wrapper_uml resolvedUmlWrapper = ...;
								// falls unterschied: Fehler/Warning und setzen
							}
							
							// check parent validity. all required mappings must be set.
						}
						
						«FOR imp : imports»
						public «mapping.getWrapperName(imp)» get«imp.toFirstUpperName»() {
							if (!stateHas«imp.toFirstUpperName»())
								throw new «typeRef(IllegalStateException)»("Cannot get «imp.package.name» in state " + state.toString());
								
							validateCorrespondence();
							
							this.«imp.wrapperFieldName».reload();
							
							return this.«imp.wrapperFieldName»;
						}
						
						public void set«imp.toFirstUpperName»(«mapping.getWrapperName(imp)» new_«imp.wrapperFieldName») {
							validateCorrespondence();
							
							if (stateHas«imp.toFirstUpperName»())
								throw new «typeRef(IllegalStateException)»("Cannot set «imp.package.name» in state " + state.toString());
								
							clone(Helper.create(
								«(requires.map[second] + imports.map[if (it == imp) ('''new_«imp.wrapperFieldName»''') else ('''get«it.toFirstUpperName»()''')]).join (", ")»
							));
						}
						«ENDFOR»
						
						«FOR pair : importPairs»
						public void unset«pair.first.toFirstUpperName»() {
							validateCorrespondence();
							if (!isMapped())
								throw new «typeRef(IllegalStateException)»("Cannot unset «pair.first.name» in state " + state.toString());
								
							Helper.removeCorrespondence(correspondence);
							«typeRef(MIRMappingHelper)».removeAll(get«pair.first.toFirstUpperName»().getElements());
							this.correspondence = null;
							this.«pair.first.wrapperFieldName» = null;
							this.state = State.«pair.second.toStateName»;
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
							«FOR imp : imports»
							if (stateHas«imp.toFirstUpperName»()) {
								«FOR defaultContainExpression : defaultContainments.get(imp)»
								«clg.checkAndCreateDefaultContainment(ih, #{}, defaultContainExpression)»
								«ENDFOR»
								
								boolean nonContainedFound;
								do {
									nonContainedFound = false;
									for («typeRef(EObject)» eObject : get«imp.toFirstUpperName»().getElements()) {
										if (!«typeRef(MIRMappingHelper)».hasContainment(eObject, result)) {
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
						
						«FOR pair : importPairs»
						public void repairFrom«pair.first.toFirstUpperName»(«typeRef(TransformationResult)» result) {
							if (!stateHas«pair.first.toFirstUpperName»())
								throw new «typeRef(IllegalStateException)»("Cannot repair from «pair.first.name» in state " + state.toString());
								
							boolean nowMapped = checkConstraintsFor«pair.first.toFirstUpperName»();
							
							if (!isMapped() && nowMapped) {
								createCorresponding«pair.second.toFirstUpperName»From«pair.first.toFirstUpperName»();
								checkAllContainments(result);
							} else if (isMapped() && !nowMapped) {
								«typeRef(List)»<«typeRef(Resource)»> resources = «typeRef(MIRMappingHelper)».getResources(get«pair.first.toFirstUpperName»().getElements());
								unset«pair.second.toFirstUpperName»();
								«typeRef(MIRMappingHelper)».addEmptyResourcesToTransformationResult(resources, result);
							}
							
							if (nowMapped) {
								restorePostConditionsFrom«pair.first.toFirstUpperName»();
							}
						}
						
						public void restorePostConditionsFrom«pair.first.toFirstUpperName»() {
							if (!stateHas«pair.first.toFirstUpperName»())
								throw new «typeRef(IllegalStateException)»("Cannot restore post conditions from «pair.first.name» in state " + state.toString() + " (must be MAPPED)");
							
							«IF mapping.constraintsBody != null»
								«FOR constraint : mapping.constraintsBody.expressions»
									«restoreBodyConstraintFrom(ih, #{}, constraint, pair.first.package)»
								«ENDFOR»
							«ELSE»
								// no post conditions, ignore
							«ENDIF»
						}
						
						public boolean checkConstraintsFor«pair.first.toFirstUpperName»() {
							reload();
							«IF import2constraint.containsKey(pair.first) && !import2constraint.get(pair.first).empty»
								«FOR constraintBlock : import2constraint.get(pair.first)»
									«FOR checkExpression : constraintBlock.expressions.map[checkSignatureConstraint(ih, #{}, it)].filterNull»
										if (!«checkExpression»)
											return false;
									«ENDFOR»
								«ENDFOR»
							«ENDIF»
							
							return true;
						}
						
						public void createCorresponding«pair.second.toFirstUpperName»From«pair.first.toFirstUpperName»() {
							throw new «typeRef(OperationNotSupportedException)»("Order and TUID generation not correct yet");
							
							if (state != State.«pair.first.toStateName»)
								throw new «typeRef(IllegalStateException)»("State must be «pair.first.toStateName»");
								
							«FOR namedEClass : import2signature.get(pair.second)?.identicalElement?.elements ?: #[]»
								«typeRef(namedEClass.type)» new_«namedEClass.name» = «eCreate(ih, namedEClass.type)»;
							«ENDFOR»
							
							set«pair.second.toFirstUpperName»(
								(new «getWrapperName(mapping, pair.second)».Builder())
									«FOR namedEClass : import2signature.get(pair.second)?.identicalElement?.elements ?: #[]»
										.set«namedEClass.name.toFirstUpper»(new_«namedEClass.name»)
									«ENDFOR»
									.build());
							
							«IF !import2constraint.getOrDefault(pair.second, #[]).empty»
								«FOR constraintBlock : import2constraint.getOrDefault(pair.second, #[])»
									«FOR constraint : constraintBlock.expressions»
										«establishSignatureConstraintOnCreate(ih, #{}, constraint)»;
									«ENDFOR»
								«ENDFOR»
							«ELSE»
								// no constraints for «pair.second.name»
							«ENDIF»
						}
						
						«ENDFOR»
						
						
						public static class Helper {
							private static final «typeRef(MIRMappingRealization)» MAPPING = «mapping.mappingClassName».INSTANCE;
							private static «typeRef(MappedCorrespondenceInstance)» mci;
							
							/*
							todo is this mechanism needed?
							private static Map<EObject, List<TUID>> oldTUIDMap = new HashMap<EObject, List<TUID>>();

							public static void addObjectToUpdate(EObject eObject) {
								if (!oldTUIDMap.containsKey(eObject))
									oldTUIDMap.put(eObject, new ArrayList<TUID>());
								oldTUIDMap.get(eObject).add(mci.calculateTUIDFromEObject(eObject));
							}
							
							public static void updateObject(EObject eObject) {
								if (!oldTUIDMap.containsKey(eObject)) {
									LOGGER.info("EObject " + eObject.toString() + " not in old tuid map");
								} else {
									for (TUID tuid : oldTUIDMap.get(eObject)) {
										mci.updateTUID(tuid, eObject);
									}
									oldTUIDMap.remove(eObject);
								}
							}
							*/
							
							private Helper() {}
							
							public static void setCI(MappedCorrespondenceInstance mci) {
								if (Helper.mci != null) {
									// warning
								}
								
								Helper.mci = mci;
							}
							
							public static <T extends «typeRef(EObject)»> T reload(T eObject) {
								return (T) mci.resolveEObjectFromTUID(mci.calculateTUIDFromEObject(eObject));
							}
							
							public static «typeRef(Set)»<«className»> getAll() {
								return mci.getCorrespondencesForMapping(MAPPING).stream().map(it -> get(it)).collect(«typeRef(Collectors)».toSet());
							}
							
							«FOR pair : importPairs»
							/**
							 * @Nullable...
							 */
							public static «className» getExistingFor«pair.first.toFirstUpperName»(«getWrapperName(mapping, pair.first)» «pair.first.wrapperFieldName») {
								«typeRef(Correspondence)» stc = mci.getMappedCorrespondence(«pair.first.wrapperFieldName».getElements(), MAPPING);
								if (stc == null) {
									return null;
								} else {
									return get(stc);
									«/*
									«typeRef(List)»<«typeRef(EObject)»> opposite = «typeRef(MappedCorrespondenceInstance)».getOpposite(stc, «wrapperFields.get(pair.first)».getElements());
									«wrapperClasses.get(pair.second)» «wrapperFields.get(pair.second)» = new «wrapperClasses.get(pair.second)»(opposite);
									
									
									«IF !requires.empty»
										«typeRef(List)»<«typeRef(Correspondence)»> dependsOn = stc.getDependsOn();
										«FOR req : requires.withIndex»
										final «req.second.first» «req.second.second» = «req.second.first».Helper.get(dependsOn.get(«req.first»));
										«ENDFOR»
									«ENDIF»
									
									
									return new «className»(
										«FOR req : requires»«req.second», «ENDFOR»
										«FOR wf : wrapperFields»«wf», «ENDFOR»
										stc, State.MAPPED);
									 */»
								}
							}
							
							public static «className» createHalfMappingFor«pair.first.toFirstUpperName»(
								«FOR req : requires»«req.first» «req.second», «ENDFOR»
								«getWrapperName(mapping, pair.first)» «pair.first.wrapperFieldName») {
								// assert getExistingFor«pair.first.toFirstUpperName» == null
								
								return new «className»(
									«FOR req : requires»«req.second», «ENDFOR»
									«FOR imp : imports»«IF (imp == pair.first)»«imp.wrapperFieldName», «ELSE»null, «ENDIF»«ENDFOR»
									null, State.«pair.first.toStateName»);
							}
							
							public static «className» getExistingOrHalfFor«pair.first.toFirstUpperName»(
									«FOR req : requires»«req.first» «req.second», «ENDFOR»
									«getWrapperName(mapping, pair.first)» «pair.first.wrapperFieldName») {
								«className» existing = getExistingFor«pair.first.toFirstUpperName»(«pair.first.wrapperFieldName»);
								if (existing != null) {
									return existing;
								} else {
									return createHalfMappingFor«pair.first.toFirstUpperName»(«FOR req : requires»«req.second», «ENDFOR»«pair.first.wrapperFieldName»);
								}
							}
							«ENDFOR»
							
							public static «className» get(«typeRef(Correspondence)» stc) {
								«IF !requires.empty»
									«typeRef(List)»<«typeRef(Correspondence)»> dependsOn = stc.getDependsOn();
									«FOR req : requires.withIndex»
									final «req.second.first» «req.second.second» = «req.second.first».Helper.get(dependsOn.get(«req.first»));
									«ENDFOR»
								«ENDIF»
								
								«IF wrapperClasses.size != 2»throw new IllegalArgumentException("more than two wrapper classes!")«ENDIF»
								
								«FOR wi : wrapperImports.map[new Pair(it, #["A", "B"].get(imports.indexOf(it)))]»
								«getWrapperName(mapping, wi.first)» «wi.first.wrapperFieldName» = new «getWrapperName(mapping, wi.first)»(stc.get«wi.second»s());
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
								«FOR req : requires»
								stc.getDependsOn().add(«req.second».getCorrespondence());
								«ENDFOR»
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
									+ (imports.map[import2signature.get(it)].filterNull.flatten.map[elements].flatten).map[new Pair(it.type.instanceTypeName.typeRef, it.name)])
								SEPARATOR ", "»
									«typeRef(Iterable)»<«el.first»> «el.second»
								«ENDFOR»
							) {
								// create cross product
								«typeRef(Set)»<«className»> result = new «typeRef(HashSet)»<«className»>();
								
								«FOR req : requires»
								for («req.first» «req.second»_it : «req.second») {
								«ENDFOR»
								
								«FOR imp : imports»
									«FOR el : getTypesAndNames(ih, import2signature.get(imp)?.identicalElement) ?: #[] SEPARATOR "\n"»
									for («el.first» «el.second»_it : «el.second») {
									«ENDFOR»

									«getWrapperName(mapping, imp)» «imp.wrapperFieldName» = new «getWrapperName(mapping, imp)».Builder()
										«FOR el : getTypesAndNames(ih, import2signature.get(imp)?.identicalElement)»
											.set«el.second.toFirstUpper»(«el.second»_it)
										«ENDFOR»
										.build();
										
									result.add(new Builder()
										«FOR el : requires.map[new Pair(second.toFirstUpper, second + "_it")] + #[new Pair(imp.toFirstUpperName, imp.wrapperFieldName)]»
											.set«el.first»(«el.second»)
										«ENDFOR»
										.build());

									«FOR el : getTypesAndNames(ih, import2signature.get(imp)?.identicalElement)»
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
							
							«FOR imp : imports»
								public Builder set«imp.toFirstUpperName»(«getWrapperName(mapping, imp)» «imp.wrapperFieldName») {
									if (this.«imp.wrapperFieldName» != null)
										throw new «typeRef(IllegalStateException)»("«imp.wrapperFieldName» has already been set.");
										
									this.«imp.wrapperFieldName» = «imp.wrapperFieldName»;
									return this; 
								}
							«ENDFOR»
								
							public «className» build() {
								«FOR el : requires AFTER "\n"»
									if (this.«el.second» == null)
										throw new «typeRef(IllegalStateException)»("«el.second» has not been set.");
								«ENDFOR»
								if («FOR imp : imports SEPARATOR " && "»(this.«imp.wrapperFieldName» != null)«ENDFOR»)
									return Helper.create(«FOR el : requires.map[second] + imports.map[wrapperFieldName] SEPARATOR  ", "»«el»«ENDFOR»);
								
								«FOR imp : imports SEPARATOR "\n else "»
								if (this.«imp.wrapperFieldName» != null)
									return Helper.getExistingOrHalfFor«imp.toFirstUpperName»(
										«FOR req : requires SEPARATOR ", " AFTER ", "»«req.second»«ENDFOR»
										this.«imp.wrapperFieldName»
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
								
								«FOR mapping : file.mappings.sortBy[^default]»
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