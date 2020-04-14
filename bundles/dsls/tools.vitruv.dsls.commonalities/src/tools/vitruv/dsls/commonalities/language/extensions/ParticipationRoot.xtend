package tools.vitruv.dsls.commonalities.language.extensions

import java.util.LinkedHashSet
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationClass

/**
 * The variable <code>root</code> portion of a participation's containment
 * context.
 * <p>
 * In the context of participation matching the term 'root' has a different
 * meaning than the term 'root (container) class(es)':</br>
 * Usually a participation will specify <code>Resource</code> as its root
 * container class. This Resource participation class is then also the 'root'
 * of the participation. However, while participations only support a single
 * Resource root container class (TODO currently), their 'root' may actually
 * comprise multiple participation classes. For example, when marking a
 * participation class as <code>root</code> (TODO not actually supported yet)
 * or as <code>singleton</code> (via the keyword <code>single</code>), then
 * this participation class resembles the <code>head</code> of a chain of root
 * participation classes, in which each class in that chain is related with
 * another root class via a containment relationship.
 * <p>
 * For example, in a participation of the form <code>{'A', 'B', 'single C',
 * 'Resource'}</code> with containment relations <code>{'A in C', 'B in C',
 * 'C in Resource'}</code>, the <code>'Resource'</code> class is the root
 * container, <code>{'C', 'Resource'}</code> is the set of root classes and
 * <code>{'A', 'B'}</code> is the set of non-root classes.
 * <p>
 * When commonalities are referenced by other commonalities (in the form of
 * commonality references), the mappings for these references specify different
 * roots for the referenced participations. These roots substitute the
 * referenced participation's own root during the matching process.
 * <p>
 * It is also possible for a participation to not specify any root (i.e. by not
 * specifying any participation class of type <code>Resource</code>). In that
 * case the participation can only exist in contexts in which its commonality
 * is externally referenced by other commonalities.
 * <p>
 * Root objects of commonality participations are implicitly contained in the
 * root of their intermediate model. Their {@link ParticipationRoot} is
 * therefore empty.
 */
@EqualsHashCode
@ToString
class ParticipationRoot {
	/**
	 * The external commonality reference mapping that specifies this root, or
	 * <code>null</code> if this root is specified by the participation itself.
	 */
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var CommonalityReferenceMapping referenceMapping
	/**
	 * Note: In the context of an external reference mapping, these
	 * participation classes may originate from an external participation.
	 * <p>
	 * They are ordered according to their chain of containment relationships,
	 * with the head class as first element.
	 */
	@Accessors(PUBLIC_GETTER)
	val Set<ParticipationClass> classes = new LinkedHashSet
	/**
	 * Containment relationships within the root itself, as well as between the
	 * root's head class and non-root participation classes.
	 */
	@Accessors(PUBLIC_GETTER)
	val Set<Containment> containments = new LinkedHashSet

	/**
	 * Whether the root is empty.
	 * <p>
	 * For instance, this is the case if the participation does not specify a
	 * Resource class.
	 * <p>
	 * Commonality participations have an empty root as well, since their root
	 * container is not explicitly specified.
	 */
	def isEmpty() {
		return classes.empty
	}

	def isRootClass(ParticipationClass participationClass) {
		return classes.contains(participationClass)
	}

	/**
	 * Gets the root participation class that is located at the boundary
	 * between root and non-root participation classes.
	 * <p>
	 * All containment relationships between root and non-root classes use this
	 * class as their container.
	 */
	def getHead() {
		return classes.head
	}

	/**
	 * Gets all containment relations between the root's head class and
	 * non-root classes.
	 */
	def getBoundaryContainments() {
		val rootHead = head
		return containments.filter[container == rootHead]
	}
}
