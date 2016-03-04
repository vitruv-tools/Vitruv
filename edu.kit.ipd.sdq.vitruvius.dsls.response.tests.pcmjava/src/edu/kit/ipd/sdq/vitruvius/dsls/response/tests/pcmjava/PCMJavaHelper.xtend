package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.pcmjava

import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.Interface
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.core.entity.Entity
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypesFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.types.TypeReference
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.imports.Import
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.imports.ClassifierImport
import java.util.HashSet
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.operators.OperatorsFactory
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.parameters.ParametersFactory
import java.util.List
import java.util.Comparator
import org.emftext.language.java.members.Method
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.types.Type
import org.palladiosimulator.pcm.repository.PrimitiveDataType

class PCMJavaHelper {
	private static def String getQualifiedName(Repository repository) {
		repository.entityName.toFirstLower.trim;
	}
	
	public static final val interfaceToInterface = new CorrespondingInterface();
	public static final val repositoryToPackageInfo = new CorrespondingRepositoryPackageInfo();
	public static final val repositoryToContractsPackageInfo = new CorrespondingContractsRepositoryPackageInfo();
	public static final val repositoryToDatatypesPackageInfo = new CorrespondingDatatypesRepositoryPackageInfo();
	public static final val componentToPackageInfo = new CorrespondingComponentPackageInfo();
	public static final val dataTypeToClass = new CorrespondingDataTypeClass();
	public static final val componentToClass = new CorrespondingJavaClass();
		
	public abstract static class CorrespondingClass<E extends EObject> {
		public abstract def String getClassName(E object);
		public abstract def String getPackageName(E object);
		public def String getQualifiedClassName(E object) {
			return object.packageName + "." + object.className;
		}
		public def String getPathInProject(E object) {
			return "src/" + object.qualifiedClassName.replace(".", "/") + ".java";
		}
	}
	
	public abstract static class CorrespondingToEntityClass<E extends Entity> extends CorrespondingClass<E> {
		override getClassName(E object) {
			return object.entityName.toFirstUpper;
		}
	}
	
	public abstract static class CorrespondingPackageInfo<E extends Entity> extends CorrespondingToEntityClass<E> {
		override getClassName(E object) {
			return "package-info";
		}
	}
	
	public static class CorrespondingInterface extends CorrespondingToEntityClass<Interface> {
		override getPackageName(Interface object) {
			object.repository__Interface.qualifiedName.toFirstLower;
		}
	}
	
	public static class CorrespondingRepositoryPackageInfo extends CorrespondingPackageInfo<Repository> {
		override getPackageName(Repository object) {
			object.qualifiedName.toFirstLower;
		}
	}
	
	public static class CorrespondingContractsRepositoryPackageInfo extends CorrespondingPackageInfo<Repository> {
		override getPackageName(Repository object) {
			object.qualifiedName.toFirstLower + ".contracts";
		}
	}
	
	public static class CorrespondingDatatypesRepositoryPackageInfo extends CorrespondingPackageInfo<Repository> {
		override getPackageName(Repository object) {
			object.qualifiedName.toFirstLower + ".datatypes";
		}
	}
	
	public static class CorrespondingComponentPackageInfo extends CorrespondingToEntityClass<RepositoryComponent> {
		override getClassName(RepositoryComponent object) {
			return "package-info";
		}
		
		override getPackageName(RepositoryComponent object) {
			object.repository__RepositoryComponent.qualifiedName.toFirstLower + "." + object.entityName.toFirstLower;
		}
	}
	
	public static class CorrespondingDataTypeClass extends CorrespondingToEntityClass<CompositeDataType> {
		override getClassName(CompositeDataType object) {
			return object.entityName;
		}
		
		override getPackageName(CompositeDataType object) {
			object.repository__DataType.qualifiedName.toFirstLower + "." + "datatypes";
		}
	}
	
	public static class CorrespondingJavaClass extends CorrespondingToEntityClass<RepositoryComponent> {
		override getClassName(RepositoryComponent object) {
			return super.getClassName(object) + "Impl";
		}
		
		override getPackageName(RepositoryComponent object) {
			return object.repository__RepositoryComponent.qualifiedName.toFirstLower + "." + object.entityName.toFirstLower;
		}
	}
	
	public static def void initializeCompilationUnitAndJavaClassifier(CompilationUnit compilationUnit, ConcreteClassifier javaClassifier, String name) {
		compilationUnit.name = name;
		javaClassifier.name = name;
		javaClassifier.addModifier(ModifiersFactory.eINSTANCE.createPublic());
		compilationUnit.classifiers.add(javaClassifier);
	}
	
	def static NamespaceClassifierReference createNamespaceClassifierReference(ConcreteClassifier concreteClassifier) {
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = EcoreUtil.copy(concreteClassifier)
		namespaceClassifierReference.classifierReferences.add(classifierRef)

		// namespaceClassifierReference.namespaces.addAll(concreteClassifier.containingCompilationUnit.namespaces)
		return namespaceClassifierReference
	}
	
	def static createNamespaceClassifierReference(NamespaceClassifierReference namespaceClassifierReference, ConcreteClassifier concreteClassifier) {
		val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = EcoreUtil.copy(concreteClassifier)
		namespaceClassifierReference.classifierReferences.add(classifierRef)

		// namespaceClassifierReference.namespaces.addAll(concreteClassifier.containingCompilationUnit.namespaces)
	}
	
	def static createPrivateField(Field field, TypeReference reference, String name) {
		field.typeReference = EcoreUtil.copy(reference)
		field.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPrivate)
		var String fieldName = name
		if (fieldName.nullOrEmpty) {
			fieldName = "field_" + PCM2JaMoPPUtils.getNameFromJaMoPPType(reference)
		}
		field.name = fieldName
	}
	
	def static addConstructorToClass(Constructor constructor, Class jaMoPPClass) {
		constructor.name = jaMoPPClass.name
		constructor.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		jaMoPPClass.members.add(constructor)
	}

	def static addImportToCompilationUnitOfClassifier(ClassifierImport classifierImport, Classifier classifier,
		ConcreteClassifier classifierToImport) {
		if (null != classifierToImport.containingCompilationUnit) {
			if (null != classifierToImport.containingCompilationUnit.namespaces) {
				classifierImport.namespaces.addAll(classifierToImport.containingCompilationUnit.namespaces)
			}
			classifier.containingCompilationUnit.imports.add(classifierImport)
		}
		classifierImport.classifier = classifierToImport
	}	
	
	def static createNewForFieldInConstructor(NewConstructorCall newConstructorCall, Constructor constructor, Field field) {
		val classifier = field.containingConcreteClassifier
		if (!(classifier instanceof Class)) {
			return null
		}
		val jaMoPPClass = classifier as Class

		addNewStatementToConstructor(newConstructorCall, constructor, field, jaMoPPClass.fields,
				constructor.parameters)
	}
	
	def static addNewStatementToConstructor(NewConstructorCall newConstructorCall, Constructor constructor, Field field,
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
		newConstructorCall.typeReference = EcoreUtil.copy(field.typeReference)

		// get order of type references of the constructor
		PCM2JaMoPPUtils.updateArgumentsOfConstructorCall(field, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall)

		assigmentExpression.value = newConstructorCall
		expressionStatement.expression = assigmentExpression
		constructor.statements.add(expressionStatement)
	}
	
	static def createSetter(Field field, ClassMethod method) {
		method.name = "set" + field.name.toFirstUpper
		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		method.typeReference = TypesFactory.eINSTANCE.createVoid
		val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
		parameter.name = field.name
		parameter.typeReference = EcoreUtil.copy(field.typeReference);
		method.parameters.add(parameter)
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		//this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		assigmentExpression.child = selfReference

		//.fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = field
		selfReference.next = fieldReference
		selfReference.^self = LiteralsFactory.eINSTANCE.createThis();
		//=
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		//name		
		val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierReference.target = parameter

		assigmentExpression.value = identifierReference
		expressionStatement.expression = assigmentExpression
		method.statements.add(expressionStatement)
		return method
	}

	static def createGetter(Field field, ClassMethod method) {
		method.name = "get" + field.name.toFirstUpper
		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		method.typeReference = EcoreUtil.copy(field.typeReference);

		//this.fieldname
		val identifierRef = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierRef.target = field

		// return
		val ret = StatementsFactory.eINSTANCE.createReturn
		ret.returnValue = identifierRef
		method.statements.add(ret);
		return method
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
	
	
	private static var ClaimableMap<PrimitiveTypeEnum, Type> primitveTypeMappingMap;

	private def static initPrimitiveTypeMap() {
		primitveTypeMappingMap = new ClaimableHashMap<PrimitiveTypeEnum, Type>()
		val stringClassifier = ClassifiersFactory.eINSTANCE.createClass
		stringClassifier.setName("String")
		primitveTypeMappingMap.put(PrimitiveTypeEnum.BOOL, TypesFactory.eINSTANCE.createBoolean)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.BYTE, TypesFactory.eINSTANCE.createByte)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.CHAR, TypesFactory.eINSTANCE.createChar)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.DOUBLE, TypesFactory.eINSTANCE.createDouble)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.INT, TypesFactory.eINSTANCE.createInt)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.LONG, TypesFactory.eINSTANCE.createLong)
		primitveTypeMappingMap.put(PrimitiveTypeEnum.STRING, stringClassifier)
	}

	public synchronized def static Type claimJaMoPPTypeForPrimitiveDataType(PrimitiveDataType pdt) {
		if (null == primitveTypeMappingMap) {
			initPrimitiveTypeMap()
		}
		return EcoreUtil.copy(primitveTypeMappingMap.claimValueForKey(pdt.type))
	}
}