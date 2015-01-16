package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import com.google.common.collect.Sets
import de.uka.ipd.sdq.pcm.core.entity.NamedElement
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.io.ByteArrayInputStream
import java.util.ArrayList
import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.ImportsFactory
import org.emftext.language.java.instantiations.InstantiationsFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

abstract class PCM2JaMoPPUtils extends PCMJaMoPPUtils {
	private static val Logger logger = Logger.getLogger(PCM2JaMoPPUtils.simpleName)

	private new() {
	}

	def static addPCM2JaMoPPCorrespondenceToFeatureCorrespondenceMap(String pcmFeatureName, String jaMoPPFeatureName,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		var entityNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(pcmFeatureName)].iterator.next
		var nameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(jaMoPPFeatureName)].iterator.next
		featureCorrespondenceMap.put(entityNameAttribute, nameAttribute)
	}

	def static addEntityName2NameCorrespondence(Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		addPCM2JaMoPPCorrespondenceToFeatureCorrespondenceMap(PCMJaMoPPNamespace.PCM::PCM_ATTRIBUTE_ENTITY_NAME,
			PCMJaMoPPNamespace.JaMoPP::JAMOPP_ATTRIBUTE_NAME, featureCorrespondenceMap)
	}

	def static updateNameAttribute(
		Set<EObject> correspondingEObjects,
		Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance,
		boolean markFilesOfChangedEObjectsAsFilesToSave
	) {
		val Set<java.lang.Class<? extends EObject>> jaMoPPRootClasses = Sets.newHashSet(JavaRoot)
		updateNameAttribute(correspondingEObjects, newValue, affectedFeature, featureCorrespondenceMap,
			correspondenceInstance, markFilesOfChangedEObjectsAsFilesToSave, jaMoPPRootClasses)
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(EObject eObject) {
		throw new RuntimeException("eObject " + eObject + " is not a primitve datatype")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Boolean bool) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Boolean")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Int integer) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Integer")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Short shortInt) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Short")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Byte b) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Byte")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Long l) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Long")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Float f) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Float")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Double d) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Double")
	}

	def dispatch static createAndReturnNamespaceClassifierReferenceForSimpleDataType(Char c) {
		return createAndReturnNamespaceClassifierReferenceForName("", "Char")
	}

	def static NamespaceClassifierReference createAndReturnNamespaceClassifierReferenceForName(String namespace,
		String name) {
		val classifier = ClassifiersFactory.eINSTANCE.createClass
		classifier.setName(name)
		val classifierReference = TypesFactory.eINSTANCE.createClassifierReference
		classifierReference.setTarget(classifier)
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		namespaceClassifierReference.classifierReferences.add(classifierReference)
		if (!namespace.empty) {
			namespaceClassifierReference.namespaces.addAll(namespace.split("."))
		}
		return namespaceClassifierReference
	}

	def public static void handleClassifierNameChange(Classifier classifier, Object newValue,
		TransformationChangeResult tcr, CorrespondenceInstance correspondenceInstance) {
		val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(classifier)
		classifier.name = newValue.toString
		if (classifier instanceof Class) {
			classifier.name = classifier.name + "Impl"
		}
		tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, classifier, null)
	}

	def public static void handleJavaRootNameChange(JavaRoot javaRoot, EStructuralFeature affectedFeature,
		Object newValue, TransformationChangeResult transformationChangeResult,
		CorrespondenceInstance correspondenceInstance, boolean changeNamespanceIfCompilationUnit) {
		val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(javaRoot)

		//change name
		var String newName = newValue.toString
		if (javaRoot instanceof CompilationUnit) {
			if (changeNamespanceIfCompilationUnit) {

				//change package if compilation unit and change new Name
				javaRoot.namespaces.remove(javaRoot.namespaces.size - 1)
				javaRoot.namespaces.add(newValue.toString)
				newName = newName + "Impl"
			}
			newName = newName + "." + PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION
			handleClassifierNameChange(javaRoot.classifiers.get(0), newValue, transformationChangeResult,
				correspondenceInstance)
		}
		javaRoot.name = newName;

		val VURI oldVURI = VURI.getInstance(javaRoot.eResource.getURI)
		transformationChangeResult.existingObjectsToDelete.add(oldVURI)
		transformationChangeResult.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, javaRoot, null)
		transformationChangeResult.newRootObjectsToSave.add(javaRoot)
	}

	def static createPrivateField(TypeReference reference, String name) {
		val Field field = MembersFactory.eINSTANCE.createField
		field.typeReference = reference
		field.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPrivate)
		var String fieldName = name
		if (fieldName.nullOrEmpty) {
			fieldName = PCM2JaMoPPUtils.getNameFromJaMoPPType(reference)
		}
		if (Character.isUpperCase(fieldName.charAt(0))) {
			fieldName = fieldName.toFirstLower
		}
		if (fieldName.nullOrEmpty) {
			fieldName = "field_" + PCM2JaMoPPUtils.getNameFromJaMoPPType(reference)
		}
		field.name = fieldName
		return field
	}

	def static Parameter createOrdinaryParameter(TypeReference typeReference, String name) {
		val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
		parameter.name = name
		parameter.typeReference = typeReference
		return parameter
	}

	def static Statement createAssignmentFromParameterToField(Field field, Parameter parameter) {
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		//this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		assigmentExpression.child = selfReference

		//.fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = EcoreUtil.copy(field)
		selfReference.next = fieldReference

		//=
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		//name		
		val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierReference.target = parameter

		assigmentExpression.value = identifierReference
		expressionStatement.expression = assigmentExpression
		return expressionStatement
	}

	/**
	 * sorts the member list to ensure that fields occure before constructors and constructors before methods
	 */
	def static sortMembers(List<? extends EObject> members) {
		members.sort(
			new Comparator<EObject> {

				override compare(EObject o1, EObject o2) {

					//fields before constructors and methods
					if (o1 instanceof Field && (o2 instanceof Method || o2 instanceof Constructor)) {
						return 1
					} else if ((o1 instanceof Method || o1 instanceof Constructor) && o2 instanceof Field) {
						return -1

					//constructors before Methods	
					} else if (o1 instanceof Constructor && o2 instanceof Method) {
						return 1
					} else if (o1 instanceof Method && o2 instanceof Constructor) {
						return -1
					}
					return 0;
				}

				override equals(Object obj) {
					return this == obj;
				}

			})
	}

	def static NamespaceClassifierReference createNamespaceClassifierReference(ConcreteClassifier jaMoPPInterface) {
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = jaMoPPInterface
		namespaceClassifierReference.classifierReferences.add(classifierRef)
		return namespaceClassifierReference
	}

	def static Import addImportToCompilationUnitOfClassifier(Classifier classifier,
		ConcreteClassifier classifierToImport) {
		val classifierImport = ImportsFactory.eINSTANCE.createClassifierImport
		if (null != classifierToImport.containingCompilationUnit) {
			if (null != classifierToImport.containingCompilationUnit.namespaces) {
				classifierImport.namespaces.addAll(classifierToImport.containingCompilationUnit.namespaces)
			}
			classifier.containingCompilationUnit.imports.add(classifierImport)
		}
		classifierImport.classifier = classifierToImport
		return classifierImport
	}

	def static Import addImportToCompilationUnitOfClassifier(Classifier classifier,
		NamespaceClassifierReference namespaceClassifierReference) {
		val classifierToImport = namespaceClassifierReference.target
		if (classifierToImport instanceof ConcreteClassifier) {
			return addImportToCompilationUnitOfClassifier(classifier, classifierToImport)
		}

		//return empty import - should not change class at all
		return ImportsFactory.eINSTANCE.createClassifierImport
	}

	def static addConstructorToClass(Class jaMoPPClass) {
		val Constructor constructor = MembersFactory.eINSTANCE.createConstructor
		constructor.name = jaMoPPClass.name
		constructor.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		jaMoPPClass.members.add(constructor)
		return constructor
	}

	def static Package getContainingPackageFromCorrespondenceInstance(Classifier classifier,
		CorrespondenceInstance correspondenceInstance) {
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$") || namespace.endsWith(".")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		val finalNamespace = namespace
		var Set<Package> packagesWithCorrespondences = correspondenceInstance.
			getAllEObjectsInCorrespondencesWithType(Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter[pack|
			finalNamespace.equals(pack.namespacesAsString + pack.name)]
		if (null != packagesWithNamespace && 0 < packagesWithNamespace.size &&
			null != packagesWithNamespace.iterator.next) {
			return packagesWithNamespace.iterator.next
		}
		return null;
	}

	def public static Package findCorrespondingPackageByName(String name, CorrespondenceInstance correspondenceInstance,
		Repository repo) {
		val packages = correspondenceInstance.getCorrespondingEObjectsByType(repo, Package)
		if (null == packages) {
			return null
		}
		for (package : packages) {
			if (package.name.equalsIgnoreCase(name)) {
				return package
			}
		}
		return null
	}

	def static CompilationUnit createCompilationUnit(String name, String content) {
		return createJavaRoot(name, content) as CompilationUnit
	}

	def static Package createPackage(String namespace) {
		val String content = '''package «namespace»;'''
		return createJavaRoot("package-info", content) as Package
	}

	def static JavaRoot createJavaRoot(String name, String content) {
		val JaMoPPParser jaMoPPParser = new JaMoPPParser
		val inStream = new ByteArrayInputStream(content.bytes)
		val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(VURI.getInstance(name + ".java").getEMFUri,
			inStream)
		javaRoot.name = name + ".java"
		return javaRoot
	}

	def public static createJaMoPPMethod(String content) {
		try {
			val String cuContent = "class Dummy{" + content + "}"
			val String name = "vitruvius.meta/src/dummy.java";
			val cu = createJavaRoot(name, cuContent) as CompilationUnit
			return cu.classifiers.get(0).methods.get(0)
		} catch (Throwable t) {
			logger.warn("Exception during createJaMoPPMethod with content " + content + " Exception: " + t)
			return null;
		}

	}

	def dispatch static getNameFromJaMoPPType(ClassifierReference reference) {
		return reference.target.name
	}

	def dispatch static getNameFromJaMoPPType(NamespaceClassifierReference reference) {
		val classRef = reference.pureClassifierReference
		return classRef.target.name

	// is currently not possible: see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=404817
	//return getNameFromJaMoPPType(classRef)
	}

	def dispatch static getNameFromJaMoPPType(Boolean reference) {
		return "boolean"
	}

	def dispatch static getNameFromJaMoPPType(Byte reference) {
		return "byte"
	}

	def dispatch static getNameFromJaMoPPType(Char reference) {
		return "char"
	}

	def dispatch static getNameFromJaMoPPType(Double reference) {
		return "double"
	}

	def dispatch static getNameFromJaMoPPType(Float reference) {
		return "float"
	}

	def dispatch static getNameFromJaMoPPType(Int reference) {
		return "int"
	}

	def dispatch static getNameFromJaMoPPType(Long reference) {
		return "long"
	}

	def dispatch static getNameFromJaMoPPType(Short reference) {
		return "short"
	}

	def dispatch static getNameFromJaMoPPType(Void reference) {
		return "void"
	}

	def static askUserForPackage(CorrespondenceInstance correspondenceInstance, Repository repository,
		UserInteracting userInteractiong, String message) {
		val packages = correspondenceInstance.getAllCorrespondingEObjects(repository).filter(typeof(Package))
		val List<String> options = new ArrayList<String>
		for (pack : packages) {
			options.add(pack.name)
		}
		val int selection = userInteractiong.selectFromMessage(UserInteractionType.MODAL, message, options)
		return packages.findFirst[pack|pack.name.equals(options.get(selection))]
	}

	def static createPackageProgrammatically(NamedElement pcmNamedElement, Package rootPackage) {
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = pcmNamedElement.entityName
		if (null != rootPackage) {
			jaMoPPPackage.namespaces.addAll(rootPackage.namespaces)
			jaMoPPPackage.namespaces.add(rootPackage.name)
		}
		return jaMoPPPackage
	}

	def static EObject[] createCompilationUnitAndJaMoPPClass(NamedElement pcmNamedElement, Package jaMoPPPackage) {
		val Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass
		jaMoPPClass.name = pcmNamedElement.entityName + "Impl"
		jaMoPPClass.addModifier(ModifiersFactory.eINSTANCE.createPublic)
		val CompilationUnit jaMoPPCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		jaMoPPCompilationUnit.name = jaMoPPClass.name + ".java"

		// add classifier to compilation unit
		jaMoPPCompilationUnit.classifiers.add(jaMoPPClass)

		// add compilation unit to package		
		jaMoPPPackage.compilationUnits.add(jaMoPPCompilationUnit)
		jaMoPPCompilationUnit.namespaces.addAll(jaMoPPPackage.namespaces)
		jaMoPPCompilationUnit.namespaces.add(jaMoPPPackage.name)
		jaMoPPPackage.compilationUnits.add(jaMoPPCompilationUnit)
		return #[jaMoPPCompilationUnit, jaMoPPClass]
	}

	def static EObject[] createPackageCompilationUnitAndJaMoPPClass(NamedElement pcmNamedElement, Package rootPackage) {

		// create JaMoPP Package
		val Package jaMoPPPackage = PCM2JaMoPPUtils.createPackageProgrammatically(pcmNamedElement, rootPackage)

		//create JaMoPP compilation unit and JaMoPP class in package
		val List<EObject> newEObjects = new ArrayList
		newEObjects.addAll(PCM2JaMoPPUtils.createCompilationUnitAndJaMoPPClass(pcmNamedElement, jaMoPPPackage))

		newEObjects.add(jaMoPPPackage)
		return newEObjects
	}

	def static TransformationChangeResult updateNameAsSingleValuedEAttribute(EObject eObject,
		EAttribute affectedAttribute, Object oldValue, Object newValue,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = new TransformationChangeResult
		val jaMoPPPackages = affectedEObjects.filter(typeof(Package))
		if (!jaMoPPPackages.nullOrEmpty) {

			//filter contracts and datatypes packages
			val jaMoPPPackage = jaMoPPPackages.filter[pack|
				!pack.name.equals("contracts") && !pack.name.equals("datatypes")].get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(jaMoPPPackage, affectedAttribute, newValue, tcr,
				correspondenceInstance, true)
		}
		val cus = affectedEObjects.filter(typeof(CompilationUnit))
		if (!cus.nullOrEmpty) {
			val CompilationUnit cu = cus.get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(cu, affectedAttribute, newValue, tcr, correspondenceInstance, true)
		}
		return tcr
	}

	/**
	 * creates this.<fieldName> = new <typeOfField>(params)
	 */
	def static Statement createNewOfFieldInConstructor(Constructor constructor, Field field, Field[] requiredFields) {
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		//this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		assigmentExpression.child = selfReference

		//.fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = EcoreUtil.copy(field)
		selfReference.next = fieldReference

		//=
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		//new fieldType
		val newConstructorCall = InstantiationsFactory.eINSTANCE.createNewConstructorCall
		newConstructorCall.typeReference = field.typeReference

		assigmentExpression.value = newConstructorCall
		expressionStatement.expression = assigmentExpression
		return expressionStatement
	}

	def static TransformationChangeResult deleteCorrespondingEObjectsAndGetTransformationChangeResult(
		EObject[] correspondingEObjects, CorrespondenceInstance correspondenceInstance) {
		val TransformationChangeResult tcr = TransformationUtils.createEmptyTransformationChangeResult
		val rootObjectAffected = !(correspondingEObjects.filter(typeof(JavaRoot)).nullOrEmpty)
		for (eObject : correspondingEObjects) {
			if (rootObjectAffected) {
				val VURI vuri = VURI.getInstance(eObject.eResource)
				tcr.existingObjectsToDelete.add(vuri)
			} else {
				if (null != eObject.eContainer) {
					tcr.existingObjectsToSave.add(eObject.eContainer)
				}
			}
			val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(eObject)
			tcr.addCorrespondenceToDelete(correspondenceInstance, oldTUID)
		}
		return tcr
	}

}
