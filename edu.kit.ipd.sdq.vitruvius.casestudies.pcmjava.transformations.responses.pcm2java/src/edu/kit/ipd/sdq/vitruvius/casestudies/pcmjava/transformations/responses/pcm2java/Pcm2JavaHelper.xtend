package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.responses.pcm2java

import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypesFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.imports.ClassifierImport
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.operators.OperatorsFactory
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
import org.palladiosimulator.pcm.repository.DataType
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.ReferenceableElement
import java.util.ArrayList
import org.emftext.language.java.containers.JavaRoot
import java.io.ByteArrayInputStream
import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser
import org.emftext.language.java.imports.ImportsFactory
import org.eclipse.emf.common.util.URI
import org.emftext.language.java.types.PrimitiveType

class Pcm2JavaHelper {
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
			fieldName = "field_" + getNameFromJaMoPPType(reference)
		}
		field.name = fieldName
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
	
	def static addConstructorToClass(Class javaClass) {
		val Constructor constructor = MembersFactory.eINSTANCE.createConstructor
		addConstructorToClass(constructor, javaClass)
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
	
	def static addConstructorToClass(Constructor constructor, Class javaClass) {
		constructor.name = javaClass.name
		constructor.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		javaClass.members.add(constructor)
	}
	
	def static Parameter createOrdinaryParameter(TypeReference typeReference, String name) {
		val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
		parameter.name = name
		parameter.typeReference = typeReference
		return parameter
	}
	
	def static createClassifierImportInClassifier(ConcreteClassifier classifier, ConcreteClassifier classifierToImport) {
		val classifierImport = ImportsFactory.eINSTANCE.createClassifierImport;
		addImportToCompilationUnitOfClassifier(classifierImport, classifier, classifierToImport);
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
		updateArgumentsOfConstructorCall(field, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall)

		assigmentExpression.value = newConstructorCall
		expressionStatement.expression = assigmentExpression
		constructor.statements.add(expressionStatement)
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
	
	public static def String getJavaProjectSrc() {
		return "src/";
	}
	
	public static def String getPackageInfoClassName() {
		"package-info"
	} 
	
	public static def String buildJavaFilePath(String fileName, Iterable<String> namespaces) {
		return '''src/«FOR namespace : namespaces SEPARATOR "/" AFTER "/"»«namespace»«ENDFOR»«fileName».java''';
	}
	
	public static def String buildJavaFilePath(CompilationUnit compilationUnit) {
		return '''src/«FOR namespace : compilationUnit.namespaces SEPARATOR "/" AFTER "/"»«namespace»«ENDFOR»«compilationUnit.name».java''';
	}
	
	public static def String buildJavaFilePath(org.emftext.language.java.containers.Package javaPackage) {
		return '''src/«FOR namespace : javaPackage.namespaces SEPARATOR "/" AFTER "/"»«namespace»«ENDFOR»«javaPackage.name»/«packageInfoClassName».java''';
	}
	
	public static def TypeReference createTypeReference(DataType originalDataType, Class correspondingJavaClassIfExisting) {
		if (null == originalDataType) {
			return TypesFactory.eINSTANCE.createVoid
		}
		var TypeReference innerDataTypeReference = null;
		if (originalDataType instanceof PrimitiveDataType) {
			val type = EcoreUtil.copy(claimJaMoPPTypeForPrimitiveDataType(originalDataType));
			if (type instanceof TypeReference) {
				innerDataTypeReference = type;
			} else if (type instanceof ConcreteClassifier) {
				innerDataTypeReference = createNamespaceClassifierReference(type);
			} else {
				// This cannot be since the claimForPrimitiveType function does only return TypeReference or ConcreteClassifier
			}
		} else if (correspondingJavaClassIfExisting != null) {
			innerDataTypeReference = createNamespaceClassifierReference(correspondingJavaClassIfExisting);	
		} else {
			throw new IllegalArgumentException("Either the dataType must be primitive or a correspondingJavaClass must be specified");
		}
		return innerDataTypeReference;
	}
	
	public static def void initializeClassMethod(ClassMethod classMethod, Method implementedMethod, boolean ensurePublic) {
		initializeClassMethod(classMethod, implementedMethod.name, implementedMethod.typeReference, implementedMethod.modifiers, implementedMethod.parameters, ensurePublic)
	}
	
	public static def void initializeClassMethod(ClassMethod classMethod, String name, TypeReference typeReference, Modifier[] modifiers,
		Parameter[] parameters, boolean ensurePublic) {
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
	}
	
	public static def ClassMethod findMethodInClass(ConcreteClassifier concreteClassifier, ClassMethod method) {
		for (Method currentMethod : concreteClassifier.methods) {
			if (currentMethod instanceof ClassMethod && currentMethod.name.equals(method.name) &&
				currentMethod.typeParameters.size == method.typeParameters.size) {
				// todo: finish check by comparing type reference and type of each parameter 
				return currentMethod as ClassMethod
			}
		}
		null
	}
	
	def static ClassifierImport getJavaClassImport(String name) {
		val content = "package dummyPackage;\n " +
				"import " + name + ";\n" +
				"public class DummyClass {}";
		val dummyCU = createCompilationUnit("DummyClass", content);
		val classifierImport = (dummyCU.getImports().get(0) as ClassifierImport)
		EcoreUtil.copy(classifierImport);
		return classifierImport;
		
	}
	
	def static ConcreteClassifier getJavaClass(String name) {
		val content = "package dummyPackage;\n " +
				"import " + name + ";\n" +
				"public class DummyClass {}";
		val dummyCU = createCompilationUnit("DummyClass", content);
		val classifier = (dummyCU.getImports().get(0) as ClassifierImport).getClassifier();
		EcoreUtil.copy(classifier);
		return classifier;
		
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
		val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(URI.createFileURI(name + ".java"),
			inStream)
		javaRoot.name = name + ".java"
		EcoreUtil.remove(javaRoot)
		return javaRoot
	}
	
	/**
	 * returns the class object for a primitive type, e.g, Integer for int
	 */
	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(PrimitiveType type) {
		return null
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Boolean type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Boolean")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Byte type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Byte")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Char type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Character")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Double type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Double")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Float type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Float")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Int type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Integer")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Long type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Long")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Short type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Short")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(
		org.emftext.language.java.types.Void type) {
		createAndReturnNamespaceClassifierReferenceForName("java.lang", "Void")
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
}
	