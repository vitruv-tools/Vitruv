package tools.vitruv.applications.pcmjava.util.pcm2java

import com.google.common.collect.Sets
import tools.vitruv.domains.java.util.jamoppparser.JaMoPPParser
import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.util.datatypes.ClaimableMap
import java.io.ByteArrayInputStream
import java.util.ArrayList
import java.util.Comparator
import java.util.HashSet
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
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.ReferenceableElement
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
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.palladiosimulator.pcm.core.composition.ComposedStructure
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Public

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.applications.pcmjava.util.PCMJaMoPPUtils
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.domains.pcm.PcmNamespace
import tools.vitruv.domains.java.JavaNamespace

abstract class PCM2JaMoPPUtils extends PCMJaMoPPUtils {
	private static val Logger logger = Logger.getLogger(PCM2JaMoPPUtils.simpleName)

	private new() {
	}

	def static addPCM2JaMoPPCorrespondenceToFeatureCorrespondenceMap(String pcmFeatureName, String jaMoPPFeatureName,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		var entityNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter [ attribute |
			attribute.name.equalsIgnoreCase(pcmFeatureName)
		].iterator.next
		var nameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter [ attribute |
			attribute.name.equalsIgnoreCase(jaMoPPFeatureName)
		].iterator.next
		featureCorrespondenceMap.put(entityNameAttribute, nameAttribute)
	}

	def static addEntityName2NameCorrespondence(Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap) {
		addPCM2JaMoPPCorrespondenceToFeatureCorrespondenceMap(PcmNamespace.PCM_ATTRIBUTE_ENTITY_NAME,
			JavaNamespace.JAMOPP_ATTRIBUTE_NAME, featureCorrespondenceMap)
	}

	def static updateNameAttribute(
		Set<EObject> correspondingEObjects,
		Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceModel correspondenceModel,
		boolean saveFilesOfChangedEObjects
	) {
		val Set<java.lang.Class<? extends EObject>> jaMoPPRootClasses = Sets.newHashSet(JavaRoot)
		updateNameAttribute(correspondingEObjects, newValue, affectedFeature, featureCorrespondenceMap,
			correspondenceModel, saveFilesOfChangedEObjects, jaMoPPRootClasses)
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
		if (!namespace.nullOrEmpty) {
			namespaceClassifierReference.namespaces.addAll(namespace.split("."))
		} else {
			namespaceClassifierReference.namespaces.add("")
		}
		return namespaceClassifierReference
	}

	def public static void handleClassifierNameChange(Classifier classifier, Object newValue,
		CorrespondenceModel correspondenceModel, boolean appendImpl, TUID oldClassifierTUID) {
		classifier.name = newValue.toString
		if (classifier instanceof Class) {
			if (appendImpl) {
				classifier.name = classifier.name + "Impl"
			}
			val constructors = classifier.members.filter(Constructor)
			for (Constructor c : constructors) {
				c.name = classifier.name
			}
		}
		oldClassifierTUID.updateTuid(classifier)
	}

	def public static void handleJavaRootNameChange(JavaRoot javaRoot, EStructuralFeature affectedFeature,
		Object newValue, CorrespondenceModel correspondenceModel, boolean changeNamespanceIfCompilationUnit,
		ChangePropagationResult transformationResult, EObject affectedEObject) {
		val TUID oldTUID = correspondenceModel.calculateTUIDFromEObject(javaRoot)
		var TUID oldClassifierTUID = null
		if (javaRoot instanceof CompilationUnit && !(javaRoot as CompilationUnit).classifiers.nullOrEmpty) {
			oldClassifierTUID = correspondenceModel.calculateTUIDFromEObject(
				(javaRoot as CompilationUnit).classifiers.get(0))
		}
		var VURI vuriToDelete = null
		if (null != javaRoot.eResource) {
			vuriToDelete = VURI.getInstance(javaRoot.eResource)
		}
		// change name
		var String newName = newValue.toString
		if (javaRoot instanceof CompilationUnit) {
			if (changeNamespanceIfCompilationUnit) {
				// change package if compilation unit and change new Name
				javaRoot.namespaces.remove(javaRoot.namespaces.size - 1)
				javaRoot.namespaces.add(newValue.toString)
				newName = newName + "Impl"
			}
			newName = newName + "." + JavaNamespace.FILE_EXTENSION
			handleClassifierNameChange((javaRoot as CompilationUnit).classifiers.get(0), newValue,
				correspondenceModel, changeNamespanceIfCompilationUnit, oldClassifierTUID)
		}
		javaRoot.name = newName;
		PCMJaMoPPUtils.handleRootChanges(javaRoot, correspondenceModel, PCMJaMoPPUtils.getSourceModelVURI(affectedEObject),
			transformationResult, vuriToDelete, oldTUID)
//			if (javaRoot instanceof CompilationUnit && !(javaRoot as CompilationUnit).classifiers.nullOrEmpty ){
//				
//			}
	}

	def static createPrivateField(TypeReference reference, String name) {
		val Field field = MembersFactory.eINSTANCE.createField
		field.typeReference = EcoreUtil.copy(reference)
		field.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPrivate)
		var String fieldName = name
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

		// this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		assigmentExpression.child = selfReference

		// .fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = EcoreUtil.copy(field)
		selfReference.next = fieldReference

		// =
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		// name		
		val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierReference.target = parameter

		assigmentExpression.value = identifierReference
		expressionStatement.expression = assigmentExpression
		return expressionStatement
	}

	/**
	 * sorts the member list to ensure that fields are printed before constructors and constructors before methods
	 */
	def static sortMembers(List<? extends EObject> members) {
		members.sort(new Comparator<EObject> {

			override compare(EObject o1, EObject o2) {

				// fields before constructors and methods
				if (o1 instanceof Field && (o2 instanceof Method || o2 instanceof Constructor)) {
					return -1
				} else if ((o1 instanceof Method || o1 instanceof Constructor) && o2 instanceof Field) {
					return 1

				// constructors before Methods	
				} else if (o1 instanceof Constructor && o2 instanceof Method) {
					return -1
				} else if (o1 instanceof Method && o2 instanceof Constructor) {
					return 1
				}
				return 0;
			}

			override equals(Object obj) {
				return this == obj;
			}

		})
	}

	def static NamespaceClassifierReference createNamespaceClassifierReference(ConcreteClassifier concreteClassifier) {
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = EcoreUtil.copy(concreteClassifier)
		namespaceClassifierReference.classifierReferences.add(classifierRef)

		// namespaceClassifierReference.namespaces.addAll(concreteClassifier.containingCompilationUnit.namespaces)
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

		// return empty import - should not change class at all
		return ImportsFactory.eINSTANCE.createClassifierImport
	}

	def static addConstructorToClass(Class jaMoPPClass) {
		val Constructor constructor = MembersFactory.eINSTANCE.createConstructor
		constructor.name = jaMoPPClass.name
		constructor.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		jaMoPPClass.members.add(constructor)
		return constructor
	}

	def static Package getContainingPackageFromCorrespondenceModel(Classifier classifier,
		CorrespondenceModel correspondenceModel) {
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$") || namespace.endsWith(".")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		val finalNamespace = namespace
		var Set<Package> packagesWithCorrespondences = correspondenceModel.
			getAllEObjectsOfTypeInCorrespondences(Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter [ pack |
			finalNamespace.equals(pack.namespacesAsString + pack.name)
		]
		if (null != packagesWithNamespace && 0 < packagesWithNamespace.size &&
			null != packagesWithNamespace.iterator.next) {
			return packagesWithNamespace.iterator.next
		}
		return null;
	}

	def public static Package findCorrespondingPackageByName(String name, CorrespondenceModel correspondenceModel,
		Repository repo) {
		val packages = correspondenceModel.getCorrespondingEObjectsByType(repo, Package)
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
		EcoreUtil.remove(javaRoot)
		return javaRoot
	}

	def public static createJaMoPPMethod(String content) {
		try {
			val String cuContent = "class Dummy{" + content + "}"
			val String name = "vitruvius.meta/src/dummy.java";
			val cu = createJavaRoot(name, cuContent) as CompilationUnit
			val method = cu.classifiers.get(0).methods.get(0)
			EcoreUtil.remove(method)
			return method
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
	// return getNameFromJaMoPPType(classRef)
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

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Void reference) {
		return "void"
	}

	def static askUserForPackage(CorrespondenceModel correspondenceModel, Repository repository,
		UserInteracting userInteractiong, String message) {
		val packages = correspondenceModel.getCorrespondingEObjectsByType(repository, Package)
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

		// do not add compilation unit to package --> would cause problems by TUID calculation
		/*jaMoPPPackage.compilationUnits.add(jaMoPPCompilationUnit)*/
		jaMoPPCompilationUnit.namespaces.addAll(jaMoPPPackage.namespaces)
		jaMoPPCompilationUnit.namespaces.add(jaMoPPPackage.name)
		return #[jaMoPPCompilationUnit, jaMoPPClass]
	}

	def static EObject[] createPackageCompilationUnitAndJaMoPPClass(NamedElement pcmNamedElement, Package rootPackage) {

		// create JaMoPP Package
		val Package jaMoPPPackage = PCM2JaMoPPUtils.createPackageProgrammatically(pcmNamedElement, rootPackage)

		// create JaMoPP compilation unit and JaMoPP class in package
		val List<EObject> newEObjects = new ArrayList
		newEObjects.addAll(PCM2JaMoPPUtils.createCompilationUnitAndJaMoPPClass(pcmNamedElement, jaMoPPPackage))

		newEObjects.add(jaMoPPPackage)
		return newEObjects
	}

	def static ChangePropagationResult updateNameAsSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceModel correspondenceModel) {
		val transformationResult = new ChangePropagationResult
		if (oldValue == newValue) {
			return transformationResult
		}
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel)
		if (affectedEObjects.nullOrEmpty) {
			return transformationResult
		}
		val jaMoPPPackages = affectedEObjects.filter(typeof(Package))
		if (!jaMoPPPackages.nullOrEmpty) {

			// filter contracts and datatypes packages
			val jaMoPPPackage = jaMoPPPackages.filter [ pack |
				!pack.name.equals("contracts") && !pack.name.equals("datatypes")
			].get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(jaMoPPPackage, affectedAttribute, newValue, correspondenceModel, true,
				transformationResult, eObject)
		}
		val cus = affectedEObjects.filter(typeof(CompilationUnit))
		if (!cus.nullOrEmpty) {
			val CompilationUnit cu = cus.get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(cu, affectedAttribute, newValue, correspondenceModel, true,
				transformationResult, eObject)
		}
		return transformationResult
	}

	/**
	 * creates this.<fieldName> = new <typeOfField>(params)
	 */
	def static NewConstructorCall addNewStatementToConstructor(Constructor constructor, Field field,
		Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		// this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		assigmentExpression.child = selfReference

		// .fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = EcoreUtil.copy(field)
		selfReference.next = EcoreUtil.copy(fieldReference)

		// =
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		// new fieldType
		val newConstructorCall = InstantiationsFactory.eINSTANCE.createNewConstructorCall
		newConstructorCall.typeReference = EcoreUtil.copy(field.typeReference)

		// get order of type references of the constructor
		updateArgumentsOfConstructorCall(field, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall)

		assigmentExpression.value = newConstructorCall
		expressionStatement.expression = assigmentExpression
		constructor.statements.add(expressionStatement)
		return newConstructorCall
	}

	def static updateArgumentsOfConstructorCall(Field field, Field[] fieldsToUseAsArgument,
		Parameter[] parametersToUseAsArgument, NewConstructorCall newConstructorCall) {
		val List<TypeReference> typeListForConstructor = new ArrayList<TypeReference>
		if (null != field.typeReference && null != field.typeReference.pureClassifierReference &&
			null != field.typeReference.pureClassifierReference.target) {
			val classifier = EcoreUtil.copy(field.typeReference.pureClassifierReference.target)
			if (classifier instanceof Class) {
				val jaMoPPClass = classifier as Class
				val constructorsForClass = jaMoPPClass.members.filter(typeof(Constructor))
				if (!constructorsForClass.nullOrEmpty) {
					val constructorForClass = constructorsForClass.get(0)
					for (parameter : constructorForClass.parameters) {
						typeListForConstructor.add(parameter.typeReference)
					}
				}
			}
		}

		// find type with same name in fields or parameters (start with parameter)
		for (typeRef : typeListForConstructor) {
			val refElement = typeRef.findMatchingTypeInParametersOrFields(fieldsToUseAsArgument,
				parametersToUseAsArgument)
			if (refElement != null) {
				val IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
				identifierReference.target = refElement
			} else {
				newConstructorCall.arguments.add(LiteralsFactory.eINSTANCE.createNullLiteral)
			}
		}
	}

	def static ReferenceableElement findMatchingTypeInParametersOrFields(TypeReference typeReferenceToFind,
		Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
		for (parameter : parametersToUseAsArgument) {
			if (parameter.typeReference.target == typeReferenceToFind.target) {
				return parameter
			}
		}
		for (field : fieldsToUseAsArgument) {
			if (field.typeReference.target == typeReferenceToFind.target) {
				return field
			}
		}
		return null
	}

	def static createNewForFieldInConstructor(Field field) {
		val classifier = field.containingConcreteClassifier
		if (!(classifier instanceof Class)) {
			return null
		}
		val jaMoPPClass = classifier as Class

		// create constructor if none exists
		if (classifier.members.filter(typeof(Constructor)).nullOrEmpty) {
			PCM2JaMoPPUtils.addConstructorToClass(jaMoPPClass)
		}
		val newObjects = new HashSet<EObject>
		for (constructor : classifier.members.filter(typeof(Constructor))) {
			newObjects.add(
				PCM2JaMoPPUtils.addNewStatementToConstructor(constructor, field, jaMoPPClass.fields,
					constructor.parameters))
		}
		return newObjects
	}

	def static void handleAssemblyContextAddedAsNonRootEObjectInList(ComposedStructure composedEntity,
		NamedElement namedElement, EObject[] newCorrespondingEObjects, CorrespondenceModel correspondenceModel) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return
		}
		for (newCorrespondingEObject : newCorrespondingEObjects) {
			correspondenceModel.createAndAddCorrespondence(namedElement, newCorrespondingEObject)
		}
	}

	def static getDatatypePackage(CorrespondenceModel correspondenceModel, Repository repo, String dataTypeName,
		UserInteracting userInteracting) {
		var datatypePackage = PCM2JaMoPPUtils.findCorrespondingPackageByName("datatypes", correspondenceModel, repo)
		if (null == datatypePackage) {
			logger.info("datatype package not found")
			val String message = "Datatype " + dataTypeName +
				" created. Please specify to which package the datatype should be added"
			datatypePackage = PCM2JaMoPPUtils.askUserForPackage(correspondenceModel, repo, userInteracting, message)
		} else {
			logger.info("found datatype package")
		}
		datatypePackage
	}

	/**
	 * returns the class object for a primitive type, e.g, Integer for int
	 */
	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(PrimitiveType type) {
		logger.warn("no dispatch method found for type: " + type)
		return null
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Boolean type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Boolean")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Byte type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Byte")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Char type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Character")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Double type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Double")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Float type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Float")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Int type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Integer")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Long type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Long")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Short type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Short")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(
		org.emftext.language.java.types.Void type) {
		PCM2JaMoPPUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Void")
	}

	public static def createClassMethod(String name, TypeReference typeReference, Modifier[] modifiers,
		Parameter[] parameters, boolean ensurePublic) {
		val ClassMethod classMethod = MembersFactory.eINSTANCE.createClassMethod
		classMethod.name = name
		if (null != typeReference) {
			classMethod.typeReference = EcoreUtil.copy(typeReference)
		}
		if (null != modifiers) {
			classMethod.annotationsAndModifiers.addAll(EcoreUtil.copyAll(modifiers))
		}
		if (ensurePublic) {
			val alreadyPublic = classMethod.annotationsAndModifiers.filter[modifier|modifier instanceof Public].size > 0
			if (!alreadyPublic) {
				classMethod.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
			}
		}
		if (null != parameters) {
			classMethod.parameters.addAll(EcoreUtil.copyAll(parameters))
		}
		return classMethod
	}

	public static def createClassMethod(Method method, boolean ensurePublic) {
		createClassMethod(method.name, method.typeReference, method.modifiers, method.parameters, ensurePublic)
	}

}
