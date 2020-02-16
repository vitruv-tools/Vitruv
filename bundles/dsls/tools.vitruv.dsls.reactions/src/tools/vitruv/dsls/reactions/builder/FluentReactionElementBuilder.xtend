package tools.vitruv.dsls.reactions.builder

import java.util.ArrayList
import java.util.Collections
import java.util.LinkedList
import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xtype.XtypeFactory
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEReferenceReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*

/**
 * Parent class of all fluent builders. The builders work in three phases:
 * 
 * <ol>
 * <li>Building phase. The user is using the offered methods to create the
 * desired elements. They must leave all builders in a sufficiently initialized
 * state (such that {@link #readyToBeAttached} is {@code true}.
 * <li>Attachment preparation phase. Just before the builder is to be attached
 * to a resource, this phase is triggered. The user may not modify builders
 * anymore and the builders do outstanding initializations (which can now rely
 * on the fact that things will not be changed by the user anymore).
 * <li>Jvm Types Linking phase. After the builders have been attached to a 
 * resource, generated JVM types will be available. The builders now use them
 * to create the missing elements. Building is finished afterwards.
 * </ol>
 */
abstract package class FluentReactionElementBuilder {

	val beforeAttached = new PatientList<Runnable>
	val afterJvmTypeCreation = new PatientList<Runnable>
	protected val FluentBuilderContext context

	/**
	 * Signals whether enough methods have been called on this builder and its
	 * subbuilders. If this is {@code false}, attaching the builder to a
	 * resource will certainly fail. It might, however, still fail if this is
	 * {@code true}.
	 */
	protected var readyToBeAttached = false

	@Accessors(PROTECTED_GETTER)
	protected var jvmTypesAvailable = false

	/**
	 * Builders mimic the tree structure of the elements they create. Events
	 * ({@link #triggerBeforeAttached} and {@link #triggerAfterJvmTypeCreation})
	 * are handled bottom-up, so children have a chance to modify their parent
	 * before they enter the phase.
	 */
	@Accessors(PROTECTED_GETTER)
	val childBuilders = new PatientList<FluentReactionElementBuilder>
	@Accessors(PROTECTED_GETTER)
	var ReactionsFile attachedReactionsFile
	@Accessors(PROTECTED_GETTER)
	var Resource targetResource

	protected new(FluentBuilderContext context) {
		this.context = context
	}

	def package void triggerBeforeAttached(ReactionsFile reactionsFile, Resource targetResource) {
		checkState(attachedReactionsFile === null, "This builder was already attached to a reactions file!")
		childBuilders.patientForEach[triggerBeforeAttached(reactionsFile, targetResource)]
		this.attachedReactionsFile = reactionsFile
		this.targetResource = targetResource
		attachmentPreparation()
		beforeAttached.patientForEach[run()]
		beforeAttached.discardAndClose()
	}

	def package void triggerAfterJvmTypeCreation() {
		checkState(attachedReactionsFile !== null, "This builder was not yet attached to a reactions file!")
		jvmTypesAvailable = true
		childBuilders.patientForEach[triggerAfterJvmTypeCreation()]
		childBuilders.discardAndClose()
		afterJvmTypeCreation.patientForEach[run()]
		afterJvmTypeCreation.discardAndClose()
	}

	def protected checkNotYetAttached() {
		checkState(attachedReactionsFile ===
			null, '''This operation is only allowed before the «this» is attached to a resource!''')
	}

	def protected attachmentPreparation() {
		checkState(readyToBeAttached, '''The «this» is not sufficiently initialised to be attached to a resource!''')
	}

	/**
	 * Executes the given {@code initializer} just before this builder is being
	 * attached to a resource. The initializer may rely on that the client will
	 * not change the builder anymore. 
	 */
	def protected <T> T beforeAttached(T element, Consumer<? super T> initializer) {
		beforeAttached.add([initializer.accept(element)])
		element
	}

	/**
	 * Executes the given {@code initializer} after this builder has been added
	 * to a resource and inferred JVM types are available.
	 */
	def protected <T> T whenJvmTypes(T element, Consumer<? super T> initializer) {
		afterJvmTypeCreation.add([initializer.accept(element)])
		element
	}

	def protected delegateTypeProvider() {
		context.typeProviderFactory.findOrCreateTypeProvider(attachedReactionsFile.eResource.resourceSet)
	}

	def protected referenceBuilderFactory() {
		context.referenceBuilderFactory.create(attachedReactionsFile.eResource.resourceSet)
	}

	def private <T extends JvmDeclaredType> boolean equalImportTypes(T importedType, T type) {
		return (importedType.qualifiedName == type.qualifiedName)
	}

	def protected <T extends JvmDeclaredType> imported(T type) {
		XImportSection.importDeclarations.findFirst[equalImportTypes(importedType, type)] ?: createTypeImport(type)
		return type
	}

	def protected <T extends JvmIdentifiableElement> possiblyImported(T type) {
		switch type {
			JvmDeclaredType: imported(type as JvmDeclaredType)
			JvmMember: imported(type.declaringType)
		}
		return type
	}

	def protected staticExtensionAllImported(JvmDeclaredType declaredType) {
		(XImportSection.importDeclarations.findFirst [
			isWildcard && equalImportTypes(importedType, declaredType)
		] ?: createTypeWildcardImport(declaredType)) => [
			extension = true
		]
		return declaredType
	}

	def protected staticExtensionImported(JvmOperation operation) {
		staticImport(operation, true)
	}

	def protected staticExtensionWildcardImported(JvmOperation operation) {
		operation.declaringType.staticExtensionAllImported
		return operation
	}

	def protected staticImported(JvmOperation operation) {
		staticImport(operation, false)
	}

	def private staticImport(JvmOperation operation, boolean asExtension) {
		val existingStarImport = XImportSection.importDeclarations.findFirst [
			isWildcard && equalImportTypes(importedType, operation.declaringType)
		]
		if (existingStarImport !== null) {
			existingStarImport.extension = existingStarImport.extension || asExtension
		} else {
			(XImportSection.importDeclarations.findFirst [
				equalImportTypes(importedType, operation.declaringType) && memberName == operation.simpleName && static == true
			] ?: createStaticOperationImport(operation)) => [
				extension = extension || asExtension
			]
		}
		return operation
	}

	def private createStaticOperationImport(JvmOperation operation) {
		XtypeFactory.eINSTANCE.createXImportDeclaration => [
			importedType = operation.declaringType
			memberName = operation.simpleName
			static = true
			XImportSection.importDeclarations += it
		]
	}

	def private createTypeWildcardImport(JvmDeclaredType type) {
		val newDeclaration = XtypeFactory.eINSTANCE.createXImportDeclaration => [
			importedType = type
			static = true
			wildcard = true
		]
		val oldImports = new ArrayList(XImportSection.importDeclarations)
		XImportSection.importDeclarations.clear()
		XImportSection.importDeclarations += oldImports.filter [
			!static || !equalImportTypes(importedType, type)
		]
		XImportSection.importDeclarations += newDeclaration
		return newDeclaration
	}

	def private createTypeImport(JvmDeclaredType type) {
		XtypeFactory.eINSTANCE.createXImportDeclaration => [
			importedType = type
			XImportSection.importDeclarations += it
		]
	}

	def private getXImportSection() {
		attachedReactionsFile.namespaceImports ?: XtypeFactory.eINSTANCE.createXImportSection => [
			attachedReactionsFile.namespaceImports = it
		]
	}

	def protected metamodelImport(EPackage ePackage) {
		checkState(attachedReactionsFile !== null && !jvmTypesAvailable,
			"Metamodel imports can only be created in the attachment preparation phase!")
		// there will usually only be a few metamodel imports, so no need for caching
		attachedReactionsFile.metamodelImports.findFirst[package == ePackage] ?: createMetamodelImport(ePackage)
	}

	def protected metamodelImport(EPackage ePackage, String pname) {
		checkState(attachedReactionsFile !== null && !jvmTypesAvailable,
			"Metamodel imports can only be created in the attachment preparation phase!")
		createMetamodelImport(ePackage, ePackage.name)
	}

	def private createMetamodelImport(EPackage ePackage) {
		createMetamodelImport(ePackage, ePackage.name)
	}

	def private createMetamodelImport(EPackage ePackage, String pname) {
		val newImport = MirBaseFactory.eINSTANCE.createMetamodelImport => [
			package = ePackage
			name = pname
		]
		attachedReactionsFile.metamodelImports += newImport
		return newImport
	}

	def protected <T extends MetaclassReference> reference(T metaclassReference, EClass eClass) {
		(metaclassReference => [
			metaclass = eClass
		]).beforeAttached [
			metamodel = eClass.EPackage.metamodelImport
		]
	}

	def protected <T extends MetaclassEReferenceReference> reference(T referenceReference, EClass eClass,
		EReference reference) {
		(referenceReference => [
			feature = reference
			metaclass = eClass
		]).beforeAttached [
			metamodel = eClass.EPackage.metamodelImport
		]
	}

	def protected <T extends MetaclassEAttributeReference> reference(T attributeReference, EClass eClass,
		EAttribute attribute) {
		(attributeReference => [
			feature = attribute
			metaclass = eClass
		]).beforeAttached [
			metamodel = eClass.EPackage.metamodelImport
		]
	}

	def protected <T extends NamedJavaElement> reference(T javaElementReference, Class<?> clazz) {
		javaElementReference.beforeAttached [
			type = context.typeReferences.getTypeForName(clazz, targetResource)
		]
	}

	def protected List<XExpression> requiredArgumentsFrom(FluentRoutineBuilder routineBuilder,
		JvmOperation routineCallMethod) {
		val parameterList = new ArrayList<XExpression>(3)
		if (routineBuilder.requireAffectedEObject) {
			parameterList += routineCallMethod.argument(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		}
		if (routineBuilder.requireNewValue) {
			parameterList += routineCallMethod.argument(CHANGE_NEW_VALUE_ATTRIBUTE)
		}
		if (routineBuilder.requireOldValue) {
			parameterList += routineCallMethod.argument(CHANGE_OLD_VALUE_ATTRIBUTE)
		}
		return parameterList
	}

	def protected argument(JvmOperation routineCallMethod, String parameterName) {
		val parameter = routineCallMethod.parameters.findFirst[name == parameterName]
		if (parameter === null) {
			throw new IllegalStateException('''The routine call method “«routineCallMethod»” does not provide a value called “«parameterName»”!''')
		}
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = parameter
		]
	}

	/**
	 * List offering iteration while the list is being modified.
	 */
	package static class PatientList<T> implements List<T> {
		@Delegate List<T> delegate = new LinkedList

		/**
		 * Calls the provided {@code consumer} on every element in this list.
		 * If elements are attempted to be added to this list during the
		 * iteration, these elements are stored and will be iterated over after
		 * the current iteration ends. This ends as soon as no new elements are
		 * added to the list.
		 */
		def void patientForEach(Consumer<T> consumer) {
			val allList = delegate
			while (delegate.size > 0) {
				val fromLastIteration = delegate
				delegate = new LinkedList
				fromLastIteration.forEach(consumer)
				allList += delegate
			}
			delegate = allList
		}

		/**
		 * Discards all elements stored in this list. After this method was
		 * called, attempting to modify (but not read from) this list will
		 * throw an exception.
		 */
		def void discardAndClose() {
			delegate = Collections.emptyList
		}
	}

}
