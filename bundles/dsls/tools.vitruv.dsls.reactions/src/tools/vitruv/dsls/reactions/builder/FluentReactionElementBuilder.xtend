package tools.vitruv.dsls.reactions.builder

import java.util.Collections
import java.util.LinkedList
import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile

import static com.google.common.base.Preconditions.*

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
	
	protected new(FluentBuilderContext context) {
		this.context = context
	}
	
	def package void triggerBeforeAttached(ReactionsFile reactionsFile) {
		checkState(attachedReactionsFile === null, "This builder was already attached to a reactions file!")
		childBuilders.patientForEach [triggerBeforeAttached(reactionsFile)]
		attachedReactionsFile = reactionsFile
		attachmentPreparation()
		beforeAttached.patientForEach [run()]
		beforeAttached.discardAndClose()
	}

	def package void triggerAfterJvmTypeCreation() {
		checkState(attachedReactionsFile !== null, "This builder was not yet attached to a reactions file!")
		jvmTypesAvailable = true
		childBuilders.patientForEach [triggerAfterJvmTypeCreation()]
		childBuilders.discardAndClose()
		afterJvmTypeCreation.patientForEach [run()]
		afterJvmTypeCreation.discardAndClose()
	}
	
	def protected checkNotYetAttached() {
		checkState(attachedReactionsFile === null, '''This operation is only allowed before the «this» is attached to a resource!''')
	}
	
	def protected attachmentPreparation() {
		checkState(readyToBeAttached, '''The «this» is not sufficiently initialised to be attached to a resource!''')
	}
	
	def protected <T> T beforeAttached(T element, Consumer<? super T> initializer) {
		beforeAttached.add([initializer.accept(element)])
		element
	}
	
	def protected <T> T whenJvmTypes(T element, Consumer<? super T> initializer) {
		afterJvmTypeCreation.add([initializer.accept(element)])
		element
	}
	
	def protected metamodelImport(EPackage ePackage) {
		checkState(attachedReactionsFile !== null && !jvmTypesAvailable, "Metamodel imports can only be created in the attachment preparation phase!")
		// there will usually only be a few metamodel imports, so no need for caching
		attachedReactionsFile.metamodelImports.findFirst[package == ePackage] ?: createMetamodelImport(ePackage)
	}

	def private createMetamodelImport(EPackage ePackage) {
		val newImport = MirBaseFactory.eINSTANCE.createMetamodelImport => [
			package = ePackage
			name = ePackage.name
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
