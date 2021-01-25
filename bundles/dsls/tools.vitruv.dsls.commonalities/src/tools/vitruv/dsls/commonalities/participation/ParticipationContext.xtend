package tools.vitruv.dsls.commonalities.participation

import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.Collections
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass

import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

/**
 * Represents a participation in the context of a specific containment
 * hierarchy.
 * <p>
 * If a commonality is referenced by other commonalities, its participations
 * need to be matched in differently rooted containment hierarchies: A
 * participation may specify an own containment hierarchy that roots inside a
 * <code>Resource</code>, and/or it may be referenced by external commonality
 * reference mappings, which each specify different root containers for the
 * participation's non-root objects. This class represents the participation
 * adapted to one of those various contexts.
 * <p>
 * When a participation is referenced via commonality reference mappings, a
 * single participation context is derived from the combination of all
 * reference mappings involving the same participation domain.
 * <p>
 * For a commonality participation in its own context the root is empty, since
 * the participation objects are implicitly contained inside their intermediate
 * model's root.
 */
@FinalFieldsConstructor
@ToString
class ParticipationContext {

	enum ParticipationClassRole {
		EXTERNAL_ROOT,
		ROOT,
		NON_ROOT
	}

	/**
	 * A ParticipationClass with its context specific role.
	 */
	@Data
	static class ContextClass {

		val ParticipationClass participationClass
		val ParticipationClassRole role

		def isRootClass() {
			return (role == ParticipationClassRole.ROOT
				|| role == ParticipationClassRole.EXTERNAL_ROOT)
		}

		/**
		 * Returns whether the ParticipationClass belongs to an external
		 * participation, i.e. to the referencing participation if the
		 * participation context is for a reference mapping.
		 * <p>
		 * Note that if a commonality references itself, its participations act
		 * both as referencing and as referenced participation. In that case,
		 * the participation context may contain multiple ContextClasses for
		 * the same ParticipationClass, once for the 'external' and once for
		 * the non-external role.
		 */
		def isExternal() {
			return (role == ParticipationClassRole.EXTERNAL_ROOT)
		}
	}

	@Data
	static class ContextContainment<T extends Containment> {
		val ContextClass container
		val ContextClass contained
		val T containment
	}

	@Accessors(PUBLIC_GETTER)
	val Participation participation
	@Accessors(PUBLIC_GETTER)
	val ParticipationRoot root // not null
	@Lazy val List<ContextClass> rootClasses = calculateRootClasses()
	@Lazy val List<ContextClass> nonRootClasses = calculateNonRootClasses()
	@Lazy val List<ContextContainment<?>> rootContainments = calculateRootContainments()
	@Lazy val List<ContextContainment<?>> boundaryContainments = calculateBoundaryContainments()
	@Lazy val List<ContextContainment<?>> nonRootContainments = calculateNonRootContainments()

	@Lazy val ContextClass attributeReferenceRoot = calculateAttributeReferenceRoot()
	@Lazy val List<ContextContainment<OperatorContainment>> attributeReferenceContainments = calculateAttributeReferenceContainments()

	private def calculateRootClasses() {
		return Collections.unmodifiableList(root.classes.map [
			val role = (!rootContext) ? ParticipationClassRole.EXTERNAL_ROOT : ParticipationClassRole.ROOT
			new ContextClass(it, role)
		].toList)
	}

	private def calculateNonRootClasses() {
		return Collections.unmodifiableList(participation.nonRootClasses.map [
			new ContextClass(it, ParticipationClassRole.NON_ROOT)
		].toList)
	}

	private def getRootClass(ParticipationClass participationClass) {
		return rootClasses.findFirst[it.participationClass == participationClass]
	}

	private def getNonRootClass(ParticipationClass participationClass) {
		return nonRootClasses.findFirst[it.participationClass == participationClass]
	}

	private def calculateRootContainments() {
		return Collections.unmodifiableList(root.rootContainments.map [
			new ContextContainment(container.rootClass, contained.rootClass, it) as ContextContainment<?>
		].toList)
	}

	private def calculateBoundaryContainments() {
		return Collections.unmodifiableList(root.boundaryContainments.map [
			new ContextContainment(container.rootClass, contained.nonRootClass, it) as ContextContainment<?>
		].toList)
	}

	private def calculateNonRootContainments() {
		return Collections.unmodifiableList(participation.nonRootContainments.map [
			new ContextContainment(container.nonRootClass, contained.nonRootClass, it) as ContextContainment<?>
		].toList)
	}

	private def calculateAttributeReferenceRoot() {
		if (root.attributeReferenceRoot === null) return null
		else {
			return new ContextClass(root.attributeReferenceRoot, ParticipationClassRole.EXTERNAL_ROOT)
		}
	}

	private def calculateAttributeReferenceContainments() {
		return Collections.unmodifiableList(root.attributeReferenceContainments.map [
			assertTrue(attributeReferenceRoot !== null)
			new ContextContainment(attributeReferenceRoot, contained.nonRootClass, it)
		].toList)
	}

	def isRootContext() {
		return (!isForReferenceMapping || isForAttributeReferenceMapping)
	}

	def isForSingletonRoot() {
		return (isRootContext && participation.hasSingletonClass)
	}

	def isForReferenceMapping() {
		return (!referenceMappings.empty)
	}

	def isForAttributeReferenceMapping() {
		return (isForReferenceMapping && attributeReferenceRoot !== null)
	}

	def getReferenceMappings() {
		return root.referenceMappings
	}

	def getDeclaringReference() {
		return referenceMappings.head?.declaringReference
	}

	def getReferencingCommonality() {
		return referenceMappings.head?.declaringReference.declaringCommonality
	}

	def getReferencedCommonality() {
		return referenceMappings.head?.referencedCommonality
	}

	/**
	 * Note: In the context of an external reference mapping, this may include
	 * participation classes that originate from an external participation. If
	 * the referenced commonality is the referencing commonality itself (eg. a
	 * Package commonality containing other packages as subpackages), the same
	 * participation class may act as both root and non-root class.
	 * <p>
	 * This does not include the attribute reference root class.
	 */
	def getClasses() {
		return rootClasses + nonRootClasses
	}

	/**
	 * Gets all classes that are managed by the corresponding Intermediate.
	 * <p>
	 * In the context of a reference mapping this does not include the external
	 * reference root classes (since those are managed by another
	 * Intermediate).
	 * <p>
	 * For root participation contexts it does include the root Resource
	 * container class. If the participation has a singleton root, the
	 * singleton root classes are not included.
	 */
	def getManagedClasses() {
		if (isForSingletonRoot) {
			return nonRootClasses
		} else {
			return classes.filter[!external]
		}
	}

	/**
	 * Gets the list of external reference root classes.
	 * <p>
	 * These act as containers for objects of the referenced participation.
	 * <p>
	 * For attribute reference contexts this returns a list containing the
	 * attribute reference root class. For regular reference mapping contexts
	 * this returns the root classes. Otherwise this return an empty list.
	 */
	def getReferenceRootClasses() {
		if (forAttributeReferenceMapping) {
			return #[attributeReferenceRoot]
		} else if (forReferenceMapping) {
			return rootClasses
		} else {
			return #[]
		}
	}

	/**
	 * Gets the Resource root class, or <code>null</code> if there is none.
	 */
	def getResourceClass() {
		return rootClasses.filter[participationClass.isForResource].head
	}

	/**
	 * Gets all containments: Root, boundary, non-root and attribute reference
	 * containments.
	 */
	def getContainments() {
		return rootContainments + boundaryContainments + nonRootContainments + attributeReferenceContainments
	}

	/**
	 * Gets all containments that this participation context is responsible
	 * for.
	 * <p>
	 * I.e. this omits the root containments if this context is for a singleton
	 * root.
	 */
	def getManagedContainments() {
		if (isForSingletonRoot) {
			// Omit the root containments, since those are handled by the singleton root:
			return boundaryContainments + nonRootContainments + attributeReferenceContainments
		} else {
			return containments
		}
	}
}
