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

class PCMJavaHelper {
	private static def String getQualifiedName(Repository repository) {
		repository.entityName.toFirstLower.trim;
	}
	
	public static final val interfaceToInterface = new CorrespondingInterface();
	public static final val repositoryToPackageInfo = new CorrespondingRepositoryPackageInfo();
	public static final val repositoryToContractsPackageInfo = new CorrespondingContractsRepositoryPackageInfo();
	public static final val repositoryToDatatypesPackageInfo = new CorrespondingDatatypesRepositoryPackageInfo();
	public static final val componentToPackageInfo = new CorrespondingComponentPackageInfo();
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
}