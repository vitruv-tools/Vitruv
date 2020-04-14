package tools.vitruv.dsls.commonalities.language.extensions

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

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
 * For a commonality participation in its own context the root is empty, since
 * the participation objects are implicitly contained inside their intermediate
 * model's root.
 */
@FinalFieldsConstructor
@EqualsHashCode
@ToString
class ParticipationContext {

	@Accessors(PUBLIC_GETTER)
	val Participation participation
	@Accessors(PUBLIC_GETTER)
	val ParticipationRoot root // not null

	def getAllContainments() {
		return root.containments + participation.nonRootContainments
	}

	def isForReferenceMapping() {
		return (referenceMapping !== null)
	}

	def getReferenceMapping() {
		return root.referenceMapping
	}

	def getReferencingCommonality() {
		return referenceMapping?.containingCommonality
	}

	/**
	 * Note: In the context of an external reference mapping, this may include
	 * participation classes that originate from an external participation.
	 */
	def getParticipationClasses() {
		return root.classes + participation.nonRootClasses
	}

	def getRootContainerClass() {
		return root.classes.last
	}

	def isRootContainerClass(ParticipationClass participationClass) {
		return (participationClass == rootContainerClass)
	}

	def isRootClass(ParticipationClass participationClass) {
		return root.isRootClass(participationClass)
	}
}
